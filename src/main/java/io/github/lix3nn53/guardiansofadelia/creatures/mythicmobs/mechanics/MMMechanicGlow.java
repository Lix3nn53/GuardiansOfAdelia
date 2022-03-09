package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.SkillManager;
import io.lumine.mythic.core.skills.SkillMechanic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class MMMechanicGlow extends SkillMechanic implements ITargetedEntitySkill {

    protected final int duration;
    private final ChatColor color;

    public MMMechanicGlow(SkillManager skillManager, MythicLineConfig config) {
        super(skillManager, config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.duration = config.getInteger(new String[]{"duration", "d"}, 100);
        this.color = ChatColor.valueOf(config.getString(new String[]{"color", "c"}, "WHITE"));
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        String teamName = color.name();

        Team team = board.getTeam(teamName) != null ? board.getTeam(teamName) : board.registerNewTeam(teamName);
        team.setColor(color);

        target.setGlowing(true);

        if (target instanceof Player) {
            team.addEntry(target.getName());
        } else {
            team.addEntry(target.getUniqueId().toString());
        }

        new BukkitRunnable() { //remove glow

            @Override
            public void run() {
                target.setGlowing(false);

                if (target instanceof Player) {
                    team.removeEntry(target.getName());
                } else {
                    team.removeEntry(target.getUniqueId().toString());
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration);

        return SkillResult.SUCCESS;
    }
}
