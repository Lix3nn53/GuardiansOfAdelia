package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class DisguiseMechanic extends MechanicComponent {

    private final DisguiseType disguiseType;
    private final boolean isAdult;

    /**
     * @param disguiseType Must be mob type
     * @param isAdult
     */
    public DisguiseMechanic(DisguiseType disguiseType, boolean isAdult) {
        this.disguiseType = disguiseType;
        this.isAdult = isAdult;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            MobDisguise disguise = new MobDisguise(disguiseType, isAdult);
            disguise = disguise.setReplaceSounds(true);
            DisguiseAPI.disguiseToAll(ent, disguise);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
