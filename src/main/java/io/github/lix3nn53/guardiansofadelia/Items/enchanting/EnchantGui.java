package io.github.lix3nn53.guardiansofadelia.Items.enchanting;


import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class EnchantGui extends GuiGeneric {

    public EnchantGui(Player player) {
        super(27, ChatColor.AQUA + "Item Enchanting", 0);

        ItemStack itemGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemStack finalGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
        ItemStack stoneGlass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);

        ItemStack wool = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta im = itemGlass.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "§ePlace the item you");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "want to enchant here.");
        lore.add("");
        lore.add(ChatColor.GOLD + "Enhanceable items: ");
        lore.add(ChatColor.GRAY + "Armors and Shields");
        lore.add(ChatColor.GRAY + "Weapons");
        lore.add(ChatColor.GRAY + "Jewelry, Gloves, Parrots");
        im.setLore(lore);
        itemGlass.setItemMeta(im);
        ItemMeta im2 = finalGlass.getItemMeta();
        im2.setDisplayName(ChatColor.GREEN + "Enchant success rates:");
        lore = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            lore.add(ChatColor.GRAY.toString() + i + " -> " + (i + 1) + " = " + (int) (EnchantManager.getChance(player, i) * 100) + "%");
        }
        im2.setLore(lore);
        finalGlass.setItemMeta(im2);
        ItemMeta im3 = stoneGlass.getItemMeta();
        im3.setDisplayName(ChatColor.AQUA + "Place the appropriate level of");
        lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "enchant stone here.");
        lore.add("");
        lore.add(ChatColor.DARK_AQUA + "for +0~2 items > Level 1 enchant stone");
        lore.add(ChatColor.DARK_AQUA + "for +3~5 items > Level 2 enchant stone");
        lore.add(ChatColor.DARK_AQUA + "for +6~8 items > Level 3 enchant stone");
        lore.add(ChatColor.DARK_AQUA + "for +9~11 items > Level 4 enchant stone");
        im3.setLore(lore);
        stoneGlass.setItemMeta(im3);
        ItemMeta im4 = wool.getItemMeta();
        im4.setDisplayName(ChatColor.GREEN + "After you place the item");
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "and the enchant stone");
        lore.add(ChatColor.GREEN + "click emerald block to enchant!");
        lore.add("");
        lore.add(ChatColor.RED + "Warning:");
        lore.add(ChatColor.GRAY + "If it fails the enchant level");
        lore.add(ChatColor.GRAY + "of item decreases");
        im4.setLore(lore);
        wool.setItemMeta(im4);

        setItem(0, itemGlass);
        setItem(1, itemGlass);
        setItem(2, itemGlass);
        setItem(3, stoneGlass);
        setItem(4, stoneGlass);
        setItem(5, stoneGlass);
        setItem(6, finalGlass);
        setItem(7, finalGlass);
        setItem(8, finalGlass);
        setItem(9, itemGlass);
        setItem(11, itemGlass);
        setItem(12, stoneGlass);
        setItem(14, stoneGlass);
        setItem(15, finalGlass);
        setItem(17, finalGlass);
        setItem(16, wool);
        setItem(18, itemGlass);
        setItem(19, itemGlass);
        setItem(20, itemGlass);
        setItem(21, stoneGlass);
        setItem(22, stoneGlass);
        setItem(23, stoneGlass);
        setItem(24, finalGlass);
        setItem(25, finalGlass);
        setItem(26, finalGlass);
    }

    public boolean setItemToEnchant(ItemStack itemStack) {
        if (StatUtils.hasStatType(itemStack.getType())) {
            setItem(10, itemStack);
            return true;
        }
        return false;
    }

    public ItemStack getItemToEnchant() {
        return getItem(10);
    }

    public ItemStack getEnchantStone() {
        return getItem(13);
    }

    public void setEnchantStone(ItemStack enchantStone) {
        setItem(13, enchantStone);
    }

    public void startEnchanting(Player owner) {
        ItemStack enchantStone = getEnchantStone();
        final ItemStack itemToEnchant = getItemToEnchant();
        if (enchantStone != null && itemToEnchant != null) {
            if (!enchantStone.getType().equals(Material.AIR) && !itemToEnchant.getType().equals(Material.AIR)) {
                String name = enchantStone.getItemMeta().getDisplayName();
                if (name.contains("Enchant Stone")) {
                    int stoneLevel = PersistentDataContainerUtil.getInteger(enchantStone, "ench_stone");
                    int enchantLevel = EnchantManager.getEnchantLevel(itemToEnchant);
                    if (enchantLevel < 12) {
                        int requiredEnchantStoneLevel = EnchantManager.getRequiredEnchantStoneLevel(enchantLevel);
                        boolean stoneFits = stoneLevel == requiredEnchantStoneLevel;
                        if (stoneFits) {
                            Location loc = owner.getLocation();

                            GuiGeneric gui = this;

                            new BukkitRunnable() {

                                // We don't want the task to run indefinitely
                                int ticksRun;

                                @Override
                                public void run() {
                                    ticksRun++;
                                    if (ticksRun == 1) { // 20 ticks = 1 seconds
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 2) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 4) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 6) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 9) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 12) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 16) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 20) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 25) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 30) {
                                        InventoryUtils.fillWithRandomGlasses(gui);
                                    } else if (ticksRun == 40) {
                                        cancel();
                                        Enchant enchant = new Enchant(owner, itemToEnchant);
                                        boolean success = enchant.enchantItem();
                                        if (success) {
                                            setSuccessGui();
                                            CustomSound succ = GoaSound.SUCCESS.getCustomSound();
                                            succ.play(loc);
                                        } else {
                                            setFailGui();
                                            CustomSound fail = GoaSound.FAIL.getCustomSound();
                                            fail.play(loc);
                                        }
                                        InventoryUtils.removeMaterialFromInventory(owner.getInventory(), enchantStone.getType(), 1);
                                        InventoryUtils.removeItemFromInventory(owner.getInventory(), itemToEnchant, 1);
                                        InventoryUtils.giveItemToPlayer(owner, enchant.getItemStack());
                                        openInventory(owner);
                                    }
                                }
                            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 1L);

                        } else {
                            owner.sendMessage(ChatColor.RED + "You must place Enchant Stone Tier " + requiredEnchantStoneLevel + " to enchant this item");
                        }
                    } else {
                        owner.sendMessage(ChatColor.RED + "Your item is at max enchant level");
                    }
                }
            }
        }
    }

    private void setSuccessGui() {
        ItemStack success = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = success.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Success");
        itemMeta.setCustomModelData(5);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        success.setItemMeta(itemMeta);

        InventoryUtils.fillWithItem(this, success);
    }

    private void setFailGui() {
        ItemStack fail = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = fail.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Fail");
        itemMeta.setCustomModelData(6);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        fail.setItemMeta(itemMeta);

        InventoryUtils.fillWithItem(this, fail);
    }
}
