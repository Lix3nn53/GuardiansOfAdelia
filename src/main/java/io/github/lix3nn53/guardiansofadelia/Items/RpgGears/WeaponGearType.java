package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponDamageType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

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

    public List<RPGClass> getRequiredClasses() {
        List<RPGClass> classList = new ArrayList<>();

        switch (this) {
            //ONE HANDED MELEE
            case SWORD:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.WARRIOR);
                classList.add(RPGClass.ROGUE);
                break;
            case DAGGER:
                classList.add(RPGClass.ROGUE);
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.HUNTER);
                classList.add(RPGClass.MAGE);
                break;
            //TWO HANDED MELEE
            case BATTLE_AXE:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.WARRIOR);
                break;
            case WAR_HAMMER:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.WARRIOR);
                break;
            case GREAT_SWORD:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.WARRIOR);
                break;
            //TWO HANDED HYBRID
            case SPEAR:
                classList.add(RPGClass.MONK);
                break;
            //TWO HANDED RANGED
            case BOW:
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.HUNTER);
                classList.add(RPGClass.ROGUE);
                break;
            case CROSSBOW:
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.HUNTER);
                classList.add(RPGClass.ROGUE);
                break;
            //TWO HANDED MAGICAL
            case STAFF:
                classList.add(RPGClass.MAGE);
                break;
            case WAND:
                classList.add(RPGClass.MAGE);
                break;
        }

        return classList;
    }

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
                return Material.GOLDEN_HOE;
            case DAGGER:
                return Material.DIAMOND_HOE;
            case BATTLE_AXE:
                return Material.DIAMOND_AXE;
            case WAR_HAMMER:
                return Material.DIAMOND_SWORD;
            case GREAT_SWORD:
                return Material.GOLDEN_AXE;
            case SPEAR:
                return Material.TRIDENT;
            case BOW:
                return Material.BOW;
            case CROSSBOW:
                return Material.CROSSBOW;
            case STAFF:
                return Material.GOLDEN_SHOVEL;
            case WAND:
                return Material.DIAMOND_SHOVEL;
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
}
