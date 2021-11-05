package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileCallback;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanicBase;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementDrawCylinder;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ParticleArrangement;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.Skill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MMMechanicProjectile extends SkillMechanic implements ITargetedEntitySkill, ProjectileCallback {

    protected final Optional<Skill> onHitSkill;
    private final ProjectileMechanicBase base;
    private SkillMetadata data;

    public MMMechanicProjectile(MythicLineConfig config) {
        super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        String onHitSkillName = config.getString(new String[]{"onHit"}, null);
        if (onHitSkillName != null) {
            this.onHitSkill = getPlugin().getSkillManager().getSkill(onHitSkillName);
        } else {
            this.onHitSkill = Optional.empty();
        }

        String projectileClass = config.getString(new String[]{"projectile", "p"}, "Arrow");

        Class<? extends Projectile> projectileType = null;
        try {
            projectileType = (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + projectileClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SpreadType spreadType = SpreadType.valueOf(config.getString(new String[]{"spreadType", "st"}, "CONE"));
        double speed = config.getDouble(new String[]{"speed", "s"}, 1);

        List<Integer> amountList = new ArrayList<>();
        int amount = config.getInteger(new String[]{"amount", "a"}, 1);
        amountList.add(amount);

        String amountValueKey = config.getString(new String[]{"amountValueKey"}, null);
        double angle = config.getDouble(new String[]{"angle"}, 30);
        double range = config.getDouble(new String[]{"range"}, 200);
        boolean mustHitToWork = config.getBoolean(new String[]{"mustHitToWork"}, true);

        double radius = config.getDouble(new String[]{"radius"}, 0);
        double height = config.getDouble(new String[]{"height"}, 0);

        //Particle projectile
        ParticleArrangement particleArrangement = null;

        String particleStr = config.getString(new String[]{"particle"}, null);
        if (particleStr != null) {
            Particle particle = Particle.valueOf(particleStr);

            int dustColor = config.getInteger(new String[]{"dustColor"}, 10040319);
            int dustSize = config.getInteger(new String[]{"dustSize"}, 1);

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);

            particleArrangement = new ArrangementDrawCylinder(particle, dustOptions, 1, 1, 1, 1, 1);
        }

        double upward = config.getDouble(new String[]{"upward"}, 0);

        //custom options
        boolean addCasterAsFirstTargetIfHitSuccess = config.getBoolean(new String[]{"addCasterAsFirstTargetIfHitSuccess"}, false);
        boolean addCasterAsSecondTargetIfHitFail = config.getBoolean(new String[]{"addCasterAsSecondTargetIfHitFail"}, false);

        boolean isProjectileInvisible = config.getBoolean(new String[]{"isProjectileInvisible"}, false);

        // Disguise
        Optional<Material> disguiseMaterial = Optional.empty();
        String disguiseMaterialStr = config.getString(new String[]{"disguiseMaterial"}, null);
        if (disguiseMaterialStr != null) {
            disguiseMaterial = Optional.of(Material.valueOf(disguiseMaterialStr));
        }

        int disguiseCustomModelData = config.getInteger(new String[]{"disguiseCustomModelData"}, 1);

        this.base = new ProjectileMechanicBase(projectileType, spreadType, radius, height, speed, amountList,
                amountValueKey, angle, upward, range, mustHitToWork, particleArrangement, isProjectileInvisible,
                disguiseMaterial, disguiseCustomModelData, addCasterAsFirstTargetIfHitSuccess, addCasterAsSecondTargetIfHitFail);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);
        this.data = data;

        ArrayList<LivingEntity> targets = new ArrayList<>();
        targets.add(target);

        return this.base.execute((LivingEntity) data.getCaster().getEntity().getBukkitEntity(), 1, targets,
                base.getCastCounter(), base.getSkillIndex(), this);
    }

    @Override
    public ArrayList<LivingEntity> callback(Projectile projectile, Entity hit) {
        ArrayList<LivingEntity> targets = this.base.callback(projectile, hit);

        List<AbstractEntity> abstractTargets = new ArrayList<>();

        for (LivingEntity target : targets) {
            AbstractEntity adapt = BukkitAdapter.adapt(target);
            abstractTargets.add(adapt);
        }

        // AbstractEntity shooter = BukkitAdapter.adapt((LivingEntity) projectile.getShooter());

        if (onHitSkill.isPresent()) {
            Skill skill = onHitSkill.get();

            SkillMetadata sData = this.data.deepClone();
            sData.setEntityTargets(abstractTargets);
            sData.setOrigin(abstractTargets.get(0).getLocation());
            if (skill.isUsable(sData)) {
                skill.execute(sData);
            }
        }

        return targets;
    }
}
