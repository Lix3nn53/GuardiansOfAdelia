package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SkillOnGroundWithOffset {

    private final SkillOnGround skillOnGround;
    private final Vector offset;

    public SkillOnGroundWithOffset(SkillOnGround skillOnGround, Vector offset) {
        this.skillOnGround = skillOnGround;
        this.offset = offset;
    }

    public void activate(Location location, long delay) {
        Location add = location.clone().add(offset);

        skillOnGround.activate(add, delay);
    }

    public void deactivate() {
        skillOnGround.deactivate();
    }

    public Vector getOffset() {
        return offset;
    }
}
