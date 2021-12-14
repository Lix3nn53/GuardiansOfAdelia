package io.github.lix3nn53.guardiansofadelia.menu.quest;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.config.ArmorReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.config.WeaponReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.npc.NPCAvatar;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacter;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuiQuestList extends GuiGeneric {

    NPC npc;

    public GuiQuestList(NPC npc, Player player, GuardianData guardianData) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK +
                Translation.t(guardianData, "quest.list_of") + " " + npc.getName(), npc.getId());
        this.npc = npc;

        int npcId = npc.getId();
        formAvatar(npcId);

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
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        RPGCharacter rpgCharacter;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            } else {
                return;
            }
        } else {
            return;
        }

        ItemStack item = this.getItem(event.getSlot());
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
                            GuiQuestList questGui = new GuiQuestList(this.npc, player, guardianData);
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

                        CustomCharacter customCharacter = CustomCharacterGui.MENU_27_FLAT;
                        if (guiSize < 27) {
                            guiSize = 27;
                        } else {
                            guiSize = 54;
                            customCharacter = CustomCharacterGui.MENU_54_FLAT;
                        }

                        // CREATE GUI
                        GuiQuestPrizeSelect gui = new GuiQuestPrizeSelect(guiSize, customCharacter, questNo, resourceNPC,
                                itemPrizesSelectOneOf, weaponPrizesSelectOneOf, armorPrizesSelectOneOf, rpgCharacter, guardianData);

                        gui.openInventory(player);
                    }
                } else {
                    NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                    NPC byId = npcRegistry.getById(whoCanCompleteThisQuest);
                    player.sendMessage(ChatPalette.RED + Translation.t(guardianData, "quest.error.wrong_turn_in") + " " + ChatPalette.WHITE + byId.getName());
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
                        GuiQuestList questGui = new GuiQuestList(this.npc, player, guardianData);
                        questGui.openInventory(player);
                    } else {
                        player.sendMessage(ChatPalette.RED + Translation.t(guardianData, "quest.error.full"));
                    }
                } else {
                    NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                    NPC byId = npcRegistry.getById(whoCanGiveThisQuest);
                    player.sendMessage(ChatPalette.RED + Translation.t(guardianData, "quest.error.wrong_take") + " " + ChatPalette.WHITE + byId.getName());
                }
            }
        }
    }

    public void formAvatar(int npcId) {
        ItemStack avatar = NPCAvatar.getAvatar(npcId);

        this.setItem(18, avatar);

        ItemMeta itemMeta = avatar.getItemMeta();
        ItemStack empty = OtherItems.getEmpty(itemMeta.getDisplayName(), itemMeta.getLore());

        this.setItem(0, empty);
        this.setItem(1, empty);
        this.setItem(2, empty);
        this.setItem(9, empty);
        this.setItem(10, empty);
        this.setItem(11, empty);
        this.setItem(19, empty);
        this.setItem(20, empty);
    }
}
