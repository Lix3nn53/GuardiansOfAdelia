package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NormalAttackTrigger extends TriggerComponent {

    private final List<Integer> cooldowns;
    private final boolean melee;
    private final boolean projectile;
    LivingEntity caster;
    int skillLevel;
    int castCounter;

    public NormalAttackTrigger(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (configurationSection.contains("cooldowns")) {
            this.cooldowns = configurationSection.getIntegerList("cooldowns");
        } else {
            this.cooldowns = new ArrayList<>();
        }

        this.melee = configurationSection.contains("melee") && configurationSection.getBoolean("melee");
        this.projectile = configurationSection.contains("projectile") && configurationSection.getBoolean("projectile");
    }

    public LivingEntity getCaster() {
        return caster;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        this.skillIndex = skillIndex;
        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castCounter = castCounter;

        NormalAttackTrigger normalAttackTrigger = this;

        for (LivingEntity target : targets) {
            if (target instanceof Player) {
                TriggerListener.add((Player) target, normalAttackTrigger);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public boolean callback(Player attacker, LivingEntity target, boolean isProjectile) {
        if (CommandAdmin.DEBUG_MODE) attacker.sendMessage("NormalAttackTrigger callback, skillIndex: " + skillIndex);
        if (!(this.melee && this.projectile)) {
            if (this.melee && isProjectile) {
                return false;
            } else if (this.projectile && !isProjectile) {
                return false;
            }
        }

        ArrayList<LivingEntity> targets = new ArrayList<>();
        targets.add(target);

        return executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public List<Integer> getCooldowns() {
        return cooldowns;
    }
}
