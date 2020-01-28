package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DisguiseMechanic extends MechanicComponent {

    private final DisguiseType disguiseType;
    private final boolean isAdult;
    private final List<Integer> ticks;

    /**
     * @param disguiseType Must be mob type
     * @param isAdult
     * @param ticks
     */
    public DisguiseMechanic(DisguiseType disguiseType, boolean isAdult, List<Integer> ticks) {
        this.disguiseType = disguiseType;
        this.isAdult = isAdult;
        this.ticks = ticks;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            MobDisguise disguise = new MobDisguise(disguiseType, isAdult);
            disguise = disguise.setReplaceSounds(true);
            DisguiseAPI.disguiseToAll(ent, disguise);
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                for (LivingEntity ent : targets) {
                    DisguiseAPI.undisguiseToAll(ent);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), ticks.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.LIGHT_PURPLE + "Disguise duration: " + (ticks.get(skillLevel) / 20));
        } else if (skillLevel == ticks.size()) {
            additions.add(ChatColor.LIGHT_PURPLE + "Disguise duration: " + (ticks.get(skillLevel - 1) / 20));
        } else {
            additions.add(ChatColor.LIGHT_PURPLE + "Disguise duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
