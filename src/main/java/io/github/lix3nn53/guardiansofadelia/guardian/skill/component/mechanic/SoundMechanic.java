package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SoundMechanic extends MechanicComponent {

    private final GoaSound goaSound;

    public SoundMechanic(GoaSound goaSound) {
        this.goaSound = goaSound;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            goaSound.getCustomSound().play(ent.getLocation());
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
