package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryUtils {

    public static void fillWithRandomGlasses(GuiGeneric inventory) {
        List<ItemStack> glasses = new ArrayList<ItemStack>();

        glasses.add(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.LIME_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.PINK_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.CYAN_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.BROWN_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
        glasses.add(new ItemStack(Material.RED_STAINED_GLASS_PANE));

        for (int i = 0; i < inventory.getSize(); i++) {
            int glassNo = new Random().nextInt((13) + 1);
            inventory.setItem(i, glasses.get(glassNo));
        }
    }

    public static void fillWithItem(GuiGeneric inventory, ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
        }
    }

    public static void fillEmptySlotsWithGlass(GuiGeneric inventory) {
        ItemStack fillItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = fillItem.getItemMeta();
        statusItemMeta.setDisplayName(org.bukkit.ChatColor.DARK_GRAY + "-");
        fillItem.setItemMeta(statusItemMeta);
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                inventory.setItem(i, fillItem);
            } else if (item.getType().equals(Material.AIR)) {
                inventory.setItem(i, fillItem);
            }
        }
    }

    public static int getHowManyEmptySlots(Inventory inventory) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            if (inventory.getItem(i) != null) {
                if (!inventory.getItem(i).getType().equals(Material.AIR)) {
                    count = count++;
                }
            }
        }
        return inventory.getSize() - count;
    }

    public static void giveItemToPlayer(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(itemStack);
        } else {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Inventory Full !");
            player.sendMessage(ChatColor.RED + "Your item have been dropped near you since your inventory is full");
        }
    }

    public static void removeItemFromInventory(Inventory inventory, ItemStack itemStack, int amount) {
        ItemStack[] inventoryContents = inventory.getContents();
        for (int i = 0; i < inventoryContents.length; i++) {
            if (inventoryContents[i] != null) {
                if (inventoryContents[i].isSimilar(itemStack)) {
                    if (inventoryContents[i].getAmount() > amount) {
                        inventoryContents[i].setAmount(inventoryContents[i].getAmount() - amount);
                        break;
                    } else if (inventoryContents[i].getAmount() == amount) {
                        inventoryContents[i] = null;
                        break;
                    } else {
                        amount -= inventoryContents[i].getAmount();
                        inventoryContents[i] = null;
                    }
                }
            }
        }
        inventory.setContents(inventoryContents);
    }

    public static void removeMaterialFromInventory(Inventory inventory, Material material, int amount) {
        ItemStack[] inventoryContents = inventory.getContents();
        for (int i = 0; i < inventoryContents.length; i++) {
            if (inventoryContents[i] != null) {
                if (inventoryContents[i].getType().equals(material)) {
                    if (inventoryContents[i].getAmount() > amount) {
                        inventoryContents[i].setAmount(inventoryContents[i].getAmount() - amount);
                        break;
                    } else if (inventoryContents[i].getAmount() == amount) {
                        inventoryContents[i] = null;
                        break;
                    } else {
                        amount -= inventoryContents[i].getAmount();
                        inventoryContents[i] = null;
                    }
                }
            }
        }
        inventory.setContents(inventoryContents);
    }

    public static void setMenuItemPlayer(Player player) {
        ItemStack menu = new ItemStack(Material.BOOK);
        ItemMeta im = menu.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Menu");
        im.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to open the menu!");
        }});
        menu.setItemMeta(im);
        player.getInventory().setItem(17, menu);
    }
}
