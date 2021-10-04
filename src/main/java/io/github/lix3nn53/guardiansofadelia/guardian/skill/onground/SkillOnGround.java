package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.events.MyChunkEvents;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
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
    private final int skillLevel;

    // State
    ArmorStand armorStand;
    BukkitTask bukkitTask;
    int castCounter = 1;

    public SkillOnGround(String name, long period, int skillLevel, SkillComponent... triggerComponents) {
        this.name = name;
        this.period = period;
        this.skillLevel = skillLevel;

        ArrayList<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(0);
        List<String> description = new ArrayList<>();

        Skill skill = new Skill("skillOnGround", 1, Material.IRON_HOE, 1, description,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cooldowns);

        for (SkillComponent trigger : triggerComponents) {
            skill.addTrigger(trigger);
        }

        this.skill = skill;
    }

    public SkillOnGround(ConfigurationSection configurationSection) {
        this.name = configurationSection.contains("name") ? configurationSection.getString("name") : null;
        this.period = configurationSection.getLong("period");
        this.skillLevel = configurationSection.getInt("skillLevel");

        String skillKey = configurationSection.getString("skillKey");

        this.skill = SkillListForGround.getSkill(skillKey);
    }

    public ArmorStand activate(Location location, long delay) {
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

                boolean cast = skill.cast(armorStand, skillLevel, targets, castCounter, 999);
                castCounter++;

                for (LivingEntity target : targets) {
                    if (target.getCustomName() != null)
                        GuardiansOfAdelia.getInstance().getLogger().info("custom name: " + target.getCustomName());
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), delay, period);

        return armorStand;
    }

    public void deactivate() {
        MyChunkEvents.DO_NOT_DELETE.remove(armorStand);
        armorStand.remove();
        this.bukkitTask.cancel();
    }
}
