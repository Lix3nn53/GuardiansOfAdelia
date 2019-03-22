package io.github.lix3nn53.guardiansofadelia.Items.enchanting;


import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class EnchantGui {

    GuiGeneric gui;
    Player owner;

    public EnchantGui(Player owner) {
        this.owner = owner;
        gui = new GuiGeneric(27, ChatColor.AQUA + "Item Enchanting", 0);

        ItemStack itemGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemStack finalGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
        ItemStack stoneGlass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);

        ItemStack wool = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta im = itemGlass.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "§ePlace the item you");
        im.setLore(new ArrayList() {{
            add(ChatColor.YELLOW + "want to enchant here.");
            add("");
            add(ChatColor.GOLD + "Enhanceable items: ");
            add(ChatColor.GRAY + "Armors and Shields");
            add(ChatColor.GRAY + "Weapons");
            add(ChatColor.GRAY + "Jewelry, Gloves, Parrots");
        }});
        itemGlass.setItemMeta(im);
        ItemMeta im2 = finalGlass.getItemMeta();
        im2.setDisplayName(ChatColor.GREEN + "Enchant success rates:");
        im2.setLore(new ArrayList() {{
            add(ChatColor.GRAY + "0 -> 1 = 84%");
            add(ChatColor.GRAY + "1 -> 2 = 8%");
            add(ChatColor.GRAY + "2 -> 3 = 76%");
            add(ChatColor.GRAY + "3 -> 4 = 72%");
            add(ChatColor.GRAY + "4 -> 5 = 67%");
            add(ChatColor.GRAY + "5 -> 6 = 60%");
            add(ChatColor.GRAY + "6 -> 7 = 53%");
            add(ChatColor.GRAY + "7 -> 8 = 47%");
            add(ChatColor.GRAY + "8 -> 9 = 40%");
            add(ChatColor.GRAY + "9 -> 10 = 32%");
            add(ChatColor.GRAY + "10 -> 11 = 26%");
            add(ChatColor.GRAY + "11 -> 12 = 20%");
        }});
        finalGlass.setItemMeta(im2);
        ItemMeta im3 = stoneGlass.getItemMeta();
        im3.setDisplayName(ChatColor.AQUA + "Place the appropriate level of");
        im3.setLore(new ArrayList() {{
            add(ChatColor.AQUA + "enchant stone here.");
            add("");
            add(ChatColor.DARK_AQUA + "for +0~2 items > Level 1 enchant stone");
            add(ChatColor.DARK_AQUA + "for +3~5 items > Level 2 enchant stone");
            add(ChatColor.DARK_AQUA + "for +6~8 items > Level 3 enchant stone");
            add(ChatColor.DARK_AQUA + "for +9~11 items > Level 4 enchant stone");
        }});
        stoneGlass.setItemMeta(im3);
        ItemMeta im4 = wool.getItemMeta();
        im4.setDisplayName(ChatColor.GREEN + "After you place the item");
        im4.setLore(new ArrayList() {{
            add(ChatColor.GREEN + "and the enchant stone");
            add(ChatColor.GREEN + "click emerald block to enchant!");
            add("");
            add(ChatColor.RED + "NOTE:");
            add(ChatColor.GRAY + "If it fails the level of");
            add(ChatColor.GRAY + "enchant falls back.");
        }});
        wool.setItemMeta(im4);

        gui.setItem(0, itemGlass);
        gui.setItem(1, itemGlass);
        gui.setItem(2, itemGlass);
        gui.setItem(3, stoneGlass);
        gui.setItem(4, stoneGlass);
        gui.setItem(5, stoneGlass);
        gui.setItem(6, finalGlass);
        gui.setItem(7, finalGlass);
        gui.setItem(8, finalGlass);
        gui.setItem(9, itemGlass);
        gui.setItem(11, itemGlass);
        gui.setItem(12, stoneGlass);
        gui.setItem(14, stoneGlass);
        gui.setItem(15, finalGlass);
        gui.setItem(17, finalGlass);
        gui.setItem(16, wool);
        gui.setItem(18, itemGlass);
        gui.setItem(19, itemGlass);
        gui.setItem(20, itemGlass);
        gui.setItem(21, stoneGlass);
        gui.setItem(22, stoneGlass);
        gui.setItem(23, stoneGlass);
        gui.setItem(24, finalGlass);
        gui.setItem(25, finalGlass);
        gui.setItem(26, finalGlass);
    }

    public boolean setItemToEnchant(ItemStack itemStack) {
        if (StatUtils.hasStatType(itemStack.getType())) {
            gui.setItem(10, itemStack);
            return true;
        }
        return false;
    }

    public boolean setEnchantStone(ItemStack itemStack, ItemStack enchantStone) {
        String name = enchantStone.getItemMeta().getDisplayName();
        if (name.contains("Enchant Stone")) {
            int stoneLevel = 0;
            if (name.contains("1")) {
                stoneLevel = 1;
            } else if (name.contains("2")) {
                stoneLevel = 3;
            } else if (name.contains("3")) {
                stoneLevel = 3;
            } else if (name.contains("4")) {
                stoneLevel = 4;
            }
            boolean b = stoneLevel == EnchantManager.getRequiredEnchantStoneLevel(EnchantManager.getEnchantLevel(itemStack));
            if (b) {
                gui.setItem(13, enchantStone);
            }
            return b;
        }
        return false;
    }

    public ItemStack getItemToEnchant() {
        return gui.getItem(10);
    }

    public ItemStack getEnchantStone() {
        return gui.getItem(13);
    }

    public void startEnchanting() {
        Location loc = owner.getLocation();
        World w = loc.getWorld();

        w.playSound(loc, "randoming", 0.5F, 0.9F);

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
                    Enchant enchant = new Enchant(owner, getItemToEnchant());
                    boolean success = enchant.enchantItem();
                    if (success) {
                        setSuccessGui(enchant.getItemStack());
                    } else {
                        setFailGui(enchant.getItemStack());
                    }
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 5L);
    }

    private void setSuccessGui(ItemStack itemStack) {
        ItemStack success = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = success.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Success");
        Damageable damageable = (Damageable) itemMeta;
        damageable.setDamage(5);
        success.setItemMeta(itemMeta);

        InventoryUtils.fillWithItem(gui, success);
        gui.setItem(13, itemStack);
    }

    private void setFailGui(ItemStack itemStack) {
        ItemStack fail = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = fail.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Fail");
        Damageable damageable = (Damageable) itemMeta;
        damageable.setDamage(4);
        fail.setItemMeta(itemMeta);

        InventoryUtils.fillWithItem(gui, fail);
        gui.setItem(13, itemStack);
    }

    public GuiGeneric getGui() {
        return gui;
    }
}
