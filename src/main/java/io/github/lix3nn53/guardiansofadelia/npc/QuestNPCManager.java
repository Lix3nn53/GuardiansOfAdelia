package io.github.lix3nn53.guardiansofadelia.npc;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.QuestHologram;
import io.github.lix3nn53.guardiansofadelia.quests.QuestIconType;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class QuestNPCManager {

    private HashMap<Integer, List<Quest>> npcNoToCanGiveQuests = new HashMap<>();
    private HashMap<Integer, List<Quest>> npcNoToCanCompleteQuests = new HashMap<>();
    private HashMap<Integer, QuestHologram> npcNoToHologram = new HashMap<>();

    public GuiGeneric getQuestGui(Player player, int npcId) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);

        if (npc != null) {
            GuiGeneric questMenu = new GuiGeneric(27, ChatColor.YELLOW + "Quest List of " + npc.getName(), npcId);

            if (GuardiansOfAdelia.getGuardianDataManager().hasGuardianData(player.getUniqueId())) {
                List<Quest> npcQuestList = new ArrayList<>();

                if (npcNoToCanGiveQuests.containsKey(npcId)) {
                    List<Quest> canGiveQuests = npcNoToCanGiveQuests.get(npcId);
                    npcQuestList.addAll(canGiveQuests);
                }

                if (npcNoToCanCompleteQuests.containsKey(npcId)) {
                    List<Quest> canCompleteQuests = npcNoToCanCompleteQuests.get(npcId);
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
                    questMenu.addItem(quest.getGuiItem(player));
                }
            }
            return questMenu;
        }
        return null;
    }

    public void addQuest(Quest quest, int npcToTakeFrom, int npcToComplete) {
        if (npcNoToCanGiveQuests.containsKey(npcToTakeFrom)) {
            npcNoToCanGiveQuests.get(npcToTakeFrom).add(quest);
        } else {
            List<Quest> questList = new ArrayList<>();
            questList.add(quest);
            npcNoToCanGiveQuests.put(npcToTakeFrom, questList);
        }

        if (npcNoToCanCompleteQuests.containsKey(npcToComplete)) {
            npcNoToCanCompleteQuests.get(npcToComplete).add(quest);
        } else {
            List<Quest> questList = new ArrayList<>();
            questList.add(quest);
            npcNoToCanCompleteQuests.put(npcToComplete, questList);
        }

        NPC npcTake = CitizensAPI.getNPCRegistry().getById(npcToTakeFrom);
        if (npcTake != null) {
            //create main hologram to disguise to players
            createHologramBase(npcTake, npcToTakeFrom);
        }
        NPC npcComplete = CitizensAPI.getNPCRegistry().getById(npcToComplete);
        if (npcComplete != null) {
            //create main hologram to disguise to players
            createHologramBase(npcComplete, npcToComplete);
        }
    }

    public void setNpcHologramForPlayer(Player player, int npcId) {
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            if (npcNoToHologram.containsKey(npcId)) {
                QuestHologram questHologram = npcNoToHologram.get(npcId);
                GuardianDataManager guardianDataManager = GuardiansOfAdelia.getGuardianDataManager();
                UUID uuid = player.getUniqueId();
                if (guardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = guardianDataManager.getGuardianData(uuid);

                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    List<Quest> playerQuestList = activeCharacter.getQuestList();
                    boolean playerHasQuestToCompleteFromThisNPC = false;

                    boolean npcHasQuest1 = false;
                    boolean npcHasQuest2 = false;

                    //check for canComplete quest since Q > ALL
                    if (npcNoToCanCompleteQuests.containsKey(npcId)) {
                        npcHasQuest1 = true;
                        List<Quest> npcCanCompleteQuestList = npcNoToCanCompleteQuests.get(npcId);

                        for (Quest playerQuest : playerQuestList) {
                            playerHasQuestToCompleteFromThisNPC = npcCanCompleteQuestList.stream()
                                    .anyMatch(npcQuest -> npcQuest.getQuestID() == playerQuest.getQuestID());
                            if (playerHasQuestToCompleteFromThisNPC) {
                                break;
                            }
                        }

                        if (playerHasQuestToCompleteFromThisNPC) {
                            for (Quest playerQuest : playerQuestList) {
                                Optional<Quest> questGenericOptional = npcCanCompleteQuestList.stream()
                                        .filter(npcQuest -> npcQuest.getQuestID() == playerQuest.getQuestID())
                                        .findAny();
                                if (questGenericOptional.isPresent()) {
                                    Quest quest = questGenericOptional.get();
                                    if (quest.isCompleted()) {
                                        //has at least one completed quest from this npc
                                        Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                            questHologram.disguiseToPlayer(QuestIconType.COMPLETED, player);
                                        });
                                        return;
                                    }
                                }
                            }
                            //we don't put current icon here cuz ! is only for where you take the quest. This is where you complete the quest

                        }
                    }
                    //if code reaches here there is no completed quest
                    //check for ? then !
                    if (npcNoToCanGiveQuests.containsKey(npcId)) {
                        npcHasQuest2 = true;
                        List<Quest> npcCanTakeQuestList = npcNoToCanGiveQuests.get(npcId);

                        //check if player can take at least one quest
                        for (Quest npcQuest : npcCanTakeQuestList) {
                            List<Integer> turnedInQuests = activeCharacter.getTurnedInQuests();
                            if (turnedInQuests.contains(npcQuest.getQuestID())) {
                                continue;
                            }
                            boolean playerAlreadyAcceptedThisAvaiableQuest = activeCharacter.hasQuest(npcQuest.getQuestID());
                            if (playerAlreadyAcceptedThisAvaiableQuest) {
                                continue;
                            }
                            if (npcQuest.isAvailable(activeCharacter, player.getLevel())) {
                                Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                    questHologram.disguiseToPlayer(QuestIconType.NEW, player);
                                });
                                return;
                            }
                        }

                        //on upper check, if player has quest to complete but not completed set !
                        if (playerHasQuestToCompleteFromThisNPC) {
                            Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                questHologram.disguiseToPlayer(QuestIconType.CURRENT, player);
                            });
                            return;
                        }

                        //check if player has at least one quest taken from this npc
                        for (Quest playerQuest : playerQuestList) {
                            boolean playerHasQuestTakenFromThisNPC = npcCanTakeQuestList.stream()
                                    .anyMatch(npcQuest -> npcQuest.getQuestID() == playerQuest.getQuestID());
                            if (playerHasQuestTakenFromThisNPC) {
                                Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                    questHologram.disguiseToPlayer(QuestIconType.CURRENT, player);
                                });
                                return;
                            }
                        }
                    }
                    //check if npc is quest npc
                    if (npcHasQuest1 || npcHasQuest2) {
                        if (questHologram.isDisguisedToPlayer(player)) {
                            //if code reaches here player doesn't have any quest from this npc and can't take any quest from this npc
                            Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                questHologram.disguiseToPlayer(QuestIconType.EMPTY, player);
                            });
                        }
                    }
                }
            }
        });
    }

    public Quest getQuestCopyById(int questId) {
        for (Integer npcId : npcNoToCanGiveQuests.keySet()) {
            List<Quest> questList = npcNoToCanGiveQuests.get(npcId);
            Optional<Quest> questOptional = questList.stream()
                    .filter(item -> item.getQuestID() == questId)
                    .findAny();
            if (questOptional.isPresent()) {
                Quest quest = questOptional.get();
                return new Quest(quest);
            }
        }
        return null;
    }

    public HashMap<Integer, QuestHologram> getNpcNoToHologram() {
        return npcNoToHologram;
    }

    public void setAllNpcHologramForPlayer(Player player) {
        for (int i : npcNoToHologram.keySet()) {
            setNpcHologramForPlayer(player, i);
        }
    }

    public void onChunkLoad(Entity entity) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        NPC npc = npcRegistry.getNPC(entity);
        int npcId = npc.getId();
        if (npcNoToCanGiveQuests.containsKey(npcId)) {
            //create main hologram to disguise to players
            createHologramBase(npc, npcId);
            setNpcHologramForAllPlayers(npcId);
        } else if (npcNoToCanCompleteQuests.containsKey(npcId)) {
            //create main hologram to disguise to players
            createHologramBase(npc, npcId);
            setNpcHologramForAllPlayers(npcId);
        }
    }

    public void setNpcHologramForAllPlayers(int npcId) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setNpcHologramForPlayer(player, npcId);
        }
    }

    private void createHologramBase(NPC npc, int npcId) {
        if (!npcNoToHologram.containsKey(npcId)) {
            Location holoLocation = npc.getStoredLocation().clone();
            QuestHologram questHologram = new QuestHologram(holoLocation);
            npcNoToHologram.put(npcId, questHologram);
        } else {
            QuestHologram questHologram = npcNoToHologram.get(npcId);
            ArmorStand armorStand = questHologram.getHolo().getArmorStand();
            if (armorStand != null) {
                if (armorStand.isDead()) {
                    armorStand.remove();
                    Location holoLocation = npc.getStoredLocation().clone();
                    questHologram = new QuestHologram(holoLocation);
                    npcNoToHologram.put(npcId, questHologram);
                }
            } else {
                Location holoLocation = npc.getStoredLocation().clone();
                questHologram = new QuestHologram(holoLocation);
                npcNoToHologram.put(npcId, questHologram);
            }
        }
    }

    public int getWhoCanCompleteThisQuest(int questNo) {
        for (int npcNo : npcNoToCanCompleteQuests.keySet()) {
            List<Quest> canCompleteQuestList = npcNoToCanCompleteQuests.get(npcNo);
            boolean canComplete = canCompleteQuestList.stream()
                    .anyMatch(npcQuest -> npcQuest.getQuestID() == questNo);
            if (canComplete) {
                return npcNo;
            }
        }
        return 0;
    }

    public int getWhoCanGiveThisQuest(int questNo) {
        for (int npcNo : npcNoToCanGiveQuests.keySet()) {
            List<Quest> canCompleteQuestList = npcNoToCanGiveQuests.get(npcNo);
            boolean canGive = canCompleteQuestList.stream()
                    .anyMatch(npcQuest -> npcQuest.getQuestID() == questNo);
            if (canGive) {
                return npcNo;
            }
        }
        return 0;
    }
}
