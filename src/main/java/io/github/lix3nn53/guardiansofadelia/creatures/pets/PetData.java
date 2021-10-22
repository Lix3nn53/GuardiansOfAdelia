package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigurationUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetData {

    private final int speed; // speed potion level modifier
    HashMap<Integer, Skill> skills = new HashMap<>();

    private final int range;

    public PetData(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("speed")) {
            configLoadError("speed");
        }

        this.speed = configurationSection.getInt("speed");
        this.range = configurationSection.getInt("range");

        int skillCount = ConfigurationUtils.getChildComponentCount(configurationSection, "skill");
        if (skillCount > 0) {
            for (int i = 1; i <= skillCount; i++) {
                ConfigurationSection skillSection = configurationSection.getConfigurationSection("skill" + i);
                int cooldown = skillSection.getInt("cooldown");
                ArrayList<Integer> cooldowns = new ArrayList<>();
                cooldowns.add(cooldown);

                List<String> description = skillSection.getStringList("description");

                Skill skill = new Skill("petskill", 1, Material.IRON_HOE, 1, description,
                        new ArrayList<>(), new ArrayList<>(), cooldowns);

                SkillComponent triggerComponent = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger"));
                skill.addTrigger(triggerComponent);

                int triggerCount = ConfigurationUtils.getChildComponentCount(skillSection, "trigger");
                for (int t = 1; t <= triggerCount; t++) {
                    SkillComponent triggerComponentExtra = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger" + t));
                    skill.addTrigger(triggerComponentExtra);
                }

                int level = skillSection.getInt("level");
                this.skills.put(level, skill);
            }
        }
    }

    public Skill getSkill(int index) {
        for (int i = index; i > 0; i--) {
            if (skills.containsKey(i)) {
                return skills.get(i);
            }
        }

        return skills.get(1);
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public HashMap<Integer, Skill> getSkills() {
        return skills;
    }

    public void configLoadError(String section) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "ERROR WHILE LOADING PET: ");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "Section: " + section);
    }
}
