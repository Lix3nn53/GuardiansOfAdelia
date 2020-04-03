package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum RPGGearType {
    //One handed Melee Weapons
    SWORD,
    MACE,
    DAGGER,
    WAR_AXE,
    //Two handed Melee Weapons
    BATTLE_AXE,
    GREAT_SWORD,
    WAR_HAMMER,
    //Ranged + Melee weapons
    SPEAR,
    //Ranged Weapons
    BOW,
    CROSSBOW,
    //Magic Weapons
    WAND,
    STAFF,
    //Other Offhand
    SHIELD,
    //Armors
    LIGHT_ARMOR,
    MEDIUM_ARMOR,
    HEAVY_ARMOR,
    //passive
    PASSIVE,
    //other
    EGG_COMPANION,
    EGG_MOUNT;

    public List<RPGClass> getRequiredClasses() {
        List<RPGClass> classList = new ArrayList<>();

        return classList;
    }

    public WeaponType getWeaponType() {

        return WeaponType.MAGICAL;
    }

    public Material getMaterial() {

        return Material.ACACIA_BUTTON;
    }

    public String getDisplayName() {

        return "";
    }

    public AttackSpeed getAttackSpeed() {

        return AttackSpeed.FAST;
    }
}
