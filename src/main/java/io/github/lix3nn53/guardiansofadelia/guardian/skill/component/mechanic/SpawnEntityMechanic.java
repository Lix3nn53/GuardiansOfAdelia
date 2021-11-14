package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityMechanic extends MechanicComponent {

    private final String mobCode;
    private final List<Integer> amounts;
    private final List<Integer> DURATION;
    private final List<Integer> levels;
    private boolean SAVE = false;

    public SpawnEntityMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("mobCode")) {
            configLoadError("mobCode");
        }

        if (!configurationSection.contains("amounts")) {
            configLoadError("amounts");
        }

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        if (!configurationSection.contains("levels")) {
            configLoadError("levels");
        }

        this.mobCode = configurationSection.getString("mobCode");
        this.amounts = configurationSection.getIntegerList("amounts");
        this.levels = configurationSection.getIntegerList("levels");

        if (configurationSection.contains("durations")) {
            this.DURATION = configurationSection.getIntegerList("durations");
        } else {
            this.DURATION = new ArrayList<>();
        }

        if (configurationSection.contains("save")) {
            this.SAVE = configurationSection.getBoolean("save");
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int amount = amounts.get(skillLevel - 1);
        int level = levels.get(skillLevel - 1);

        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player owner = (Player) target;
            for (int i = 0; i < amount; i++) {

                Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);

                BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
                Entity entity = null;
                try {
                    entity = apiHelper.spawnMythicMob(mobCode, spawnLoc, level);
                } catch (InvalidMobTypeException e) {
                    GuardiansOfAdelia.getInstance().getLogger().info("SpawnEntityMechanic mythicMob code error: " + mobCode);
                    e.printStackTrace();
                }
                if (entity == null) continue;
                if (!(entity instanceof LivingEntity)) {
                    GuardiansOfAdelia.getInstance().getLogger().info("SpawnEntityMechanic is not LivingEntity, petCode: " + mobCode);
                    continue;
                }

                LivingEntity livingEntity = (LivingEntity) entity;

                if (SAVE) {
                    SkillDataManager.onSkillEntityCreateWithSaveOption(caster, livingEntity, castCounter);
                }

                if (!this.DURATION.isEmpty()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (SAVE) {
                                SkillDataManager.removeSavedEntity(caster, castCounter, livingEntity);
                            } else {
                                livingEntity.remove();
                            }
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * DURATION.get(skillLevel - 1));
                }
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
