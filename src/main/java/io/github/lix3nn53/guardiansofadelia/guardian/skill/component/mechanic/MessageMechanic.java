package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageMechanic extends MechanicComponent {

    private final String message;

    public MessageMechanic(String message) {
        this.message = message;
    }

    public MessageMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("message")) {
            configLoadError("message");
        }

        this.message = configurationSection.getString("message");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean messageSent = false;
        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                messageSent = true;
                ent.sendMessage(message);
            }
        }

        return messageSent;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
