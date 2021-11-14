package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class SoundMechanic extends MechanicComponent {

    private final CustomSound goaSound;

    public SoundMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("sound")) {
            configLoadError("sound");
        }
        if (!configurationSection.contains("volume")) {
            configLoadError("volume");
        }
        if (!configurationSection.contains("pitch")) {
            configLoadError("pitch");
        }

        String soundString = configurationSection.getString("sound").toLowerCase();
        float volume = (float) (float) configurationSection.getDouble("volume");
        float pitch = (float) (float) configurationSection.getDouble("pitch");

        this.goaSound = new CustomSound(soundString, volume, pitch);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            goaSound.play(ent.getLocation());
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
