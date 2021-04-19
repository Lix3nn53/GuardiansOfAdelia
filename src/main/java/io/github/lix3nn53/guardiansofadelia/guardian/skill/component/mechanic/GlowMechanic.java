package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GlowMechanic extends MechanicComponent {

    private final GlowAPI.Color color;
    private final List<Integer> ticks;

    public GlowMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("color")) {
            configLoadError("color");
        }

        this.color = GlowAPI.Color.valueOf(configurationSection.getString("color"));

        if (configurationSection.contains("ticks")) {
            this.ticks = configurationSection.getIntegerList("ticks");
        } else {
            this.ticks = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (LivingEntity target : targets) {
            GlowAPI.setGlowing(target, color, onlinePlayers);
        }

        if (!ticks.isEmpty()) {
            new BukkitRunnable() { //remove glow

                @Override
                public void run() {
                    for (LivingEntity target : targets) {
                        GlowAPI.setGlowing(target, false, onlinePlayers);
                    }
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks.get(skillLevel - 1));
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
