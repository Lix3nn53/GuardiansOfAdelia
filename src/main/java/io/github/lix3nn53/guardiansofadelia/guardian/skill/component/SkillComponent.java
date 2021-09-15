package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillComponent {

    private final List<SkillComponent> children = new ArrayList<>();
    public final boolean addLore;

    protected SkillComponent(boolean addLore) {
        this.addLore = addLore;
    }

    public abstract boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex);

    /**
     * Use this in #execute method of SkillComponents
     *
     * @param caster
     * @param skillLevel
     * @param targets
     * @param skillIndex
     * @return
     */
    protected boolean executeChildren(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean worked = false;
        for (SkillComponent child : children) {
            boolean passed = child.execute(caster, skillLevel, targets, castCounter, skillIndex);
            worked = passed || worked;
        }
        return worked;
    }

    public void addChildren(SkillComponent child) {
        children.add(child);
    }

    public abstract List<String> getSkillLoreAdditions(List<String> additions, int skillLevel);

    public List<String> getSkillLoreAdditionsOfChildren(List<String> additions, int skillLevel) {
        if (children.isEmpty()) return additions;

        for (SkillComponent child : children) {
            additions = child.getSkillLoreAdditions(additions, skillLevel);
        }

        return additions;
    }

    public void configLoadError(String section) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "ERROR WHILE LOADING SKILL: ");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "Section: " + section);
    }
}
