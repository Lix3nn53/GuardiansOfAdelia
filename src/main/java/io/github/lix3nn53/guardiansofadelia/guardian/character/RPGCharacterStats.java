package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.stats.*;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
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

public class RPGCharacterStats {

    private final Player player;
    private String rpgClassStr;
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
    private double baseCriticalChance = 0.05;
    private double baseCriticalDamageBonus = 0.6;
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

    private GearSetEffect armorGearTypeSetEffect = null;

    public RPGCharacterStats(Player player, String rpgClassStr) {
        this.player = player;
        this.rpgClassStr = rpgClassStr;

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

    public void setRpgClassStr(String rpgClassStr) {
        this.rpgClassStr = rpgClassStr;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
        int level = RPGCharacterExperienceManager.getLevel(totalExp);
        player.setLevel(level);
        updateExpBar(level);
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
            sendLevelUpMessage(newLevel);
            player.sendTitle(ChatColor.GOLD + "Level Up!", ChatColor.YELLOW + "Your new level is " + ChatColor.GOLD + newLevel, 30, 80, 30);
            player.setHealth(player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue());
            setCurrentMana(getTotalMaxMana());
        }

        updateExpBar(currentLevel);
    }

    private void updateExpBar(int currentLevel) {
        float requiredExperience = RPGCharacterExperienceManager.getRequiredExperience(currentLevel);
        float currentExperience = RPGCharacterExperienceManager.getCurrentExperience(this.totalExp, currentLevel);
        float percentage = currentExperience / requiredExperience;
        if (percentage >= 1) {
            percentage = 0.99f;
        }
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
                    im.setCustomModelData(34);
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

        return (int) (totalMaxHealth + earth.getIncrement(player.getLevel(), rpgClassStr) + 0.5);
    }

    public int getTotalMaxMana() {
        return (int) (maxMana + water.getIncrement(player.getLevel(), rpgClassStr) + 0.5);
    }

    public int getTotalDefense() {
        return (int) ((defense + helmet.getDefense() + chestplate.getDefense() + leggings.getDefense() + boots.getDefense() + shield.getDefense()) * physicalDefenseBuff + 0.5);
    }

    public int getTotalMagicDefense() {
        return (int) ((magicDefense + helmet.getMagicDefense() + chestplate.getMagicDefense() + leggings.getMagicDefense() + boots.getMagicDefense() + shield.getMagicDefense()) * magicalDefenseBuff + 0.5);
    }

    public double getTotalCriticalChance() {
        double chance = baseCriticalChance + wind.getIncrement(player.getLevel(), rpgClassStr);
        if (chance > 0.4) {
            chance = 0.4;
        }

        chance += criticalChanceBonusBuff;

        return chance;
    }

    public double getTotalCriticalDamageBonus() {
        return baseCriticalDamageBonus + criticalDamageBonusBuff;
    }

