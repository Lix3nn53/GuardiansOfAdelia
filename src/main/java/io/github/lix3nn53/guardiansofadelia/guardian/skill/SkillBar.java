package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.list.SkillList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SkillBar {

    private final Player player;
    private final RPGClass rpgClass;

    private final List<Integer> investedSkillPoints = new ArrayList<>();

    private final List<Integer> skillsOnCooldown = new ArrayList<>();

    public SkillBar(Player player, RPGClass rpgClass, int one, int two, int three, int passive, int ultimate) {
        this.player = player;
        this.rpgClass = rpgClass;

        investedSkillPoints.add(one);
        investedSkillPoints.add(two);
        investedSkillPoints.add(three);
        investedSkillPoints.add(passive);
        investedSkillPoints.add(ultimate);

        updateSkillBar();
    }

    /**
     * @param skillIndex 0,1,2 normal skills, 3 passive, 4 ultimate
     * @param points  to invest in skill
     */
    public void investSkillPoints(int skillIndex, int points) {
        int invested = investedSkillPoints.get(skillIndex);
        invested += points;
        investedSkillPoints.set(skillIndex, invested);

        updateSkillBar();
    }

    public int getInvestedSkillPoints(int skillIndex) {
        return investedSkillPoints.get(skillIndex);
    }

    public int getSkillPointsLeftToSpend() {
        int result = 0;

        for (int invested : investedSkillPoints) {
            result += invested;
        }

        return result;
    }

    public void updateSkillBar() {
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

    public boolean castSkill(int skillIndex) {
        if (skillsOnCooldown.contains(skillIndex)) {
            //TODO play sound to player on cast fail
            return false;
        }

        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);

        Skill skill = skillSet.get(skillIndex);

        skill.cast(player, investedSkillPoints.get(skillIndex), null);

        int cooldown = skill.getCooldown();
        PlayerInventory inventory = player.getInventory();

        skillsOnCooldown.add(skillIndex);

        new BukkitRunnable() {

            int seconds = 0;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                if (seconds >= cooldown) {
                    cancel();
                    skillsOnCooldown.remove(skillIndex);
                } else {
                    inventory.getItem(skillIndex).setAmount(cooldown - seconds);
                }
                seconds++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 20L * cooldown);

        return true;
    }
}
