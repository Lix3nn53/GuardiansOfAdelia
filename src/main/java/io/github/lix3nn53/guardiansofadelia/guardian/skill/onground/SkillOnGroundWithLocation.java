package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;

public class SkillOnGroundWithLocation {

    private final SkillOnGround skillOnGround;
    private final Location location;

    public SkillOnGroundWithLocation(SkillOnGround skillOnGround, Location location) {
        this.skillOnGround = skillOnGround;
        this.location = location;
    }

    public SkillOnGroundWithLocation(ConfigurationSection configurationSection) {
        skillOnGround = new SkillOnGround(configurationSection);

        String worldStr = configurationSection.getString("loc.world");
        World world = Bukkit.getWorld(worldStr);
        double x = configurationSection.getDouble("loc.x");
        double y = configurationSection.getDouble("loc.y");
        double z = configurationSection.getDouble("loc.z");

        location = new Location(world, x, y, z);
    }

    public ArmorStand activate(long delay) {
        return skillOnGround.activate(location, delay);
    }

    public void deactivate() {
        skillOnGround.deactivate();
    }

    public Location getLocation() {
        return location;
    }
}
