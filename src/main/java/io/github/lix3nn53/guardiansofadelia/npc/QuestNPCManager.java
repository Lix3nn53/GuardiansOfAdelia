package io.github.lix3nn53.guardiansofadelia.npc;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.QuestHologram;
import io.github.lix3nn53.guardiansofadelia.quests.QuestIconType;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.*;

public class QuestNPCManager {

    public final static HashMap<Integer, List<Quest>> npcNoToCanGiveQuests = new HashMap<>();
    public final static HashMap<Integer, List<Quest>> npcNoToCanCompleteQuests = new HashMap<>();

    private final static HashMap<Integer, QuestHologram> npcNoToHologram = new HashMap<>();
    private final static Set<ArmorStand> questHologramArmorStands = new HashSet<>(); //only for chunk events, weirdly, they don't work fine with npcNoToHologram

    public static void addQuest(Quest quest, int npcToTakeFrom, int npcToComplete) {
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

    public static void setNpcHologramForPlayer(Player player, int npcId) {
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            if (npcNoToHologram.containsKey(npcId)) {
                QuestHologram questHologram = npcNoToHologram.get(npcId);

                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);

                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        List<Quest> playerQuestList = activeCharacter.getQuestList();

                        boolean npcHasQuest1 = false;
                        boolean npcHasQuest2 = false;

                        List<Quest> npcCanCompleteQuestList = npcNoToCanCompleteQuests.get(npcId);

                        //check for canComplete quest since Q > ALL
                        if (npcNoToCanCompleteQuests.containsKey(npcId)) {
                            npcHasQuest1 = true;

                            for (Quest playerQuest : playerQuestList) {
                                if (playerQuest.isCompleted()) {
                                    boolean thisCompletedQuestIsFromThisNpc = npcCanCompleteQuestList.stream()
                                            .anyMatch(npcQuest -> npcQuest.getQuestID() == playerQuest.getQuestID());
                                    if (thisCompletedQuestIsFromThisNpc) {
                                        //has at least one completed quest from this npc
                                        Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                            questHologram.disguiseToPlayer(QuestIconType.COMPLETED, player);
                                        });
                                        return;
                                    }
                                }
                            }
                        }
                        //if code reaches here there is no completed quest
                        //check for ? then !
                        List<Quest> npcCanTakeQuestList = npcNoToCanGiveQuests.get(npcId);
                        if (npcNoToCanGiveQuests.containsKey(npcId)) {
                            npcHasQuest2 = true;

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
                            for (Quest playerQuest : playerQuestList) {
                                boolean playerHasQuestTakenFromThisNPC = npcCanCompleteQuestList.stream()
                                        .anyMatch(npcQuest -> npcQuest.getQuestID() == playerQuest.getQuestID());
                                if (playerHasQuestTakenFromThisNPC) {
                                    Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                                        questHologram.disguiseToPlayer(QuestIconType.CURRENT, player);
                                    });
                                    return;
                                }
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
            }
        });
    }

    public static Quest getQuestCopyById(int questId) {
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

    public static HashMap<Integer, QuestHologram> getNpcNoToHologram() {
        return npcNoToHologram;
    }

    public static void setAllNpcHologramForPlayer(Player player) {
        for (int i : npcNoToHologram.keySet()) {
            setNpcHologramForPlayer(player, i);
        }
    }

    public static void onNPCSpawn(NPC npc) {
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

    public static void onNPCDespawn(NPC npc) {
        int npcId = npc.getId();
        if (npcNoToHologram.containsKey(npcId)) {
            QuestHologram questHologram = npcNoToHologram.get(npcId);
            ArmorStand armorStand = questHologram.getHolo().getArmorStand();
            if (armorStand.isValid()) {
                if (!armorStand.getPassengers().isEmpty()) {
                    armorStand.getPassengers().get(0).remove();
                }
                armorStand.remove();
            }
            npcNoToHologram.remove(npcId);
        }
    }

    public static void setNpcHologramForAllPlayers(int npcId) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setNpcHologramForPlayer(player, npcId);
        }
    }

    private static void createHologramBase(NPC npc, int npcId) {
        if (!npcNoToHologram.containsKey(npcId)) {
            Location holoLocation = npc.getStoredLocation().clone();
            QuestHologram questHologram = new QuestHologram(holoLocation);
            npcNoToHologram.put(npcId, questHologram);
            ArmorStand newArmorStand = questHologram.getHolo().getArmorStand();
            questHologramArmorStands.add(newArmorStand);
            if (!newArmorStand.getPassengers().isEmpty()) {
                questHologramArmorStands.add((ArmorStand) newArmorStand.getPassengers().get(0));
            }
        } else {
            QuestHologram questHologram = npcNoToHologram.get(npcId);
            ArmorStand armorStand = questHologram.getHolo().getArmorStand();
            if (armorStand != null) {
                if (armorStand.isDead()) {
                    armorStand.remove();
                    Location holoLocation = npc.getStoredLocation().clone();
                    questHologram = new QuestHologram(holoLocation);
                    npcNoToHologram.put(npcId, questHologram);
                    ArmorStand newArmorStand = questHologram.getHolo().getArmorStand();
                    questHologramArmorStands.add(newArmorStand);
                    if (!newArmorStand.getPassengers().isEmpty()) {
                        questHologramArmorStands.add((ArmorStand) newArmorStand.getPassengers().get(0));
                    }
                }
            } else {
                Location holoLocation = npc.getStoredLocation().clone();
                questHologram = new QuestHologram(holoLocation);
                npcNoToHologram.put(npcId, questHologram);
                ArmorStand newArmorStand = questHologram.getHolo().getArmorStand();
                questHologramArmorStands.add(newArmorStand);
                if (!newArmorStand.getPassengers().isEmpty()) {
                    questHologramArmorStands.add((ArmorStand) newArmorStand.getPassengers().get(0));
                }
            }
        }
    }

    public static int getWhoCanCompleteThisQuest(int questNo) {
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

    public static int getWhoCanGiveThisQuest(int questNo) {
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

    public static boolean isQuestIcon(ArmorStand armorStand) {
        for (ArmorStand holo : questHologramArmorStands) {
            if (holo.equals(armorStand)) return true;
        }
        return false;
    }
}
