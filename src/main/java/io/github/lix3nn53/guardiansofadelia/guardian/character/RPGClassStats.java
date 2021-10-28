package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
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

    public void giveExpNoMessage(int expToGive) {
        int currentLevel = RPGClassExperienceManager.getLevel(this.totalExp);

        if (currentLevel >= 20) { //last level is 20
            return;
        }

        this.totalExp += expToGive;
    }

    public void giveExp(Player player, int expToGive) {
        int currentLevel = RPGClassExperienceManager.getLevel(this.totalExp);

        if (currentLevel >= 20) { //last level is 20
            return;
        }

        this.totalExp += expToGive;

        int newLevel = RPGClassExperienceManager.getLevel(this.totalExp);

        if (currentLevel < newLevel) { //level up
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    String rpgClassStr = activeCharacter.getRpgClassStr();

                    RPGClass aClass = RPGClassManager.getClass(rpgClassStr);

                    String classString = aClass.getClassString();

                    MessageUtils.sendCenteredMessage(player, classString + ChatPalette.GOLD + " Level up!");
                    MessageUtils.sendCenteredMessage(player, ChatPalette.YELLOW + "Congratulations, your new " + classString +
                            ChatPalette.YELLOW + " level is " + ChatPalette.GOLD + newLevel);
                    CustomSound customSound = GoaSound.LEVEL_UP.getCustomSound();
                    customSound.play(player.getLocation());
                }
            }
        }
    }

    public int getTotalExperience() {
        return totalExp;
    }
}
