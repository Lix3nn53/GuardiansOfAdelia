package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanionDeathTrigger extends TriggerComponent {

    private final List<Integer> cooldowns;
    private final Optional<String> mobCode;
    LivingEntity caster;
    int skillLevel;
    int castCounter;

    public CompanionDeathTrigger(ConfigurationSection configurationSection) {
        if (configurationSection.contains("cooldowns")) {
            this.cooldowns = configurationSection.getIntegerList("cooldowns");
        } else {
            this.cooldowns = new ArrayList<>();
        }

        if (configurationSection.contains("mobCode")) {
            this.mobCode = Optional.of(configurationSection.getString("mobCode"));
        } else {
            this.mobCode = Optional.empty();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castCounter = castCounter;

        CompanionDeathTrigger rangedAttackTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningCompanionDeath((Player) target, rangedAttackTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public boolean callback(Player player, LivingEntity death) {
        boolean cast = false;
        if (mobCode.isPresent()) { // Compare mob code if mobCode is present
            String mobCodeGet = mobCode.get();
            BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
            boolean mythicMob = apiHelper.isMythicMob(death);
            if (mythicMob) {
                ActiveMob mythicMobInstance = apiHelper.getMythicMobInstance(death);
                String internalName = mythicMobInstance.getType().getInternalName();
                if (internalName.equals(mobCodeGet)) {
                    List<LivingEntity> targets = new ArrayList<>();
                    targets.add(death);
                    cast = executeChildren(caster, skillLevel, targets, castCounter);
                }
            }
        } else { // executeChildren without comparison if mobCode is not present
            List<LivingEntity> targets = new ArrayList<>();
            targets.add(death);
            cast = executeChildren(caster, skillLevel, targets, castCounter);
        }

        if (!cast) return false;

        CompanionDeathTrigger trigger = this;

        if (cooldowns.isEmpty()) { // Start listening again immediately if cooldowns is empty
            TriggerListener.startListeningCompanionDeath(player, trigger);
        } else { // Start listening again after the delay stored in cooldowns
            new BukkitRunnable() {
                @Override
                public void run() {
                    TriggerListener.startListeningCompanionDeath(player, trigger);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1) * 20);
        }

        return true;
    }
}
