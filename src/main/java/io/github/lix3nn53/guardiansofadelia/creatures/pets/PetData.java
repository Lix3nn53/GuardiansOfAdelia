package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigurationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class PetData {

    private final int speed; // speed potion level modifier
    private final List<Skill> skills;
    private final int range;

    public PetData(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("speed")) {
            configLoadError("speed");
        }

        this.speed = configurationSection.getInt("speed");
        this.range = configurationSection.getInt("range");
        this.skills = new ArrayList<>();

        int skillCount = ConfigurationUtils.getChildComponentCount(configurationSection, "skill");
        if (skillCount > 0) {
            for (int i = 1; i <= skillCount; i++) {
                ConfigurationSection skillSection = configurationSection.getConfigurationSection("skill" + i);
                int cooldown = skillSection.getInt("cooldown");
                ArrayList<Integer> cooldowns = new ArrayList<>();
                cooldowns.add(cooldown);

                Skill skill = new Skill("petskill", 1, Material.IRON_HOE, 1, new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cooldowns);

                SkillComponent triggerComponent = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger"));
                skill.addTrigger(triggerComponent);

                int triggerCount = ConfigurationUtils.getChildComponentCount(skillSection, "trigger");
                for (int t = 1; t <= triggerCount; t++) {
                    SkillComponent triggerComponentExtra = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger" + t));
                    skill.addTrigger(triggerComponentExtra);
                }

                this.skills.add(skill);
            }
        }
    }

    public Skill getSkill(int index) {
        return skills.get(index);
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public void configLoadError(String section) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "ERROR WHILE LOADING PET: ");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "Section: " + section);
    }
}
