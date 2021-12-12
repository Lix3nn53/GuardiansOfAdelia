package io.github.lix3nn53.guardiansofadelia.rpginventory;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.*;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RPGInventory extends GuiGeneric {

    private final List<Player> petSpawnCooldownList = new ArrayList<>();

    private final RPGSlotParrot parrotSlot = new RPGSlotParrot();
    private final RPGSlotEarring earringSlot = new RPGSlotEarring();
    private final RPGSlotNecklace necklaceSlot = new RPGSlotNecklace();
    private final RPGSlotGlove gloveSlot = new RPGSlotGlove();
    private final RPGSlotRing ringSlot = new RPGSlotRing();
    private final EggSlot eggSlot = new EggSlot();
    private final VanillaSlotHelmet helmetSlot = new VanillaSlotHelmet();
    private final VanillaSlotChestplate chestplateSlot = new VanillaSlotChestplate();
    private final VanillaSlotLeggings leggingsSlot = new VanillaSlotLeggings();
    private final VanillaSlotBoots bootsSlot = new VanillaSlotBoots();
    private final VanillaSlotOffhand offhandSlot = new VanillaSlotOffhand();
    private final HotBarSlotWeapon hotBarSlotWeapon = new HotBarSlotWeapon();

    public RPGInventory(Player player) {
        super(54, CustomCharacterGui.MENU_54_FLAT + ChatPalette.BLACK.toString() + ChatColor.BOLD + "RPG Inventory", 0);

        if (parrotSlot.isEmpty()) {
            this.setItem(RPGSlotType.PARROT.getSlotNo(), parrotSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.PARROT.getSlotNo(), parrotSlot.getItemOnSlot());
        }

        if (earringSlot.isEmpty()) {
            this.setItem(RPGSlotType.EARRING.getSlotNo(), earringSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.EARRING.getSlotNo(), earringSlot.getItemOnSlot());
        }

        if (necklaceSlot.isEmpty()) {
            this.setItem(RPGSlotType.NECKLACE.getSlotNo(), necklaceSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.NECKLACE.getSlotNo(), necklaceSlot.getItemOnSlot());
        }

        if (gloveSlot.isEmpty()) {
            this.setItem(RPGSlotType.GLOVE.getSlotNo(), gloveSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.GLOVE.getSlotNo(), gloveSlot.getItemOnSlot());
        }

        if (ringSlot.isEmpty()) {
            this.setItem(RPGSlotType.RING.getSlotNo(), ringSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.RING.getSlotNo(), ringSlot.getItemOnSlot());
        }

        if (eggSlot.isEmpty()) {
            this.setItem(RPGSlotType.PET.getSlotNo(), eggSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.PET.getSlotNo(), eggSlot.getItemOnSlot());
        }

        if (helmetSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.HELMET.getSlotNo(), helmetSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.HELMET.getSlotNo(), helmetSlot.getItemOnSlot(player));
        }

        if (chestplateSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.CHESTPLATE.getSlotNo(), chestplateSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.CHESTPLATE.getSlotNo(), chestplateSlot.getItemOnSlot(player));
        }

        if (leggingsSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.LEGGINGS.getSlotNo(), leggingsSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.LEGGINGS.getSlotNo(), leggingsSlot.getItemOnSlot(player));
        }

        if (bootsSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.BOOTS.getSlotNo(), bootsSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.BOOTS.getSlotNo(), bootsSlot.getItemOnSlot(player));
        }

        if (offhandSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.OFFHAND.getSlotNo(), offhandSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.OFFHAND.getSlotNo(), offhandSlot.getItemOnSlot(player));
        }

        if (hotBarSlotWeapon.isEmpty(player)) {
            this.setItem(RPGSlotType.MAINHAND.getSlotNo(), hotBarSlotWeapon.getFillItem());
        } else {
            this.setItem(RPGSlotType.MAINHAND.getSlotNo(), hotBarSlotWeapon.getItemOnSlot(player));
        }

        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
        this.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));

        // InventoryUtils.fillEmptySlotsWithGlass(this);
        this.setLocked(false);
    }

    public void update(Player player) {
        if (parrotSlot.isEmpty()) {
            this.setItem(RPGSlotType.PARROT.getSlotNo(), parrotSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.PARROT.getSlotNo(), parrotSlot.getItemOnSlot());
        }

        if (earringSlot.isEmpty()) {
            this.setItem(RPGSlotType.EARRING.getSlotNo(), earringSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.EARRING.getSlotNo(), earringSlot.getItemOnSlot());
        }

        if (necklaceSlot.isEmpty()) {
            this.setItem(RPGSlotType.NECKLACE.getSlotNo(), necklaceSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.NECKLACE.getSlotNo(), necklaceSlot.getItemOnSlot());
        }

        if (gloveSlot.isEmpty()) {
            this.setItem(RPGSlotType.GLOVE.getSlotNo(), gloveSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.GLOVE.getSlotNo(), gloveSlot.getItemOnSlot());
        }

        if (ringSlot.isEmpty()) {
            this.setItem(RPGSlotType.RING.getSlotNo(), ringSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.RING.getSlotNo(), ringSlot.getItemOnSlot());
        }

        if (eggSlot.isEmpty()) {
            this.setItem(RPGSlotType.PET.getSlotNo(), eggSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.PET.getSlotNo(), eggSlot.getItemOnSlot());
        }

        if (helmetSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.HELMET.getSlotNo(), helmetSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.HELMET.getSlotNo(), helmetSlot.getItemOnSlot(player));
        }

        if (chestplateSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.CHESTPLATE.getSlotNo(), chestplateSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.CHESTPLATE.getSlotNo(), chestplateSlot.getItemOnSlot(player));
        }

        if (leggingsSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.LEGGINGS.getSlotNo(), leggingsSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.LEGGINGS.getSlotNo(), leggingsSlot.getItemOnSlot(player));
        }

        if (bootsSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.BOOTS.getSlotNo(), bootsSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.BOOTS.getSlotNo(), bootsSlot.getItemOnSlot(player));
        }

        if (offhandSlot.isEmpty(player)) {
            this.setItem(RPGSlotType.OFFHAND.getSlotNo(), offhandSlot.getFillItem());
        } else {
            this.setItem(RPGSlotType.OFFHAND.getSlotNo(), offhandSlot.getItemOnSlot(player));
        }

        if (hotBarSlotWeapon.isEmpty(player)) {
            this.setItem(RPGSlotType.MAINHAND.getSlotNo(), hotBarSlotWeapon.getFillItem());
        } else {
            this.setItem(RPGSlotType.MAINHAND.getSlotNo(), hotBarSlotWeapon.getItemOnSlot(player));
        }

        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
        this.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
    }

    public StatPassive getTotalPassiveStat() {
        StatPassive parrot = parrotSlot.getBonusStats();
        StatPassive earring = earringSlot.getBonusStats();
        StatPassive necklace = necklaceSlot.getBonusStats();
        StatPassive glove = gloveSlot.getBonusStats();
        StatPassive ring = ringSlot.getBonusStats();

        // Add all attributes
        HashMap<AttributeType, Integer> attributeTypeToValue = new HashMap<>();
        for (AttributeType attributeType : AttributeType.values()) {
            int bonus = parrot.getAttributeValue(attributeType) + earring.getAttributeValue(attributeType) + necklace.getAttributeValue(attributeType)
                    + glove.getAttributeValue(attributeType) + ring.getAttributeValue(attributeType);
            attributeTypeToValue.put(attributeType, bonus);
        }

        // Add all elements
        HashMap<ElementType, Integer> elementTypeToValue = new HashMap<>();
        for (ElementType elementType : ElementType.values()) {
            int bonus = parrot.getElementValue(elementType) + earring.getElementValue(elementType) + necklace.getElementValue(elementType)
                    + glove.getElementValue(elementType) + ring.getElementValue(elementType);
            elementTypeToValue.put(elementType, bonus);
        }

        return new StatPassive(attributeTypeToValue, elementTypeToValue);
    }

    private int getSlotNo(RPGSlotType slotType) {
        return slotType.getSlotNo();
    }

    public boolean setParrot(ItemStack itemStack, Player player) {
        if (this.parrotSlot.doesFit(itemStack)) {
            this.parrotSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            manageShoulderEntity(player);
            return true;
        }
        return false;
    }

    public boolean setEarring(ItemStack itemStack, Player player) {
        if (this.earringSlot.doesFit(itemStack)) {
            this.earringSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setNecklace(ItemStack itemStack, Player player) {
        if (this.necklaceSlot.doesFit(itemStack)) {
            this.necklaceSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setGlove(ItemStack itemStack, Player player) {
        if (this.gloveSlot.doesFit(itemStack)) {
            this.gloveSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setRing(ItemStack itemStack, Player player) {
        if (this.ringSlot.doesFit(itemStack)) {
            this.ringSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setEgg(ItemStack itemStack, Player player) {
        if (this.eggSlot.doesFit(itemStack)) {
            this.eggSlot.setItemOnSlot(itemStack);
            return true;
        }
        return false;
    }

    public boolean setHelmet(ItemStack itemStack, Player player) {
        if (this.helmetSlot.doesFit(itemStack)) {
            this.helmetSlot.setItemOnSlot(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setChestplate(ItemStack itemStack, Player player) {
        if (this.chestplateSlot.doesFit(itemStack)) {
            this.chestplateSlot.setItemOnSlot(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setLeggings(ItemStack itemStack, Player player) {
        if (this.leggingsSlot.doesFit(itemStack)) {
            this.leggingsSlot.setItemOnSlot(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setBoots(ItemStack itemStack, Player player) {
        if (this.bootsSlot.doesFit(itemStack)) {
            this.bootsSlot.setItemOnSlot(player, itemStack);
            return true;
        }
        return false;
    }

    public boolean setOffhand(ItemStack itemStack, Player player) {
        if (this.offhandSlot.doesFit(itemStack)) {
            this.offhandSlot.setItemOnSlot(player, itemStack);
            return true;
        }
        return false;
    }

    public RPGSlotParrot getParrotSlot() {
        return parrotSlot;
    }

    public RPGSlotEarring getEarringSlot() {
        return earringSlot;
    }

    public RPGSlotNecklace getNecklaceSlot() {
        return necklaceSlot;
    }

    public RPGSlotGlove getGloveSlot() {
        return gloveSlot;
    }

    public RPGSlotRing getRingSlot() {
        return ringSlot;
    }

    public EggSlot getEggSlot() {
        return eggSlot;
    }

    public VanillaSlotHelmet getHelmetSlot() {
        return helmetSlot;
    }

    public VanillaSlotChestplate getChestplateSlot() {
        return chestplateSlot;
    }

    public VanillaSlotLeggings getLeggingsSlot() {
        return leggingsSlot;
    }

    public VanillaSlotBoots getBootsSlot() {
        return bootsSlot;
    }

    public VanillaSlotOffhand getOffhandSlot() {
        return offhandSlot;
    }

    public HotBarSlotWeapon getHotBarSlotWeapon() {
        return hotBarSlotWeapon;
    }

    public boolean onShiftClick(ItemStack itemStack, Player player, int slot, Inventory topInventory, String rpgClass) {
        boolean doesCharacterMeetRequirements = StatUtils.doesCharacterMeetRequirements(itemStack, player, rpgClass);
        if (!doesCharacterMeetRequirements) return false;

        ItemStack oldItemOnSlot = null;
        boolean change = false;
        RPGSlotType rpgSlotType = null;
        if (parrotSlot.doesFit(itemStack)) {
            if (!parrotSlot.isEmpty()) {
                oldItemOnSlot = parrotSlot.getItemOnSlot();
            }
            change = setParrot(itemStack, player);
            rpgSlotType = RPGSlotType.PARROT;
        } else if (earringSlot.doesFit(itemStack)) {
            if (!earringSlot.isEmpty()) {
                oldItemOnSlot = earringSlot.getItemOnSlot();
            }
            change = setEarring(itemStack, player);
            rpgSlotType = RPGSlotType.EARRING;
        } else if (necklaceSlot.doesFit(itemStack)) {
            if (!necklaceSlot.isEmpty()) {
                oldItemOnSlot = necklaceSlot.getItemOnSlot();
            }
            change = setNecklace(itemStack, player);
            rpgSlotType = RPGSlotType.NECKLACE;
        } else if (gloveSlot.doesFit(itemStack)) {
            if (!gloveSlot.isEmpty()) {
                oldItemOnSlot = gloveSlot.getItemOnSlot();
            }
            change = setGlove(itemStack, player);
            rpgSlotType = RPGSlotType.GLOVE;
        } else if (ringSlot.doesFit(itemStack)) {
            if (!ringSlot.isEmpty()) {
                oldItemOnSlot = ringSlot.getItemOnSlot();
            }
            change = setRing(itemStack, player);
            rpgSlotType = RPGSlotType.RING;
        } else if (eggSlot.doesFit(itemStack)) {
            if (PetManager.isPetDead(player) || petSpawnCooldownList.contains(player)) {
                return false;
            }
            if (!eggSlot.isEmpty()) {
                oldItemOnSlot = eggSlot.getItemOnSlot();
            }
            change = setEgg(itemStack, player);
            rpgSlotType = RPGSlotType.PET;
        }
        if (change) {
            if (oldItemOnSlot != null) {
                player.getInventory().setItem(slot, oldItemOnSlot);
                if (rpgSlotType.equals(RPGSlotType.PET)) {
                    PetManager.onEggUnequip(player);
                }
            } else {
                player.getInventory().setItem(slot, new ItemStack(Material.AIR));
            }
            topInventory.setItem(rpgSlotType.getSlotNo(), itemStack);
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
            if (rpgSlotType.equals(RPGSlotType.PARROT)) {
                manageShoulderEntity(player);
            }
            if (rpgSlotType.equals(RPGSlotType.PET)) {
                PetManager.onEggEquip(player);
                startPetSpawnCooldown(player);
            }
        }
        return change;
    }

    public void removeBonusStats(Player player, ItemStack itemStack) {
        GearStatType gearStatType = StatUtils.getStatType(itemStack.getType());
        if (gearStatType.equals(GearStatType.PASSIVE_GEAR)) {
            StatPassive statPassive = (StatPassive) StatUtils.getStat(itemStack);

            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    for (AttributeType attributeType : AttributeType.values()) {
                        rpgCharacterStats.getAttribute(attributeType).removeFromTotalPassive(statPassive.getAttributeValue(attributeType), rpgCharacterStats, true);
                    }
                    for (ElementType elementType : ElementType.values()) {
                        rpgCharacterStats.getElement(elementType).removeFromTotalPassive(statPassive.getElementValue(elementType));
                    }
                }
            }
        }
    }

    public void addBonusStats(Player player, ItemStack itemStack) {
        GearStatType gearStatType = StatUtils.getStatType(itemStack.getType());
        if (gearStatType.equals(GearStatType.PASSIVE_GEAR)) {
            StatPassive statPassive = (StatPassive) StatUtils.getStat(itemStack);

            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    for (AttributeType attributeType : AttributeType.values()) {
                        rpgCharacterStats.getAttribute(attributeType).addToTotalPassive(statPassive.getAttributeValue(attributeType), rpgCharacterStats, true);
                    }
                    for (ElementType elementType : ElementType.values()) {
                        rpgCharacterStats.getElement(elementType).addToTotalPassive(statPassive.getElementValue(elementType));
                    }
                }
            }
        }
    }

    public boolean onCursorClickWithItem(Player player, int slot, ItemStack cursor, Inventory topInventory, String rpgClass) {
        boolean doesCharacterMeetRequirements = StatUtils.doesCharacterMeetRequirements(cursor, player, rpgClass);
        if (!doesCharacterMeetRequirements) return false;

        GuardianData guardianData = GuardianDataManager.getGuardianData(player);

        if (slot == RPGSlotType.PARROT.getSlotNo()) {
            RPGSlotParrot rpgSlot = getParrotSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setParrot(cursor, player);
                if (didEquip) {
                    removeBonusStats(player, itemOnSlot);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    manageShoulderEntity(player);
                    return true;
                }
            } else {
                boolean didEquip = setParrot(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    manageShoulderEntity(player);
                    return true;
                }
            }
        } else if (slot == RPGSlotType.EARRING.getSlotNo()) {
            RPGSlotEarring rpgSlot = getEarringSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setEarring(cursor, player);
                if (didEquip) {
                    removeBonusStats(player, itemOnSlot);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            } else {
                boolean didEquip = setEarring(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            }
        } else if (slot == RPGSlotType.NECKLACE.getSlotNo()) {
            RPGSlotNecklace rpgSlot = getNecklaceSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setNecklace(cursor, player);
                if (didEquip) {
                    removeBonusStats(player, itemOnSlot);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            } else {
                boolean didEquip = setNecklace(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            }
        } else if (slot == RPGSlotType.RING.getSlotNo()) {
            RPGSlotRing rpgSlot = getRingSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setRing(cursor, player);
                if (didEquip) {
                    removeBonusStats(player, itemOnSlot);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            } else {
                boolean didEquip = setRing(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            }
        } else if (slot == RPGSlotType.GLOVE.getSlotNo()) {
            RPGSlotGlove rpgSlot = getGloveSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setGlove(cursor, player);
                if (didEquip) {
                    removeBonusStats(player, itemOnSlot);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            } else {
                boolean didEquip = setGlove(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                    return true;
                }
            }
        } else if (slot == RPGSlotType.PET.getSlotNo()) {
            if (PetManager.isPetDead(player) || petSpawnCooldownList.contains(player)) {
                return false;
            }
            EggSlot rpgSlot = getEggSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setEgg(cursor, player);
                if (didEquip) {
                    PetManager.onEggUnequip(player);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    PetManager.onEggEquip(player);
                    startPetSpawnCooldown(player);
                    return true;
                }
            } else {
                boolean didEquip = setEgg(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    PetManager.onEggEquip(player);
                    startPetSpawnCooldown(player);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onCursorClickWithAir(Player player, int slot, Inventory topInventory, boolean isShiftClick) {
        GuardianData guardianData = GuardianDataManager.getGuardianData(player);

        if (slot == RPGSlotType.PARROT.getSlotNo()) {
            RPGSlotParrot rpgSlot = getParrotSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, parrotSlot.getFillItem());
                removeBonusStats(player, itemOnSlot);
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                manageShoulderEntity(player);
                return true;
            }
        } else if (slot == RPGSlotType.EARRING.getSlotNo()) {
            RPGSlotEarring rpgSlot = getEarringSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, earringSlot.getFillItem());
                removeBonusStats(player, itemOnSlot);
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                return true;
            }
        } else if (slot == RPGSlotType.NECKLACE.getSlotNo()) {
            RPGSlotNecklace rpgSlot = getNecklaceSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, necklaceSlot.getFillItem());
                removeBonusStats(player, itemOnSlot);
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                return true;
            }
        } else if (slot == RPGSlotType.RING.getSlotNo()) {
            RPGSlotRing rpgSlot = getRingSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, ringSlot.getFillItem());
                removeBonusStats(player, itemOnSlot);
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                return true;
            }
        } else if (slot == RPGSlotType.GLOVE.getSlotNo()) {
            RPGSlotGlove rpgSlot = getGloveSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, gloveSlot.getFillItem());
                removeBonusStats(player, itemOnSlot);
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem(guardianData));
                return true;
            }
        } else if (slot == RPGSlotType.PET.getSlotNo()) {
            if (PetManager.isPetDead(player)) {
                return false;
            }
            EggSlot rpgSlot = getEggSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, eggSlot.getFillItem());
                PetManager.onEggUnequip(player);
                return true;
            }
        }
        return false;
    }

    public void clearInventory(Player player) {
        if (!parrotSlot.isEmpty()) {
            ItemStack itemOnSlot = parrotSlot.getItemOnSlot();
            removeBonusStats(player, itemOnSlot);
            parrotSlot.clearItemOnSlot();
            manageShoulderEntity(player);
        }
        if (!earringSlot.isEmpty()) {
            ItemStack itemOnSlot = earringSlot.getItemOnSlot();
            removeBonusStats(player, itemOnSlot);
            earringSlot.clearItemOnSlot();
        }
        if (!necklaceSlot.isEmpty()) {
            ItemStack itemOnSlot = necklaceSlot.getItemOnSlot();
            removeBonusStats(player, itemOnSlot);
            necklaceSlot.clearItemOnSlot();
        }
        if (!gloveSlot.isEmpty()) {
            ItemStack itemOnSlot = gloveSlot.getItemOnSlot();
            removeBonusStats(player, itemOnSlot);
            gloveSlot.clearItemOnSlot();
        }
        if (!ringSlot.isEmpty()) {
            ItemStack itemOnSlot = ringSlot.getItemOnSlot();
            removeBonusStats(player, itemOnSlot);
            ringSlot.clearItemOnSlot();
        }
        if (!eggSlot.isEmpty()) {
            eggSlot.clearItemOnSlot();
            PetManager.onEggUnequip(player);
        }
    }

    private void manageShoulderEntity(Player player) {
        if (!this.parrotSlot.isEmpty()) {
            ItemStack itemOnSlot = this.parrotSlot.getItemOnSlot();
            Parrot parrot = (Parrot) player.getWorld().spawnEntity(player.getLocation(), EntityType.PARROT);
            ItemMeta itemMeta = itemOnSlot.getItemMeta();
            String displayName = itemMeta.getDisplayName();

            parrot.setVariant(Parrot.Variant.GRAY);
            if (displayName.contains("Green")) {
                parrot.setVariant(Parrot.Variant.GREEN);
            } else if (displayName.contains("Blue")) {
                parrot.setVariant(Parrot.Variant.BLUE);
            } else if (displayName.contains("Red")) {
                parrot.setVariant(Parrot.Variant.RED);
            } else if (displayName.contains("LightBlue")) {
                parrot.setVariant(Parrot.Variant.CYAN);
            }
            player.setShoulderEntityLeft(parrot);
        } else {
            player.setShoulderEntityLeft(null);
        }
    }

    private void startPetSpawnCooldown(Player player) {
        petSpawnCooldownList.add(player);
        Bukkit.getScheduler().runTaskLater(GuardiansOfAdelia.getInstance(), () -> petSpawnCooldownList.remove(player), 20 * 8L);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        RPGCharacter rpgCharacter;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            } else {
                return;
            }
        } else {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();
        ItemStack current = event.getCurrentItem();
        int slot = event.getSlot();

        Inventory topInventory = event.getView().getTopInventory();

        ItemStack cursor = event.getCursor();
        Material cursorType = Material.AIR;
        if (cursor != null) {
            cursorType = cursor.getType();
        }

        String rpgClassStr = rpgCharacter.getRpgClassStr();
        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            event.setCancelled(true);
            if (cursorType.equals(Material.AIR)) {
                boolean change = this.onCursorClickWithAir(player, slot, topInventory, event.isShiftClick());
            } else {
                boolean change = this.onCursorClickWithItem(player, slot, cursor, topInventory, rpgClassStr);
            }
        } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
            if (cursorType.equals(Material.AIR)) {
                if (event.isShiftClick()) {
                    event.setCancelled(true);
                    boolean change = this.onShiftClick(current, player, slot, topInventory, rpgClassStr);
                }
            }
        }
    }
}
