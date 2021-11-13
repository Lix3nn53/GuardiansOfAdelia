package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.menu.ActiveGuiManager;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiMain;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class MyInventoryClickEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();

        ItemStack current = event.getCurrentItem();
        Material currentType = Material.AIR;
        if (current != null) {
            currentType = current.getType();
        }

        String title = event.getView().getTitle();

        Inventory clickedInventory = event.getClickedInventory();

        ItemStack cursor = event.getCursor();
        Material cursorType = Material.AIR;
        if (cursor != null) {
            cursorType = cursor.getType();
        }

        if (event.getAction() != InventoryAction.NOTHING) {
            if (event.getClick().equals(ClickType.NUMBER_KEY)) {
                int hotbarButton = event.getHotbarButton();
                if (hotbarButton >= 0 && hotbarButton <= 3) { //skill bar
                    event.setCancelled(true);
                    return;
                }
            } else if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (slot >= 0 && slot <= 3) { //skill bar
                    event.setCancelled(true);
                    return;
                }
            }
        }

        GuardianData guardianData = null;
        RPGCharacter rpgCharacter = null;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            }
        }

        //Open RPG-Inventory
        if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.CRAFTING)) {
            event.setCancelled(true);
            if (cursorType.equals(Material.AIR) && rpgCharacter != null) {
                RPGInventory rpgInventory = rpgCharacter.getRpgInventory();
                rpgInventory.update(player);
                rpgInventory.openInventory(player);
            }
            return;
        }

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        if (!(event.getCurrentItem().hasItemMeta())) return;
        if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        if (ActiveGuiManager.hasActiveGui(player)) {
            Gui activeGui = ActiveGuiManager.getActiveGui(player);

            if (activeGui.isLocked()) {
                event.setCancelled(true);
            }

            if (activeGui instanceof GuiBookGeneric) {
                GuiBookGeneric guiBookGeneric = (GuiBookGeneric) activeGui;
                if (slot == 53) {
                    //next page
                    String[] split = title.split("Page-");
                    int pageIndex = Integer.parseInt(split[1]) - 1;
                    pageIndex++;
                    guiBookGeneric.openInventoryPage(player, pageIndex);
                    return;
                } else if (slot == 45) {
                    //previous page
                    String[] split = title.split("Page-");
                    int pageIndex = Integer.parseInt(split[1]) - 1;
                    pageIndex--;
                    guiBookGeneric.openInventoryPage(player, pageIndex);
                    return;
                }
            }

            activeGui.onClick(event);
        }

        ItemMeta itemMeta = current.getItemMeta();
        String currentName = itemMeta.getDisplayName();

        if (currentName.equals(ChatPalette.GREEN_DARK + "Menu")) {
            event.setCancelled(true);
            if (!cursorType.equals(Material.AIR)) {
                return;
            }

            final GuardianData guardianDataCopy = guardianData;
            new BukkitRunnable() {

                @Override
                public void run() {
                    GuiMain mainMenu = new GuiMain(guardianDataCopy);
                    mainMenu.openInventory(player);
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 1);
        } else if (currentType.equals(Material.ARROW)) {
            event.setCancelled(true);
        } else if (title.equals("Crafting")) { // player inventory click listeners for helmet skins
            if (event.isShiftClick()) {
                if (currentType.equals(HelmetSkin.getHelmetMaterial())) {
                    InventoryType.SlotType slotType = event.getSlotType();
                    if (slotType.equals(InventoryType.SlotType.CONTAINER) || slotType.equals(InventoryType.SlotType.QUICKBAR)) {
                        EntityEquipment equipment = player.getEquipment();
                        ItemStack helmet = equipment.getHelmet();
                        equipment.setHelmet(current);
                        clickedInventory.setItem(slot, helmet);
                        event.setCancelled(true);
                        return;
                    } else if (event.getRawSlot() == ArmorSlot.HELMET.getSlot()) {
                        PlayerInventory playerInventory = player.getInventory();
                        if (playerInventory.firstEmpty() != -1) { //has empty slot
                            EntityEquipment equipment = player.getEquipment();
                            ItemStack helmet = equipment.getHelmet();
                            equipment.setHelmet(null);
                            InventoryUtils.giveItemToPlayer(player, helmet);
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            } else if (event.getRawSlot() == ArmorSlot.HELMET.getSlot()) {
                if (cursorType.equals(HelmetSkin.getHelmetMaterial())) {
                    EntityEquipment equipment = player.getEquipment();
                    ItemStack helmet = equipment.getHelmet();
                    equipment.setHelmet(cursor);
                    player.setItemOnCursor(helmet);
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
