package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SavedCompanionsTarget extends TargetComponent {

    private final Optional<String> mobCode;

    public SavedCompanionsTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (configurationSection.contains("mobCode")) {
            this.mobCode = Optional.of(configurationSection.getString("mobCode"));
        } else {
            this.mobCode = Optional.empty();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        List<LivingEntity> companions = new ArrayList<>();

        boolean present = mobCode.isPresent();
        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player owner = (Player) target;

            List<LivingEntity> companionsOfTarget;
            if (present) {
                companionsOfTarget = PetManager.getCompanions(owner, mobCode.get());
            } else {
                companionsOfTarget = PetManager.getCompanions(owner);
            }
            if (companionsOfTarget == null) continue;
            companions.addAll(companionsOfTarget);
        }

        if (companions.isEmpty()) return false;

        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                Collections.reverse(companions);
                for (LivingEntity single : companions) {
                    targets.add(0, single);
                }
            } else {
                targets.addAll(companions);
            }
        } else {
            targets = companions;
        }

        return executeChildren(caster, skillLevel, targets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
