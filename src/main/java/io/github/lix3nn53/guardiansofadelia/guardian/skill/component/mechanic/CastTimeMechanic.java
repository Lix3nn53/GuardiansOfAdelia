package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.DamageIndicator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * It's like delay but with indicator
 */
public class CastTimeMechanic extends MechanicComponent {

    private static final int MIN_DELAY = 2;
    private final int DELAY;
    private final String TEXT;

    public CastTimeMechanic(ConfigurationSection configurationSection) {
        super(true);

        if (!configurationSection.contains("delay")) {
            configLoadError("delay");
        }

        if (!configurationSection.contains("text")) {
            configLoadError("text");
        }

        this.DELAY = configurationSection.getInt("delay");

        this.TEXT = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("text"));
    }

    /**
     * passes created hologram to children
     *
     * @param caster
     * @param skillLevel
     * @param targets
     * @param skillIndex
     * @return
     */
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int holoTime = Math.max(DELAY, 50);

        for (LivingEntity target : targets) {
            if (DELAY - MIN_DELAY > 0) {
                StatusEffectManager.addStatus(target, StatusEffectType.STUN, DELAY - 2);
            }

            double height = target.getHeight();
            Location location = target.getLocation().add(new Vector(0, height + 0.2, 0));

            List<Entity> nearbyEntities = target.getNearbyEntities(24, 24, 24);
            if (target instanceof Player) nearbyEntities.add(target);
            for (Entity nearby : nearbyEntities) {
                if (!(nearby instanceof Player)) continue;

                DamageIndicator.showPlayer((Player) nearby, TEXT, location, holoTime);
            }
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!caster.isDead()) {
                    executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), DELAY);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (DELAY - MIN_DELAY > 0) {
            additions.add(ChatPalette.GOLD + "Cast time: " + ChatPalette.YELLOW + DELAY + " ticks");
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
