package io.github.lix3nn53.guardiansofadelia.items.RpgGears;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public enum WeaponGearType {
    //One handed Melee Weapons
    SWORD, // Normal attack speed, normal damage, can equip with shield
    DAGGER, // Fast attack speed, low damage, can dual wield
    //Two handed Melee Weapons
    BATTLE_AXE, // Slow attack speed, max damage
    WAR_HAMMER, // Slow attack speed, normal damage, sweep attack
    GREAT_SWORD, // Normal attack speed, high damage
    //Ranged + Melee weapons
    SPEAR, // Normal attack speed, normal damage, ranged + melee
    //Ranged Weapons
    BOW, // Normal attack speed, normal damage
    CROSSBOW, // slow attack speed, high damage
    //Magic Weapons
    STAFF, // high damage, Skill cooldown reduction
    WAND; // max damage

    public Material getMaterial() {
        switch (this) {
            case SWORD:
                return Material.NETHERITE_PICKAXE; // return Material.NETHERITE_SWORD;
            case DAGGER:
                return Material.NETHERITE_HOE;
            case BATTLE_AXE:
                return Material.NETHERITE_AXE;
            case WAR_HAMMER:
                return Material.NETHERITE_SWORD; // return Material.NETHERITE_PICKAXE;
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
            case WAR_HAMMER:
                return WeaponAttackDamage.NORMAL;
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

    public float getCriticalChance() {
        switch (this) {
            case SWORD:
            case GREAT_SWORD:
            case SPEAR:
            case BOW:
            case WAND:
                return 0.07f;
            case STAFF:
            case DAGGER:
                return 0.05f;
            case BATTLE_AXE:
            case WAR_HAMMER:
            case CROSSBOW:
                return 0.09f;
        }

        return 0.05f;
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

    public float getMeleeDamageReduction() {
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
                return 0.2f;
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
        /*if (this.equals(WeaponGearType.WAR_HAMMER)) {
            ArrayList<Float> speedList = new ArrayList<>();
            speedList.add(1.2);
            speedList.add(1.2);
            speedList.add(1.2);
            speedList.add(1.2);
            PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speedList, true, 0);

            ArrayList<LivingEntity> targets = new ArrayList<>();
            targets.add(target);
            pushMechanic.execute(caster, 1, targets, 0);
        }*/
    }

    public String getItemLoreAddition() {
        switch (this) {
            case WAR_HAMMER:
                return ChatPalette.PURPLE_LIGHT + "Sweep Attack";
            case STAFF:
                return ChatPalette.PURPLE_LIGHT + "Ability Haste: " + ChatPalette.GRAY + "+20";
        }

        return null;
    }

    public ArrayList<Enchantment> getEnchantments() {
        ArrayList<Enchantment> list = new ArrayList<>();

        if (this == WeaponGearType.SPEAR) {
            list.add(Enchantment.LOYALTY);
        }

        return list;
    }

    public boolean isTwoHanded() {
        switch (this) {
            case SWORD:
            case DAGGER:
                return false;
            case BATTLE_AXE:
            case WAR_HAMMER:
            case GREAT_SWORD:
            case SPEAR:
            case BOW:
            case CROSSBOW:
            case STAFF:
            case WAND:
                return true;
        }

        return true;
    }
}
