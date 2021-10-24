package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FlyMechanic extends MechanicComponent {

    private final List<Integer> tickList;

    private final String multiplyDurationValue;

    public FlyMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("ticks")) {
            configLoadError("ticks");
        }

        this.tickList = configurationSection.getIntegerList("ticks");
        this.multiplyDurationValue = configurationSection.contains("multiplyDurationValue") ? configurationSection.getString("multiplyDurationValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int ticks = tickList.get(skillLevel - 1);
        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player player = (Player) target;
            player.setAllowFlight(true);
            player.setFlying(true);

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
                    player.setFlying(false);
                    player.setAllowFlight(false);
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), ticksCurrent);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.PURPLE_LIGHT + "Fly duration: " + (tickList.get(skillLevel) / 20));
        } else if (skillLevel == tickList.size()) {
            additions.add(ChatPalette.PURPLE_LIGHT + "Fly duration: " + (tickList.get(skillLevel - 1) / 20));
        } else {
            additions.add(ChatPalette.PURPLE_LIGHT + "Fly duration: " + (tickList.get(skillLevel - 1) / 20) + " -> " + (tickList.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
