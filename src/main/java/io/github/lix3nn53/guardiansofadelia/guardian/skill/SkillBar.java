package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassStats;
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
import java.util.UUID;

/**
 * Each player character has unique skill-bar
 */
public class SkillBar {

    private final HashMap<String, Boolean> skillsOnCooldown = new HashMap<>();
    private final Player player;
    private final HashMap<Integer, Integer> investedSkillPoints = new HashMap<>();
    private final HashMap<Integer, Skill> skillSet;

    private int castCounter = 0;

    public SkillBar(Player player, int one, int two, int three, int passive, int ultimate, HashMap<Integer, Skill> skillSet) {
        this.player = player;
        this.skillSet = skillSet;

        player.getInventory().setHeldItemSlot(4);

        investedSkillPoints.put(0, one);
        investedSkillPoints.put(1, two);
        investedSkillPoints.put(2, three);
        investedSkillPoints.put(3, passive);
        investedSkillPoints.put(4, ultimate);

        remakeSkillBar();

        //activate init triggers
        for (int i = 0; i <= 4; i++) {
            if (investedSkillPoints.get(i) <= 0) continue;

            Skill skill = this.skillSet.get(i);
            InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
            if (initializeTrigger != null) {
                int nextSkillLevel = skill.getCurrentSkillLevel(getInvestedSkillPoints(i));
                TriggerListener.onSkillUpgrade(player, initializeTrigger, nextSkillLevel, castCounter);
                castCounter++;
            }
        }
    }

    /**
     * @param skillIndex 0,1,2 normal skills, 3 passive, 4 ultimate
     */
    public boolean upgradeSkill(int skillIndex, RPGClassStats rpgClassStats) {
        if (!this.skillSet.containsKey(skillIndex)) return false;

        Skill skill = this.skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int currentSkillLevel = skill.getCurrentSkillLevel(invested);

        if (currentSkillLevel >= skill.getMaxSkillLevel()) {
            return false;
        }

        int reqSkillPoints = skill.getReqSkillPoints(currentSkillLevel);

        if (getSkillPointsLeftToSpend() >= reqSkillPoints) {
            int reqPlayerLevel = skill.getReqPlayerLevel(currentSkillLevel);
            if (player.getLevel() >= reqPlayerLevel) {
                InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
                if (initializeTrigger != null) {
                    TriggerListener.onSkillUpgrade(player, initializeTrigger, currentSkillLevel + 1, castCounter);
                    castCounter++;
                }
                int newInvested = invested + reqSkillPoints;
                investedSkillPoints.put(skillIndex, newInvested);
                remakeSkillBarIcon(skillIndex);

                rpgClassStats.setInvestedSkillPoint(skillIndex, newInvested);
                return true;
            }
        }

        return false;
    }

    /**
     * @param skillIndex 0,1,2 normal skills, 3 passive, 4 ultimate
     */
    public boolean downgradeSkill(int skillIndex) {
        if (!this.skillSet.containsKey(skillIndex)) return false;

        Skill skill = this.skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int currentSkillLevel = skill.getCurrentSkillLevel(invested);

        if (currentSkillLevel <= 0) return false;

        InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
        if (initializeTrigger != null) {
            TriggerListener.onSkillDowngrade(player, initializeTrigger, currentSkillLevel - 1, castCounter);
        }

        int reqSkillPoints = skill.getReqSkillPoints(currentSkillLevel - 1);

        investedSkillPoints.put(skillIndex, invested - reqSkillPoints);
        remakeSkillBarIcon(skillIndex);
        return true;
    }

