package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RPGClassStats {
    private final List<Integer> investedSkillPoints = new ArrayList<>();
    private int totalExp;

    public RPGClassStats(int one, int two, int three, int passive, int ultimate, int totalExp) {
        investedSkillPoints.add(one);
        investedSkillPoints.add(two);
        investedSkillPoints.add(three);
        investedSkillPoints.add(passive);
        investedSkillPoints.add(ultimate);
        this.totalExp = totalExp;
    }

    public RPGClassStats() {
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        this.totalExp = 0;
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

    public void giveExp(Player player, int expToGive) {
        int currentLevel = RPGClassExperienceManager.getLevel(this.totalExp);

        if (currentLevel >= 20) { //last level is 20
            return;
        }

        this.totalExp += expToGive;

        int newLevel = RPGClassExperienceManager.getLevel(this.totalExp);

        if (currentLevel < newLevel) { //level up
            player.sendTitle(ChatPalette.PURPLE + "Class Level Up!", ChatPalette.YELLOW + "Your new level is " + ChatPalette.GOLD + newLevel, 30, 80, 30);
        }
    }

    public int getTotalExperience() {
        return totalExp;
    }
}
