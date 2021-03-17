package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class CompanionsOfCasterTargetMechanic extends ConditionComponent {

    private final Optional<String> mobCode;

    public CompanionsOfCasterTargetMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (configurationSection.contains("mobCode")) {
            this.mobCode = Optional.of(configurationSection.getString("mobCode"));
        } else {
            this.mobCode = Optional.empty();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;
        if (!(caster instanceof Player)) return false;
        Player owner = (Player) caster;

        List<LivingEntity> companions = mobCode.map(s -> PetManager.getCompanions(owner, s)).orElseGet(() -> PetManager.getCompanions(owner));

        if (companions == null) return false;

        LivingEntity target = targets.get(0);
        for (LivingEntity companion : companions) {
            if (!(companion instanceof Mob)) continue;
            Mob mob = (Mob) companion;

            mob.setTarget(target);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        additions.add("Caster's companions changes their target");

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
