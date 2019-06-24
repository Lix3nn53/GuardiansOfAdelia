package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Skill {

    String name;
    Material material;
    int maxSkillLevel;
    int reqPlayerLevel;
    List<String> description;
    private int investedPoints;

    //skill attributes
    int manaCost;
    int cooldown;

    private final List<SkillComponent> triggers = new ArrayList<>();

    public Skill(String name, Material material, List<String> description, int maxSkillLevel, int reqPlayerLevel) {
        this.name = name;
        this.material = material;
        this.maxSkillLevel = maxSkillLevel;
        this.reqPlayerLevel = reqPlayerLevel;
        this.description = description;


    }

    //TODO copy constructor
    public Skill(Skill skillToCopy) {

    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getMaxSkillLevel() {
        return maxSkillLevel;
    }

    public int getReqPlayerLevel() {
        return reqPlayerLevel;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getIcon(int skillLevel) {

        ItemStack icon = new ItemStack(getMaterial());

        ItemMeta itemMeta = icon.getItemMeta();

        itemMeta.setDisplayName(getName() + " (" + investedPoints + "/" + getMaxSkillLevel() + ")");
        List<String> lore = new ArrayList<>();

        lore.addAll(getDescription());

        itemMeta.setLore(lore);

        icon.setItemMeta(itemMeta);
        return icon;
    }

    public boolean canPlayersCast() {
        //TODO
        return true;
    }

    public boolean cast(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        boolean didCast = false;
        for (SkillComponent trigger : triggers) {
            didCast = trigger.execute(caster, skillLevel, targets) || didCast;
        }
        return didCast;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void addTrigger(SkillComponent skillComponent) {
        triggers.add(skillComponent);
    }
}