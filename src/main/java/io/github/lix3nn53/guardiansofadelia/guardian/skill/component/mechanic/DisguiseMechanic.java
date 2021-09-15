package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DisguiseMechanic extends MechanicComponent {

    private final DisguiseType disguiseType;
    private final boolean isAdult;
    private final List<Integer> ticks;

    public DisguiseMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("disguiseType")) {
            configLoadError("disguiseType");
        }

        if (!configurationSection.contains("isAdult")) {
            configLoadError("isAdult");
        }

        if (!configurationSection.contains("ticks")) {
            configLoadError("ticks");
        }

        this.disguiseType = DisguiseType.valueOf(configurationSection.getString("disguiseType"));
        this.isAdult = configurationSection.getBoolean("isAdult");
        this.ticks = configurationSection.getIntegerList("ticks");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
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
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.PURPLE_LIGHT + "Disguise duration: " + (ticks.get(skillLevel) / 20));
        } else if (skillLevel == ticks.size()) {
            additions.add(ChatPalette.PURPLE_LIGHT + "Disguise duration: " + (ticks.get(skillLevel - 1) / 20));
        } else {
            additions.add(ChatPalette.PURPLE_LIGHT + "Disguise duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
