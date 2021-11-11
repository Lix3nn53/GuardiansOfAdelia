package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;

import java.util.HashMap;
import java.util.List;

public class RPGClass {
    private final ChatPalette color;
    private final ElementType mainElement;
    private final String name;
    private final int tier;
    private final int customModelData;
    private final HashMap<AttributeType, Integer> attributeTiers;
    private final HashMap<Integer, Skill> skillSet;

    private final ActionBarInfo actionBarInfo;

    private final List<ShieldGearType> shieldGearTypes;
    private final List<WeaponGearType> weaponGearTypes;
    private final List<ArmorGearType> armorGearTypes;

    private final List<String> description;

    public RPGClass(ChatPalette color, ElementType mainElement, String name, int tier, int customModelData, HashMap<AttributeType, Integer> attributeTiers,
                    HashMap<Integer, Skill> skillSet, ActionBarInfo actionBarInfo, List<ShieldGearType> shieldGearTypes, List<WeaponGearType> weaponGearTypes,
                    List<ArmorGearType> armorGearTypes, List<String> description) {
        this.color = color;
        this.mainElement = mainElement;
        this.name = name.toUpperCase();
        this.tier = tier;
        this.customModelData = customModelData;
        this.attributeTiers = attributeTiers;
        this.skillSet = skillSet;
        this.actionBarInfo = actionBarInfo;
        this.shieldGearTypes = shieldGearTypes;
        this.weaponGearTypes = weaponGearTypes;
        this.armorGearTypes = armorGearTypes;
        this.description = description;
    }

    public String getClassString() {
        return color + name;
    }

    public ChatPalette getClassColor() {
        return color;
    }

    public String getClassStringNoColor() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public int getClassIconCustomModelData() {
        return customModelData;
    }

    public int getAttributeBonusForLevel(AttributeType attributeType, int level) {
        int tier = attributeTiers.get(attributeType);
        float levelD = level;
        float tierD = tier;

        return (int) ((levelD * ((levelD * tierD) / (levelD * 0.2))) + 0.5);
    }

    public HashMap<AttributeType, Integer> getAttributeTiers() {
        return attributeTiers;
    }

    public HashMap<Integer, Skill> getSkillSet() {
        return skillSet;
    }

    public ActionBarInfo getActionBarInfo() {
        return actionBarInfo;
    }

    public WeaponGearType getDefaultWeaponGearType() {
        return weaponGearTypes.get(0);
    }

    public ArmorGearType getDefaultArmorGearType() {
        return armorGearTypes.get(0);
    }

    public List<ShieldGearType> getShieldGearTypes() {
        return shieldGearTypes;
    }

    public List<WeaponGearType> getWeaponGearTypes() {
        return weaponGearTypes;
    }

    public List<ArmorGearType> getArmorGearTypes() {
        return armorGearTypes;
    }

    public List<String> getDescription() {
        return description;
    }

    public ElementType getMainElement() {
        return mainElement;
    }
}
