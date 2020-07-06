package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponDamageType;
import org.bukkit.Material;

public enum WeaponGearType {
    //One handed Melee Weapons
    SWORD,
    DAGGER,
    //Two handed Melee Weapons
    BATTLE_AXE,
    WAR_HAMMER,
    GREAT_SWORD,
    //Ranged + Melee weapons
    SPEAR,
    //Ranged Weapons
    BOW,
    CROSSBOW,
    //Magic Weapons
    STAFF,
    WAND;

    public WeaponDamageType getWeaponType() {
        switch (this) {
            case SWORD:
            case GREAT_SWORD:
            case BATTLE_AXE:
            case WAR_HAMMER:
            case DAGGER:
                return WeaponDamageType.MELEE;
            case SPEAR:
            case CROSSBOW:
            case BOW:
                return WeaponDamageType.RANGED;
            case STAFF:
            case WAND:
                return WeaponDamageType.MAGICAL;
        }

        return null;
    }

    public Material getMaterial() {
        switch (this) {
            case SWORD:
                return Material.NETHERITE_SWORD;
            case DAGGER:
                return Material.NETHERITE_HOE;
            case BATTLE_AXE:
                return Material.NETHERITE_AXE;
            case WAR_HAMMER:
                return Material.NETHERITE_PICKAXE;
            case GREAT_SWORD:
                return Material.DIAMOND_AXE;
            case SPEAR:
                return Material.TRIDENT;
            case BOW:
                return Material.BOW;
            case CROSSBOW:
                return Material.CROSSBOW;
            case STAFF:
                return Material.DIAMOND_SHOVEL;
            case WAND:
                return Material.NETHERITE_SHOVEL;
        }

        return null;
    }

    public AttackSpeed getAttackSpeed() {
        switch (this) {
            case SWORD:
            case STAFF:
                return AttackSpeed.FAST;
            case DAGGER:
                return AttackSpeed.RAPID;
            case BATTLE_AXE:
            case WAR_HAMMER:
            case CROSSBOW:
                return AttackSpeed.SLOW;
            case GREAT_SWORD:
            case SPEAR:
            case BOW:
            case WAND:
                return AttackSpeed.NORMAL;
        }

        return AttackSpeed.SLOW;
    }

    public double getDamageReduction() {
        switch (this) {
            case SWORD:
                return 0.6;
            case DAGGER:
                return 0.4;
            case BATTLE_AXE:
                return 1;
            case WAR_HAMMER:
                return 0.8;
            case GREAT_SWORD:
                return 0.8;
            case SPEAR:
                return 0.5;
            case BOW:
                return 0.6;
            case CROSSBOW:
                return 0.8;
            case STAFF:
                return 0.4;
            case WAND:
                return 0.6;
        }

        return 0;
    }

    public String getDisplayName() {
        switch (this) {
            case SWORD:
                return "Sword";
            case DAGGER:
                return "Dagger";
            case BATTLE_AXE:
                return "BattleAxe";
            case WAR_HAMMER:
                return "WarHammer";
            case GREAT_SWORD:
                return "GreatSword";
            case SPEAR:
                return "Spear";
            case BOW:
                return "Bow";
            case CROSSBOW:
                return "Crossbow";
            case STAFF:
                return "Staff";
            case WAND:
                return "Wand";
        }

        return "WEAPON GEAR NAME BUG";
    }

    public boolean canEquipToOffHand() {
        switch (this) {
            case SWORD:
                return false;
            case DAGGER:
                return true;
            case BATTLE_AXE:
                return false;
            case WAR_HAMMER:
                return false;
            case GREAT_SWORD:
                return false;
            case SPEAR:
                return false;
            case BOW:
                return false;
            case CROSSBOW:
                return false;
            case STAFF:
                return false;
            case WAND:
                return false;
        }

        return false;
    }

    public boolean getReduceMeleeDamage() {
        return this != WeaponGearType.SPEAR;
    }
}
