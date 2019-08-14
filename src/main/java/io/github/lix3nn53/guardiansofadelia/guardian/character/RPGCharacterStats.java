package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.*;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RPGCharacterStats {

    private final Player player;
    private final Attribute fire = new Attribute(AttributeType.FIRE);
    private final Attribute lightning = new Attribute(AttributeType.LIGHTNING);
    private final Attribute earth = new Attribute(AttributeType.EARTH);
    private final Attribute water = new Attribute(AttributeType.WATER);
    private final Attribute wind = new Attribute(AttributeType.WIND);
    private int totalExp;
    private int maxHealth = 100;
    private int maxMana = 100;
    private int currentMana = 100;
    private int defense = 1;
    private int magicDefense = 1;
    private double criticalChance = 0.05;
    private double criticalDamageBonus = 0.5;
    //armor slots
    private ArmorStatHolder helmet;
    private ArmorStatHolder chestplate;
    private ArmorStatHolder leggings;
    private ArmorStatHolder boots;
    //offhand slot
    private ArmorStatHolder shield;
    private int damageBonusFromOffhand = 0;

    //buff multipliers from skills
    private double physicalDamageBuff = 1;
    private double magicalDamageBuff = 1;
    private double physicalDefenseBuff = 1;
    private double magicalDefenseBuff = 1;
    private double criticalChanceBonusBuff = 0;
    private double criticalDamageBonusBuff = 0;

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

        onMaxHealthChange();

        //start action bar scheduler
        new BukkitRunnable() {
            @Override
            public void run() {
                String message = ChatColor.RED + "❤" + ((int) (player.getHealth() + 0.5)) + "/" + getTotalMaxHealth() + "                    " + ChatColor.AQUA + "✧" + currentMana + "/" + getTotalMaxMana();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 5L, 10L);
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
        int level = RPGCharacterExperienceManager.getLevel(totalExp);
        player.setLevel(level);
        player.setExp(0);
    }

    public void giveExp(int give) {
        if (player.getLevel() >= 90) return; //last level is 90

        int currentLevel = RPGCharacterExperienceManager.getLevel(this.totalExp);

        this.totalExp += give;

        int newLevel = RPGCharacterExperienceManager.getLevel(this.totalExp);

        if (currentLevel < newLevel) { //level up
            player.setLevel(newLevel);
            currentLevel = newLevel;

            playLevelUpAnimation();

            onMaxHealthChange();
        }

        float requiredExperience = RPGCharacterExperienceManager.getRequiredExperience(currentLevel);
        float currentExperience = RPGCharacterExperienceManager.getCurrentExperience(this.totalExp, currentLevel);
        float percentage = currentExperience / requiredExperience;
        player.setExp(percentage);
    }

    private void playLevelUpAnimation() {
        Location location = player.getLocation().add(0, 2.4, 0);
        CustomSound customSound = GoaSound.LEVEL_UP.getCustomSound();
        customSound.play(location);

        new BukkitRunnable() {

            ArmorStand armorStand;
            ArmorStand rider;
            int ticksPass = 0;
            int ticksLimit = 100;

            @Override
            public void run() {
                if (ticksPass == ticksLimit) {
                    cancel();
                    armorStand.remove();
                    rider.remove();
                } else if (ticksPass == 0) {
                    rider = new Hologram(location).getArmorStand();
                    armorStand = new Hologram(location, rider).getArmorStand();

                    ItemStack holoItem = new ItemStack(Material.STONE_PICKAXE);
                    ItemMeta im = holoItem.getItemMeta();
                    im.setCustomModelData(10000034);
                    holoItem.setItemMeta(im);

                    MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
                    DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
                    watcher.setItemStack(holoItem);

                    DisguiseAPI.disguiseToAll(rider, disguise);
                }
                Location location = player.getLocation().add(0, 2.4, 0);
                armorStand.eject();
                armorStand.teleport(location);
                armorStand.addPassenger(rider);
                ticksPass++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 2L);
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

    public void consumeMana(int manaToConsume) {
        this.currentMana -= manaToConsume;
        if (this.currentMana < 0) this.currentMana = 0;
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

        int bonusHealthForLevel = 0;
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGClass rpgClass = guardianData.getActiveCharacter().getRpgClass();
                int level = RPGCharacterExperienceManager.getLevel(totalExp);
                bonusHealthForLevel = rpgClass.getBonusHealthForLevel(level);
            }
        }

        return (int) (totalMaxHealth + earth.getIncrement() + bonusHealthForLevel + 0.5);
    }

    public int getTotalMaxMana() {
        return (int) (maxMana + water.getIncrement() + 0.5);
    }

    public int getTotalDefense() {
        return (int) ((defense + helmet.getDefense() + chestplate.getDefense() + leggings.getDefense() + boots.getDefense() + shield.getDefense()) * physicalDefenseBuff + 0.5);
    }

    public int getTotalMagicDefense() {
        return (int) ((magicDefense + helmet.getMagicDefense() + chestplate.getMagicDefense() + leggings.getMagicDefense() + boots.getMagicDefense() + shield.getMagicDefense()) * magicalDefenseBuff + 0.5);
    }

    public double getTotalCriticalChance() {
        double chance = criticalChance + wind.getIncrement();
        if (chance > 0.4) {
            chance = 0.4;
        }

        chance += criticalChanceBonusBuff;

        return chance;
    }

    public double getTotalCriticalDamageBonus() {
        return criticalDamageBonus + criticalDamageBonusBuff;
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
        return (int) (lightningBonus * magicalDamageBuff + 0.5);
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
        return (int) (bonus * physicalDamageBuff + 0.5);
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

            if (statType == StatType.HYBRID) {
                StatHybrid statHybrid = (StatHybrid) StatUtils.getStat(itemInMainHand);
                return statHybrid.getRangedDamage() + fireBonus;
            }
        }
        return (int) (fireBonus * physicalDamageBuff + 0.5);
    }

    public void resetAttributes() {
        fire.setInvested(0, this, false);
        lightning.setInvested(0, this, false);
        earth.setInvested(0, this, false);
        water.setInvested(0, this, false);
        wind.setInvested(0, this, false);

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public int getInvestedAttributePoints() {
        return this.fire.getInvested() + water.getInvested() + earth.getInvested() + lightning.getInvested() + wind.getInvested();
    }

    public int getAttributePointsLeftToSpend() {
        int totalExp = getTotalExp();
        int level = RPGCharacterExperienceManager.getLevel(totalExp);

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

    public void onArmorEquip(ItemStack itemStack, boolean fixDisplay) {
        Material material = itemStack.getType();
        ArmorType armorType = ArmorType.getArmorType(material);
        if (armorType != null) {
            int health = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "health")) {
                health = PersistentDataContainerUtil.getInteger(itemStack, "health");
            }

            int defense = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "defense")) {
                defense = PersistentDataContainerUtil.getInteger(itemStack, "defense");
            }

            int magicDefense = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "magicDefense")) {
                magicDefense = PersistentDataContainerUtil.getInteger(itemStack, "magicDefense");
            }

            switch (armorType) {
                case HELMET:
                    helmet = new ArmorStatHolder(health, defense, magicDefense);
                    setPassiveStatBonuses(EquipmentSlot.HEAD, itemStack, fixDisplay);
                    break;
                case CHESTPLATE:
                    chestplate = new ArmorStatHolder(health, defense, magicDefense);
                    setPassiveStatBonuses(EquipmentSlot.CHEST, itemStack, fixDisplay);
                    break;
                case LEGGINGS:
                    leggings = new ArmorStatHolder(health, defense, magicDefense);
                    setPassiveStatBonuses(EquipmentSlot.LEGS, itemStack, fixDisplay);
                    break;
                case BOOTS:
                    boots = new ArmorStatHolder(health, defense, magicDefense);
                    setPassiveStatBonuses(EquipmentSlot.FEET, itemStack, fixDisplay);
                    break;
            }

            if (fixDisplay) {
                onMaxHealthChange();
            }
        }
    }

    public void onOffhandEquip(ItemStack itemStack, boolean fixDisplay) {
        Material material = itemStack.getType();
        if (material.equals(Material.SHIELD)) {
            int health = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "health")) {
                health = PersistentDataContainerUtil.getInteger(itemStack, "health");
            }

            int defense = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "defense")) {
                defense = PersistentDataContainerUtil.getInteger(itemStack, "defense");
            }

            int magicDefense = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "magicDefense")) {
                magicDefense = PersistentDataContainerUtil.getInteger(itemStack, "magicDefense");
            }

            shield = new ArmorStatHolder(health, defense, magicDefense);
            setPassiveStatBonuses(EquipmentSlot.OFF_HAND, itemStack, fixDisplay);
        } else if (material.equals(Material.DIAMOND_HOE)) {
            StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
            int damage = stat.getValue();
            damageBonusFromOffhand = (int) ((damage * 0.6) + 0.5);
            setPassiveStatBonuses(EquipmentSlot.OFF_HAND, itemStack, fixDisplay);
        }
    }

    public void onOffhandUnequip(ItemStack offhand) {
        Material material = offhand.getType();
        if (material.equals(Material.SHIELD)) {
            shield = new ArmorStatHolder(0, 0, 0);
            removePassiveStatBonuses(EquipmentSlot.OFF_HAND);
        } else if (material.equals(Material.DIAMOND_HOE)) {
            damageBonusFromOffhand = 0;
            removePassiveStatBonuses(EquipmentSlot.OFF_HAND);
        }
    }

    private void setPassiveStatBonuses(EquipmentSlot equipmentSlot, ItemStack itemStack, boolean fixDisplay) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "fire")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "fire");
            getFire().setBonus(player, equipmentSlot, this, bonus, false);
        } else {
            getFire().setBonus(player, equipmentSlot, this, 0, false);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "water")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "water");
            getWater().setBonus(player, equipmentSlot, this, bonus, fixDisplay);
        } else {
            getWater().setBonus(player, equipmentSlot, this, 0, false);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "earth")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "earth");
            getEarth().setBonus(player, equipmentSlot, this, bonus, fixDisplay);
        } else {
            getEarth().setBonus(player, equipmentSlot, this, 0, false);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "lightning")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "lightning");
            getLightning().setBonus(player, equipmentSlot, this, bonus, false);
        } else {
            getLightning().setBonus(player, equipmentSlot, this, 0, false);
        }
        if (PersistentDataContainerUtil.hasInteger(itemStack, "wind")) {
            int bonus = PersistentDataContainerUtil.getInteger(itemStack, "wind");
            getWind().setBonus(player, equipmentSlot, this, bonus, false);
        } else {
            getWind().setBonus(player, equipmentSlot, this, 0, false);
        }
    }

    private void removePassiveStatBonuses(EquipmentSlot equipmentSlot) {
        getFire().removeBonus(equipmentSlot, this, false);
        getWater().removeBonus(equipmentSlot, this, false);
        getEarth().removeBonus(equipmentSlot, this, false);
        getLightning().removeBonus(equipmentSlot, this, false);
        getWind().removeBonus(equipmentSlot, this, false);

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public void recalculateRPGInventory(RPGInventory rpgInventory) {
        getFire().clearPassive(this, false);
        getWater().clearPassive(this, false);
        getEarth().clearPassive(this, false);
        getLightning().clearPassive(this, false);
        getWind().clearPassive(this, false);

        StatPassive totalPassiveStat = rpgInventory.getTotalPassiveStat();
        getFire().addBonusToPassive(totalPassiveStat.getFire(), this, false);
        getWater().addBonusToPassive(totalPassiveStat.getWater(), this, false);
        getEarth().addBonusToPassive(totalPassiveStat.getEarth(), this, false);
        getLightning().addBonusToPassive(totalPassiveStat.getLightning(), this, false);
        getWind().addBonusToPassive(totalPassiveStat.getWind(), this, false);

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public void recalculateEquipment(RPGClass rpgClass) {
        getFire().clearEquipment(this, false);
        getWater().clearEquipment(this, false);
        getEarth().clearEquipment(this, false);
        getLightning().clearEquipment(this, false);
        getWind().clearEquipment(this, false);

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
                setPassiveStatBonuses(EquipmentSlot.HAND, itemInMainHand, false);
            }
        }

        ItemStack itemInOffHand = inventory.getItemInOffHand();
        if (!InventoryUtils.isAirOrNull(itemInOffHand)) {
            Material type = itemInOffHand.getType();
            if ((type.equals(Material.SHIELD) || type.equals(Material.DIAMOND_HOE)) && StatUtils.doesCharacterMeetRequirements(itemInOffHand, player, rpgClass)) {
                onOffhandEquip(itemInOffHand, false);
            } else {
                InventoryUtils.giveItemToPlayer(player, itemInOffHand);
                itemInOffHand.setAmount(0);
            }
        }

        ItemStack inventoryHelmet = inventory.getHelmet();
        if (!InventoryUtils.isAirOrNull(inventoryHelmet)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryHelmet, player, rpgClass)) {
                onArmorEquip(inventoryHelmet, false);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryHelmet);
                inventoryHelmet.setAmount(0);
            }
        }

        ItemStack inventoryChestplate = inventory.getChestplate();
        if (!InventoryUtils.isAirOrNull(inventoryChestplate)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryChestplate, player, rpgClass)) {
                onArmorEquip(inventoryChestplate, false);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryChestplate);
                inventoryChestplate.setAmount(0);
            }
        }

        ItemStack inventoryLeggings = inventory.getLeggings();
        if (!InventoryUtils.isAirOrNull(inventoryLeggings)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryLeggings, player, rpgClass)) {
                onArmorEquip(inventoryLeggings, false);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryLeggings);
                inventoryLeggings.setAmount(0);
            }
        }

        ItemStack inventoryBoots = inventory.getBoots();
        if (!InventoryUtils.isAirOrNull(inventoryBoots)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryBoots, player, rpgClass)) {
                onArmorEquip(inventoryBoots, false);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryBoots);
                inventoryBoots.setAmount(0);
            }
        }

        onMaxHealthChange();
        onCurrentManaChange();
    }


    public int getTotalDamageBonusFromOffhand() {
        return (int) (damageBonusFromOffhand * physicalDamageBuff + 0.5);
    }

    public boolean setMainHandBonuses(ItemStack itemStack, RPGClass rpgClass, boolean fixDisplay) {
        if (StatUtils.doesCharacterMeetRequirements(itemStack, player, rpgClass)) {

            //manage stats on item drop
            if (PersistentDataContainerUtil.hasInteger(itemStack, "fire")) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, "fire");
                getFire().setBonus(player, EquipmentSlot.HAND, this, bonus, false);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "water")) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, "water");
                getWater().setBonus(player, EquipmentSlot.HAND, this, bonus, fixDisplay);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "earth")) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, "earth");
                getEarth().setBonus(player, EquipmentSlot.HAND, this, bonus, fixDisplay);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "lightning")) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, "lightning");
                getLightning().setBonus(player, EquipmentSlot.HAND, this, bonus, false);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "wind")) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, "wind");
                getWind().setBonus(player, EquipmentSlot.HAND, this, bonus, false);
            }
            return true;
        }
        return false;
    }

    public boolean removeMainHandBonuses(ItemStack itemStack, RPGClass rpgClass, boolean fixDisplay) {
        if (StatUtils.doesCharacterMeetRequirements(itemStack, player, rpgClass)) {

            //manage stats on item drop
            if (PersistentDataContainerUtil.hasInteger(itemStack, "fire")) {
                getFire().removeBonus(EquipmentSlot.HAND, this, false);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "water")) {
                getWater().removeBonus(EquipmentSlot.HAND, this, fixDisplay);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "earth")) {
                getEarth().removeBonus(EquipmentSlot.HAND, this, fixDisplay);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "lightning")) {
                getLightning().removeBonus(EquipmentSlot.HAND, this, false);
            }
            if (PersistentDataContainerUtil.hasInteger(itemStack, "wind")) {
                getWind().removeBonus(EquipmentSlot.HAND, this, false);
            }
            return true;
        }
        return false;
    }

    public void clearMainHandBonuses(RPGClass rpgClass, boolean fixDisplay) {
        getFire().removeBonus(EquipmentSlot.HAND, this, false);
        getWater().removeBonus(EquipmentSlot.HAND, this, false);
        getEarth().removeBonus(EquipmentSlot.HAND, this, false);
        getLightning().removeBonus(EquipmentSlot.HAND, this, false);
        getWind().removeBonus(EquipmentSlot.HAND, this, false);

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public void addToBuffMultiplier(BuffType buffType, double addToMultiplier) {
        if (buffType.equals(BuffType.PHYSICAL_DAMAGE)) {
            this.physicalDamageBuff += addToMultiplier;
        } else if (buffType.equals(BuffType.MAGIC_DAMAGE)) {
            this.magicalDamageBuff += addToMultiplier;
        } else if (buffType.equals(BuffType.PHYSICAL_DEFENSE)) {
            this.physicalDefenseBuff += addToMultiplier;
        } else if (buffType.equals(BuffType.MAGIC_DEFENSE)) {
            this.magicalDefenseBuff += addToMultiplier;
        } else if (buffType.equals(BuffType.CRIT_DAMAGE)) {
            this.criticalDamageBonusBuff += addToMultiplier;
        } else if (buffType.equals(BuffType.CRIT_CHANCE)) {
            this.criticalChanceBonusBuff += addToMultiplier;
        }
    }

    public double getBuffMultiplier(BuffType buffType) {
        if (buffType.equals(BuffType.PHYSICAL_DAMAGE)) {
            return this.physicalDamageBuff;
        } else if (buffType.equals(BuffType.MAGIC_DAMAGE)) {
            return this.magicalDamageBuff;
        } else if (buffType.equals(BuffType.PHYSICAL_DEFENSE)) {
            return this.physicalDefenseBuff;
        } else if (buffType.equals(BuffType.MAGIC_DEFENSE)) {
            return this.magicalDefenseBuff;
        } else if (buffType.equals(BuffType.CRIT_DAMAGE)) {
            return this.criticalDamageBonusBuff;
        } else if (buffType.equals(BuffType.CRIT_CHANCE)) {
            return this.criticalChanceBonusBuff;
        }
        return 1;
    }
}
