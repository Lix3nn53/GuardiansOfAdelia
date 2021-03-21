package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RPGClassStats {
    private int totalExp = 0;
    private final List<Integer> investedSkillPoints = new ArrayList<>();

    public RPGClassStats(int totalExp, int one, int two, int three, int passive, int ultimate) {
        this.totalExp = totalExp;
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

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public void giveExp(int expToGive, Player player, String rpgClassStr) {
        int currentLevel = RPGCharacterExperienceManager.getLevel(this.totalExp);

        this.totalExp += expToGive;

        int newLevel = RPGCharacterExperienceManager.getLevel(this.totalExp);

        if (currentLevel < newLevel) { //level up
            sendLevelUpMessage(newLevel, player, rpgClassStr);
            player.sendTitle(ChatColor.GOLD + "Class Level Up!", ChatColor.YELLOW + "Your new level is " + ChatColor.GOLD + newLevel, 30, 80, 30);
        }
    }

    private void sendLevelUpMessage(int newLevel, Player player, String rpgClassStr) {
        MessageUtils.sendCenteredMessage(player, ChatColor.GRAY + "------------------------");
        MessageUtils.sendCenteredMessage(player, ChatColor.GOLD + "Level up!");
        MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "Congratulations, your new level is " + ChatColor.GOLD + newLevel + "");

        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        player.sendMessage("");
        MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "Stats Gained");
        final StringBuilder sb = new StringBuilder();
        for (AttributeType attributeType : AttributeType.values()) {
            int bonus = rpgClass.getAttributeBonusForLevel(attributeType, newLevel) - rpgClass.getAttributeBonusForLevel(attributeType, newLevel - 1);
            if (bonus > 0) {
                sb.append(ChatColor.RED + "+" + bonus + attributeType.getCustomName());
            }
        }
        MessageUtils.sendCenteredMessage(player, sb.toString());

        MessageUtils.sendCenteredMessage(player, ChatColor.GRAY + "------------------------");
    }
}
