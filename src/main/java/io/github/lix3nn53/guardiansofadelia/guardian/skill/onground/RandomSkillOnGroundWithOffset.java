package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

import java.util.List;

public class RandomSkillOnGroundWithOffset {

    private final List<SkillOnGround> skillOnGroundList;
    private final Vector offset;

    public RandomSkillOnGroundWithOffset(List<SkillOnGround> skillOnGroundList, Vector offset) {
        this.skillOnGroundList = skillOnGroundList;
        this.offset = offset;
    }

    public ArmorStand activate(Location location, long delay) {
        Location add = location.clone().add(offset);

        int size = this.skillOnGroundList.size();
        if (size == 0) {
            Hologram hologram = new Hologram(add, "EMPTY SKILL ERROR");

            return hologram.getArmorStand();
        }
        if (size == 1) return skillOnGroundList.get(0).activate(add, delay);

        int i = GuardiansOfAdelia.RANDOM.nextInt(size);

        return skillOnGroundList.get(i).activate(add, delay);
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
}
