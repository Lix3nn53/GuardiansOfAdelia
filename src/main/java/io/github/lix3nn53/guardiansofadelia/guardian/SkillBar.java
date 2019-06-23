package io.github.lix3nn53.guardiansofadelia.guardian;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.list.SkillList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillBar {

    private final Player player;

    private final List<Integer> investedSkillPoints = new ArrayList<>();

    public SkillBar(Player player) {
        this.player = player;

        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
    }

    /**
     * @param skillNo 1,2,3 normal skills, 4 passive, 5 ultimate
     * @param points  to invest in skill
     */
    public void investSkillPoints(int skillNo, int points) {
        int index = skillNo - 1;
        int invested = investedSkillPoints.get(index);
        invested += points;
        investedSkillPoints.set(index, invested);
    }

    public int getInvestedSkillPoints(int skillNo) {
        int index = skillNo - 1;
        return investedSkillPoints.get(index);
    }

    public int getSkillPointsLeftToSpend() {
        int result = 0;

        for (int invested : investedSkillPoints) {
            result += invested;
        }

        return result;
    }

    public void updateSkillBar(RPGClass rpgClass) {
        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);
        for (int i = 0; i < investedSkillPoints.size(); i++) {
            Integer invested = investedSkillPoints.get(i);
            if (invested > 0) {
                Skill skill = skillSet.get(i);
                ItemStack icon = skill.getIcon(invested);
                player.getInventory().setItem(i, icon);
            } else {
                player.getInventory().setItem(i, OtherItems.getUnassignedSkill());
            }
        }
    }
}
