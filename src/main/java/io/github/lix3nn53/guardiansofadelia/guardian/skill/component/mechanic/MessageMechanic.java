package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageMechanic extends MechanicComponent {

    private final String message;

    public MessageMechanic(String message) {
        this.message = message;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
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
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
