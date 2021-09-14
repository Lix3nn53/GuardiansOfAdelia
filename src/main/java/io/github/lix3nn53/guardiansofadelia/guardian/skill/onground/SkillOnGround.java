package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigurationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class SkillOnGround {

    private final String name;
    private final long period;
    private final Skill skill;

    // State
    ArmorStand armorStand;
    BukkitTask bukkitTask;
    int castCounter = 1;

    public SkillOnGround(String name, long period, SkillComponent... triggerComponents) {
        this.name = name;
        this.period = period;

        ArrayList<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(0);
        List<String> description = new ArrayList<>();

        Skill skill = new Skill("trapskill", 1, Material.IRON_HOE, 1, description,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cooldowns);

        for (SkillComponent trigger : triggerComponents) {
            skill.addTrigger(trigger);
        }

        this.skill = skill;
    }

    public SkillOnGround(ConfigurationSection configurationSection) {
        this.name = configurationSection.contains("name") ? configurationSection.getString("name") : null;
        this.period = configurationSection.getLong("period");

        ArrayList<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(0);
        List<String> description = new ArrayList<>();

        Skill skill = new Skill("trapskill", 1, Material.IRON_HOE, 1, description,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cooldowns);

        SkillComponent triggerComponent = SkillComponentLoader.loadSection(configurationSection.getConfigurationSection("trigger"));
        skill.addTrigger(triggerComponent);

        int triggerCount = ConfigurationUtils.getChildComponentCount(configurationSection, "trigger");
        for (int t = 1; t <= triggerCount; t++) {
            SkillComponent triggerComponentExtra = SkillComponentLoader.loadSection(configurationSection.getConfigurationSection("trigger" + t));
            skill.addTrigger(triggerComponentExtra);
        }

        this.skill = skill;
    }

    public void activate(Location location, long delay) {
        Hologram hologram;
        if (name == null) {
            hologram = new Hologram(location);
        } else {
            hologram = new Hologram(location, this.name);
        }

        armorStand = hologram.getArmorStand();

        ArrayList<LivingEntity> targets = new ArrayList<>();

        this.bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!armorStand.isValid()) {
                    cancel();
                    return;
                }

                boolean cast = skill.cast(armorStand, 1, targets, castCounter, 999);
                castCounter++;

                for (LivingEntity target : targets) {
                    if (target.getCustomName() != null)
                        GuardiansOfAdelia.getInstance().getLogger().info("custom name: " + target.getCustomName());
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), delay, period);
    }

    public void deactivate() {
        armorStand.remove();
        this.bukkitTask.cancel();
    }
}
