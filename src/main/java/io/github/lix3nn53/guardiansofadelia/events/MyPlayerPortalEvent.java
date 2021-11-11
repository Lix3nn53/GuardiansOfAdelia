package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.List;

public class MyPlayerPortalEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        Town town = TownManager.getTown(5);
        Location alberstolRuinsBase = town.getLocation();
        float distanceSquared = (float) location.distanceSquared(alberstolRuinsBase);

        if (distanceSquared < 1200) { //portal to Alberstol Ruins at Uruga
            World world = Bukkit.getWorld("world");
            Location toAlberstolRuins = new Location(world, -2558.5, 63.5, 2744.5, -90, -2);
            player.teleport(toAlberstolRuins);
            player.sendTitle(ChatPalette.PURPLE + "Uruga", ChatPalette.PURPLE_LIGHT + "", 25, 35, 25);
        } else {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
            List<Integer> turnedInQuests = activeCharacter.getTurnedInQuests();

            if (turnedInQuests.contains(69)) {
                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                NPC npcGatekeeper = npcRegistry.getById(47);

                Location gatekeeperLocation = npcGatekeeper.getStoredLocation();
                float distanceSquared2 = (float) location.distanceSquared(gatekeeperLocation);

                if (distanceSquared2 < 900) { //portal to Alberstol Ruins at Uruga
                    World world = Bukkit.getWorld("world");
                    Location toAlberstolRuins = new Location(world, -4835.5, 103.5, 2236.5, -80, -2);
                    player.teleport(toAlberstolRuins);
                    player.sendTitle(ChatPalette.PURPLE + "Alberstol Ruins", ChatPalette.PURPLE_LIGHT + "", 25, 35, 25);
                }
            } else {
                player.sendMessage(ChatPalette.RED + "You can't enter this portal without Gatekeeper's permission.");
            }
        }
    }

}