    public int getTotalMagicDamage(Player player, String rpgClass) {
        int lightningBonus = (int) (lightning.getIncrement(player.getLevel(), rpgClass) + 0.5);

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        if (type.equals(Material.DIAMOND_SHOVEL)) {
            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) return lightningBonus;

            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "magicDamage")) {
                return lightningBonus + PersistentDataContainerUtil.getInteger(itemInMainHand, "magicDamage");
            }
        }
        return (int) (lightningBonus * magicalDamageBuff + 0.5);
    }

    public int getTotalMeleeDamage(Player player, String rpgClass) {
        int bonus = (int) (fire.getIncrement(player.getLevel(), rpgClass) + 0.5) + damageBonusFromOffhand;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) return bonus;

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

    public int getTotalRangedDamage(Player player, String rpgClass) {
        int fireBonus = (int) (fire.getIncrement(player.getLevel(), rpgClass) + 0.5);

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        if (type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) return fireBonus;

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
        ArmorSlot armorSlot = ArmorSlot.getArmorSlot(material);
        if (armorSlot != null) {
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

            switch (armorSlot) {
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

    public void recalculateEquipment(String rpgClass) {
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

        ItemStack itemInMainHand = inventory.getItem(4);
        if (!InventoryUtils.isAirOrNull(itemInMainHand)) {
            if (StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) {
                setPassiveStatBonuses(EquipmentSlot.HAND, itemInMainHand, false);
            }
        }

        ItemStack itemInOffHand = inventory.getItemInOffHand();
        if (!InventoryUtils.isAirOrNull(itemInOffHand)) {
            Material type = itemInOffHand.getType();
            if ((type.equals(Material.SHIELD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.ARROW) ||
                    type.equals(Material.TIPPED_ARROW) || type.equals(Material.SPECTRAL_ARROW)) &&
                    StatUtils.doesCharacterMeetRequirements(itemInOffHand, player, rpgClass)) {
                onOffhandEquip(itemInOffHand, false);
            } else {
                InventoryUtils.giveItemToPlayer(player, itemInOffHand);
                itemInOffHand.setAmount(0);
            }
        }

        ItemStack inventoryHelmet = inventory.getHelmet();
        ArmorGearType helmetType = null;
        if (!InventoryUtils.isAirOrNull(inventoryHelmet)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryHelmet, player, rpgClass)) {
                onArmorEquip(inventoryHelmet, false);
                helmetType = ArmorGearType.typeOf(inventoryHelmet);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryHelmet);
                inventoryHelmet.setAmount(0);
            }
        }

        ItemStack inventoryChestplate = inventory.getChestplate();
        ArmorGearType chestplateType = null;
        if (!InventoryUtils.isAirOrNull(inventoryChestplate)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryChestplate, player, rpgClass)) {
                onArmorEquip(inventoryChestplate, false);
                chestplateType = ArmorGearType.typeOf(inventoryChestplate);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryChestplate);
                inventoryChestplate.setAmount(0);
            }
        }

        ItemStack inventoryLeggings = inventory.getLeggings();
        ArmorGearType leggingsType = null;
        if (!InventoryUtils.isAirOrNull(inventoryLeggings)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryLeggings, player, rpgClass)) {
                onArmorEquip(inventoryLeggings, false);
                leggingsType = ArmorGearType.typeOf(inventoryLeggings);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryLeggings);
                inventoryLeggings.setAmount(0);
            }
        }

        ItemStack inventoryBoots = inventory.getBoots();
        ArmorGearType bootsType = null;
        if (!InventoryUtils.isAirOrNull(inventoryBoots)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryBoots, player, rpgClass)) {
                onArmorEquip(inventoryBoots, false);
                bootsType = ArmorGearType.typeOf(inventoryBoots);
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryBoots);
                inventoryBoots.setAmount(0);
            }
        }

        onMaxHealthChange();
        onCurrentManaChange();
        boolean wearingSameArmorType = isWearingSameArmorType(helmetType, chestplateType, leggingsType, bootsType);
        if (wearingSameArmorType) {
            GearSetEffect setEffect = helmetType.getSetEffect();
            if (this.armorGearTypeSetEffect != null) {
                if (this.armorGearTypeSetEffect.equals(setEffect)) return;
            }
            setEffect.applySetEffect(player);
            this.armorGearTypeSetEffect = setEffect;
        } else if (this.armorGearTypeSetEffect != null) {
            this.armorGearTypeSetEffect.clearSetEffect(player);
            this.armorGearTypeSetEffect = null;
        }
    }

    private boolean isWearingSameArmorType(ArmorGearType helmet, ArmorGearType chestplate, ArmorGearType leggings, ArmorGearType boots) {
        if (helmet == null || chestplate == null || leggings == null || boots == null) return false;

        return helmet == chestplate && helmet == leggings && helmet == boots;
    }

    public int getTotalDamageBonusFromOffhand() {
        return (int) (damageBonusFromOffhand * physicalDamageBuff + 0.5);
    }

    public boolean setMainHandBonuses(ItemStack itemStack, String rpgClass, boolean fixDisplay) {
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

    public boolean removeMainHandBonuses(ItemStack itemStack, String rpgClass, boolean fixDisplay) {
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

    public void clearMainHandBonuses() {
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

    private void sendLevelUpMessage(int newLevel) {
        MessageUtils.sendCenteredMessage(player, ChatColor.GRAY + "------------------------");
        MessageUtils.sendCenteredMessage(player, ChatColor.GOLD + "Level up!");
        MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "Congratulations, your new level is " + ChatColor.GOLD + newLevel + "");

        int lastNum = newLevel % 10;
        switch (lastNum) {
            case 0:
                player.sendMessage("");
                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "You can equip stronger " + ChatColor.GOLD + "weapons");
                break;
            case 2:
                player.sendMessage("");
                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "You can equip stronger " + ChatColor.GOLD + "boots!");
                break;
            case 4:
                player.sendMessage("");
                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "You can equip stronger " + ChatColor.GOLD + "helmets");
                break;
            case 6:
                player.sendMessage("");
                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "You can equip stronger " + ChatColor.GOLD + "leggings");
                break;
            case 8:
                player.sendMessage("");
                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "You can equip stronger " + ChatColor.GOLD + "chestplates");
                break;
        }

        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        int fireBonus = rpgClass.getAttributeBonusForLevel(AttributeType.FIRE, newLevel) - rpgClass.getAttributeBonusForLevel(AttributeType.FIRE, newLevel - 1);
        int waterBonus = rpgClass.getAttributeBonusForLevel(AttributeType.WATER, newLevel) - rpgClass.getAttributeBonusForLevel(AttributeType.WATER, newLevel - 1);
        int earthBonus = rpgClass.getAttributeBonusForLevel(AttributeType.EARTH, newLevel) - rpgClass.getAttributeBonusForLevel(AttributeType.EARTH, newLevel - 1);
        int lightningBonus = rpgClass.getAttributeBonusForLevel(AttributeType.LIGHTNING, newLevel) - rpgClass.getAttributeBonusForLevel(AttributeType.LIGHTNING, newLevel - 1);
        int windBonus = rpgClass.getAttributeBonusForLevel(AttributeType.WIND, newLevel) - rpgClass.getAttributeBonusForLevel(AttributeType.WIND, newLevel - 1);

        if (fireBonus + waterBonus + earthBonus + lightningBonus + windBonus > 0) {
            player.sendMessage("");
            MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "Stats Gained");
            final StringBuilder sb = new StringBuilder();
            if (fireBonus > 0) sb.append(ChatColor.RED + "+" + fireBonus + " Fire ");
            if (waterBonus > 0) sb.append(ChatColor.BLUE + "+" + waterBonus + " Water ");
            if (earthBonus > 0) sb.append(ChatColor.DARK_GREEN + "+" + earthBonus + " Earth ");
            if (lightningBonus > 0) sb.append(ChatColor.AQUA + "+" + lightningBonus + " Lightning ");
            if (windBonus > 0) sb.append(ChatColor.WHITE + "+" + windBonus + " Wind");
            MessageUtils.sendCenteredMessage(player, sb.toString());
        }

        MessageUtils.sendCenteredMessage(player, ChatColor.GRAY + "------------------------");
    }
}
