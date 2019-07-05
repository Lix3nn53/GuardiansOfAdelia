package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.TargetHelper;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TeleportMechanic extends MechanicComponent {

    private final boolean throughWalls;
    private final double forward;
    private final double upward;
    private final double right;

    public TeleportMechanic(boolean throughWalls, double forward, double upward, double right) {
        this.throughWalls = throughWalls;
        this.forward = forward;
        this.upward = upward;
        this.right = right;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            Vector dir = target.getLocation().getDirection();
            Vector side = dir.clone().crossProduct(UP).multiply(right);
            Location loc = target.getLocation().add(dir.multiply(forward)).add(side).add(0, upward, 0).add(0, 1, 0);
            loc = TargetHelper.getOpenLocation(target.getLocation().add(0, 1, 0), loc, throughWalls);
            if (!loc.getBlock().getType().isSolid() && loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                loc.add(0, 1, 0);
            }
            target.teleport(loc.subtract(0, 1, 0));
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
