package io.github.lix3nn53.guardiansofadelia.guardian.character;

import java.util.ArrayList;
import java.util.List;

public class RPGClassStats {
    private final List<Integer> investedSkillPoints = new ArrayList<>();

    public RPGClassStats(int one, int two, int three, int passive, int ultimate) {
        investedSkillPoints.add(one);
        investedSkillPoints.add(two);
        investedSkillPoints.add(three);
        investedSkillPoints.add(passive);
        investedSkillPoints.add(ultimate);
    }

    public RPGClassStats() {
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
    }

    public int getOne() {
        return investedSkillPoints.get(0);
    }

    public int getTwo() {
        return investedSkillPoints.get(1);
    }

    public int getThree() {
        return investedSkillPoints.get(2);
    }

    public int getPassive() {
        return investedSkillPoints.get(3);
    }

    public int getUltimate() {
        return investedSkillPoints.get(4);
    }

    public void setInvestedSkillPoint(int index, int points) {
        if (index > 4) return;

        investedSkillPoints.set(index, points);
    }

}
