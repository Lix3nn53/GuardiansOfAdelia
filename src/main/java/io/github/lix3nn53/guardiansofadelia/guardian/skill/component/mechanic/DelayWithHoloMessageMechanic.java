package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.chat.ChatManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DelayWithHoloMessageMechanic extends MechanicComponent {

    private final long ticks;
    private final String message;
    private final double offsetY;

    public DelayWithHoloMessageMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("ticks")) {
            configLoadError("ticks");
        }

        if (!configurationSection.contains("message")) {
            configLoadError("message");
        }

        this.ticks = configurationSection.getLong("ticks");
        this.message = configurationSection.getString("message");

        if (configurationSection.contains("offsetY")) {
            this.offsetY = configurationSection.getDouble("offsetY");
        } else {
            this.offsetY = 0;
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            String a = ChatColor.translateAlternateColorCodes('&', message);
            ChatManager.chatHologramEntityWithCountDown(target, a, (int) ticks, offsetY);
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target.isDead()) continue;
                    List<LivingEntity> one = new ArrayList<>();

                    one.add(target);
                    executeChildren(caster, skillLevel, one, castCounter);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), ticks);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
