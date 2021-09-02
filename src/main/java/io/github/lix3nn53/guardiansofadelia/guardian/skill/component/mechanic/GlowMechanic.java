package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class GlowMechanic extends MechanicComponent {

    private final ChatColor color;
    private final List<Integer> ticks;

    public GlowMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("color")) {
            configLoadError("color");
        }

        this.color = ChatColor.valueOf(configurationSection.getString("color"));

        if (configurationSection.contains("ticks")) {
            this.ticks = configurationSection.getIntegerList("ticks");
        } else {
            this.ticks = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        String teamName = color.name() + "GlowMechanic";

        Team team = board.getTeam(teamName) != null ? board.getTeam(teamName) : board.registerNewTeam(teamName);
        team.setColor(color);

        for (LivingEntity target : targets) {
            target.setGlowing(true);

            if (target instanceof Player) {
                team.addEntry(target.getName());
            } else {
                team.addEntry(target.getUniqueId().toString());
            }
        }

        if (!ticks.isEmpty()) {
            new BukkitRunnable() { //remove glow

                @Override
                public void run() {
                    for (LivingEntity target : targets) {
                        target.setGlowing(false);

                        if (target instanceof Player) {
                            team.removeEntry(target.getName());
                        } else {
                            team.removeEntry(target.getUniqueId().toString());
                        }
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
