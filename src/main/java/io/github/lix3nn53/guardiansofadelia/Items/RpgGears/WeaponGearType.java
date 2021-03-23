package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.PushMechanic;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public enum WeaponGearType {
    //One handed Melee Weapons
    SWORD, // Normal attack speed, more damage than dagger
    DAGGER, // Fast attack speed, can dual wield
    //Two handed Melee Weapons
    BATTLE_AXE, // Slow attack speed, more damage than great sword
    WAR_HAMMER, // Slow attack speed, same damage with great sword, normal attacks knockback target
    GREAT_SWORD, // Normal attack speed, more damage than sword
    //Ranged + Melee weapons
    SPEAR, // Normal attack speed, ranged + melee
    //Ranged Weapons
    BOW, // Normal attack speed
    CROSSBOW, // slow attack speed, more damage than bow
    //Magic Weapons
    STAFF, // Skill cooldown reduction
    WAND; // More damage than staff

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
                return Material.NETHERITE_SHOVEL;
            case WAND:
                return Material.DIAMOND_SHOVEL;
        }

        return null;
    }

    public static WeaponGearType fromMaterial(Material material) {
        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            if (weaponGearType.getMaterial().equals(material)) {
                return weaponGearType;
            }
        }

        return null;
    }

    public WeaponAttackDamage getWeaponAttackDamage() {
        switch (this) {
            case DAGGER:
                return WeaponAttackDamage.LOW;
            case SWORD:
            case SPEAR:
            case BOW: // Reduced melee damage
                return WeaponAttackDamage.NORMAL;
            case WAR_HAMMER:
            case GREAT_SWORD:
            case CROSSBOW: // Reduced melee damage
            case STAFF: // Reduced melee damage
                return WeaponAttackDamage.HIGH;
            case BATTLE_AXE:
            case WAND: // Reduced melee damage
                return WeaponAttackDamage.MAXIMUM;
        }

        return WeaponAttackDamage.LOW;
    }

    public WeaponAttackSpeed getAttackSpeed() {
        switch (this) {
            case SWORD:
            case GREAT_SWORD:
            case SPEAR:
            case BOW:
            case WAND:
                return WeaponAttackSpeed.NORMAL;
            case STAFF:
            case DAGGER:
                return WeaponAttackSpeed.FAST;
            case BATTLE_AXE:
            case WAR_HAMMER:
            case CROSSBOW:
                return WeaponAttackSpeed.SLOW;
        }

        return WeaponAttackSpeed.SLOW;
    }

    public double getCriticalChance() {
        switch (this) {
            case SWORD:
            case GREAT_SWORD:
            case SPEAR:
            case BOW:
            case WAND:
                return 0.06;
            case STAFF:
            case DAGGER:
                return 0.04;
            case BATTLE_AXE:
            case WAR_HAMMER:
            case CROSSBOW:
                return 0.09;
        }

        return 0.05;
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

    public boolean isMelee() {
        switch (this) {
            case SWORD:
            case GREAT_SWORD:
            case WAR_HAMMER:
            case BATTLE_AXE:
            case DAGGER:
                return true;
            case SPEAR:
            case WAND:
            case STAFF:
            case CROSSBOW:
            case BOW:
                return false;
        }

        return true;
    }

    public double getMeleeDamageReduction() {
        switch (this) {
            case SWORD:
            case BATTLE_AXE:
            case DAGGER:
            case GREAT_SWORD:
            case WAR_HAMMER:
            case SPEAR:
                return 1;
            case BOW:
            case CROSSBOW:
            case STAFF:
            case WAND:
                return 0.2;
        }

        return 0;
    }

    public boolean canEquipToOffHand() {
        return this == WeaponGearType.DAGGER;
    }

    public int getBonusAbilityHaste() {
        if (this.equals(WeaponGearType.STAFF)) {
            return 20;
        }

        return 0;
    }

    public void onHitEffect(LivingEntity caster, LivingEntity target) {
        if (this.equals(WeaponGearType.WAR_HAMMER)) {
            ArrayList<Double> speedList = new ArrayList<>();
            speedList.add(1.2);
            speedList.add(1.2);
            speedList.add(1.2);
            speedList.add(1.2);
            PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speedList, true, 0);

            ArrayList<LivingEntity> targets = new ArrayList<>();
            targets.add(target);
            pushMechanic.execute(caster, 1, targets, 0);
        }
    }

    public String getItemLoreAddition() {
        switch (this) {
            case WAR_HAMMER:
                return ChatColor.LIGHT_PURPLE + "Knockback bonus: " + ChatColor.GRAY + "1.2";
            case STAFF:
                return ChatColor.LIGHT_PURPLE + "Ability Haste: " + ChatColor.GRAY + "+20";
        }

        return null;
    }
}
