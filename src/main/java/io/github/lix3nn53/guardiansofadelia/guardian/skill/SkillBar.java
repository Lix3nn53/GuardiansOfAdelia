package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.ChatColor;
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

        //activate init triggers
        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);
        for (int i = 0; i <= 4; i++) {
            if (investedSkillPoints.get(i) <= 0) continue;

            Skill skill = skillSet.get(i);
            InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
            if (initializeTrigger != null) {
                int nextSkillLevel = skill.getCurrentSkillLevel(getInvestedSkillPoints(i));
                TriggerListener.onSkillUpgrade(player, initializeTrigger, nextSkillLevel);
            }
        }
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
            InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
            if (initializeTrigger != null) {
                TriggerListener.onSkillUpgrade(player, initializeTrigger, currentSkillLevel + 1);
            }
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

        if (currentSkillLevel <= 0) return false;

        InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
        if (initializeTrigger != null) {
            TriggerListener.onSkillDowngrade(player, initializeTrigger, currentSkillLevel - 1);
        }

        int reqSkillPoints = skill.getReqSkillPoints(currentSkillLevel - 1);

        investedSkillPoints.set(skillIndex, invested - reqSkillPoints);
        remakeSkillBarIcon(skillIndex);
        return true;
    }

    public boolean resetSkillPoints() {
        investedSkillPoints.clear();
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);

        for (int skillIndex = 0; skillIndex < 5; skillIndex++) {
            Skill skill = SkillList.getSkill(rpgClass, skillIndex);

            InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
            if (initializeTrigger != null) {
                TriggerListener.onSkillDowngrade(player, initializeTrigger, 0);
            }

            remakeSkillBarIcon(skillIndex);
        }
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
            //TODO test silence info
            player.sendTitle(ChatColor.LIGHT_PURPLE + "", ChatColor.LIGHT_PURPLE + "Silenced..", 0, 20, 0);
            return false;
        }

        if (skillsOnCooldown.containsKey(skillIndex)) {
            return false;
        }

        int slot = skillIndex;

        if (skillIndex > 3) {
            slot--;
        }

        List<Skill> skillSet = SkillList.getSkillSet(rpgClass);

        Skill skill = skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int skillLevel = skill.getCurrentSkillLevel(invested);

        if (skillLevel <= 0) {
            return false;
        }

        boolean cast = skill.cast(player, skillLevel, null);//cast ends when this returns

        if (!cast) return false; //dont go on cooldown if cast failed

        int cooldown = skill.getCooldown(skillLevel);
        PlayerInventory inventory = player.getInventory();

        skillsOnCooldown.put(skillIndex, true);

        int finalSlot = slot;
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
                    ItemStack item = inventory.getItem(finalSlot);
                    if (InventoryUtils.isAirOrNull(item)) {
                        remakeSkillBarIcon(skillIndex);
                        item = inventory.getItem(finalSlot);
                    }
                    item.setAmount(cooldown - seconds);
                }
                seconds++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 20L);

        return true;
    }
}
