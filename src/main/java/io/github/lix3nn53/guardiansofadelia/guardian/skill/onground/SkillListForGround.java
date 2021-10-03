package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;

import java.util.HashMap;

public class SkillListForGround {

    private static final HashMap<String, Skill> skills = new HashMap<>();

    public static void addSkill(String key, Skill skill) {
        skills.put(key, skill);
    }

    public static Skill getSkill(String key) {
        if (!skills.containsKey(key)) {
            GuardiansOfAdelia.getInstance().getLogger().warning("ERR SKILL ON GROUND KEY DOES NOT EXIST: " + key);

            return null;
        }

        return skills.get(key);
    }

    public static void clear() {
        skills.clear();
    }
}
