package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangementLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ParticleArrangement;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectileMechanic extends MechanicComponent implements ProjectileCallback {

    private final ProjectileMechanicBase base;

    public ProjectileMechanic(ConfigurationSection configurationSection) throws ClassNotFoundException {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        String projectileClass = configurationSection.getString("projectileClass");
        Class<? extends Projectile> projectileType = (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + projectileClass);

        SpreadType spreadType = SpreadType.valueOf(configurationSection.getString("spreadType"));
        double speed = configurationSection.getDouble("speed");
        List<Integer> amountList = configurationSection.getIntegerList("amountList");
        String amountValueKey = configurationSection.getString("amountValueKey");
        double angle = configurationSection.getDouble("angle");
        double range = configurationSection.getDouble("range");
        boolean mustHitToWork = configurationSection.getBoolean("mustHitToWork");

        double radius;
        double height;
        if (spreadType.equals(SpreadType.RAIN)) {
            radius = configurationSection.getDouble("radius");
            height = configurationSection.getDouble("height");
        } else {
            radius = 0;
            height = 0;
        }

        //Particle projectile
        ParticleArrangement particleArrangement;
        if (configurationSection.contains("particle")) {
            ConfigurationSection particle = configurationSection.getConfigurationSection("particle");
            particleArrangement = ParticleArrangementLoader.load(particle);
        } else {
            particleArrangement = null;
        }

        double upward;
        if (configurationSection.contains("upward")) {
            upward = configurationSection.getDouble("upward");
        } else {
            upward = 0;
        }

        //custom options
        boolean addCasterAsFirstTargetIfHitSuccess = false;
        if (configurationSection.contains("addCasterAsFirstTargetIfHitSuccess")) {
            addCasterAsFirstTargetIfHitSuccess = configurationSection.getBoolean("addCasterAsFirstTargetIfHitSuccess");
        }
        boolean addCasterAsSecondTargetIfHitFail = false;
        if (configurationSection.contains("addCasterAsSecondTargetIfHitFail")) {
            addCasterAsSecondTargetIfHitFail = configurationSection.getBoolean("addCasterAsSecondTargetIfHitFail");
        }

        boolean isProjectileInvisible;
        if (configurationSection.contains("isProjectileInvisible")) {
            isProjectileInvisible = configurationSection.getBoolean("isProjectileInvisible");
        } else {
            isProjectileInvisible = false;
        }

        // Disguise
        Optional<Material> disguiseMaterial = Optional.empty();
        if (configurationSection.contains("disguiseMaterial")) {
            disguiseMaterial = Optional.of(Material.valueOf(configurationSection.getString("disguiseMaterial")));
        }
        int disguiseCustomModelData = 1;
        if (configurationSection.contains("disguiseCustomModelData")) {
            disguiseCustomModelData = configurationSection.getInt("disguiseCustomModelData");
        }

        this.base = new ProjectileMechanicBase(projectileType, spreadType, radius, height, speed, amountList,
                amountValueKey, angle, upward, range, mustHitToWork, particleArrangement, isProjectileInvisible,
                disguiseMaterial, disguiseCustomModelData, addCasterAsFirstTargetIfHitSuccess, addCasterAsSecondTargetIfHitFail);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        return this.base.execute(caster, skillLevel, targets, castCounter, skillIndex, this);
    }

    @Override
    public ArrayList<LivingEntity> callback(Projectile projectile, Entity hit) {
        ArrayList<LivingEntity> targets = this.base.callback(projectile, hit);

        ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof LivingEntity) {
            LivingEntity shooterLiving = (LivingEntity) shooter;

            int skillLevel = 1;
            if (PersistentDataContainerUtil.hasInteger(projectile, "skillLevel")) {
                skillLevel = PersistentDataContainerUtil.getInteger(projectile, "skillLevel");
            }

            int castCounter = base.getCastCounter();
            int skillIndex = base.getSkillIndex();

            if (PetManager.isCompanion(shooterLiving)) {
                Player owner = PetManager.getOwner(shooterLiving);

                executeChildren(owner, skillLevel, targets, castCounter, skillIndex);
            } else if (SkillDataManager.isSavedEntity(shooterLiving)) {
                LivingEntity owner = SkillDataManager.getOwner(shooterLiving);

                executeChildren(owner, skillLevel, targets, castCounter, skillIndex);
            } else {
                executeChildren(shooterLiving, skillLevel, targets, castCounter, skillIndex);
            }
        }

        return targets;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        List<Integer> amountList = this.base.getAmountList();
        String amountValueKey = this.base.getAmountValueKey();

        if (skillLevel == 0) {
            String s = ChatPalette.YELLOW + "Projectile amount: " + amountList.get(skillLevel);

            if (amountValueKey != null) {
                s += " x[" + amountValueKey + "]";
            }

            additions.add(s);
        } else if (skillLevel == amountList.size()) {
            String s = ChatPalette.YELLOW + "Projectile amount: " + amountList.get(skillLevel - 1);

            if (amountValueKey != null) {
                s += " x[" + amountValueKey + "]";
            }

            additions.add(s);
        } else {
            String s = ChatPalette.YELLOW + "Projectile amount: " + amountList.get(skillLevel - 1) + " -> " + amountList.get(skillLevel);

            if (amountValueKey != null) {
                s += " x[" + amountValueKey + "]";
            }

            additions.add(s);
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
