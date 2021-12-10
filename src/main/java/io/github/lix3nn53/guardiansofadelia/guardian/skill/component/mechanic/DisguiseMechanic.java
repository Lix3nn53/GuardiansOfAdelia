package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
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
    private final List<Integer> tickList;

    private final String multiplyDurationValue;

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
        this.tickList = configurationSection.getIntegerList("ticks");
        this.multiplyDurationValue = configurationSection.contains("multiplyDurationValue") ? configurationSection.getString("multiplyDurationValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int ticks = tickList.get(skillLevel - 1);
        for (LivingEntity target : targets) {
            MobDisguise disguise = new MobDisguise(disguiseType, isAdult);
            disguise = disguise.setReplaceSounds(true);
            DisguiseAPI.disguiseToAll(target, disguise);

            int ticksCurrent = ticks;

            if (multiplyDurationValue != null) {
                int value = SkillDataManager.getValue(target, multiplyDurationValue);
                if (value > 0) {
                    ticksCurrent *= value;
                }
            }

            new BukkitRunnable() {

                @Override
                public void run() {
                    DisguiseAPI.undisguiseToAll(target);
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), ticksCurrent);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.PURPLE_LIGHT + "Disguise duration: " + (tickList.get(skillLevel) / 20));
        } else if (skillLevel == tickList.size()) {
            additions.add(ChatPalette.PURPLE_LIGHT + "Disguise duration: " + (tickList.get(skillLevel - 1) / 20));
        } else {
            additions.add(ChatPalette.PURPLE_LIGHT + "Disguise duration: " + (tickList.get(skillLevel - 1) / 20) + " -> " + (tickList.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
