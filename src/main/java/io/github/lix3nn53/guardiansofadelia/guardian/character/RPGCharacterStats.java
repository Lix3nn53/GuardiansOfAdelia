package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.element.Element;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSet;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatOneType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class RPGCharacterStats {

    private final Player player;
    private String rpgClassStr;

    private final HashMap<AttributeType, Attribute> attributeHashMap = new HashMap<>();

    private final HashMap<ElementType, Element> elementHashMap = new HashMap<>();

    private int totalExp;
    private final int maxHealth = 100;
    private final int maxMana = 100;
    private int currentMana = 100;

    private final double baseAbilityHaste = 0;
    private final double baseCriticalChance = 0.01;
    private final double baseCriticalDamageDamage = 1.5;
    //armor slots
    private ArmorStatHolder helmet;
    private ArmorStatHolder chestplate;
    private ArmorStatHolder leggings;
    private ArmorStatHolder boots;
    //offhand slot
    private ArmorStatHolder shield;
    private int damageBonusFromOffhand = 0;

    //buff multipliers from skills
    private double buffElementDamage = 1;
    private double buffElementDefense = 1;
    private double buffCriticalChance = 0;
    private double buffCriticalDamage = 0;
    private double buffAbilityHaste = 0;
    private double buffLifeSteal = 0;

    private ArmorGearType sameTypeArmorSet = null;
    private List<GearSet> gearSets = new ArrayList<>();

    public RPGCharacterStats(Player player, String startClass) {
        this.player = player;
        this.rpgClassStr = startClass;

        player.setLevel(1);
        player.setHealthScale(20);

        attributeHashMap.put(AttributeType.BONUS_ELEMENT_DAMAGE, new Attribute(AttributeType.BONUS_ELEMENT_DAMAGE));
        attributeHashMap.put(AttributeType.BONUS_ELEMENT_DEFENSE, new Attribute(AttributeType.BONUS_ELEMENT_DEFENSE));
        attributeHashMap.put(AttributeType.BONUS_MAX_HEALTH, new Attribute(AttributeType.BONUS_MAX_HEALTH));
        attributeHashMap.put(AttributeType.BONUS_MAX_MANA, new Attribute(AttributeType.BONUS_MAX_MANA));
        attributeHashMap.put(AttributeType.BONUS_CRITICAL_CHANCE, new Attribute(AttributeType.BONUS_CRITICAL_CHANCE));

        elementHashMap.put(ElementType.FIRE, new Element(ElementType.FIRE));
        elementHashMap.put(ElementType.WATER, new Element(ElementType.WATER));
        elementHashMap.put(ElementType.EARTH, new Element(ElementType.EARTH));
        elementHashMap.put(ElementType.AIR, new Element(ElementType.AIR));
        elementHashMap.put(ElementType.LIGHTNING, new Element(ElementType.LIGHTNING));

        helmet = new ArmorStatHolder(0, 0);
        chestplate = new ArmorStatHolder(0, 0);
        leggings = new ArmorStatHolder(0, 0);
        boots = new ArmorStatHolder(0, 0);

        //offhand slot
        shield = new ArmorStatHolder(0, 0);

        onMaxHealthChange();

        //start action bar scheduler
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                ActionBarInfo actionBarInfo = rpgClass.getActionBarInfo();

                String between = actionBarInfo.getActionBarBetween(player);

                StringBuilder message = new StringBuilder(ChatPalette.RED + "❤" + ((int) (player.getHealth() + 0.5)) + "/" + getTotalMaxHealth() + between + ChatPalette.BLUE_LIGHT + "✦" + currentMana + "/" + getTotalMaxMana());

                ArrayList<String> buffs = new ArrayList<>();
                if (buffElementDamage != 1) {
                    if (buffElementDamage > 1) {
                        buffs.add(ChatPalette.RED + "Dmg +" + (int) ((buffElementDamage - 1) * 100 + 0.5) + "%");
                    } else {
                        buffs.add(ChatPalette.RED + "Dmg -" + (int) ((1 - buffElementDamage) * 100 + 0.5) + "%");
                    }
                }
                if (buffElementDefense != 1) {
                    if (buffElementDefense > 1) {
                        buffs.add(ChatPalette.BLUE_LIGHT + "Def +" + (int) ((buffElementDefense - 1) * 100 + 0.5) + "%");
                    } else {
                        buffs.add(ChatPalette.BLUE_LIGHT + "Def -" + (int) ((1 - buffElementDefense) * 100 + 0.5) + "%");
                    }
                }
                if (buffCriticalChance != 0) {
                    buffs.add(ChatPalette.GOLD + "Crit +" + (int) ((buffCriticalChance * 100) + 0.5) + "%");
                }
                if (buffCriticalDamage != 0) {
                    buffs.add(ChatPalette.ORANGE + "CritDmg +" + (int) ((buffCriticalDamage * 100) + 0.5) + "%");
                }
                if (buffLifeSteal != 0) {
                    buffs.add(ChatPalette.RED + "LifeSteal +" + (int) ((buffLifeSteal * 100) + 0.5) + "%");
                }
                if (buffAbilityHaste != 0) {
                    buffs.add(ChatPalette.BLUE + "Haste +" + buffAbilityHaste + "");
                }

                for (int i = 0; i < buffs.size(); i++) {
                    if (i % 2 == 0) {
                        message.insert(0, buffs.get(i) + " ");
                    } else {
                        message.append(" ").append(buffs.get(i));
                    }
                }

                if (buffs.size() % 2 == 1) {
                    message.append("          ");
                }

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message.toString()));
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

    public void giveExp(int expToGive) {
        if (player.getLevel() >= 90) { //last level is 90
            return;
        }

        int currentLevel = RPGCharacterExperienceManager.getLevel(this.totalExp);

        this.totalExp += expToGive;

        int newLevel = RPGCharacterExperienceManager.getLevel(this.totalExp);

        if (currentLevel < newLevel) { //level up
            player.setLevel(newLevel);
            currentLevel = newLevel;

            playLevelUpAnimation();
            onMaxHealthChange();
            sendLevelUpMessage(newLevel);
            player.sendTitle(ChatPalette.GOLD + "Level Up!", ChatPalette.YELLOW + "Your new level is " + ChatPalette.GOLD + newLevel, 30, 80, 30);
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
            final int ticksLimit = 100;

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
                    im.setCustomModelData(7);
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

    public Attribute getAttribute(AttributeType attributeType) {
        return attributeHashMap.get(attributeType);
    }

    public Element getElement(ElementType elementType) {
        return elementHashMap.get(elementType);
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

        return (int) (totalMaxHealth + attributeHashMap.get(AttributeType.BONUS_MAX_HEALTH).getIncrement(player.getLevel(), rpgClassStr) + 0.5);
    }

    public int getTotalMaxMana() {
        return (int) (maxMana + attributeHashMap.get(AttributeType.BONUS_MAX_MANA).getIncrement(player.getLevel(), rpgClassStr) + 0.5);
    }

    public int getTotalElementDefense() {
        int equipment = helmet.getDefense() + chestplate.getDefense() + leggings.getDefense() + boots.getDefense() + shield.getDefense();
        double attr = attributeHashMap.get(AttributeType.BONUS_ELEMENT_DEFENSE).getIncrement(player.getLevel(), rpgClassStr);

        int def = (int) ((equipment + attr) * buffElementDefense + 0.5);

        if (def < 1) return 1;

        return def;
    }

    public double getTotalCriticalChance() {
        double chance = baseCriticalChance + attributeHashMap.get(AttributeType.BONUS_CRITICAL_CHANCE).getIncrement(player.getLevel(), rpgClassStr);
        if (chance > 0.4) {
            chance = 0.4;
        }

        chance += buffCriticalChance;

        ItemStack item = player.getInventory().getItem(4);
        if (item != null) {
            WeaponGearType weaponGearType = WeaponGearType.fromMaterial(item.getType());
            if (weaponGearType != null) {
                chance += weaponGearType.getCriticalChance();
            }
        }

        return chance;
    }

    public double getTotalCriticalDamage() {
        return baseCriticalDamageDamage + buffCriticalDamage;
    }

    public double getTotalAbilityHaste() {
        double v = baseAbilityHaste + buffAbilityHaste;

        Material type = player.getInventory().getItemInMainHand().getType();
        WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);
        if (weaponGearType != null) {
            v += weaponGearType.getBonusAbilityHaste();
        }

        return v;
    }

    public int getTotalElementDamage(Player player, String rpgClass) {
        int bonus = (int) (attributeHashMap.get(AttributeType.BONUS_ELEMENT_DAMAGE).getIncrement(player.getLevel(), rpgClass) + 0.5) + damageBonusFromOffhand;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Material type = itemInMainHand.getType();

        int weapon = 0;
        WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);
        if (weaponGearType != null) {
            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) return bonus;

            GearStatType gearStatType = StatUtils.getStatType(type);

            if (gearStatType == GearStatType.WEAPON_GEAR) {
                StatOneType stat = (StatOneType) StatUtils.getStat(itemInMainHand);

                weapon = stat.getValue();
            }
        }

        return (int) ((weapon + bonus) * buffElementDamage + 0.5);
    }

    public void resetAttributes() {
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).setInvested(0, this, false); // we fix display on bottom of this method
        }

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public int getInvestedAttributePoints() {
        int total = 0;
        for (Attribute attribute : attributeHashMap.values()) {
            int invested = attribute.getInvested();
            total += invested;
        }

        return total;
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

        if (foodLevel == 20) foodLevel = 19;

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

            switch (armorSlot) {
                case HELMET:
                    helmet = new ArmorStatHolder(health, defense);
                    setPassiveStatBonuses(EquipmentSlot.HEAD, itemStack);
                    break;
                case CHESTPLATE:
                    chestplate = new ArmorStatHolder(health, defense);
                    setPassiveStatBonuses(EquipmentSlot.CHEST, itemStack);
                    break;
                case LEGGINGS:
                    leggings = new ArmorStatHolder(health, defense);
                    setPassiveStatBonuses(EquipmentSlot.LEGS, itemStack);
                    break;
                case BOOTS:
                    boots = new ArmorStatHolder(health, defense);
                    setPassiveStatBonuses(EquipmentSlot.FEET, itemStack);
                    break;
            }

            if (fixDisplay) {
                onMaxHealthChange();

                if (PersistentDataContainerUtil.hasString(itemStack, "gearSet")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            PlayerInventory inventory = player.getInventory();

                            ItemStack inventoryHelmet = inventory.getHelmet();
                            ItemStack inventoryChestplate = inventory.getChestplate();
                            ItemStack inventoryLeggings = inventory.getLeggings();
                            ItemStack inventoryBoots = inventory.getBoots();
                            ItemStack itemInMainHand = inventory.getItemInMainHand();
                            ItemStack itemInOffHand = inventory.getItemInOffHand();

                            ArmorGearType helmetType = inventoryHelmet == null ? null : ArmorGearType.fromMaterial(inventoryHelmet.getType());
                            ArmorGearType chestplateType = inventoryChestplate == null ? null : ArmorGearType.fromMaterial(inventoryChestplate.getType());
                            ArmorGearType leggingsType = inventoryLeggings == null ? null : ArmorGearType.fromMaterial(inventoryLeggings.getType());
                            ArmorGearType bootsType = inventoryBoots == null ? null : ArmorGearType.fromMaterial(inventoryBoots.getType());

                            recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                                    helmetType, chestplateType, leggingsType, bootsType);
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);
                }
            }
        }
    }

    public void onOffhandEquip(ItemStack itemStack, boolean fixDisplay) {
        Material type = itemStack.getType();

        ShieldGearType shieldGearType = ShieldGearType.fromMaterial(type);

        if (shieldGearType != null) {
            int health = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "health")) {
                health = PersistentDataContainerUtil.getInteger(itemStack, "health");
            }

            int defense = 0;
            if (PersistentDataContainerUtil.hasInteger(itemStack, "defense")) {
                defense = PersistentDataContainerUtil.getInteger(itemStack, "defense");
            }

            shield = new ArmorStatHolder(health, defense);
            setPassiveStatBonuses(EquipmentSlot.OFF_HAND, itemStack);
        } else {
            WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);

            if (weaponGearType != null) {
                if (weaponGearType.canEquipToOffHand()) {
                    StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                    int damage = stat.getValue();
                    damageBonusFromOffhand = (int) ((damage * 0.6) + 0.5);
                    setPassiveStatBonuses(EquipmentSlot.OFF_HAND, itemStack);
                }
            }
        }

        if (fixDisplay) {
            if (PersistentDataContainerUtil.hasString(itemStack, "gearSet")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerInventory inventory = player.getInventory();

                        ItemStack inventoryHelmet = inventory.getHelmet();
                        ItemStack inventoryChestplate = inventory.getChestplate();
                        ItemStack inventoryLeggings = inventory.getLeggings();
                        ItemStack inventoryBoots = inventory.getBoots();
                        ItemStack itemInMainHand = inventory.getItemInMainHand();
                        ItemStack itemInOffHand = inventory.getItemInOffHand();

                        ArmorGearType helmetType = InventoryUtils.isAirOrNull(inventoryHelmet) ? null : ArmorGearType.fromMaterial(inventoryHelmet.getType());
                        ArmorGearType chestplateType = InventoryUtils.isAirOrNull(inventoryChestplate) ? null : ArmorGearType.fromMaterial(inventoryChestplate.getType());
                        ArmorGearType leggingsType = InventoryUtils.isAirOrNull(inventoryLeggings) ? null : ArmorGearType.fromMaterial(inventoryLeggings.getType());
                        ArmorGearType bootsType = InventoryUtils.isAirOrNull(inventoryBoots) ? null : ArmorGearType.fromMaterial(inventoryBoots.getType());

                        recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                                helmetType, chestplateType, leggingsType, bootsType);
                    }
                }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);
            }
        }
    }

    public void onOffhandUnequip(ItemStack itemStack) {
        Material type = itemStack.getType();
        ShieldGearType shieldGearType = ShieldGearType.fromMaterial(type);

        if (shieldGearType != null) {
            shield = new ArmorStatHolder(0, 0);
            removePassiveStatBonusesOfEquipment(EquipmentSlot.OFF_HAND);
        } else {
            WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);

            if (weaponGearType != null) {
                if (weaponGearType.canEquipToOffHand()) {
                    damageBonusFromOffhand = 0;
                    removePassiveStatBonusesOfEquipment(EquipmentSlot.OFF_HAND);
                }
            }
        }

        if (PersistentDataContainerUtil.hasString(itemStack, "gearSet")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerInventory inventory = player.getInventory();

                    ItemStack inventoryHelmet = inventory.getHelmet();
                    ItemStack inventoryChestplate = inventory.getChestplate();
                    ItemStack inventoryLeggings = inventory.getLeggings();
                    ItemStack inventoryBoots = inventory.getBoots();
                    ItemStack itemInMainHand = inventory.getItemInMainHand();
                    ItemStack itemInOffHand = inventory.getItemInOffHand();

                    ArmorGearType helmetType = InventoryUtils.isAirOrNull(inventoryHelmet) ? null : ArmorGearType.fromMaterial(inventoryHelmet.getType());
                    ArmorGearType chestplateType = InventoryUtils.isAirOrNull(inventoryChestplate) ? null : ArmorGearType.fromMaterial(inventoryChestplate.getType());
                    ArmorGearType leggingsType = InventoryUtils.isAirOrNull(inventoryLeggings) ? null : ArmorGearType.fromMaterial(inventoryLeggings.getType());
                    ArmorGearType bootsType = InventoryUtils.isAirOrNull(inventoryBoots) ? null : ArmorGearType.fromMaterial(inventoryBoots.getType());

                    recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                            helmetType, chestplateType, leggingsType, bootsType);
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);
        }
    }

    private void setPassiveStatBonuses(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        // Attributes
        for (AttributeType attributeType : AttributeType.values()) {
            if (PersistentDataContainerUtil.hasInteger(itemStack, attributeType.name())) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, attributeType.name());
                attributeHashMap.get(attributeType).setEquipmentBonus(equipmentSlot, bonus, this, false);
            } else {
                attributeHashMap.get(attributeType).setEquipmentBonus(equipmentSlot, 0, this, false);
            }
        }
        // Elements
        for (ElementType elementType : ElementType.values()) {
            if (PersistentDataContainerUtil.hasInteger(itemStack, elementType.name())) {
                int bonus = PersistentDataContainerUtil.getInteger(itemStack, elementType.name());
                elementHashMap.get(elementType).setEquipmentBonus(equipmentSlot, bonus);
            } else {
                elementHashMap.get(elementType).setEquipmentBonus(equipmentSlot, 0);
            }
        }
    }

    private void removePassiveStatBonusesOfEquipment(EquipmentSlot equipmentSlot) {
        // Attributes
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).clearEquipmentBonus(equipmentSlot, this, false);
        }
        // Elements
        for (ElementType elementType : ElementType.values()) {
            elementHashMap.get(elementType).clearEquipmentBonus(equipmentSlot);
        }

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public void recalculateRPGInventory(RPGInventory rpgInventory) {
        // Clear Attribute
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).clearTotalPassive(this, false);
        }
        // Clear Elements
        for (ElementType elementType : ElementType.values()) {
            elementHashMap.get(elementType).clearTotalPassive();
        }

        StatPassive totalPassiveStat = rpgInventory.getTotalPassiveStat();

        // Read Attributes
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).addToTotalPassive(totalPassiveStat.getAttributeValue(attributeType), this, false);
        }

        // Read Elements
        for (ElementType elementType : ElementType.values()) {
            elementHashMap.get(elementType).addToTotalPassive(totalPassiveStat.getElementValue(elementType));
        }

        onMaxHealthChange();
        onCurrentManaChange();
    }

    public void recalculateEquipment(String rpgClass) {
        // Clear attributes
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).clearAllEquipment(this, false);
        }
        // Clear elements
        for (ElementType elementType : ElementType.values()) {
            elementHashMap.get(elementType).clearAllEquipment();
        }

        helmet = new ArmorStatHolder(0, 0);
        chestplate = new ArmorStatHolder(0, 0);
        leggings = new ArmorStatHolder(0, 0);
        boots = new ArmorStatHolder(0, 0);

        //offhand slot
        shield = new ArmorStatHolder(0, 0);
        damageBonusFromOffhand = 0;

        PlayerInventory inventory = player.getInventory();

        ItemStack itemInMainHand = inventory.getItem(4);
        if (!InventoryUtils.isAirOrNull(itemInMainHand)) {
            if (StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClass)) {
                setPassiveStatBonuses(EquipmentSlot.HAND, itemInMainHand);
                Material mainHandType = itemInMainHand.getType();
                if (mainHandType.equals(Material.BOW) || mainHandType.equals(Material.CROSSBOW)) {
                    ItemStack arrow = OtherItems.getArrow(2);
                    player.getInventory().setItemInOffHand(arrow);
                }
            }
        }

        ItemStack itemInOffHand = inventory.getItemInOffHand();
        if (!InventoryUtils.isAirOrNull(itemInOffHand)) {
            Material type = itemInOffHand.getType();

            ShieldGearType shieldGearType = ShieldGearType.fromMaterial(type);

            if (shieldGearType != null) {
                if (StatUtils.doesCharacterMeetRequirements(itemInOffHand, player, rpgClass)) {
                    onOffhandEquip(itemInOffHand, false);
                } else {
                    InventoryUtils.giveItemToPlayer(player, itemInOffHand);
                    itemInOffHand.setAmount(0);
                }
            } else {
                WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);

                if (weaponGearType != null && weaponGearType.canEquipToOffHand()) {
                    onOffhandEquip(itemInOffHand, false);
                } else if (!type.equals(Material.ARROW)) {
                    InventoryUtils.giveItemToPlayer(player, itemInOffHand);
                    itemInOffHand.setAmount(0);
                }
            }
        }

        ItemStack inventoryHelmet = inventory.getHelmet();
        ArmorGearType helmetType = null;
        if (!InventoryUtils.isAirOrNull(inventoryHelmet)) {
            if (StatUtils.doesCharacterMeetRequirements(inventoryHelmet, player, rpgClass)) {
                onArmorEquip(inventoryHelmet, false);
                helmetType = ArmorGearType.fromMaterial(inventoryHelmet.getType());
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
                chestplateType = ArmorGearType.fromMaterial(inventoryChestplate.getType());
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
                leggingsType = ArmorGearType.fromMaterial(inventoryLeggings.getType());
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
                bootsType = ArmorGearType.fromMaterial(inventoryBoots.getType());
            } else {
                InventoryUtils.giveItemToPlayer(player, inventoryBoots);
                inventoryBoots.setAmount(0);
            }
        }

        onMaxHealthChange();
        onCurrentManaChange();

        // GEAR SET EFFECTS
        recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                helmetType, chestplateType, leggingsType, bootsType);
    }

    public int getTotalDamageBonusFromOffhand() {
        return (int) (damageBonusFromOffhand * buffElementDamage + 0.5);
    }

    public boolean onMainHandEquip(ItemStack itemStack, boolean fixDisplay) {
        if (StatUtils.doesCharacterMeetRequirements(itemStack, player, rpgClassStr)) {
            // Add attribute bonuses
            for (AttributeType attributeType : AttributeType.values()) {
                if (PersistentDataContainerUtil.hasInteger(itemStack, attributeType.name())) {
                    int bonus = PersistentDataContainerUtil.getInteger(itemStack, attributeType.name());
                    attributeHashMap.get(attributeType).setEquipmentBonus(EquipmentSlot.HAND, bonus, this, false);
                }
            }
            // Add element bonuses
            for (ElementType elementType : ElementType.values()) {
                if (PersistentDataContainerUtil.hasInteger(itemStack, elementType.name())) {
                    int bonus = PersistentDataContainerUtil.getInteger(itemStack, elementType.name());
                    elementHashMap.get(elementType).setEquipmentBonus(EquipmentSlot.HAND, bonus);
                }
            }

            if (fixDisplay) {
                if (PersistentDataContainerUtil.hasString(itemStack, "gearSet")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            PlayerInventory inventory = player.getInventory();

                            ItemStack inventoryHelmet = inventory.getHelmet();
                            ItemStack inventoryChestplate = inventory.getChestplate();
                            ItemStack inventoryLeggings = inventory.getLeggings();
                            ItemStack inventoryBoots = inventory.getBoots();
                            ItemStack itemInMainHand = inventory.getItemInMainHand();
                            ItemStack itemInOffHand = inventory.getItemInOffHand();

                            ArmorGearType helmetType = InventoryUtils.isAirOrNull(inventoryHelmet) ? null : ArmorGearType.fromMaterial(inventoryHelmet.getType());
                            ArmorGearType chestplateType = InventoryUtils.isAirOrNull(inventoryChestplate) ? null : ArmorGearType.fromMaterial(inventoryChestplate.getType());
                            ArmorGearType leggingsType = InventoryUtils.isAirOrNull(inventoryLeggings) ? null : ArmorGearType.fromMaterial(inventoryLeggings.getType());
                            ArmorGearType bootsType = InventoryUtils.isAirOrNull(inventoryBoots) ? null : ArmorGearType.fromMaterial(inventoryBoots.getType());

                            recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                                    helmetType, chestplateType, leggingsType, bootsType);
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);
                }
            }

            return true;
        }
        return false;
    }

    public void onMainHandUnequip(boolean fixDisplay) {
        // Remove attributes
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).clearEquipmentBonus(EquipmentSlot.HAND, this, false);
        }
        // Remove elements
        for (ElementType elementType : ElementType.values()) {
            elementHashMap.get(elementType).clearEquipmentBonus(EquipmentSlot.HAND);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerInventory inventory = player.getInventory();

                ItemStack inventoryHelmet = inventory.getHelmet();
                ItemStack inventoryChestplate = inventory.getChestplate();
                ItemStack inventoryLeggings = inventory.getLeggings();
                ItemStack inventoryBoots = inventory.getBoots();
                ItemStack itemInMainHand = inventory.getItemInMainHand();
                ItemStack itemInOffHand = inventory.getItemInOffHand();

                ArmorGearType helmetType = InventoryUtils.isAirOrNull(inventoryHelmet) ? null : ArmorGearType.fromMaterial(inventoryHelmet.getType());
                ArmorGearType chestplateType = InventoryUtils.isAirOrNull(inventoryChestplate) ? null : ArmorGearType.fromMaterial(inventoryChestplate.getType());
                ArmorGearType leggingsType = InventoryUtils.isAirOrNull(inventoryLeggings) ? null : ArmorGearType.fromMaterial(inventoryLeggings.getType());
                ArmorGearType bootsType = InventoryUtils.isAirOrNull(inventoryBoots) ? null : ArmorGearType.fromMaterial(inventoryBoots.getType());

                recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                        helmetType, chestplateType, leggingsType, bootsType);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);

        if (fixDisplay) {
            onMaxHealthChange();
            onCurrentManaChange();
        }
    }

