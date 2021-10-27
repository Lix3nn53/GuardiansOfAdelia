package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.*;
import java.util.function.Predicate;

public class CharacterSelectionScreenManager {

    private static final HashMap<UUID, HashMap<Integer, Location>> charLocationsForSelection = new HashMap<>();
    private static final HashMap<UUID, HashMap<Integer, Integer>> charLevelsForSelection = new HashMap<>();
    private static final List<Player> players = new ArrayList<>();
    private static List<Location> armorStandLocationBases;
    private static Location tutorialStart;
    private static Location characterSelectionCenter;
    private static final HashMap<Integer, List<ArmorStand>> characterNoToArmorStands = new HashMap<>();

    public static void setArmorStandLocationBases(List<Location> armorStandLocationBases) {
        CharacterSelectionScreenManager.armorStandLocationBases = armorStandLocationBases;
    }

    private static void createHolograms() {
        //remove old armorStands since chunk events doesn't work for them. Because spawn chunks are always loaded.
        BoundingBox boundingBox = new BoundingBox(-2750, 41, 6108, -2772, 53, 6130);
        Predicate<Entity> predicate = entity -> entity.getType().equals(EntityType.ARMOR_STAND);
        Collection<Entity> oldArmorStands = Bukkit.getWorld("world").getNearbyEntities(boundingBox, predicate);
        for (Entity entity : oldArmorStands) {
            entity.remove();
        }

        //create new armorStands
        int i = 1;
        for (Location location : armorStandLocationBases) {
            List<ArmorStand> holoList = new ArrayList<>();
            Location clone = location.clone();

            holoList.add(new Hologram(clone.add(0.0, 0.4, 0.0), ChatPalette.GREEN_DARK + "create new character").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), ChatPalette.GREEN_DARK + "Right click NPC to").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), ChatPalette.GOLD + "Character-slot " + ChatPalette.YELLOW + i).getArmorStand());

            characterNoToArmorStands.put(i, holoList);
            i++;
        }
    }

    public static void startCharacterSelection(Player player) {
        if (players.isEmpty()) {
            createHolograms();
        }
        players.add(player);
        DatabaseManager.loadPlayerDataAndCharacterSelection(player);
        player.teleport(characterSelectionCenter);
    }

    private static void removeDisguisesFromPlayer(Player player) {
        for (int charNo = 1; charNo <= 8; charNo++) {
            if (characterNoToArmorStands.containsKey(charNo)) {
                List<ArmorStand> armorStands = characterNoToArmorStands.get(charNo);
                for (ArmorStand armorStand : armorStands) {
                    Disguise disguise = DisguiseAPI.getDisguise(player, armorStand);
                    if (disguise != null) {
                        disguise.removeDisguise();
                    }
                }
            }
        }
    }

    private static void removeHolograms() {
        for (int charNo = 1; charNo <= 8; charNo++) {
            if (characterNoToArmorStands.containsKey(charNo)) {
                List<ArmorStand> armorStands = characterNoToArmorStands.get(charNo);
                for (ArmorStand armorStand : armorStands) {
                    armorStand.remove();
                }
                characterNoToArmorStands.remove(charNo);
            }
        }
    }

    public static HashMap<Integer, List<ArmorStand>> getCharacterNoToArmorStands() {
        return characterNoToArmorStands;
    }

    public static void selectCharacter(Player player, int charNo, Location location) {
        player.sendMessage(ChatPalette.YELLOW + "Loading character-" + charNo);
        DatabaseManager.loadCharacter(player, charNo, location);
        clear(player);
    }

    public static void clear(Player player) {
        removeDisguisesFromPlayer(player);
        players.remove(player);
        charLocationsForSelection.remove(player.getUniqueId());
        charLevelsForSelection.remove(player.getUniqueId());
        if (players.isEmpty()) {
            removeHolograms();
        }
    }

    public static Location getCharacterSelectionCenter() {
        return characterSelectionCenter;
    }

    public static void setCharacterSelectionCenter(Location characterSelectionCenter) {
        CharacterSelectionScreenManager.characterSelectionCenter = characterSelectionCenter;
    }

    public static Location getTutorialStart() {
        return tutorialStart;
    }

    public static void setTutorialStart(Location tutorialStart) {
        CharacterSelectionScreenManager.tutorialStart = tutorialStart;
    }

    public static void createCharacter(Player player, int charNo) {
        player.sendMessage(ChatPalette.YELLOW + "Creating character-" + charNo);
        clear(player);
        //start tutorial
        TutorialManager.startTutorial(player, charNo, tutorialStart);
    }

    public static void createCharacterWithoutTutorial(Player player, int charNo) {
        player.sendMessage(ChatPalette.YELLOW + "Creating character-" + charNo);
        clear(player);
        //start character at first world quest
        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
        String startingClass = RPGClassManager.getStartingClass();
        RPGCharacter rpgCharacter = new RPGCharacter(startingClass, player);
        guardianData.setActiveCharacter(rpgCharacter, charNo);

        Quest questCopyById = QuestNPCManager.getQuestCopyById(4);

        boolean accept = rpgCharacter.acceptQuest(questCopyById, player);
        questCopyById.onComplete(player);

        rpgCharacter.getRpgCharacterStats().recalculateEquipment(rpgCharacter.getRpgClassStr());
        rpgCharacter.getRpgCharacterStats().recalculateRPGInventory(rpgCharacter.getRpgInventory());

        RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
        rpgCharacterStats.setCurrentHealth(rpgCharacterStats.getTotalMaxHealth());
        rpgCharacterStats.setCurrentMana(rpgCharacterStats.getTotalMaxMana());
    }


    public static void setCharLocation(UUID uuid, int charNo, Location location) {
        HashMap<Integer, Location> integerLocationHashMap = new HashMap<>();
        if (charLocationsForSelection.containsKey(uuid)) {
            integerLocationHashMap = charLocationsForSelection.get(uuid);
        }
        integerLocationHashMap.put(charNo, location);
        charLocationsForSelection.put(uuid, integerLocationHashMap);
    }

    public static void setCharLevel(UUID uuid, int charNo, int level) {
        HashMap<Integer, Integer> integerLevelHashMap = new HashMap<>();
        if (charLevelsForSelection.containsKey(uuid)) {
            integerLevelHashMap = charLevelsForSelection.get(uuid);
        }
        integerLevelHashMap.put(charNo, level);
        charLevelsForSelection.put(uuid, integerLevelHashMap);
    }

    /**
     * @return last leave location if valid else null
     */
    public static Location getCharLocation(Player player, int charNo) {
        UUID uuid = player.getUniqueId();
        if (charLocationsForSelection.containsKey(uuid)) {
            HashMap<Integer, Location> integerLocationHashMap = charLocationsForSelection.get(uuid);
            if (integerLocationHashMap.containsKey(charNo)) {
                Location location = integerLocationHashMap.get(charNo);
                if (location.getWorld().getName().equals("world")) {
                    return location;
                }
            }
        }
        return null;
    }

    public static int getCharLevel(Player player, int charNo) {
        UUID uuid = player.getUniqueId();
        if (charLevelsForSelection.containsKey(uuid)) {
            HashMap<Integer, Integer> integerLevelHashMap = charLevelsForSelection.get(uuid);
            if (integerLevelHashMap.containsKey(charNo)) {
                return integerLevelHashMap.get(charNo);
            }
        }
        return 1;
    }

    public static boolean isPlayerInCharSelection(Player player) {
        return players.contains(player);
    }
}