package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class DisguiseRemoveMechanic extends MechanicComponent {

    public DisguiseRemoveMechanic(boolean addLore) {
        super(addLore);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            DisguiseAPI.undisguiseToAll(target);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
