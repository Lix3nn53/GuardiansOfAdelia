package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportGuiItem;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportManager;
import io.github.lix3nn53.guardiansofadelia.transportation.TeleportationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GuiTeleportation extends GuiBookGeneric {

    public GuiTeleportation(GuardianData guardianData) {
        super(ChatPalette.PURPLE_LIGHT + "Instant Teleportation", 0);

        List<Integer> turnedInQuests = guardianData.getActiveCharacter().getTurnedInQuests();

        for (int questNo : InstantTeleportManager.questNoToTeleport.keySet()) {
            InstantTeleportGuiItem instantTeleportGuiItem = InstantTeleportManager.questNoToTeleport.get(questNo);
            boolean contains = turnedInQuests.contains(questNo);
            ItemStack itemStack = instantTeleportGuiItem.getItemStack(contains, questNo);

            this.addToFirstAvailableWord(itemStack);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
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
