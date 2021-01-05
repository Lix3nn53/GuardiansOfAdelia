package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpawnCompanionMechanic extends MechanicComponent {

    private final String mobCode;
    private final List<Integer> amounts;
    private final List<Integer> DURATION;
    private final List<Integer> levels;
    private boolean SAVE = false;

    public SpawnCompanionMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("mobCode")) {
            configLoadError("mobCode");
        }

        if (!configurationSection.contains("amounts")) {
            configLoadError("amounts");
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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        int amount = amounts.get(skillLevel - 1);
        int level = levels.get(skillLevel - 1);

        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player owner = (Player) target;
            for (int i = 0; i < amount; i++) {

                LivingEntity livingEntity = PetManager.spawnCompanion(owner, mobCode, level, 9999999);

                if (livingEntity == null) {
                    GuardiansOfAdelia.getInstance().getLogger().info("SpawnPetMechanic error: " + mobCode);
                    continue;
                }

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
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), DURATION.get(skillLevel - 1));
                }
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            String lore = "Spawn " + amounts.get(skillLevel) + "x" + mobCode + " at level " + levels.get(skillLevel);

            if (!DURATION.isEmpty()) {
                lore = lore + " for " + DURATION.get(skillLevel) / 20;
            }

            additions.add(lore);
        } else if (skillLevel == amounts.size()) {
            String lore = "Spawn " + amounts.get(skillLevel - 1) + "x" + mobCode + " at level " + levels.get(skillLevel - 1);

            if (!DURATION.isEmpty()) {
                lore = lore + " for " + DURATION.get(skillLevel - 1) / 20;
            }

            additions.add(lore);
        } else {
            String lore1 = "Spawn " + amounts.get(skillLevel - 1) + "x" + mobCode + " at level " + levels.get(skillLevel - 1);
            String lore2 = amounts.get(skillLevel) + "x at level " + levels.get(skillLevel);

            if (!DURATION.isEmpty()) {
                lore1 = lore1 + " for " + DURATION.get(skillLevel - 1) / 20;
                lore2 = lore2 + " for " + DURATION.get(skillLevel) / 20;
            }

            additions.add(lore1 + " -> " + lore2);
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
