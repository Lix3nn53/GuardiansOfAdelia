package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

public abstract class TriggerComponent extends SkillComponent {

    public int skillIndex;

    public TriggerComponent(boolean addLore) {
        super(addLore);
    }

    public int getSkillIndex() {
        return skillIndex;
    }
}
