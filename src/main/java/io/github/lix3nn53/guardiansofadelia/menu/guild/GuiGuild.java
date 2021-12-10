package io.github.lix3nn53.guardiansofadelia.menu.guild;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiGuild extends GuiGeneric {

    public GuiGuild() {
        super(27, ChatPalette.GRAY_DARK + "Guild", 0);

        ItemStack guildWar = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = guildWar.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "Join Guild War");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to join a guild war");
        itemMeta.setLore(lore);
        guildWar.setItemMeta(itemMeta);
        this.setItem(17, guildWar);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            int slot = event.getSlot();

            if (slot == 17) {
                GuiGuildWar gui = new GuiGuildWar();
                gui.openInventory(player);
            }
        }
    }
}
