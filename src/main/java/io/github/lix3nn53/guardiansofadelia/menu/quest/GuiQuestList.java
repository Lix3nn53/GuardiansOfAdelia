package io.github.lix3nn53.guardiansofadelia.menu.quest;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.config.ArmorReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.config.WeaponReferenceData;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuiQuestList extends GuiGeneric {

    NPC npc;

    public GuiQuestList(NPC npc, Player player) {
        super(27, ChatPalette.GRAY_DARK + "Quest List of " + npc.getName(), npc.getId());
        this.npc = npc;

        int npcId = npc.getId();

        if (GuardianDataManager.hasGuardianData(player)) {
            List<Quest> npcQuestList = new ArrayList<>();

            if (QuestNPCManager.npcNoToCanGiveQuests.containsKey(npcId)) {
                List<Quest> canGiveQuests = QuestNPCManager.npcNoToCanGiveQuests.get(npcId);
                npcQuestList.addAll(canGiveQuests);
            }

            if (QuestNPCManager.npcNoToCanCompleteQuests.containsKey(npcId)) {
                List<Quest> canCompleteQuests = QuestNPCManager.npcNoToCanCompleteQuests.get(npcId);
                for (Quest npcCanCompleteQuest : canCompleteQuests) {
                    boolean alreadyInList = npcQuestList.stream()
                            .anyMatch(questInList -> questInList.getQuestID() == npcCanCompleteQuest.getQuestID());
                    if (!alreadyInList) {
                        npcQuestList.add(npcCanCompleteQuest);
                    }
                }
            }

            npcQuestList.sort(Comparator.comparingInt(Quest::getQuestID));

            for (Quest quest : npcQuestList) {
                this.addItem(quest.getGuiItem(player));
            }
        }
    }

    @Override
    public void onClick(Player player, GuardianData guardianData, String title, int slot) {
        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
        if (rpgCharacter == null) return;

        ItemStack item = this.getItem(slot);
        Material type = item.getType();
        ItemMeta itemMeta = item.getItemMeta();
        String currentName = itemMeta.getDisplayName();

        int resourceNPC = this.getResourceNPC();
        if (resourceNPC != 0) {
            if (type.equals(Material.MAGENTA_WOOL)) {
                String[] split = currentName.split("#");
                int questNo = Integer.parseInt(split[1]);
                int whoCanCompleteThisQuest = QuestNPCManager.getWhoCanCompleteThisQuest(questNo);
                if (whoCanCompleteThisQuest == resourceNPC) {
                    List<Quest> questList = rpgCharacter.getQuestList();
                    Quest quest = null;
                    for (Quest q : questList) {
                        if (q.getQuestID() == questNo) {
                            quest = q;
                            break;
                        }
                    }
                    List<ItemStack> itemPrizesSelectOneOf = quest.getItemPrizesSelectOneOf();
                    WeaponReferenceData weaponPrizesSelectOneOf = quest.getWeaponPrizesSelectOneOf();
                    ArmorReferenceData armorPrizesSelectOneOf = quest.getArmorPrizesSelectOneOf();
                    if (itemPrizesSelectOneOf.isEmpty() &&
                            weaponPrizesSelectOneOf == null &&
                            armorPrizesSelectOneOf == null) {
                        //turnin quest
                        boolean didTurnIn = rpgCharacter.turnInQuest(questNo, player, false);
                        if (didTurnIn) {
                            GuiQuestList questGui = new GuiQuestList(this.npc, player);
                            questGui.openInventory(player);
                        } else {
                            player.sendMessage(ChatPalette.RED + "Couldn't turn in the quest ERROR report pls");
                        }
                    } else {
                        // GUISIZE
                        int guiSize = 18;
                        if (weaponPrizesSelectOneOf != null) {
                            guiSize += 9;
                        }
                        if (armorPrizesSelectOneOf != null) {
                            guiSize += 9;
                        }
                        int normalSelectOneOfSize = itemPrizesSelectOneOf.size();
                        if (normalSelectOneOfSize > 4) {
                            guiSize += 9;
                        }
                        if (normalSelectOneOfSize > 8) {
                            guiSize += 9;
                        }
                        if (normalSelectOneOfSize > 12) {
                            guiSize += 9;
                        }

                        // CREATE GUI
                        GuiQuestPrizeSelect gui = new GuiQuestPrizeSelect(guiSize, questNo, resourceNPC, itemPrizesSelectOneOf, weaponPrizesSelectOneOf, armorPrizesSelectOneOf, rpgCharacter);

                        gui.openInventory(player);
                    }
                } else {
                    NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                    NPC byId = npcRegistry.getById(whoCanCompleteThisQuest);
                    player.sendMessage(ChatPalette.RED + "You can't turn in this quest from this NPC. You need to talk with " + ChatPalette.WHITE + byId.getName());
                }
            } else if (type.equals(Material.LIME_WOOL)) {
                String[] split = currentName.split("#");
                int questNo = Integer.parseInt(split[1]);
                int whoCanGiveThisQuest = QuestNPCManager.getWhoCanGiveThisQuest(questNo);
                if (whoCanGiveThisQuest == resourceNPC) {
                    //give quest
                    Quest questCopyById = QuestNPCManager.getQuestCopyById(questNo);

                    boolean questListIsNotFull = rpgCharacter.acceptQuest(questCopyById, player);
                    if (questListIsNotFull) {
                        GuiQuestList questGui = new GuiQuestList(this.npc, player);
                        questGui.openInventory(player);
                    } else {
                        player.sendMessage(ChatPalette.RED + "Your quest list is full");
                    }
                } else {
                    NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                    NPC byId = npcRegistry.getById(whoCanGiveThisQuest);
                    player.sendMessage(ChatPalette.RED + "You can't take this quest from this NPC. You need to talk with " + ChatPalette.WHITE + byId.getName());
                }
            }
        }
    }
}
