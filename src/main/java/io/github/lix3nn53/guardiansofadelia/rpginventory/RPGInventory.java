package io.github.lix3nn53.guardiansofadelia.rpginventory;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.*;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RPGInventory {

    private final GuiGeneric rpgGui = new GuiGeneric(54, ChatColor.YELLOW.toString() + ChatColor.BOLD + "RPG Inventory", 0);

    private RPGSlotParrot parrotSlot = new RPGSlotParrot();
    private RPGSlotEarring earringSlot = new RPGSlotEarring();
    private RPGSlotNecklace necklaceSlot = new RPGSlotNecklace();
    private RPGSlotGlove gloveSlot = new RPGSlotGlove();
    private RPGSlotRing ringSlot = new RPGSlotRing();
    private PetSlot petSlot = new PetSlot();
    private VanillaSlotHelmet helmetSlot = new VanillaSlotHelmet();
    private VanillaSlotChestplate chestplateSlot = new VanillaSlotChestplate();
    private VanillaSlotLeggings leggingsSlot = new VanillaSlotLeggings();
    private VanillaSlotBoots bootsSlot = new VanillaSlotBoots();
    private VanillaSlotOffhand offhandSlot = new VanillaSlotOffhand();
    private VanillaSlotMainhand mainhandSlot = new VanillaSlotMainhand();

    public GuiGeneric formRPGInventory(Player player) {
        if (parrotSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.PARROT.getSlotNo(), parrotSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.PARROT.getSlotNo(), parrotSlot.getItemOnSlot());
        }

        if (earringSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.EARRING.getSlotNo(), earringSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.EARRING.getSlotNo(), earringSlot.getItemOnSlot());
        }

        if (necklaceSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.NECKLACE.getSlotNo(), necklaceSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.NECKLACE.getSlotNo(), necklaceSlot.getItemOnSlot());
        }

        if (gloveSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.GLOVE.getSlotNo(), gloveSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.GLOVE.getSlotNo(), gloveSlot.getItemOnSlot());
        }

        if (ringSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.RING.getSlotNo(), ringSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.RING.getSlotNo(), ringSlot.getItemOnSlot());
        }

        if (petSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.PET.getSlotNo(), petSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.PET.getSlotNo(), petSlot.getItemOnSlot());
        }

        if (helmetSlot.isEmpty(player)) {
            rpgGui.setItem(RPGSlotType.HELMET.getSlotNo(), helmetSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.HELMET.getSlotNo(), helmetSlot.getItemOnSlot(player));
        }

        if (chestplateSlot.isEmpty(player)) {
            rpgGui.setItem(RPGSlotType.CHESTPLATE.getSlotNo(), chestplateSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.CHESTPLATE.getSlotNo(), chestplateSlot.getItemOnSlot(player));
        }

        if (leggingsSlot.isEmpty(player)) {
            rpgGui.setItem(RPGSlotType.LEGGINGS.getSlotNo(), leggingsSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.LEGGINGS.getSlotNo(), leggingsSlot.getItemOnSlot(player));
        }

        if (bootsSlot.isEmpty(player)) {
            rpgGui.setItem(RPGSlotType.BOOTS.getSlotNo(), bootsSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.BOOTS.getSlotNo(), bootsSlot.getItemOnSlot(player));
        }

        if (offhandSlot.isEmpty(player)) {
            rpgGui.setItem(RPGSlotType.OFFHAND.getSlotNo(), offhandSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.OFFHAND.getSlotNo(), offhandSlot.getItemOnSlot(player));
        }

        if (mainhandSlot.isEmpty(player)) {
            rpgGui.setItem(RPGSlotType.MAINHAND.getSlotNo(), mainhandSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.MAINHAND.getSlotNo(), mainhandSlot.getItemOnSlot(player));
        }

        rpgGui.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());

        InventoryUtils.fillEmptySlotsWithGlass(rpgGui);
        rpgGui.setLocked(false);

        return rpgGui;
    }

    public StatPassive getTotalPassiveStat() {
        int fire = parrotSlot.getBonusStats().getFire() + earringSlot.getBonusStats().getFire() + necklaceSlot.getBonusStats().getFire()
                + gloveSlot.getBonusStats().getFire() + ringSlot.getBonusStats().getFire();
        int water = parrotSlot.getBonusStats().getWater() + earringSlot.getBonusStats().getWater() + necklaceSlot.getBonusStats().getWater()
                + gloveSlot.getBonusStats().getWater() + ringSlot.getBonusStats().getWater();
        int earth = parrotSlot.getBonusStats().getEarth() + earringSlot.getBonusStats().getEarth() + necklaceSlot.getBonusStats().getEarth()
                + gloveSlot.getBonusStats().getEarth() + ringSlot.getBonusStats().getEarth();
        int lightning = parrotSlot.getBonusStats().getLightning() + earringSlot.getBonusStats().getLightning() + necklaceSlot.getBonusStats().getLightning()
                + gloveSlot.getBonusStats().getLightning() + ringSlot.getBonusStats().getLightning();
        int air = parrotSlot.getBonusStats().getAir() + earringSlot.getBonusStats().getAir() + necklaceSlot.getBonusStats().getAir()
                + gloveSlot.getBonusStats().getAir() + ringSlot.getBonusStats().getAir();

        return new StatPassive(fire, water, earth, lightning, air);
    }

    private int getSlotNo(RPGSlotType slotType) {
        return slotType.getSlotNo();
    }

    public boolean setParrot(ItemStack itemStack, Player player) {
        if (this.parrotSlot.doesFit(itemStack)) {
            this.parrotSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public void clearParrot() {
        this.parrotSlot.clearItemOnSlot();
    }

    public boolean setEarring(ItemStack itemStack, Player player) {
        if (this.earringSlot.doesFit(itemStack)) {
            this.earringSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public void clearEarring() {
        this.earringSlot.clearItemOnSlot();
    }

    public boolean setNecklace(ItemStack itemStack, Player player) {
        if (this.necklaceSlot.doesFit(itemStack)) {
            this.necklaceSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public void clearNecklace() {
        this.necklaceSlot.clearItemOnSlot();
    }

    public boolean setGlove(ItemStack itemStack, Player player) {
        if (this.gloveSlot.doesFit(itemStack)) {
            this.gloveSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public void clearGlove() {
        this.gloveSlot.clearItemOnSlot();
    }

    public boolean setRing(ItemStack itemStack, Player player) {
        if (this.ringSlot.doesFit(itemStack)) {
            this.ringSlot.setItemOnSlot(itemStack);
            addBonusStats(player, itemStack);
            return true;
        }
        return false;
    }

    public void clearRing() {
        this.ringSlot.clearItemOnSlot();
    }

    public boolean setPet(ItemStack itemStack, Player player) {
        if (this.petSlot.doesFit(itemStack)) {
            this.petSlot.setItemOnSlot(itemStack);
            return true;
        }
        return false;
    }

    public void clearPet() {
        this.petSlot.clearItemOnSlot();
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

    public PetSlot getPetSlot() {
        return petSlot;
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

    public VanillaSlotMainhand getMainhandSlot() {
        return mainhandSlot;
    }

    public boolean onShiftClick(ItemStack itemStack, Player player, int slot, Inventory topInventory) {
        ItemStack oldItemOnSlot = null;
        boolean change = false;
        int topSlot = 0;
        if (parrotSlot.doesFit(itemStack)) {
            if (!parrotSlot.isEmpty()) {
                oldItemOnSlot = parrotSlot.getItemOnSlot();
            }
            change = setParrot(itemStack, player);
            topSlot = RPGSlotType.PARROT.getSlotNo();
        } else if (earringSlot.doesFit(itemStack)) {
            if (!earringSlot.isEmpty()) {
                oldItemOnSlot = earringSlot.getItemOnSlot();
            }
            change = setEarring(itemStack, player);
            topSlot = RPGSlotType.EARRING.getSlotNo();
        } else if (necklaceSlot.doesFit(itemStack)) {
            if (!necklaceSlot.isEmpty()) {
                oldItemOnSlot = necklaceSlot.getItemOnSlot();
            }
            change = setNecklace(itemStack, player);
            topSlot = RPGSlotType.NECKLACE.getSlotNo();
        } else if (gloveSlot.doesFit(itemStack)) {
            if (!gloveSlot.isEmpty()) {
                oldItemOnSlot = gloveSlot.getItemOnSlot();
            }
            change = setGlove(itemStack, player);
            topSlot = RPGSlotType.GLOVE.getSlotNo();
        } else if (ringSlot.doesFit(itemStack)) {
            if (!ringSlot.isEmpty()) {
                oldItemOnSlot = ringSlot.getItemOnSlot();
            }
            change = setRing(itemStack, player);
            topSlot = RPGSlotType.RING.getSlotNo();
        } else if (petSlot.doesFit(itemStack)) {
            if (!petSlot.isEmpty()) {
                oldItemOnSlot = petSlot.getItemOnSlot();
            }
            change = setPet(itemStack, player);
            topSlot = RPGSlotType.PET.getSlotNo();
        }
        if (change) {
            if (oldItemOnSlot != null) {
                player.getInventory().setItem(slot, oldItemOnSlot);
            } else {
                player.getInventory().setItem(slot, new ItemStack(Material.AIR));
            }
            topInventory.setItem(topSlot, itemStack);
            topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
        }
        return change;
    }

    public void removeBonusStats(Player player, ItemStack itemStack) {
        StatType statType = StatUtils.getStatType(itemStack.getType());
        if (statType.equals(StatType.PASSIVE)) {
            StatPassive statPassive = (StatPassive) StatUtils.getStat(itemStack);

            SkillAPIUtils.removeBonusAttribute(player, "fire", statPassive.getFire());
            SkillAPIUtils.removeBonusAttribute(player, "earth", statPassive.getEarth());
            SkillAPIUtils.removeBonusAttribute(player, "water", statPassive.getWater());
            SkillAPIUtils.removeBonusAttribute(player, "lightning", statPassive.getLightning());
            SkillAPIUtils.removeBonusAttribute(player, "air", statPassive.getAir());
        }
    }

    public void addBonusStats(Player player, ItemStack itemStack) {
        StatType statType = StatUtils.getStatType(itemStack.getType());
        if (statType.equals(StatType.PASSIVE)) {
            StatPassive statPassive = (StatPassive) StatUtils.getStat(itemStack);

            SkillAPIUtils.addBonusAttribute(player, "fire", statPassive.getFire());
            SkillAPIUtils.addBonusAttribute(player, "earth", statPassive.getEarth());
            SkillAPIUtils.addBonusAttribute(player, "water", statPassive.getWater());
            SkillAPIUtils.addBonusAttribute(player, "lightning", statPassive.getLightning());
            SkillAPIUtils.addBonusAttribute(player, "air", statPassive.getAir());
        }
    }

    public boolean onCursorClickWithItem(Player player, int slot, ItemStack cursor, Inventory topInventory) {
        if (slot == RPGSlotType.PARROT.getSlotNo()) {
            RPGSlotParrot rpgSlot = getParrotSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setParrot(cursor, player);
                if (didEquip) {
                    removeBonusStats(player, itemOnSlot);
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                    return true;
                }
            } else {
                boolean didEquip = setParrot(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                    return true;
                }
            } else {
                boolean didEquip = setEarring(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                    return true;
                }
            } else {
                boolean didEquip = setNecklace(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                    return true;
                }
            } else {
                boolean didEquip = setRing(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                    return true;
                }
            } else {
                boolean didEquip = setGlove(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                    return true;
                }
            }
        } else if (slot == RPGSlotType.PET.getSlotNo()) {
            PetSlot rpgSlot = getPetSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                boolean didEquip = setPet(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(itemOnSlot);
                    topInventory.setItem(slot, cursor);
                    return true;
                }
            } else {
                boolean didEquip = setPet(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onCursorClickWithAir(Player player, int slot, Inventory topInventory, boolean isShiftClick) {
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
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
                topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
                return true;
            }
        } else if (slot == RPGSlotType.PET.getSlotNo()) {
            PetSlot rpgSlot = getPetSlot();
            if (!rpgSlot.isEmpty()) {
                ItemStack itemOnSlot = rpgSlot.getItemOnSlot();
                if (isShiftClick) {
                    InventoryUtils.giveItemToPlayer(player, itemOnSlot);
                } else {
                    player.setItemOnCursor(itemOnSlot);
                }
                rpgSlot.clearItemOnSlot();
                topInventory.setItem(slot, petSlot.getFillItem());
                return true;
            }
        }
        return false;
    }
}
