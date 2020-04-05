package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponDamageType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum WeaponGearType {
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
    STAFF;

    public List<RPGClass> getRequiredClasses() {
        List<RPGClass> classList = new ArrayList<>();

        switch (this) {
            case SWORD:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.ROGUE);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.MONK);
                break;
            case MACE:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                break;
            case DAGGER:
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.MAGE);
                classList.add(RPGClass.ROGUE);
                classList.add(RPGClass.HUNTER);
                break;
            case WAR_AXE:
                classList.add(RPGClass.ROGUE);
                classList.add(RPGClass.WARRIOR);
                break;
            case BATTLE_AXE:
                classList.add(RPGClass.WARRIOR);
                break;
            case GREAT_SWORD:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.WARRIOR);
                classList.add(RPGClass.MONK);
                break;
            case WAR_HAMMER:
                classList.add(RPGClass.PALADIN);
                break;
            case SPEAR:
                classList.add(RPGClass.MONK);
                break;
            case BOW:
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.HUNTER);
                break;
            case CROSSBOW:
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.HUNTER);
                break;
            case WAND:
                classList.add(RPGClass.MAGE);
                break;
            case STAFF:
                classList.add(RPGClass.MAGE);
                break;
        }

        return classList;
    }

    public WeaponDamageType getWeaponType() {
        switch (this) {
            case SWORD:
            case WAR_HAMMER:
            case GREAT_SWORD:
            case BATTLE_AXE:
            case WAR_AXE:
            case DAGGER:
            case MACE:
                return WeaponDamageType.MELEE;
            case SPEAR:
            case CROSSBOW:
            case BOW:
                return WeaponDamageType.RANGED;
            case WAND:
            case STAFF:
                return WeaponDamageType.MAGICAL;
        }

        return null;
    }

    public Material getMaterial() {
        switch (this) {
            case SWORD:
                return Material.DIAMOND_SWORD;
            case MACE:
                return Material.IRON_SWORD;
            case DAGGER:
                return Material.DIAMOND_HOE;
            case WAR_AXE:
                return Material.IRON_HOE;
            case BATTLE_AXE:
                return Material.DIAMOND_AXE;
            case GREAT_SWORD:
                return Material.IRON_AXE;
            case WAR_HAMMER:
                return Material.GOLDEN_AXE;
            case SPEAR:
                return Material.TRIDENT;
            case BOW:
                return Material.BOW;
            case CROSSBOW:
                return Material.CROSSBOW;
            case WAND:
                return Material.IRON_SHOVEL;
            case STAFF:
                return Material.DIAMOND_SHOVEL;
        }

        return null;
    }

    public String getDisplayName() {
        switch (this) {
            case SWORD:
                return "Sword";
            case MACE:
                return "Mace";
            case DAGGER:
                return "Dagger";
            case WAR_AXE:
                return "WarAxe";
            case BATTLE_AXE:
                return "BattleAxe";
            case GREAT_SWORD:
                return "GreatSword";
            case WAR_HAMMER:
                return "WarHammer";
            case SPEAR:
                return "Spear";
            case BOW:
                return "Bow";
            case CROSSBOW:
                return "Crossbow";
            case WAND:
                return "Wand";
            case STAFF:
                return "Staff";
        }

        return "";
    }

    public AttackSpeed getAttackSpeed() {
        switch (this) {
            case SPEAR:
            case GREAT_SWORD:
            case WAND:
            case STAFF:
            case BOW:
            case CROSSBOW:
                return AttackSpeed.NORMAL;
            case SWORD:
            case MACE:
            case WAR_AXE:
                return AttackSpeed.FAST;
            case DAGGER:
                return AttackSpeed.RAPID;
            case BATTLE_AXE:
            case WAR_HAMMER:
                return AttackSpeed.SLOW;
        }

        return AttackSpeed.SLOW;
    }

    public double getDamageReduction() {
        switch (this) {
            case SWORD:
                return 0.5;
            case MACE:
                return 0.5;
            case DAGGER:
                return 0.5;
            case WAR_AXE:
                return 0.5;
            case BATTLE_AXE:
                return 0.5;
            case GREAT_SWORD:
                return 0.5;
            case WAR_HAMMER:
                return 0.5;
            case SPEAR:
                return 0.5;
            case BOW:
                return 0.5;
            case CROSSBOW:
                return 0.5;
            case WAND:
                return 0.5;
            case STAFF:
                return 0.5;
        }

        return 0;
    }
}
