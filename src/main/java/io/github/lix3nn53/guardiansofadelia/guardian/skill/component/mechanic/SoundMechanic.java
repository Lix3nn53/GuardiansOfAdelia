package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class SoundMechanic extends MechanicComponent {

    private final GoaSound goaSound;

    public SoundMechanic(GoaSound goaSound) {
        this.goaSound = goaSound;
    }

    public SoundMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("goaSound")) {
            configLoadError("goaSound");
        }

        String goaSound = configurationSection.getString("goaSound");

        this.goaSound = GoaSound.valueOf(goaSound);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            goaSound.getCustomSound().play(ent.getLocation());
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
