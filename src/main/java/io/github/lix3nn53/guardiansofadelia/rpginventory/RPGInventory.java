package io.github.lix3nn53.guardiansofadelia.rpginventory;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.*;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RPGInventory {

    private final GuiGeneric rpgGui = new GuiGeneric(54, ChatColor.YELLOW.toString() + ChatColor.BOLD + "RPG Inventory", 0);

    private final List<Player> petSpawnCooldownList = new ArrayList<>();

    private RPGSlotParrot parrotSlot = new RPGSlotParrot();
    private RPGSlotEarring earringSlot = new RPGSlotEarring();
    private RPGSlotNecklace necklaceSlot = new RPGSlotNecklace();
    private RPGSlotGlove gloveSlot = new RPGSlotGlove();
    private RPGSlotRing ringSlot = new RPGSlotRing();
    private EggSlot eggSlot = new EggSlot();
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

        if (eggSlot.isEmpty()) {
            rpgGui.setItem(RPGSlotType.PET.getSlotNo(), eggSlot.getFillItem());
        } else {
            rpgGui.setItem(RPGSlotType.PET.getSlotNo(), eggSlot.getItemOnSlot());
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
        int wind = parrotSlot.getBonusStats().getWind() + earringSlot.getBonusStats().getWind() + necklaceSlot.getBonusStats().getWind()
                + gloveSlot.getBonusStats().getWind() + ringSlot.getBonusStats().getWind();

        return new StatPassive(fire, water, earth, lightning, wind);
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

    public VanillaSlotMainhand getMainhandSlot() {
        return mainhandSlot;
    }

    public boolean onShiftClick(ItemStack itemStack, Player player, int slot, Inventory topInventory) {
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
            topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
        StatType statType = StatUtils.getStatType(itemStack.getType());
        if (statType.equals(StatType.PASSIVE)) {
            StatPassive statPassive = (StatPassive) StatUtils.getStat(itemStack);

            UUID uuid = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    rpgCharacterStats.getFire().removeBonusFromPassive(statPassive.getFire(), rpgCharacterStats, false);
                    rpgCharacterStats.getEarth().removeBonusFromPassive(statPassive.getEarth(), rpgCharacterStats, true);
                    rpgCharacterStats.getWater().removeBonusFromPassive(statPassive.getWater(), rpgCharacterStats, true);
                    rpgCharacterStats.getLightning().removeBonusFromPassive(statPassive.getLightning(), rpgCharacterStats, false);
                    rpgCharacterStats.getWind().removeBonusFromPassive(statPassive.getWind(), rpgCharacterStats, false);
                }
            }
        }
    }

    public void addBonusStats(Player player, ItemStack itemStack) {
        StatType statType = StatUtils.getStatType(itemStack.getType());
        if (statType.equals(StatType.PASSIVE)) {
            StatPassive statPassive = (StatPassive) StatUtils.getStat(itemStack);

            UUID uuid = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    rpgCharacterStats.getFire().addBonusToPassive(statPassive.getFire(), rpgCharacterStats, false);
                    rpgCharacterStats.getEarth().addBonusToPassive(statPassive.getEarth(), rpgCharacterStats, true);
                    rpgCharacterStats.getWater().addBonusToPassive(statPassive.getWater(), rpgCharacterStats, true);
                    rpgCharacterStats.getLightning().addBonusToPassive(statPassive.getLightning(), rpgCharacterStats, false);
                    rpgCharacterStats.getWind().addBonusToPassive(statPassive.getWind(), rpgCharacterStats, false);
                }
            }
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
                    manageShoulderEntity(player);
                    return true;
                }
            } else {
                boolean didEquip = setParrot(cursor, player);
                if (didEquip) {
                    player.setItemOnCursor(new ItemStack(Material.AIR));
                    topInventory.setItem(slot, cursor);
                    topInventory.setItem(RPGSlotType.CHARACTER_INFO.getSlotNo(), new CharacterInfoSlot(player).getItem());
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
}
