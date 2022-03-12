package io.github.lix3nn53.guardiansofadelia.quests;

import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.config.ArmorReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.config.WeaponReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.quests.task.*;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Quest {

    private final int questID;

    private final String name;
    private final int storyLineCount;

    private final String startMsg;
    private final String objectiveText;
    private final String turnInMsg;
    private final int moneyPrize;
    private final int expPrize;
    private final int requiredLevel;
    private final List<Integer> requiredQuests;
    private final Material advancementMaterial;
    private final List<Action> onAcceptActions;
    private final List<Action> onCompleteActions;
    private final List<Action> onTurnInActions;
    private final List<Task> tasks;

    private final List<ItemStack> itemPrizes;
    private final List<ItemStack> itemPrizesSelectOneOf;
    private final WeaponReferenceData weaponPrizesSelectOneOf;
    private final ArmorReferenceData armorPrizesSelectOneOf;
    private final List<WeaponReferenceData> weaponPrizeForPlayer;
    private final List<ArmorReferenceData> armorPrizeForPlayer;

    public Quest(
            final int questID, final String name, final int storyLineCount, final String startMsg, final String objectiveText, final String turnInMsg,
            final List<Task> tasks, final List<ItemStack> itemPrizes, final int moneyPrize, final int expPrize,
            final int requiredLevel, final List<Integer> requiredQuests, Material advancementMaterial, List<Action> onAcceptActions,
            List<Action> onCompleteActions, List<Action> onTurnInActions, List<ItemStack> itemPrizesSelectOneOf,
            WeaponReferenceData weaponPrizesSelectOneOf, ArmorReferenceData armorPrizesSelectOneOf,
            List<WeaponReferenceData> weaponPrizeForPlayer, List<ArmorReferenceData> armorPrizeForPlayer) {
        this.questID = questID;
        this.name = name;
        this.storyLineCount = storyLineCount;
        this.startMsg = startMsg;
        this.objectiveText = objectiveText;
        this.turnInMsg = turnInMsg;
        this.itemPrizes = itemPrizes;
        this.moneyPrize = moneyPrize;
        this.expPrize = expPrize;
        this.requiredLevel = requiredLevel;
        this.requiredQuests = requiredQuests;
        this.advancementMaterial = advancementMaterial;
        this.onAcceptActions = onAcceptActions;
        this.onCompleteActions = onCompleteActions;
        this.onTurnInActions = onTurnInActions;
        this.itemPrizesSelectOneOf = itemPrizesSelectOneOf;
        this.weaponPrizesSelectOneOf = weaponPrizesSelectOneOf;
        this.armorPrizesSelectOneOf = armorPrizesSelectOneOf;
        this.weaponPrizeForPlayer = weaponPrizeForPlayer;
        this.armorPrizeForPlayer = armorPrizeForPlayer;

        List<Task> copyTasks = new ArrayList<>();
        for (Task task : tasks) {
            copyTasks.add(task.freshCopy());
        }
        this.tasks = copyTasks;
    }

    /**
     * Copy constructor.
     */
    public Quest(@NotNull Quest questToCopy) {
        this(questToCopy.getQuestID(), questToCopy.getName(), questToCopy.getStoryLineCount(), questToCopy.getStartMsg(), questToCopy.getObjectiveText(), questToCopy.getTurnInMsg(),
                questToCopy.getTasks(), questToCopy.getItemPrizes(), questToCopy.getMoneyPrize(), questToCopy.getExpPrize(),
                questToCopy.getRequiredLevel(), questToCopy.getRequiredQuests(), questToCopy.getAdvancementMaterial(), questToCopy.getOnAcceptActions(),
                questToCopy.getOnCompleteActions(), questToCopy.getOnTurnInActions(), questToCopy.getItemPrizesSelectOneOf(),
                questToCopy.getWeaponPrizesSelectOneOf(), questToCopy.getArmorPrizesSelectOneOf(),
                questToCopy.getWeaponPrizeForPlayer(), questToCopy.getArmorPrizeForPlayer());
    }

    public boolean isAvailable(RPGCharacter rpgCharacter, int playerLevel) {
        List<Integer> turnedInQuests = rpgCharacter.getTurnedInQuests();

        if (requiredLevel != 0) {
            if (playerLevel < requiredLevel) {
                return false;
            }
        }
        if (!requiredQuests.isEmpty()) {
            return turnedInQuests.containsAll(requiredQuests);
        }

        return true;
    }

    public ItemStack getGuiItem(Player player) {
        ItemStack questItem = new ItemStack(Material.GRAY_WOOL, 1);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatPalette.RED + "You can't accept this quest");
        ItemMeta itemMeta = questItem.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE + this.name + ChatPalette.GRAY + " Q#" + this.questID);
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
            String lang = guardianData.getLanguage();

            List<Quest> playerQuests = rpgCharacter.getQuestList();
            List<Integer> turnedInQuests = rpgCharacter.getTurnedInQuests();

            boolean questActive = playerQuests.stream().anyMatch(item -> item.getQuestID() == this.questID);
            if (questActive) {
                questItem = new ItemStack(Material.BLUE_WOOL, 1);
                lore.set(0, ChatPalette.YELLOW + "You accepted this quest");
                Optional<Quest> playerQuestOptional = playerQuests.stream()
                        .filter(item -> item.getQuestID() == this.questID)
                        .findAny();
                if (playerQuestOptional.isPresent()) {
                    Quest playerQuest = playerQuestOptional.get();
                    if (playerQuest.isCompleted()) {
                        questItem = new ItemStack(Material.MAGENTA_WOOL, 1);
                        lore.set(0, ChatPalette.PURPLE_LIGHT + "Click to complete this quest");
                    }
                }
            } else if (turnedInQuests.contains(this.questID)) {
                questItem = new ItemStack(Material.WHITE_WOOL, 1);
                lore.set(0, ChatPalette.WHITE + "You completed this quest");
            } else if (isAvailable(rpgCharacter, player.getLevel())) {
                questItem = new ItemStack(Material.LIME_WOOL, 1);
                lore.set(0, ChatPalette.GREEN_DARK + "Click to accept this quest");
            }

            if (!tasks.isEmpty()) {
                lore.add("");
                lore.add(ChatPalette.PURPLE_LIGHT + "Objectives");
                for (Task t : tasks) {
                    lore.add(t.getItemLoreString(guardianData));
                }
            }

            lore.add("");
            lore.add(ChatPalette.GOLD + "Prizes");
            if (expPrize > 0) {
                lore.add(ChatPalette.YELLOW + "Experience: " + expPrize);
            }
            if (moneyPrize > 0) {
                lore.add(ChatPalette.YELLOW + "Money: " + EconomyUtils.priceToString(moneyPrize));
            }
            if (!itemPrizes.isEmpty()) {
                lore.add(ChatPalette.YELLOW + "Item(s): ");
                for (ItemStack it : itemPrizes) {
                    lore.add(it.getItemMeta().getDisplayName());
                }
            }
            if (!itemPrizesSelectOneOf.isEmpty()) {
                lore.add(ChatPalette.YELLOW + "Select One Prize Item: ");
                for (ItemStack it : itemPrizesSelectOneOf) {
                    lore.add(it.getItemMeta().getDisplayName());
                }
            }
            if (weaponPrizesSelectOneOf != null) {
                GearLevel gearLevel = weaponPrizesSelectOneOf.getGearLevel();
                ItemTier itemTier = weaponPrizesSelectOneOf.getItemTier();
                lore.add(ChatPalette.YELLOW + "Select One Weapon: ");
                int minLevel = gearLevel.getMinLevel();
                int maxLevel = gearLevel.getMaxLevel();
                lore.add(ChatPalette.GRAY + "Level: " + minLevel + "~" + maxLevel + ChatPalette.GRAY + ", Tier: " + itemTier.getTierString());
            }
            if (armorPrizesSelectOneOf != null) {
                ArmorSlot armorSlot = armorPrizesSelectOneOf.getArmorSlot();
                GearLevel gearLevel = armorPrizesSelectOneOf.getGearLevel();
                ItemTier itemTier = armorPrizesSelectOneOf.getItemTier();
                lore.add(ChatPalette.YELLOW + "Select One Armor: ");
                int minLevel = gearLevel.getMinLevel();
                int maxLevel = gearLevel.getMaxLevel();
                lore.add(ChatPalette.GRAY + armorSlot.toString() + " Level: " + minLevel + "~" + maxLevel + ChatPalette.GRAY + ", Tier: " + itemTier.getTierString());
            }
            if (!weaponPrizeForPlayer.isEmpty()) {
                lore.add(ChatPalette.YELLOW + "Class Main Weapon: ");

                for (WeaponReferenceData weaponReferenceData : weaponPrizeForPlayer) {
                    GearLevel gearLevel = weaponReferenceData.getGearLevel();
                    ItemTier itemTier = weaponReferenceData.getItemTier();
                    int minLevel = gearLevel.getMinLevel();
                    int maxLevel = gearLevel.getMaxLevel();
                    lore.add(ChatPalette.GRAY + "Level: " + minLevel + "~" + maxLevel + ChatPalette.GRAY + ", Tier: " + itemTier.getTierString());
                }
            }
            if (!armorPrizeForPlayer.isEmpty()) {
                lore.add(ChatPalette.YELLOW + "Class Main Armor: ");

                for (ArmorReferenceData armorReferenceData : armorPrizeForPlayer) {
                    ArmorSlot armorSlot = armorReferenceData.getArmorSlot();
                    GearLevel gearLevel = armorReferenceData.getGearLevel();
                    ItemTier itemTier = armorReferenceData.getItemTier();
                    int minLevel = gearLevel.getMinLevel();
                    int maxLevel = gearLevel.getMaxLevel();
                    lore.add(ChatPalette.GRAY + armorSlot.toString() + " Level: " + minLevel + "~" + maxLevel + ChatPalette.GRAY + ", Tier: " + itemTier.getTierString());
                }
            }

            if (requiredLevel != 0) {
                lore.add("");
                if (player.getLevel() >= requiredLevel) {
                    lore.add(ChatPalette.GREEN_DARK + "Required Level: " + requiredLevel);
                } else {
                    lore.add(ChatPalette.RED + "Required Level: " + requiredLevel);
                }
            }
            if (!requiredQuests.isEmpty()) {
                lore.add("");
                if (turnedInQuests.containsAll(requiredQuests)) {
                    lore.add(ChatPalette.GREEN_DARK + "Required Quests: " + requiredQuests);
                } else {
                    lore.add(ChatPalette.RED + "Required Quests: " + requiredQuests);
                }
            }

            lore.add("");

            Material type = questItem.getType();
            /*if (type.equals(Material.GRAY_WOOL)) {
                //sporiler protection
                lore.add(ChatColor.ITALIC.toString() + ChatPalette.RED + "SPOILER PROTECTION");
                lore.add(ChatColor.ITALIC.toString() + ChatPalette.RED + "You can't see the lore of this quest before");
                lore.add(ChatColor.ITALIC.toString() + ChatPalette.RED + "you meet the requirements.");
            } else {
                for (String st : story) {
                    String storyLine = Translation.t(lang, "quest", st);
                    lore.add(ChatColor.ITALIC.toString() + ChatPalette.YELLOW + storyLine);
                }
            }*/
            for (int i = 1; i <= storyLineCount; i++) {
                String code = questID + ".story.l" + i;
                String storyLine = Translation.t(lang, "quest", code);
                lore.add(ChatColor.ITALIC.toString() + ChatPalette.YELLOW + storyLine);
            }

            itemMeta.setLore(lore);
        }
        questItem.setItemMeta(itemMeta);
        return questItem;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
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

    public int getQuestID() {
        return questID;
    }

    public int getStoryLineCount() {
        return storyLineCount;
    }

    public String getStartMsg() {
        return startMsg;
    }

    public String getObjectiveText() {
        return objectiveText;
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

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public List<Integer> getRequiredQuests() {
        return requiredQuests;
    }

    public Material getAdvancementMaterial() {
        return advancementMaterial;
    }

    public List<ItemStack> getItemPrizes() {
        return itemPrizes;
    }

    public List<ItemStack> getItemPrizesSelectOneOf() {
        return itemPrizesSelectOneOf;
    }

    public WeaponReferenceData getWeaponPrizesSelectOneOf() {
        return weaponPrizesSelectOneOf;
    }

    public ArmorReferenceData getArmorPrizesSelectOneOf() {
        return armorPrizesSelectOneOf;
    }

    public List<WeaponReferenceData> getWeaponPrizeForPlayer() {
        return weaponPrizeForPlayer;
    }

    public List<ArmorReferenceData> getArmorPrizeForPlayer() {
        return armorPrizeForPlayer;
    }

    public boolean isCompleted() {
        for (Task t : this.tasks) {
            if (!t.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public void onTurnIn(Player player) {
        MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE + "Quest Turn In");
        MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE_LIGHT + getName());

        String turnInMsg = getTurnInMsg();
        if (!turnInMsg.equals("")) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            String lang = guardianData.getLanguage();
            String message = Translation.t(lang, "quest", turnInMsg);
            MessageUtils.sendCenteredMessage(player, message);
        }

        ToastNotification toastNotification = QuestAdvancements.getOnTurnInAdvancement(getQuestID(), this.getName(), getAdvancementMaterial());
        toastNotification.send(player);

        for (Task task : this.tasks) {
            if (task instanceof TaskCollect) {
                TaskCollect taskCollect = (TaskCollect) task;
                if (taskCollect.isRemoveOnTurnIn()) {
                    String displayName = taskCollect.getItemStack().getItemMeta().getDisplayName();
                    InventoryUtils.removeAllFromInventoryByName(player.getInventory(), displayName);
                }
            }
        }

        if (!getItemPrizes().isEmpty()) {
            for (ItemStack itemStack : getItemPrizes()) {
                InventoryUtils.giveItemToPlayer(player, itemStack);
            }
        }
        if (this.weaponPrizeForPlayer != null) {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);

                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                    String rpgClassStr = rpgCharacter.getRpgClassStr();

                    for (WeaponReferenceData data : weaponPrizeForPlayer) {
                        ItemStack item = data.getItem(rpgClassStr);
                        InventoryUtils.giveItemToPlayer(player, item);
                    }
                }
            }
        }
        if (this.armorPrizeForPlayer != null) {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);

                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                    String rpgClassStr = rpgCharacter.getRpgClassStr();

                    for (ArmorReferenceData data : armorPrizeForPlayer) {
                        ItemStack item = data.getItem(rpgClassStr);
                        InventoryUtils.giveItemToPlayer(player, item);
                    }
                }
            }
        }
        if (getExpPrize() > 0) {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    activeCharacter.getRpgCharacterStats().giveExp(getExpPrize());
                }
            }
        }
        if (getMoneyPrize() > 0) {
            int[] coins = EconomyUtils.priceToCoins(getMoneyPrize());

            if (coins[0] > 0) {
                InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.COPPER, coins[0]).getCoin());
            }
            if (coins[1] > 0) {
                InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.SILVER, coins[1]).getCoin());
            }
            if (coins[2] > 0) {
                InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.GOLD, coins[2]).getCoin());
            }
        }

        TablistUtils.updateTablist(player);

        QuestNPCManager.setAllNpcHologramForPlayer(player);

        for (Action action : getOnTurnInActions()) {
            action.perform(player, questID, -1);
        }
    }

    public void onComplete(Player player) {
        MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE + "Quest Complete");
        MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE_LIGHT + getName());
        ToastNotification toastNotification = QuestAdvancements.getOnCompleteAdvancement(getQuestID(), this.getName(), getAdvancementMaterial());
        toastNotification.send(player);

        int whoCanCompleteThisQuest = QuestNPCManager.getWhoCanCompleteThisQuest(getQuestID());
        QuestNPCManager.setNpcHologramForPlayer(player, whoCanCompleteThisQuest);

        for (Action action : getOnCompleteActions()) {
            action.perform(player, questID, -1);
        }
    }

    public void onAccept(Player player) {
        MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE + "Quest Accept");
        MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE_LIGHT + getName());
        String startMsg = getStartMsg();
        if (!startMsg.equals("")) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            String lang = guardianData.getLanguage();
            String message = Translation.t(lang, "quest", startMsg);
            MessageUtils.sendCenteredMessage(player, message);
        }

        ToastNotification toastNotification = QuestAdvancements.getOnAcceptAdvancement(getQuestID(), this.getName(), getAdvancementMaterial());
        toastNotification.send(player);
        TablistUtils.updateTablist(player);

        int whoCanGiveThisQuest = QuestNPCManager.getWhoCanGiveThisQuest(getQuestID());
        QuestNPCManager.setNpcHologramForPlayer(player, whoCanGiveThisQuest);
        int whoCanCompleteThisQuest = QuestNPCManager.getWhoCanCompleteThisQuest(getQuestID());
        QuestNPCManager.setNpcHologramForPlayer(player, whoCanCompleteThisQuest);

        for (Action action : getOnAcceptActions()) {
            action.perform(player, questID, -1);
        }
    }

    public String getObjectiveTextForTablist(String lang) {
        String key = getObjectiveText();

        if (key.equals("")) return "EMPTY OBJECTIVE";

        String replaceTaskValues = Translation.t(lang, "quest", key);

        int i = 1;
        for (Task task : this.getTasks()) {
            String tablistInfoString = task.getTablistInfoString(lang);
            replaceTaskValues = replaceTaskValues.replace("TASK_PROGRESS_" + i, tablistInfoString + ChatPalette.WHITE);
            i++;
        }
        return replaceTaskValues;
    }

    public boolean progressKillTasks(Player questOwner, String internalName) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskKill) {
                TaskKill taskKill = (TaskKill) task;
                boolean didProgress = taskKill.progress(questOwner, internalName, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressDealDamageTasks(Player questOwner, String internalName, int damage) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskDealDamage) {
                TaskDealDamage taskDealDamage = (TaskDealDamage) task;
                boolean didProgress = taskDealDamage.progress(internalName, damage, questOwner, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressCollectTasks(Player questOwner, String itemName, int amount) {
        for (Task task : this.tasks) {
            if (task.isCompleted()) continue;
            if (task instanceof TaskCollect) {
                TaskCollect taskCollect = (TaskCollect) task;
                if (taskCollect.getItemStack().getItemMeta().getDisplayName().equals(itemName)) {
                    int alreadyInInventory = InventoryUtils.getHowManyInventoryHasFromName(questOwner.getInventory(), itemName);
                    taskCollect.setProgress(alreadyInInventory + amount);

                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean progressGatheringTasks(Player questOwner, Ingredient ingredient, int amount) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskGathering) {
                TaskGathering taskCollect = (TaskGathering) task;
                if (taskCollect.getIngredient().equals(ingredient)) {
                    taskCollect.progressBy(questOwner, amount, questID, taskIndex, false);

                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressGiftTasks(Player questOwner, String itemName, int amountInHand, String clickedEntityName) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskGift) {
                TaskGift taskGift = (TaskGift) task;
                if (!taskGift.getEntityName().equals(clickedEntityName)) continue;
                if (taskGift.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                    int requiredProgress = taskGift.getRequiredProgress();
                    int progress = taskGift.getProgress();

                    int leftToComplete = requiredProgress - progress;

                    int amountToRemoveFromHand = leftToComplete; //if leftToComplete <= amountInHand

                    if (leftToComplete > amountInHand) {
                        amountToRemoveFromHand = amountInHand;
                    }

                    taskGift.progressBy(questOwner, amountToRemoveFromHand, questID, taskIndex, false);

                    //remove from hand
                    ItemStack itemInMainHand = questOwner.getInventory().getItemInMainHand();
                    itemInMainHand.setAmount(amountInHand - amountToRemoveFromHand);

                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressDungeonTasks(Player questOwner, String dungeonTheme, int darkness) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskDungeon) {
                TaskDungeon taskDungeon = (TaskDungeon) task;
                boolean didProgress = taskDungeon.progress(questOwner, dungeonTheme, darkness, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressChangeClassTasks(Player questOwner, String newClass) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskChangeClass) {
                TaskChangeClass taskChangeClass = (TaskChangeClass) task;
                boolean didProgress = taskChangeClass.progress(questOwner, newClass, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public ItemStack triggerQuestItemDrop(String internalName) {
        for (Task task : this.tasks) {
            if (task.isCompleted()) continue;
            if (task instanceof TaskCollect) {
                TaskCollect taskCollect = (TaskCollect) task;
                List<String> keyOfMobsItemDropsFrom = taskCollect.getKeyOfMobsItemDropsFrom();
                if (keyOfMobsItemDropsFrom.contains(internalName)) {
                    float chance = taskCollect.getChance();
                    if (chance > 0) {
                        float random = (float) Math.random();
                        if (random < chance) {
                            return taskCollect.getItemStack();
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean progressInteractTasks(Player questOwner, int npcId) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskInteract) {
                TaskInteract taskInteract = (TaskInteract) task;
                boolean didProgress = taskInteract.progress(npcId, questOwner, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressReachTasks(Player questOwner, Location targetBlockLoc) {
        int taskIndex = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskReach) {
                TaskReach taskReach = (TaskReach) task;
                boolean didProgress = taskReach.progress(questOwner, targetBlockLoc, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressCraftingTasks(Player questOwner, CraftingType craftingType, ItemStack crafted) {
        int taskIndex = 0;
        ItemMeta itemMeta = crafted.getItemMeta();
        String displayName = itemMeta.getDisplayName();
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                taskIndex++;
                continue;
            }
            if (task instanceof TaskCrafting) {
                TaskCrafting taskCrafting = (TaskCrafting) task;
                boolean didProgress = taskCrafting.progress(craftingType, displayName, questOwner, questID, taskIndex, false);
                if (didProgress) {
                    TablistUtils.updateTablist(questOwner);
                    if (this.isCompleted()) {
                        this.onComplete(questOwner);
                    }
                    return true;
                }
            }
            taskIndex++;
        }
        return false;
    }

    public boolean progressTaskWithIndex(Player questOwner, int taskIndex) {
        if (taskIndex >= this.tasks.size()) return false;

        Task task = this.tasks.get(taskIndex);

        if (task.isCompleted()) return false;

        task.progress(questOwner, questID, taskIndex, true);

        TablistUtils.updateTablist(questOwner);
        if (this.isCompleted()) {
            this.onComplete(questOwner);
        }

        return true;
    }
}
