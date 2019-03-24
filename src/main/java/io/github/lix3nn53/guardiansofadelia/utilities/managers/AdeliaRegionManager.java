package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class AdeliaRegionManager {

    private static final RegionManager regionManagerWorld = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
    private static final RegionManager regionManagerDungeons = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("dungeons")));
    private static final RegionManager regionManagerArena = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("arena")));

    public static boolean isBuffRegion(Location location) {
        if (location.getWorld().getName().equals("world")) {
            if (regionManagerWorld != null) {
                for (ProtectedRegion region : regionManagerWorld.getApplicableRegions(BukkitAdapter.asBlockVector(location))) {
                    if (region.getId().contains("bazaar")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCharacterSelectionRegion(Location location) {
        if (location.getWorld().getName().equals("world")) {
            if (regionManagerWorld != null) {
                for (ProtectedRegion region : regionManagerWorld.getApplicableRegions(BukkitAdapter.asBlockVector(location))) {
                    if (region.getId().contains("charselectionareawg")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
