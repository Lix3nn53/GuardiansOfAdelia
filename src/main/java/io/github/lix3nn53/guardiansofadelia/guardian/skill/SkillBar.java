package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Each player character has unique skill-bar
 */
public class SkillBar {

    private static HashMap<Integer, Boolean> skillsOnCooldown = new HashMap<>();
    private final Player player;
    private final RPGClass rpgClass;
    private final List<Integer> investedSkillPoints = new ArrayList<>();

    public SkillBar(Player player, RPGClass rpgClass, int one, int two, int three, int passive, int ultimate) {
        this.player = player;
        this.rpgClass = rpgClass;

        player.getInventory().setHeldItemSlot(4);

        investedSkillPoints.add(one);
        investedSkillPoints.add(two);
        investedSkillPoints.add(three);
        investedSkillPoints.add(passive);
        investedSkillPoints.add(ultimate);

        remakeSkillBar();
    }

    /**
     * @param skillIndex 0,1,2 normal skills, 3 passive, 4 ultimate
     */
    public boolean upgradeSkill(int skillIndex) {
        Skill skill = SkillList.getSkill(rpgClass, skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int currentSkillLevel = skill.getCurrentSkillLevel(invested);

        if (currentSkillLevel >= skill.getMaxSkillLevel()) {
            return false;
        }

        int reqSkillPoints = skill.getReqSkillPoints(currentSkillLevel);

        if (getSkillPointsLeftToSpend() >= reqSkillPoints) {
            investedSkillPoints.set(skillIndex, invested + reqSkillPoints);
            remakeSkillBarIcon(skillIndex);
            return true;
        }

        return false;
    }

    /**
     * @param skillIndex 0,1,2 normal skills, 3 passive, 4 ultimate
     */
    public boolean downgradeSkill(int skillIndex) {
        Skill skill = SkillList.getSkill(rpgClass, skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int currentSkillLevel = skill.getCurrentSkillLevel(invested);

        if (currentSkillLevel <= 0) {
            return false;
        }

        int reqSkillPoints = skill.getReqSkillPoints(currentSkillLevel - 1);

        investedSkillPoints.set(skillIndex, invested - reqSkillPoints);
        remakeSkillBarIcon(skillIndex);
        return true;
    }

    public int getInvestedSkillPoints(int skillIndex) {
        return investedSkillPoints.get(skillIndex);
    }

    public int getSkillPointsLeftToSpend() {
        int result = player.getLevel();

        for (int invested : investedSkillPoints) {
            result -= invested;
        }

        return result;
    }

    public void remakeSkillBar() {
        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);
        for (int i = 0; i < investedSkillPoints.size(); i++) {
            int slot = i;

            if (i == 3) { //don't place passive skill on hot-bar
                continue;
            } else if (i > 3) {
                slot--;
            }

            Integer invested = investedSkillPoints.get(i);
            if (invested > 0) {
                Skill skill = skillSet.get(i);
                ItemStack icon = skill.getIcon(player.getLevel(), getSkillPointsLeftToSpend(), invested);
                player.getInventory().setItem(slot, icon);
            } else {
                player.getInventory().setItem(slot, OtherItems.getUnassignedSkill());
            }
        }
    }

    public void remakeSkillBarIcon(int skillIndex) {
        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);
        int slot = skillIndex;

        if (skillIndex == 3) { //don't place passive skill on hot-bar
            return;
        } else if (skillIndex > 3) {
            slot--;
        }

        Integer invested = investedSkillPoints.get(skillIndex);
        if (invested > 0) {
            Skill skill = skillSet.get(skillIndex);
            ItemStack icon = skill.getIcon(player.getLevel(), getSkillPointsLeftToSpend(), invested);
            player.getInventory().setItem(slot, icon);
        } else {
            player.getInventory().setItem(slot, OtherItems.getUnassignedSkill());
        }
    }

    public boolean castSkill(int skillIndex) {
        if (StatusEffectManager.isSilenced(player)) {
            //TODO silence info
            return false;
        }

        if (skillsOnCooldown.containsKey(skillIndex)) {
            //TODO playSingleParticle sound to player on cast fail
            return false;
        }

        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);

        Skill skill = skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int skillLevel = skill.getCurrentSkillLevel(invested);

        if (skillLevel <= 0) {
            return false;
        }

        int nextCastNumber = SkillDataManager.getNextCastNumber(player);
        String castKey = SkillDataManager.getCastKey(player, nextCastNumber);
        skill.cast(player, skillLevel, null, castKey); //cast ends when this returns

        int cooldown = skill.getCooldown(skillLevel);
        PlayerInventory inventory = player.getInventory();

        skillsOnCooldown.put(skillIndex, true);

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
                    ItemStack item = inventory.getItem(skillIndex);
                    if (InventoryUtils.isAirOrNull(item)) {
                        remakeSkillBarIcon(skillIndex);
                        item = inventory.getItem(skillIndex);
                    }
                    item.setAmount(cooldown - seconds);
                }
                seconds++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 20L);

        return true;
    }
}
