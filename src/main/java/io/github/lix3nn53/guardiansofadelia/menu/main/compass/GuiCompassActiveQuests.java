package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskInteract;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskReach;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiCompassActiveQuests extends GuiBookGeneric {

    public GuiCompassActiveQuests(GuardianData guardianData) {
        super(ChatPalette.GRAY_DARK + "Compass Active Quests", 0);

        RPGCharacter character = guardianData.getActiveCharacter();
        List<Quest> questList = character.getQuestList();


        for (Quest quest : questList) {
            int questID = quest.getQuestID();
            int whoCanGiveThisQuest = QuestNPCManager.getWhoCanGiveThisQuest(questID);
            ItemStack itemStack = compassNpcItem(whoCanGiveThisQuest);
            this.addToFirstAvailableWord(itemStack);
            int whoCanCompleteThisQuest = QuestNPCManager.getWhoCanCompleteThisQuest(questID);
            itemStack = compassNpcItem(whoCanCompleteThisQuest);
            this.addToFirstAvailableWord(itemStack);

            List<Task> tasks = quest.getTasks();
            for (Task task : tasks) {
                if (task instanceof TaskInteract) {
                    TaskInteract taskInteract = (TaskInteract) task;
                    int npcId = taskInteract.getNpcId();

                    itemStack = compassNpcItem(npcId);
                    this.addToFirstAvailableWord(itemStack);
                } else if (task instanceof TaskReach) {
                    TaskReach taskReach = (TaskReach) task;
                    Location loc = taskReach.getBlockLoc();

                    itemStack = new ItemStack(Material.MAGENTA_WOOL);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatPalette.GRAY + "Click to select your compass target!");
                    lore.add("");
                    lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
                    itemMeta.setLore(lore);

                    itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Quest-" + questID + "Reach Loc" + " #" + loc.getWorld().getName() + "-" + loc.getX() + "-" + loc.getY() + "-" + loc.getZ());
                    itemStack.setItemMeta(itemMeta);

                    this.addToFirstAvailableWord(itemStack);
                }
            }
        }
    }

    private static ItemStack compassNpcItem(int npcId) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();

        NPC byId = npcRegistry.getById(npcId);

        if (byId == null) return null;

        ItemStack itemStack = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to select your compass target!");
        lore.add("");
        lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
        itemMeta.setLore(lore);

        itemMeta.setDisplayName(ChatPalette.GREEN + byId.getName() + " #" + npcId);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack item = event.getCurrentItem();
        ItemMeta itemMeta = item.getItemMeta();
        Material currentType = item.getType();

        if (currentType.equals(Material.LIME_WOOL)) {
            String displayName = itemMeta.getDisplayName();
            String[] split = displayName.split("#");
            int i = Integer.parseInt(split[1]);
            CompassManager.setCompassItemNPC(player, i);
            player.closeInventory();
        } else if (currentType.equals(Material.MAGENTA_WOOL)) {
            String displayName = itemMeta.getDisplayName();
            String[] split = displayName.split("#");

            String[] splitSecond = split[1].split("-");

            Location location = new Location(Bukkit.getWorld(splitSecond[0]), Float.parseFloat(splitSecond[1]),
                    Float.parseFloat(splitSecond[2]), Float.parseFloat(splitSecond[3]));

            CompassManager.setCompassItemLocation(player, split[0], location);
            player.closeInventory();
        }
    }
}
