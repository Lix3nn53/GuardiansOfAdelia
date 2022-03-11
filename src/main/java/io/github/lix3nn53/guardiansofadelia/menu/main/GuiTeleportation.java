package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportGuiItem;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportManager;
import io.github.lix3nn53.guardiansofadelia.transportation.TeleportationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiTeleportation extends GuiBookGeneric {

    public GuiTeleportation(GuardianData guardianData, int playerLevel) {
        super(CustomCharacterGui.MENU_54_FLAT.toString() + ChatPalette.BLACK + "Instant Teleportation", 0);

        ItemStack backButton = OtherItems.getBackButton("Compass Menu");
        this.addToFirstAvailableWord(backButton);
        this.disableLine(0, 0);

        for (int code : InstantTeleportManager.codeToTeleport.keySet()) {
            InstantTeleportGuiItem instantTeleportGuiItem = InstantTeleportManager.codeToTeleport.get(code);
            ItemStack itemStack = instantTeleportGuiItem.getItemStack(guardianData, playerLevel, code);

            this.addToFirstAvailableWord(itemStack);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            if (event.getSlot() == 0) {
                GuardianData guardianData;
                if (GuardianDataManager.hasGuardianData(player)) {
                    guardianData = GuardianDataManager.getGuardianData(player);
                } else {
                    return;
                }

                GuiCompass gui = new GuiCompass(guardianData);
                gui.openInventory(player);
                return;
            }

            ItemStack currentItem = event.getCurrentItem();
            Material currentType = currentItem.getType();

            if (currentType.equals(Material.LIME_WOOL)) {
                if (!player.getLocation().getWorld().getName().equals("world")) {
                    player.sendMessage(ChatPalette.RED + "You can't use teleport gui from this location.");
                    return;
                }

                ItemMeta itemMeta = currentItem.getItemMeta();
                String currentName = itemMeta.getDisplayName();
                String[] split = currentName.split("#");
                int i = Integer.parseInt(split[1]);
                InstantTeleportGuiItem teleport = InstantTeleportManager.getTeleport(i);

                int cost = teleport.getCost();
                boolean canPay = EconomyUtils.canPay(player, cost);

                player.closeInventory();
                if (canPay) {
                    Location location = teleport.getLocation();
                    TeleportationUtils.teleport(player, location, teleport.getName(), 5, null, cost);
                } else {
                    player.sendMessage(ChatPalette.RED + "You don't have enough coins to teleport.");
                }
            }
        }
    }
}
