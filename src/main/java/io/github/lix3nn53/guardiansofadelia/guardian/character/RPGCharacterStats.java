package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.*;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class RPGCharacterStats {

    private final Player player;

    private int totalExp;
    private final Attribute fire = new Attribute(AttributeType.FIRE);
    private final Attribute lightning = new Attribute(AttributeType.LIGHTNING);
    private final Attribute earth = new Attribute(AttributeType.EARTH);
    private final Attribute water = new Attribute(AttributeType.WATER);
    private final Attribute wind = new Attribute(AttributeType.WIND);
    private final List<Integer> investedSkillPoints = new ArrayList<>();
    private int maxHealth = 20;
    private int maxMana = 20;
    private int currentMana = 20;
    private int defense = 1;
    private int magicDefense = 1;
    private double criticalChance = 0.05;
    //armor slots
    private ArmorStatHolder helmet;
    private ArmorStatHolder chestplate;
    private ArmorStatHolder leggings;
    private ArmorStatHolder boots;
    //offhand slot
    private ArmorStatHolder shield;
    private int damageBonusFromOffhand = 0;

    public RPGCharacterStats(Player player) {
        this.player = player;

        player.setLevel(1);
        player.setHealthScale(20);

        helmet = new ArmorStatHolder(0, 0, 0);
        chestplate = new ArmorStatHolder(0, 0, 0);
        leggings = new ArmorStatHolder(0, 0, 0);
        boots = new ArmorStatHolder(0, 0, 0);

        //offhand slot
        shield = new ArmorStatHolder(0, 0, 0);

        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);

        //start action bar scheduler
        new BukkitRunnable() {
            @Override
            public void run() {
                String message = ChatColor.RED + "❤" + player.getHealth() + 0.5 + "/" + getTotalMaxHealth() + "                    " + ChatColor.AQUA + "✧" + currentMana + "/" + getTotalMaxMana();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 5L, 10L);
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
        int levelFromTotalExperience = RPGCharacterExperienceManager.getLevelFromTotalExperience(totalExp);
        player.setLevel(levelFromTotalExperience);
    }

    public void giveExp(int give) {
        int currentLevel = player.getLevel();

        this.totalExp += give;

        int totalExpForLevel = RPGCharacterExperienceManager.getTotalExpForLevel(currentLevel + 1);

        if (totalExp >= totalExpForLevel) {
            int levelFromTotalExperience = RPGCharacterExperienceManager.getLevelFromTotalExperience(totalExp);
            player.setLevel(levelFromTotalExperience);
        }
    }

    public void setCurrentHealth(int currentHealth) {
        player.setHealth(currentHealth);
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
        onCurrentManaChange();
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Attribute getFire() {
        return fire;
    }

    public Attribute getLightning() {
        return lightning;
    }

    public Attribute getEarth() {
        return earth;
    }

    public Attribute getWater() {
        return water;
    }

    public Attribute getWind() {
        return wind;
    }

    public int getTotalMaxHealth() {
        int totalMaxHealth = maxHealth;

        if (helmet != null) {
            totalMaxHealth += helmet.getMaxHealth();
        }
        if (chestplate != null) {
            totalMaxHealth += chestplate.getMaxHealth();
        }
        if (leggings != null) {
            totalMaxHealth += leggings.getMaxHealth();
        }
        if (boots != null) {
            totalMaxHealth += boots.getMaxHealth();
        }
        if (shield != null) {
            totalMaxHealth += shield.getMaxHealth();
        }

        return (int) (totalMaxHealth + earth.getIncrement() + 0.5);
    }

    public int getTotalMaxMana() {
        return (int) (maxMana + water.getIncrement() + 0.5);
    }

    public int getTotalDefense() {
        return defense + helmet.getDefense() + chestplate.getDefense() + leggings.getDefense() + boots.getDefense() + shield.getDefense();
    }

    public int getTotalMagicDefense() {
        return magicDefense + helmet.getMagicDefense() + chestplate.getMagicDefense() + leggings.getMagicDefense() + boots.getMagicDefense() + shield.getMagicDefense();
    }

    public double getTotalCriticalChance() {
        return criticalChance + wind.getIncrement();
    }

    public int getTotalMagicDamage(Player player, RPGClass rpgClass) {
        int lightningBonus = (int) (lightning.getIncrement() + 0.5);

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        if (type.equals(Material.DIAMOND_SHOVEL)) {
            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "reqLevel")) {
                int reqLevel = PersistentDataContainerUtil.getInteger(itemInMainHand, "reqLevel");
                if (player.getLevel() < reqLevel) {
                    return lightningBonus;
                }
            }

            if (PersistentDataContainerUtil.hasString(itemInMainHand, "reqClass")) {
                String reqClassString = PersistentDataContainerUtil.getString(itemInMainHand, "reqClass");
                RPGClass reqClass = RPGClass.valueOf(reqClassString);
                if (!rpgClass.equals(reqClass)) {
                    return lightningBonus;
                }
            }

            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "magicDamage")) {
                return lightningBonus + PersistentDataContainerUtil.getInteger(itemInMainHand, "magicDamage");
            }
        }
        return lightningBonus;
    }

    public int getTotalMeleeDamage(Player player, RPGClass rpgClass) {
        int bonus = (int) (fire.getIncrement() + 0.5) + damageBonusFromOffhand;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "reqLevel")) {
                int reqLevel = PersistentDataContainerUtil.getInteger(itemInMainHand, "reqLevel");
                if (player.getLevel() < reqLevel) {
                    return bonus;
                }
            }

            if (PersistentDataContainerUtil.hasString(itemInMainHand, "reqClass")) {
                String reqClassString = PersistentDataContainerUtil.getString(itemInMainHand, "reqClass");
                RPGClass reqClass = RPGClass.valueOf(reqClassString);
                if (!rpgClass.equals(reqClass)) {
                    return bonus;
                }
            }

            StatType statType = StatUtils.getStatType(type);

            switch (statType) {
                case MELEE:
                    StatOneType stat = (StatOneType) StatUtils.getStat(itemInMainHand);
                    return stat.getValue() + bonus;
                case HYBRID:
                    StatHybrid statHybrid = (StatHybrid) StatUtils.getStat(itemInMainHand);
                    return statHybrid.getMeleeDamage() + bonus;
                case MAGICAL:
                    StatMagical statMagical = (StatMagical) StatUtils.getStat(itemInMainHand);
                    return statMagical.getMeleeDamage() + bonus;
            }
        }
        return bonus;
    }

    public int getTotalRangedDamage(Player player, RPGClass rpgClass) {
        int fireBonus = (int) (fire.getIncrement() + 0.5);

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        if (type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "reqLevel")) {
                int reqLevel = PersistentDataContainerUtil.getInteger(itemInMainHand, "reqLevel");
                if (player.getLevel() < reqLevel) {
                    return fireBonus;
                }
            }

            if (PersistentDataContainerUtil.hasString(itemInMainHand, "reqClass")) {
                String reqClassString = PersistentDataContainerUtil.getString(itemInMainHand, "reqClass");
                RPGClass reqClass = RPGClass.valueOf(reqClassString);
                if (!rpgClass.equals(reqClass)) {
                    return fireBonus;
                }
            }

            StatType statType = StatUtils.getStatType(type);

            switch (statType) {
                case HYBRID:
                    StatHybrid statHybrid = (StatHybrid) StatUtils.getStat(itemInMainHand);
                    return statHybrid.getRangedDamage() + fireBonus;
            }
        }
        return fireBonus;
    }

    public void resetAttributes() {
        fire.setInvested(0, this);
        lightning.setInvested(0, this);
        earth.setInvested(0, this);
        water.setInvested(0, this);
        wind.setInvested(0, this);
    }

    public void clearBonuses() {
        fire.clearBonus(this);
        lightning.clearBonus(this);
        earth.clearBonus(this);
        water.clearBonus(this);
        wind.clearBonus(this);
    }

    /**
     * @param skillNo 1,2,3 normal skills, 4 passive, 5 ultimate
     * @param points  to invest in skill
     */
    public void investSkillPoints(int skillNo, int points) {
        int index = skillNo - 1;
        int invested = investedSkillPoints.get(index);
        invested += points;
        investedSkillPoints.set(index, invested);
    }

    public int getInvestedSkillPoints(int skillNo) {
        int index = skillNo - 1;
        return investedSkillPoints.get(index);
    }

    public int getSkillPointsLeftToSpend() {
        int result = 0;

        for (int invested : investedSkillPoints) {
            result += invested;
        }

        return result;
    }

    public int getInvestedAttributePoints() {
        return this.fire.getInvested() + water.getInvested() + earth.getInvested() + lightning.getInvested() + wind.getInvested();
    }

    public int getAttributePointsLeftToSpend() {
        int totalExp = getTotalExp();
        int level = RPGCharacterExperienceManager.getLevelFromTotalExperience(totalExp);

        int inventedPointsOnAttributes = getInvestedAttributePoints();

        int pointsPerLevel = 1;

        return (level * pointsPerLevel) - inventedPointsOnAttributes;
    }

    public void onMaxHealthChange() {
        int totalMaxHealth = getTotalMaxHealth();
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(totalMaxHealth);
        if (player.getHealth() > totalMaxHealth) {
            player.setHealth(totalMaxHealth);
        }
    }

    public void onCurrentManaChange() {
        int totalMaxMana = getTotalMaxMana();
        if (currentMana > totalMaxMana) {
            currentMana = totalMaxMana;
        }

        double ratio = (double) currentMana / totalMaxMana;
        int foodLevel = (int) (20 * ratio + 0.5);

        if (currentMana > 0) {
            if (foodLevel <= 0) {
                foodLevel = 1;
            }
        } else {
            foodLevel = 0;
        }

        player.setFoodLevel(foodLevel);
    }

    public void onArmorEquip(ItemStack armor) {
        Material material = armor.getType();
        ArmorType armorType = ArmorType.getArmorType(material);
        if (armorType != null) {
            int health = 0;
            if (PersistentDataContainerUtil.hasInteger(armor, "health")) {
                health = PersistentDataContainerUtil.getInteger(armor, "health");
            }

            int defense = 0;
            if (PersistentDataContainerUtil.hasInteger(armor, "defense")) {
                defense = PersistentDataContainerUtil.getInteger(armor, "defense");
            }

            int magicDefense = 0;
            if (PersistentDataContainerUtil.hasInteger(armor, "magicDefense")) {
                magicDefense = PersistentDataContainerUtil.getInteger(armor, "magicDefense");
            }

            switch (armorType) {
                case HELMET:
                    helmet = new ArmorStatHolder(health, defense, magicDefense);
                    addPassiveStatBonuses(armor);
                    break;
                case CHESTPLATE:
                    chestplate = new ArmorStatHolder(health, defense, magicDefense);
                    addPassiveStatBonuses(armor);
                    break;
                case LEGGINGS:
                    leggings = new ArmorStatHolder(health, defense, magicDefense);
                    addPassiveStatBonuses(armor);
                    break;
                case BOOTS:
                    boots = new ArmorStatHolder(health, defense, magicDefense);
                    addPassiveStatBonuses(armor);
                    break;
            }
        }
    }

    public void onArmorUnequip(ItemStack armor) {
        Material material = armor.getType();
        ArmorType armorType = ArmorType.getArmorType(material);
        if (armorType != null) {
            switch (armorType) {
                case HELMET:
                    helmet = new ArmorStatHolder(0, 0, 0);
                    removePassiveStatBonuses(armor);
                    break;
                case CHESTPLATE:
                    chestplate = new ArmorStatHolder(0, 0, 0);
                    removePassiveStatBonuses(armor);
                    break;
                case LEGGINGS:
                    leggings = new ArmorStatHolder(0, 0, 0);
                    removePassiveStatBonuses(armor);
                    break;
                case BOOTS:
                    boots = new ArmorStatHolder(0, 0, 0);
                    removePassiveStatBonuses(armor);
                    break;
            }
        }
    }

    public void onOffhandEquip(ItemStack offhand) {
        Material material = offhand.getType();
        if (material.equals(Material.SHIELD)) {
            int health = 0;
            if (PersistentDataContainerUtil.hasInteger(offhand, "health")) {
                health = PersistentDataContainerUtil.getInteger(offhand, "health");
            }

            int defense = 0;
            if (PersistentDataContainerUtil.hasInteger(offhand, "defense")) {
                defense = PersistentDataContainerUtil.getInteger(offhand, "defense");
            }

            int magicDefense = 0;
            if (PersistentDataContainerUtil.hasInteger(offhand, "magicDefense")) {
                magicDefense = PersistentDataContainerUtil.getInteger(offhand, "magicDefense");
            }

            shield = new ArmorStatHolder(health, defense, magicDefense);
            addPassiveStatBonuses(offhand);
        } else if (material.equals(Material.DIAMOND_HOE)) {
            StatOneType stat = (StatOneType) StatUtils.getStat(offhand);
            int damage = stat.getValue();
            damageBonusFromOffhand = (int) ((damage * 0.6) + 0.5);
            addPassiveStatBonuses(offhand);
        }
    }

    public void onOffhandUnequip(ItemStack offhand) {
        Material material = offhand.getType();
        if (material.equals(Material.SHIELD)) {
            shield = new ArmorStatHolder(0, 0, 0);
            removePassiveStatBonuses(offhand);
        } else if (material.equals(Material.DIAMOND_HOE)) {
            damageBonusFromOffhand = 0;
            removePassiveStatBonuses(offhand);
        }
    }

    private void addPassiveStatBonuses(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "fire")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "fire");
            getFire().addBonus(bonus, this);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "water")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "water");
            this.getWater().addBonus(bonus, this);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "earth")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "earth");
            this.getEarth().addBonus(bonus, this);
            onMaxHealthChange();
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "lightning")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "lightning");
            this.getLightning().addBonus(bonus, this);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "wind")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "wind");
            this.getWind().addBonus(bonus, this);
        }
    }

    private void removePassiveStatBonuses(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "fire")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "fire");
            getFire().removeBonus(bonus, this);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "water")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "water");
            this.getWater().removeBonus(bonus, this);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "earth")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "earth");
            this.getEarth().removeBonus(bonus, this);
            onMaxHealthChange();
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "lightning")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "lightning");
            this.getLightning().removeBonus(bonus, this);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "wind")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "wind");
            this.getWind().removeBonus(bonus, this);
        }
    }

    public void recalculateEquipmentBonuses(RPGInventory rpgInventory, RPGClass rpgClass) {
        clearBonuses();

        helmet = new ArmorStatHolder(0, 0, 0);
        chestplate = new ArmorStatHolder(0, 0, 0);
        leggings = new ArmorStatHolder(0, 0, 0);
        boots = new ArmorStatHolder(0, 0, 0);

        //offhand slot
        shield = new ArmorStatHolder(0, 0, 0);
        damageBonusFromOffhand = 0;

        PlayerInventory inventory = player.getInventory();

        ItemStack itemInMainHand = inventory.getItemInMainHand();
        if (!InventoryUtils.isAirOrNull(itemInMainHand)) {
            if (StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) {
                addPassiveStatBonuses(itemInMainHand);
            }
        }

        ItemStack itemInOffHand = inventory.getItemInOffHand();
        if (!InventoryUtils.isAirOrNull(itemInOffHand)) {
            if (StatUtils.doesCharacterMeetRequirements(itemInOffHand, player, rpgClass)) {
                onOffhandEquip(itemInOffHand);
            }
        }

        ItemStack inventoryHelmet = inventory.getHelmet();
        if (!InventoryUtils.isAirOrNull(inventoryHelmet)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryHelmet, player, rpgClass)) {
                onArmorEquip(inventoryHelmet);
            }
        }

        ItemStack inventoryChestplate = inventory.getChestplate();
        if (!InventoryUtils.isAirOrNull(inventoryChestplate)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryChestplate, player, rpgClass)) {
                onArmorEquip(inventoryChestplate);
            }
        }

        ItemStack inventoryLeggings = inventory.getLeggings();
        if (!InventoryUtils.isAirOrNull(inventoryLeggings)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryLeggings, player, rpgClass)) {
                onArmorEquip(inventoryLeggings);
            }
        }

        ItemStack inventoryBoots = inventory.getBoots();
        if (!InventoryUtils.isAirOrNull(inventoryBoots)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryBoots, player, rpgClass)) {
                onArmorEquip(inventoryBoots);
            }
        }

        //rpg inventory bonuses
        StatPassive totalPassiveStat = rpgInventory.getTotalPassiveStat();
        getFire().addBonus(totalPassiveStat.getFire(), this);
        getWater().addBonus(totalPassiveStat.getWater(), this);
        getEarth().addBonus(totalPassiveStat.getEarth(), this);
        getLightning().addBonus(totalPassiveStat.getLightning(), this);
        getWind().addBonus(totalPassiveStat.getWind(), this);
    }

    public int getDamageBonusFromOffhand() {
        return damageBonusFromOffhand;
    }
}
