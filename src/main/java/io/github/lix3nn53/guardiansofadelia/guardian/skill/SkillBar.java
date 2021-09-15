package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Each player character has unique skill-bar
 */
public class SkillBar {

    private final HashMap<String, Boolean> skillsOnCooldown = new HashMap<>();
    private final Player player;
    private final HashMap<Integer, Integer> investedSkillPoints = new HashMap<>();
    private final HashMap<Integer, Skill> skillSet;

    private int castCounter = 0;

    public SkillBar(Player player, int one, int two, int three, int passive, int ultimate, HashMap<Integer, Skill> skillSet, boolean remake) {
        this.player = player;
        this.skillSet = skillSet;

        player.getInventory().setHeldItemSlot(4);

        investedSkillPoints.put(0, one);
        investedSkillPoints.put(1, two);
        investedSkillPoints.put(2, three);
        investedSkillPoints.put(3, passive);
        investedSkillPoints.put(4, ultimate);

        if (remake) {
            remakeSkillBar();
        }

        //activate init triggers
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 4; i++) {
                    if (investedSkillPoints.get(i) <= 0) continue;

                    Skill skill = skillSet.get(i);
                    InitializeTrigger initializeTrigger = skill.getInitializeTrigger();
                    if (initializeTrigger != null) {
                        int nextSkillLevel = skill.getCurrentSkillLevel(getInvestedSkillPoints(i));
                        TriggerListener.onSkillUpgrade(player, initializeTrigger, i, nextSkillLevel, castCounter);
                        castCounter++;
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 40L);
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
                    TriggerListener.onSkillUpgrade(player, initializeTrigger, skillIndex, currentSkillLevel + 1, castCounter);
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
            TriggerListener.onSkillDowngrade(player, initializeTrigger, skillIndex, currentSkillLevel - 1, castCounter);
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
                TriggerListener.onSkillDowngrade(player, initializeTrigger, skillIndex, 0, castCounter);
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
        int points = player.getLevel();

        for (int invested : investedSkillPoints.values()) {
            points -= invested;
        }

        return points;
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

    public static double abilityHasteToMultiplier(double abilityHaste) {
        return 100 / (100 + abilityHaste);
    }

    public HashMap<Integer, Skill> getSkillSet() {
        return this.skillSet;
    }

    public HashMap<Integer, Integer> getInvestedSkillPoints() {
        return investedSkillPoints;
    }

    public int getCurrentSkillLevel(int skillIndex) {
        Skill skill = this.skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        return skill.getCurrentSkillLevel(invested);
    }

    public void reloadSkillSet(HashMap<Integer, Skill> skillSet) {
        this.skillSet.clear();
        this.skillSet.putAll(skillSet);
        remakeSkillBar();
    }

    public boolean castSkill(int slot) {
        if (StatusEffectManager.isSilenced(player)) {
            return false;
        }

        int skillIndex = slot;
        if (slot == 3) skillIndex = 4; //ultimate is one off

        if (skillsOnCooldown.containsKey("" + skillIndex)) {
            player.sendMessage(ChatPalette.RED + "Skill is on cooldown");
            return false;
        }

        Skill skill = this.skillSet.get(skillIndex);

        Integer invested = investedSkillPoints.get(skillIndex);
        int skillLevel = skill.getCurrentSkillLevel(invested);

        if (skillLevel <= 0) {
            player.sendMessage(ChatPalette.RED + "You haven't learned that skill");
            return false;
        }

        RPGCharacterStats rpgCharacterStats = null;
        int manaCost = skill.getManaCost(skillLevel);
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                int currentMana = rpgCharacterStats.getCurrentMana();
                if (currentMana < manaCost) {
                    player.sendMessage(ChatPalette.RED + "Not enough mana");
                    return false;
                }
            }
        }

        if (rpgCharacterStats == null) {
            player.sendMessage("rpgCharacterStats null");
            return false;
        }

        boolean cast = skill.cast(player, skillLevel, new ArrayList<>(), castCounter, skillIndex);//cast ends when this returns

        if (!cast) {
            player.sendMessage(ChatPalette.RED + "Failed to cast skill");
            return false; //dont go on cooldown and consume mana if cast failed
        }

        castCounter++;
        TriggerListener.onPlayerSkillCast(player);

        // mana cost
        rpgCharacterStats.consumeMana(manaCost);

        double abilityHaste = rpgCharacterStats.getTotalAbilityHaste();

        int cooldownInTicks = (int) (((skill.getCooldown(skillLevel) * 20) * abilityHasteToMultiplier(abilityHaste)) + 0.5); // Ability haste formula from League of Legends
        PlayerInventory inventory = player.getInventory();

        skillsOnCooldown.put("" + skillIndex, true);

        final int finalSkillIndex = skillIndex;
        new BukkitRunnable() {

            int ticksPassed = 0;

            @Override
            public void run() {
                if (ticksPassed >= cooldownInTicks) {
                    cancel();
                    skillsOnCooldown.remove("" + finalSkillIndex);
                } else {
                    int cooldownLeft = cooldownInTicks - ticksPassed;
                    int secondsLeft = cooldownLeft / 20;
                    double modulus = cooldownLeft % 20;

                    if (modulus > 0) {
                        secondsLeft++;
                    }

                    ItemStack item = inventory.getItem(slot);
                    int currentAmount = item.getAmount();
                    if (currentAmount != secondsLeft) {
                        if (InventoryUtils.isAirOrNull(item)) {
                            remakeSkillBarIcon(finalSkillIndex);
                            item = inventory.getItem(slot);
                        }
                        item.setAmount(secondsLeft);
                    }
                }
                ticksPassed++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 1L);

        return true;
    }
}
