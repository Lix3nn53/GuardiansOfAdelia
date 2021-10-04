package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

public class SkillOnGroundWithOffset {

    private final SkillOnGround skillOnGround;
    private final Vector offset;

    public SkillOnGroundWithOffset(SkillOnGround skillOnGround, Vector offset) {
        this.skillOnGround = skillOnGround;
        this.offset = offset;
    }

    public ArmorStand activate(Location location, long delay) {
        Location add = location.clone().add(offset);

        return skillOnGround.activate(add, delay);
    }

    /*
    This is not used because this class is only used in dungeons.
    And dungeons have to deactivate this skill with deleting ArmorStand they get from #activate method...
    ...because #deactivate only works on latest activation.

    public void deactivate() {
        skillOnGround.deactivate();
    }*/

    public Vector getOffset() {
        return offset;
    }

    public SkillOnGround getSkillOnGround() {
        return skillOnGround;
    }
}
