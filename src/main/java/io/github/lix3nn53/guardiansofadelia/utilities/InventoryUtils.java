package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

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
        if (itemStack.getType().equals(Material.AIR)) return;

        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(itemStack);
        } else {
            //make sure entity add is sync
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                }
            }.runTask(GuardiansOfAdelia.getInstance());

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

    public static boolean inventoryContains(Inventory inventory, Material mat, int amount) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType().equals(mat)) {
                count += items[i].getAmount();
            }
            if (count >= amount) {
                return true;
            }
        }
        return false;
    }

    public static int getHowManyInventoryHasFromName(Inventory inventory, String searchName) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].hasItemMeta()) {
                ItemMeta itemMeta = items[i].getItemMeta();
                if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(searchName)) {
                    count += items[i].getAmount();
                }
            }
        }
        return count;
    }

    public static void removeAllFromInventoryByName(Inventory inventory, String searchName) {
        ItemStack[] inventoryContents = inventory.getContents();
        for (int i = 0; i < inventoryContents.length; i++) {
            if (inventoryContents[i] != null && inventoryContents[i].hasItemMeta()) {
                ItemMeta itemMeta = inventoryContents[i].getItemMeta();
                if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(searchName)) {
                    inventoryContents[i] = null;
                }
            }
        }
        inventory.setContents(inventoryContents);
    }

    public static boolean isArmorEquippedOnRelatedSlot(ArmorType armorTypeOfCurrentItem, Player player) {
        if (armorTypeOfCurrentItem.equals(ArmorType.HELMET)) {
            return !isAirOrNull(player.getInventory().getHelmet());
        } else if (armorTypeOfCurrentItem.equals(ArmorType.CHESTPLATE)) {
            return !isAirOrNull(player.getInventory().getChestplate());
        } else if (armorTypeOfCurrentItem.equals(ArmorType.LEGGINGS)) {
            return !isAirOrNull(player.getInventory().getLeggings());
        } else if (armorTypeOfCurrentItem.equals(ArmorType.BOOTS)) {
            return !isAirOrNull(player.getInventory().getBoots());
        }

        return false;
    }

    /**
     * A utility method to support versions that use null or air ItemStacks.
     */
    public static boolean isAirOrNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