/*    public void clearMainHandBonuses() {
        // Clear attributes
        for (AttributeType attributeType : AttributeType.values()) {
            attributeHashMap.get(attributeType).clearEquipmentBonus(EquipmentSlot.HAND, this, false);
        }
        // Clear elements
        for (ElementType elementType : ElementType.values()) {
            elementHashMap.get(elementType).clearEquipmentBonus(EquipmentSlot.HAND);
        }

        onMaxHealthChange();
        onCurrentManaChange();

        PlayerInventory inventory = player.getInventory();

        ItemStack inventoryHelmet = inventory.getHelmet();
        ItemStack inventoryChestplate = inventory.getChestplate();
        ItemStack inventoryLeggings = inventory.getLeggings();
        ItemStack inventoryBoots = inventory.getBoots();
        ItemStack itemInMainHand = inventory.getItemInMainHand();
        ItemStack itemInOffHand = inventory.getItemInOffHand();

        ArmorGearType helmetType = ArmorGearType.typeOf(inventoryHelmet);
        ArmorGearType chestplateType = ArmorGearType.typeOf(inventoryChestplate);
        ArmorGearType leggingsType = ArmorGearType.typeOf(inventoryLeggings);
        ArmorGearType bootsType = ArmorGearType.typeOf(inventoryBoots);

        recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                helmetType, chestplateType, leggingsType, bootsType);
    }*/

    public void addToBuffMultiplier(BuffType buffType, double addToMultiplier, PotionEffect potionEffect) {
        double newValue = 1.0;
        if (buffType.equals(BuffType.ELEMENT_DAMAGE)) {
            this.buffElementDamage += addToMultiplier;
            newValue = this.buffElementDamage;
        } else if (buffType.equals(BuffType.ELEMENT_DEFENSE)) {
            this.buffElementDefense += addToMultiplier;
            newValue = this.buffElementDefense;
        } else if (buffType.equals(BuffType.CRIT_DAMAGE)) {
            this.buffCriticalDamage += addToMultiplier;
            newValue = this.buffCriticalDamage;
        } else if (buffType.equals(BuffType.CRIT_CHANCE)) {
            this.buffCriticalChance += addToMultiplier;
            newValue = this.buffCriticalChance;
        } else if (buffType.equals(BuffType.ABILITY_HASTE)) {
            this.buffAbilityHaste += addToMultiplier;
            newValue = this.buffAbilityHaste;
        } else if (buffType.equals(BuffType.LIFE_STEAL)) {
            this.buffLifeSteal += addToMultiplier;
            newValue = this.buffLifeSteal;
        }

        if (potionEffect == null) return;

        if (newValue > 1.0001 || newValue < 0.9999) {
            player.addPotionEffect(potionEffect);
        } else {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public double getBuffValue(BuffType buffType) {
        if (buffType.equals(BuffType.ELEMENT_DAMAGE)) {
            return this.buffElementDamage;
        } else if (buffType.equals(BuffType.ELEMENT_DEFENSE)) {
            return this.buffElementDefense;
        } else if (buffType.equals(BuffType.CRIT_DAMAGE)) {
            return this.buffCriticalDamage;
        } else if (buffType.equals(BuffType.CRIT_CHANCE)) {
            return this.buffCriticalChance;
        } else if (buffType.equals(BuffType.ABILITY_HASTE)) {
            return this.buffAbilityHaste;
        } else if (buffType.equals(BuffType.LIFE_STEAL)) {
            return this.buffLifeSteal;
        }
        return 1;
    }

    private void sendLevelUpMessage(int newLevel) {
        MessageUtils.sendCenteredMessage(player, ChatPalette.GOLD + "Level up!");
        MessageUtils.sendCenteredMessage(player, ChatPalette.YELLOW + "Congratulations, your new level is " + ChatPalette.GOLD + newLevel + "");
    }

    public void reapplyGearSetEffects() {
        if (sameTypeArmorSet != null) {
            GearSetEffect setEffect = sameTypeArmorSet.getSetEffect();
            setEffect.clearSetEffect(player, this);
            setEffect.applySetEffect(player, this);
        }

        for (GearSet gearSet : gearSets) {
            if (GearSetManager.hasEffect(gearSet)) {
                List<GearSetEffect> gearSetEffects = GearSetManager.getEffects(gearSet);
                for (GearSetEffect gearSetEffect : gearSetEffects) {
                    gearSetEffect.clearSetEffect(player, this);
                    gearSetEffect.applySetEffect(player, this);
                }
            }
        }
    }

    public void recalculateGearSetEffects(ItemStack inventoryHelmet, ItemStack inventoryChestplate, ItemStack inventoryLeggings,
                                          ItemStack inventoryBoots, ItemStack itemInMainHand, ItemStack itemInOffHand,
                                          ArmorGearType helmetType, ArmorGearType chestplateType, ArmorGearType leggingsType,
                                          ArmorGearType bootsType) {
        // ARMOR TYPE SET EFFECT
        boolean wearingSameArmorType = GearSetEffect.isWearingSameArmorType(helmetType, chestplateType, leggingsType, bootsType);
        if (wearingSameArmorType) {
            if (sameTypeArmorSet == null || !sameTypeArmorSet.equals(helmetType)) { // Only make change if different armor type
                // Clear old set effect
                if (sameTypeArmorSet != null) {
                    GearSetEffect oldEffect = sameTypeArmorSet.getSetEffect();
                    oldEffect.clearSetEffect(player, this); // different same armor type
                }

                player.sendMessage(ChatPalette.PURPLE + "Same Type Armor Effect Activation: "
                        + ChatPalette.PURPLE_LIGHT + helmetType.getDisplayName() + " [" + 4 + "pieces]");

                GearSetEffect setEffect = helmetType.getSetEffect();
                setEffect.applySetEffect(player, this);

                sameTypeArmorSet = helmetType;
            }
        } else if (sameTypeArmorSet != null) {
            GearSetEffect setEffect = sameTypeArmorSet.getSetEffect();
            setEffect.clearSetEffect(player, this); // no more same armor type
            sameTypeArmorSet = null;
        }

        // CUSTOM GEAR SET EFFECT
        ArrayList<String> equipmentSets = new ArrayList<>(Arrays.asList(
                GearSetEffect.getCustomSet(inventoryHelmet),
                GearSetEffect.getCustomSet(inventoryChestplate),
                GearSetEffect.getCustomSet(inventoryLeggings),
                GearSetEffect.getCustomSet(inventoryBoots),
                GearSetEffect.getCustomSet(itemInMainHand),
                GearSetEffect.getCustomSet(itemInOffHand)
        ));

        List<String> alreadyActivated = new ArrayList<>();

        List<GearSet> currentGearSets = new ArrayList<>();
        List<GearSet> newGearSets = new ArrayList<>(); // Current without old

        for (String gearSetName : equipmentSets) {
            if (gearSetName == null) continue;
            if (alreadyActivated.contains(gearSetName)) continue;

            int count = Collections.frequency(equipmentSets, gearSetName);
            if (count < 2) continue;
            alreadyActivated.add(gearSetName);

            GearSet key = new GearSet(gearSetName, count);

            if (GearSetManager.hasEffect(key)) {
                currentGearSets.add(key);

                if (this.gearSets.contains(key)) continue; // Same GearSet
                newGearSets.add(key); // New GearSet
            }
        }

        // Clear old effects
        for (GearSet gearSet : this.gearSets) {
            if (currentGearSets.contains(gearSet)) continue;

            if (GearSetManager.hasEffect(gearSet)) {
                List<GearSetEffect> gearSetEffects = GearSetManager.getEffects(gearSet);
                for (GearSetEffect gearSetEffect : gearSetEffects) {
                    gearSetEffect.clearSetEffect(player, this); // custom effect clear
                }
            }
        }

        // Apply new effects
        for (GearSet gearSet : newGearSets) {
            if (GearSetManager.hasEffect(gearSet)) {
                player.sendMessage(ChatPalette.PURPLE + "Gear Set Effect Activation: "
                        + ChatPalette.PURPLE_LIGHT + gearSet.getName() + " [" + gearSet.getPieceCount() + " pieces]");

                List<GearSetEffect> gearSetEffects = GearSetManager.getEffects(gearSet);
                for (GearSetEffect gearSetEffect : gearSetEffects) {
                    gearSetEffect.applySetEffect(player, this);
                }
            }
        }

        // Apply changes to data
        this.gearSets = currentGearSets;
    }
}
