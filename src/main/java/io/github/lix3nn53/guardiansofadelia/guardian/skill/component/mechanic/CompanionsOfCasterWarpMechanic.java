package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class CompanionsOfCasterWarpMechanic extends ConditionComponent {

    private final Optional<String> mobCode;

    public CompanionsOfCasterWarpMechanic(ConfigurationSection configurationSection) {
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

        LivingEntity target = targets.get(0);
        Location location = target.getLocation();
        for (LivingEntity companion : companions) {
            companion.teleport(location);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        additions.add("Caster's companions changes their target");

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
