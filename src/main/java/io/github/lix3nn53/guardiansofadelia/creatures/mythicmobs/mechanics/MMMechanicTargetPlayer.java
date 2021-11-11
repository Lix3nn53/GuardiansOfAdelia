package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.List;

public class MMMechanicTargetPlayer extends SkillMechanic implements ITargetedEntitySkill {

    protected final float range;

    public MMMechanicTargetPlayer(MythicLineConfig config) {
        super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.range = config.getFloat(new String[]{"range", "r"}, 10);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        Bukkit.getPlayer("Lix3nn").sendMessage("Mob: " + target.getCustomName());

        if (!(target instanceof Mob)) return false;

        Mob mob = (Mob) target;

        if (mob.getTarget() != null) return false;

        List<Entity> nearbyEntities = mob.getNearbyEntities(range, range, range);

        for (Entity e : nearbyEntities) {
            if (e instanceof Player) {
                Player player = (Player) e;
                if (GuardianDataManager.hasGuardianData(player)) {
                    mob.setTarget(player);

                    Bukkit.getPlayer("Lix3nn").sendMessage("Player: " + player.getName());
                    return true;
                }
            }
        }

        return false;
    }
}