    public boolean resetSkillPoints() {
        investedSkillPoints.clear();
        investedSkillPoints.put(0, 0);
        investedSkillPoints.put(1, 0);
        investedSkillPoints.put(2, 0);
        investedSkillPoints.put(3, 0);
        investedSkillPoints.put(4, 0);

        for (int skillIndex = 0; skillIndex < 5; skillIndex++) {
            Skill skill = this.skillSet.get(skillIndex);

            InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
            if (initializeTrigger != null) {
                TriggerListener.onSkillDowngrade(player, initializeTrigger, 0, castCounter);
                castCounter++;
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

        for (int invested : investedSkillPoints.values()) {
            result -= invested;
        }

        return result;
    }

    public void remakeSkillBar() {
        for (int i = 0; i < investedSkillPoints.size(); i++) {
            int slot = i;

            if (i == 3) { //don't place passive skill on hot-bar
                continue;
            } else if (i > 3) {
                slot--;
            }

            Integer invested = investedSkillPoints.get(i);
            if (invested > 0) {
                Skill skill = this.skillSet.get(i);
                ItemStack icon = skill.getIcon(player.getLevel(), getSkillPointsLeftToSpend(), invested);
                player.getInventory().setItem(slot, icon);
            } else {
                player.getInventory().setItem(slot, OtherItems.getUnassignedSkill());
            }
        }
    }

    public void remakeSkillBarIcon(int skillIndex) {
        int slot = skillIndex;

        if (skillIndex == 3) { //don't place passive skill on hot-bar
            return;
        } else if (skillIndex > 3) {
            slot--;
        }

        Integer invested = investedSkillPoints.get(skillIndex);
        if (invested > 0) {
            Skill skill = this.skillSet.get(skillIndex);
            ItemStack icon = skill.getIcon(player.getLevel(), getSkillPointsLeftToSpend(), invested);
            player.getInventory().setItem(slot, icon);
        } else {
            player.getInventory().setItem(slot, OtherItems.getUnassignedSkill());
        }
    }

    public boolean castSkill(int slot) {
        if (StatusEffectManager.isSilenced(player)) {
            player.sendTitle(ChatColor.LIGHT_PURPLE + "", ChatColor.LIGHT_PURPLE + "Silenced..", 0, 20, 0);
            return false;
        }

        int skillIndex = slot;
        if (slot == 3) skillIndex = 4; //ultimate is one off

        if (skillsOnCooldown.containsKey("" + skillIndex)) {
            player.sendMessage(ChatColor.RED + "Skill is on cooldown");
            return false;
        }

        Skill skill = this.skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int skillLevel = skill.getCurrentSkillLevel(invested);

        if (skillLevel <= 0) {
            player.sendMessage(ChatColor.RED + "You haven't learned that skill");
            return false;
        }

        RPGCharacterStats rpgCharacterStats = null;
        int manaCost = skill.getManaCost(skillLevel);
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                int currentMana = rpgCharacterStats.getCurrentMana();
                if (currentMana < manaCost) {
                    player.sendMessage(ChatColor.RED + "Not enough mana");
                    return false;
                }
            }
        }

        boolean cast = skill.cast(player, skillLevel, new ArrayList<>(), castCounter);//cast ends when this returns

        if (!cast) {
            player.sendMessage(ChatColor.RED + "Failed to cast skill");
            return false; //dont go on cooldown and consume mana if cast failed
        }

        castCounter++;

        if (rpgCharacterStats != null) {
            rpgCharacterStats.consumeMana(manaCost);
        }

        int cooldown = skill.getCooldown(skillLevel);
        PlayerInventory inventory = player.getInventory();

        skillsOnCooldown.put("" + skillIndex, true);

        final int finalSkillIndex = skillIndex;
        new BukkitRunnable() {

            int seconds = 0;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    skillsOnCooldown.remove("" + finalSkillIndex);
                    cancel();
                    return;
                }

                if (seconds >= cooldown) {
                    cancel();
                    skillsOnCooldown.remove("" + finalSkillIndex);
                } else {
                    ItemStack item = inventory.getItem(slot);
                    if (InventoryUtils.isAirOrNull(item)) {
                        remakeSkillBarIcon(finalSkillIndex);
                        item = inventory.getItem(slot);
                    }
                    item.setAmount(cooldown - seconds);
                }
                seconds++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 20L);

        return true;
    }

    public HashMap<Integer, Skill> getSkillSet() {
        return this.skillSet;
    }
}
