package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.chat.ChatManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class HoloMessageMechanic extends MechanicComponent {

    private final String message;
    private final int durationTicks;

    public HoloMessageMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("message")) {
            configLoadError("message");
        }

        if (!configurationSection.contains("durationTicks")) {
            configLoadError("durationTicks");
        }

        String message = configurationSection.getString("message");
        int durationTicks = configurationSection.getInt("durationTicks");

        this.message = message;
        this.durationTicks = durationTicks;
    }


    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            ChatManager.chatHologramEntity(target, message, durationTicks, 0);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
