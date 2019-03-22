package io.github.lix3nn53.guardiansofadelia.quests;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskDealDamage;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskInteract;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.advancements.Advancement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class Quest {

    private final int questID;

    private final String name;
    private final List<String> story;

    private final String startMsg;
    private final String objectiveText;
    private final String turnInMsg;
    private final int moneyPrize;
    private final int expPrize;
    private final int minLevel;
    private final int questReq;
    private final Material advancementMaterial;
    private final List<Action> onAcceptActions = new ArrayList<>();
    private final List<Action> onCompleteActions = new ArrayList<>();
    private final List<Action> onTurnInActions = new ArrayList<>();
    private List<Task> tasks;
    private List<ItemStack> itemPrizes;

    public Quest(
            final int questID, final String name, final List<String> story, final String startMsg, final String objectiveText, final String turnInMsg,
            final List<Task> tasks, final List<ItemStack> itemPrizes, final int moneyPrize, final int expPrize,
            final int minLevel, final int questReq, Material advancementMaterial) {
        this.questID = questID;
        this.name = name;
        this.story = story;
        this.startMsg = startMsg;
        this.objectiveText = objectiveText;
        this.turnInMsg = turnInMsg;
        this.tasks = tasks;
        this.itemPrizes = itemPrizes;
        this.moneyPrize = moneyPrize;
        this.expPrize = expPrize;
        this.minLevel = minLevel;
        this.questReq = questReq;
        this.advancementMaterial = advancementMaterial;
    }

    /**
     * Copy constructor.
     */
    public Quest(Quest questToCopy) {
        this(questToCopy.getQuestID(), questToCopy.getName(), questToCopy.getStory(), questToCopy.getStartMsg(), questToCopy.getObjectiveText(), questToCopy.getTurnInMsg(),
                questToCopy.getTasks(), questToCopy.getItemPrizes(), questToCopy.getMoneyPrize(), questToCopy.getExpPrize(),
                questToCopy.getMinLevel(), questToCopy.getQuestReq(), questToCopy.getAdvancementMaterial());
        List<Action> copyOnAcceptActions = questToCopy.getOnAcceptActions();
        this.onAcceptActions.addAll(copyOnAcceptActions);
        List<Action> copyCompleteActions = questToCopy.getOnCompleteActions();
        this.onCompleteActions.addAll(copyCompleteActions);
        List<Action> copyOnTurnInActions = questToCopy.getOnTurnInActions();
        this.onTurnInActions.addAll(copyOnTurnInActions);
        //no defensive copies are created here, since
        //there are no mutable object fields (String is immutable)
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<Task>();
            tasks.add(task);
        } else {
            tasks.add(task);
        }
    }

    public void addItemPrize(ItemStack item) {
        if (itemPrizes == null) {
            itemPrizes = new ArrayList<ItemStack>();
            itemPrizes.add(item);
        } else {
            itemPrizes.add(item);
        }
    }

    public boolean isCompleted() {
        for (Task t : this.tasks) {
            if (!t.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable(RPGCharacter rpgCharacter, int playerLevel) {
        List<Integer> turnedInQuests = rpgCharacter.getTurnedInQuests();

        if (questReq != 0 && minLevel != 0) {
            if (playerLevel >= minLevel) {
                return turnedInQuests.contains(questReq);
            }
        } else if (minLevel != 0) {
            return playerLevel >= minLevel;
        } else if (questReq != 0) {
            return turnedInQuests.contains(questReq);
        }
        return false;
    }

    public ItemStack getGuiItem(Player player) {
        GuardianDataManager guardianDataManager = GuardiansOfAdelia.getGuardianDataManager();
        UUID uuid = player.getUniqueId();
        ItemStack questItem = new ItemStack(Material.GRAY_WOOL, 1);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.RED + "You can't accept this quest");
        ItemMeta itemMeta = questItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + getName() + ChatColor.GRAY + " Q#" + getQuestID());
        if (guardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = guardianDataManager.getGuardianData(uuid);
            RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

            List<Quest> playerQuests = rpgCharacter.getQuestList();
            List<Integer> turnedInQuests = rpgCharacter.getTurnedInQuests();

            boolean questActive = playerQuests.stream().anyMatch(item -> item.getQuestID() == this.getQuestID());
            if (questActive) {
                questItem = new ItemStack(Material.PINK_WOOL, 1);
                lore.set(0, ChatColor.LIGHT_PURPLE + "You accepted this quest");
                Optional<Quest> playerQuestOptional = playerQuests.stream()
                        .filter(item -> item.getQuestID() == this.getQuestID())
                        .findAny();
                if (playerQuestOptional.isPresent()) {
                    Quest playerQuest = playerQuestOptional.get();
                    if (playerQuest.isCompleted()) {
                        questItem = new ItemStack(Material.MAGENTA_WOOL, 1);
                        lore.set(0, ChatColor.DARK_PURPLE + "Click to complete this quest");
                    }
                }
            } else if (turnedInQuests.contains(this.getQuestID())) {
                questItem = new ItemStack(Material.WHITE_WOOL, 1);
                lore.set(0, ChatColor.WHITE + "You completed this quest");
            } else if (isAvailable(rpgCharacter, player.getLevel())) {
                questItem = new ItemStack(Material.LIME_WOOL, 1);
                lore.set(0, ChatColor.GREEN + "Click to accept this quest");
            }

            lore.add("");
            lore.add(ChatColor.LIGHT_PURPLE + "Objectives");
            for (Task t : tasks) {
                lore.add(t.getObjectiveString());
            }
            lore.add("");
            lore.add(ChatColor.GOLD + "Prizes");
            lore.add(ChatColor.YELLOW + "Experience: " + expPrize);
            lore.add(ChatColor.YELLOW + "Money: " + Coin.getStringValue(moneyPrize));
            for (ItemStack it : itemPrizes) {
                lore.add(it.getItemMeta().getDisplayName());
            }
            lore.add("");

            if (minLevel != 0) {
                if (player.getLevel() >= minLevel) {
                    lore.add(ChatColor.GREEN + "Required Level: " + minLevel);
                } else {
                    lore.add(ChatColor.RED + "Required Level: " + minLevel);
                }
            }

            if (questReq != 0) {
                if (turnedInQuests.contains(questReq)) {
                    lore.add(ChatColor.GREEN + "Required Quest: " + questReq);
                } else {
                    lore.add(ChatColor.RED + "Required Quest: " + questReq);
                }
            }

            lore.add("");
            for (String st : story) {
                lore.add(ChatColor.ITALIC.toString() + ChatColor.GRAY + st);
            }
            itemMeta.setLore(lore);
        }
        questItem.setItemMeta(itemMeta);
        return questItem;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void onTurnIn(Player player) {
        player.sendMessage(ChatColor.DARK_PURPLE + "You have turned in " + ChatColor.LIGHT_PURPLE + this.name);
        Advancement onTurnInAdvancement = QuestAdvancements.getOnTurnInAdvancement(this.questID, this.getName(), this.advancementMaterial);
        onTurnInAdvancement.displayToast(player);
        if (!itemPrizes.isEmpty()) {
            for (ItemStack itemStack : this.itemPrizes) {
                InventoryUtils.giveItemToPlayer(player, itemStack);
            }
        }
        if (expPrize != 0) {
            SkillAPIUtils.giveQuestExp(player, this.expPrize);
        }
        if (moneyPrize != 0) {
            List<Coin> coins = EconomyUtils.priceToCoins(this.moneyPrize);
            for (Coin coin : coins) {
                InventoryUtils.giveItemToPlayer(player, coin.getCoin());
            }
        }

        if (!this.turnInMsg.equals("")) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + this.turnInMsg);
        }

        TablistUtils.updateTablist(player);

        QuestNPCManager questNpcManager = GuardiansOfAdelia.getQuestNpcManager();
        questNpcManager.setAllNpcHologramForPlayer(player);

        for (Action action : onTurnInActions) {
            action.perform(player);
        }
    }

    public void onComplete(Player player) {
        player.sendMessage(ChatColor.DARK_PURPLE + "You have completed " + ChatColor.LIGHT_PURPLE + this.name);
        Advancement onCompleteAdvancement = QuestAdvancements.getOnCompleteAdvancement(this.questID, this.getName(), this.advancementMaterial);
        onCompleteAdvancement.displayToast(player);

        QuestNPCManager questNpcManager = GuardiansOfAdelia.getQuestNpcManager();
        int whoCanCompleteThisQuest = questNpcManager.getWhoCanCompleteThisQuest(this.questID);
        questNpcManager.setNpcHologramForPlayer(player, whoCanCompleteThisQuest);

        for (Action action : onCompleteActions) {
            action.perform(player);
        }
    }

    public void onAccept(Player player) {
        player.sendMessage(ChatColor.DARK_PURPLE + "You have accepted " + ChatColor.LIGHT_PURPLE + this.name);
        Advancement onAcceptAdvancement = QuestAdvancements.getOnAcceptAdvancement(this.questID, this.getName(), this.advancementMaterial);
        onAcceptAdvancement.displayToast(player);
        if (!this.startMsg.equals("")) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + this.startMsg);
        }
        TablistUtils.updateTablist(player);

        QuestNPCManager questNpcManager = GuardiansOfAdelia.getQuestNpcManager();
        int whoCanGiveThisQuest = questNpcManager.getWhoCanGiveThisQuest(this.questID);
        questNpcManager.setNpcHologramForPlayer(player, whoCanGiveThisQuest);
        int whoCanCompleteThisQuest = questNpcManager.getWhoCanCompleteThisQuest(this.questID);
        questNpcManager.setNpcHologramForPlayer(player, whoCanCompleteThisQuest);

        for (Action action : onAcceptActions) {
            action.perform(player);
        }
    }

    public String getObjectiveTextForTablist() {
        String replaceTaskValues = objectiveText;
        int i = 1;
        for (Task task : this.getTasks()) {
            int progress = task.getProgress();
            replaceTaskValues = replaceTaskValues.replace("TASK_PROGRESS_" + i, progress + "");
        }
        return replaceTaskValues;
    }

    public String getObjectiveText() {
        return objectiveText;
    }

    public int getQuestID() {
        return questID;
    }

    public List<String> getStory() {
        return story;
    }

    public String getStartMsg() {
        return startMsg;
    }

    public String getTurnInMsg() {
        return turnInMsg;
    }

    public int getMoneyPrize() {
        return moneyPrize;
    }

    public int getExpPrize() {
        return expPrize;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getQuestReq() {
        return questReq;
    }

    public List<ItemStack> getItemPrizes() {
        return itemPrizes;
    }

    public Material getAdvancementMaterial() {
        return advancementMaterial;
    }

    public void progressKillTasks(Player questOwner, LivingEntity livingTarget) {
        for (Task task : this.tasks) {
            if (task instanceof TaskKill) {
                TaskKill taskKill = (TaskKill) task;
                boolean didProgress = taskKill.progress(livingTarget, questOwner);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                }
            }
        }
    }

    public void progressDealDamageTasks(Player questOwner, LivingEntity livingTarget, int damage) {
        for (Task task : this.tasks) {
            if (task instanceof TaskDealDamage) {
                TaskDealDamage taskDealDamage = (TaskDealDamage) task;
                boolean didProgress = taskDealDamage.progress(livingTarget, damage, questOwner);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                }
            }
        }
    }

    public void progressInterractTasks(Player questOwner, int npcId) {
        for (Task task : this.tasks) {
            if (task instanceof TaskInteract) {
                TaskInteract taskInteract = (TaskInteract) task;
                boolean didProgress = taskInteract.progress(npcId, questOwner);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                }
            }
        }
    }

    public void addOnAcceptAction(Action action) {
        this.onAcceptActions.add(action);
    }

    public void addOnCompleteAction(Action action) {
        this.onCompleteActions.add(action);
    }

    public void addOnTurnInAction(Action action) {
        this.onTurnInActions.add(action);
    }

    public List<Action> getOnAcceptActions() {
        return onAcceptActions;
    }

    public List<Action> getOnCompleteActions() {
        return onCompleteActions;
    }

    public List<Action> getOnTurnInActions() {
        return onTurnInActions;
    }
}
