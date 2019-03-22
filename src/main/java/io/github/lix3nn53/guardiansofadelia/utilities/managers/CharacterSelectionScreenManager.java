package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CharacterSelectionScreenManager {

    private final List<Location> armorStandLocationBases;
    private final List<Player> players = new ArrayList<>();
    private Location tutorialStart;
    private Location characterSelectionCenter;
    private HashMap<Integer, List<ArmorStand>> characterNoToArmorStands = new HashMap<>();

    public CharacterSelectionScreenManager(List<Location> armorStandLocationBases) {
        this.armorStandLocationBases = armorStandLocationBases;
    }

    private void createHolograms() {
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
        DatabaseManager databaseManager = GuardiansOfAdelia.getDatabaseManager();
        databaseManager.loadPlayerDataAndCharacterSelection(player);
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
        GuardiansOfAdelia.getDatabaseManager().loadCharacter(player, charNo, location);
        clear(player);
    }

    public void clear(Player player) {
        removeDisguisesFromPlayer(player);
        players.remove(player);
        HashMap<UUID, HashMap<Integer, Location>> charLocationsForSelection = GuardiansOfAdelia.getCharLocationsForSelection();
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
        TutorialManager tutorialManager = GuardiansOfAdelia.getTutorialManager();
        tutorialManager.startTutorial(player, rpgClass, charNo, this.tutorialStart);
    }
}