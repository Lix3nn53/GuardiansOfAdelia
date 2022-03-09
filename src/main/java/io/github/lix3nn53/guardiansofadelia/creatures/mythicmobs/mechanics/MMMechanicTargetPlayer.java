package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.SkillManager;
import io.lumine.mythic.core.skills.SkillMechanic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.List;

public class MMMechanicTargetPlayer extends SkillMechanic implements ITargetedEntitySkill {

    protected final float range;

    public MMMechanicTargetPlayer(SkillManager skillManager, MythicLineConfig config) {
        super(skillManager, config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.range = config.getFloat(new String[]{"range", "r"}, 10);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        Bukkit.getPlayer("Lix3nn").sendMessage("Mob: " + target.getCustomName());

        if (!(target instanceof Mob)) return SkillResult.CONDITION_FAILED;

        Mob mob = (Mob) target;

        if (mob.getTarget() != null) return SkillResult.CONDITION_FAILED;

        List<Entity> nearbyEntities = mob.getNearbyEntities(range, range, range);

        for (Entity e : nearbyEntities) {
            if (e instanceof Player) {
                Player player = (Player) e;
                if (GuardianDataManager.hasGuardianData(player)) {
                    mob.setTarget(player);

                    Bukkit.getPlayer("Lix3nn").sendMessage("Player: " + player.getName());
                    return SkillResult.SUCCESS;
                }
            }
        }

        return SkillResult.CONDITION_FAILED;
    }
}
