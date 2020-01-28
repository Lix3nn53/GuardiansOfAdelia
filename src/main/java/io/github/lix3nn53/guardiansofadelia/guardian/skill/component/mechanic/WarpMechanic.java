package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.TargetHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class WarpMechanic extends MechanicComponent {

    private final boolean throughWalls;
    private final List<Double> forward;
    private final List<Double> upward;
    private final List<Double> right;

    public WarpMechanic(boolean throughWalls, List<Double> forward, List<Double> upward, List<Double> right) {
        this.throughWalls = throughWalls;
        this.forward = forward;
        this.upward = upward;
        this.right = right;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.size() == 0) return false;

        for (LivingEntity target : targets) {
            Vector dir = target.getLocation().getDirection();
            Vector side = dir.clone().crossProduct(UP).multiply(right.get(skillLevel - 1));
            Location loc = target.getLocation().add(dir.multiply(forward.get(skillLevel - 1))).add(side).add(0, upward.get(skillLevel - 1), 0).add(0, 1, 0);
            loc = TargetHelper.getOpenLocation(target.getLocation().add(0, 1, 0), loc, throughWalls);
            if (!loc.getBlock().getType().isSolid() && loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                loc.add(0, 1, 0);
            }
            target.teleport(loc.subtract(0, 1, 0));
        }
        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Warp forward: " + forward.get(skillLevel));
        } else if (skillLevel == forward.size()) {
            additions.add(ChatColor.YELLOW + "Warp forward: " + forward.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Warp forward: " + forward.get(skillLevel - 1) + " -> " + forward.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
