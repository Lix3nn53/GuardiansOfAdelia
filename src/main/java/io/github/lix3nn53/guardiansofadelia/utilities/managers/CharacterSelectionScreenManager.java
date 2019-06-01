package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import net.md_5.bungee.api.ChatColor;
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
    private final List<Location> armorStandLocationBases;
    private final List<Player> players = new ArrayList<>();
    private Location tutorialStart;
    private Location characterSelectionCenter;
    private HashMap<Integer, List<ArmorStand>> characterNoToArmorStands = new HashMap<>();

    public CharacterSelectionScreenManager(List<Location> armorStandLocationBases) {
        this.armorStandLocationBases = armorStandLocationBases;
    }

    private void createHolograms() {
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

            holoList.add(new Hologram(clone.add(0.0, 0.4, 0.0), " ").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), " ").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), ChatColor.GREEN + "Create New Character").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), " ").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), " ").getArmorStand());
            holoList.add(new Hologram(clone.add(0.0, 0.3, 0.0), " ").getArmorStand());

            characterNoToArmorStands.put(i, holoList);
            i++;
        }
    }

    public void startCharacterSelection(Player player) {
        if (players.isEmpty()) {
            createHolograms();
        }
        players.add(player);
        DatabaseManager.loadPlayerDataAndCharacterSelection(player);
        player.teleport(this.characterSelectionCenter);
    }

    private void removeDisguisesFromPlayer(Player player) {
        for (int charNo = 1; charNo <= 4; charNo++) {
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

    public HashMap<Integer, List<ArmorStand>> getCharacterNoToArmorStands() {
        return characterNoToArmorStands;
    }

    public void selectCharacter(Player player, int charNo, Location location) {
        player.sendMessage("Loading character-" + charNo);
        DatabaseManager.loadCharacter(player, charNo, location);
        clear(player);
    }

    public void clear(Player player) {
        removeDisguisesFromPlayer(player);
        players.remove(player);
        charLocationsForSelection.remove(player.getUniqueId());
    }

    public Location getCharacterSelectionCenter() {
        return characterSelectionCenter;
    }

    public void setCharacterSelectionCenter(Location characterSelectionCenter) {
        this.characterSelectionCenter = characterSelectionCenter;
    }

    public Location getTutorialStart() {
        return tutorialStart;
    }

    public void setTutorialStart(Location tutorialStart) {
        this.tutorialStart = tutorialStart;
    }

    public void createCharacter(Player player, int charNo, RPGClass rpgClass) {
        player.sendMessage("Creating character-" + charNo);
        clear(player);
        //start tutorial
        TutorialManager.startTutorial(player, rpgClass, charNo, this.tutorialStart);
    }

    public void setCharLocation(UUID uuid, int charNo, Location location) {
        HashMap<Integer, Location> integerLocationHashMap = new HashMap<>();
        if (charLocationsForSelection.containsKey(uuid)) {
            integerLocationHashMap = charLocationsForSelection.get(uuid);
        }
        integerLocationHashMap.put(charNo, location);
        charLocationsForSelection.put(uuid, integerLocationHashMap);
    }

    /**
     * @return last leave location if valid else null
     */
    public Location getCharLocation(Player player, int charNo) {
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

    public boolean isPlayerInCharSelection(Player player) {
        return players.contains(player);
    }
}