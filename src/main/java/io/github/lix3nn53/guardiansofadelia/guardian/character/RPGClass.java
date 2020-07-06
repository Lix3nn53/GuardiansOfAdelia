package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;

public class RPGClass {
    private final ChatColor color;
    private final String name;
    private final int customModelData;
    private final HashMap<AttributeType, Integer> attributeTiers;
    private final HashMap<Integer, Skill> skillSet;

    private final List<ShieldGearType> shieldGearTypes;
    private final List<WeaponGearType> weaponGearTypes;
    private final List<ArmorGearType> armorGearTypes;

    private final boolean hasDefaultOffhand;
    private final boolean isDefaultOffhandWeapon;

    public RPGClass(ChatColor color, String name, int customModelData, HashMap<AttributeType, Integer> attributeTiers,
                    HashMap<Integer, Skill> skillSet, List<ShieldGearType> shieldGearTypes, List<WeaponGearType> weaponGearTypes, List<ArmorGearType> armorGearTypes, boolean hasDefaultOffhand, boolean isDefaultOffhandWeapon) {
        this.color = color;
        this.name = name;
        this.customModelData = customModelData;
        this.attributeTiers = attributeTiers;
        this.skillSet = skillSet;
        this.shieldGearTypes = shieldGearTypes;
        this.weaponGearTypes = weaponGearTypes;
        this.armorGearTypes = armorGearTypes;
        this.hasDefaultOffhand = hasDefaultOffhand;
        this.isDefaultOffhandWeapon = isDefaultOffhandWeapon;
    }

    public String getClassString() {
        return color + name;
    }

    public ChatColor getClassColor() {
        return color;
    }

    public String getClassStringNoColor() {
        return name;
    }

    public int getClassIconCustomModelData() {
        return customModelData;
    }

    public int getAttributeBonusForLevel(AttributeType attributeType, int level) {
        int tier = attributeTiers.get(attributeType);
        double levelD = level;
        double tierD = tier;

        return (int) ((levelD * ((levelD * tierD) / 125)) + 0.5);
    }

    public HashMap<Integer, Skill> getSkillSet() {
        return skillSet;
    }

    public WeaponGearType getDefaultWeaponGearType() {
        return weaponGearTypes.get(0);
    }

    public boolean hasDefaultOffhand() {
        return hasDefaultOffhand;
    }

    public boolean isDefaultOffhandWeapon() {
        return isDefaultOffhandWeapon;
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
}
