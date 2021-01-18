package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BazaarCustomerGui extends GuiGeneric {

    private final Player owner;

    public BazaarCustomerGui(Player owner) {
        super(27, net.md_5.bungee.api.ChatColor.GOLD + owner.getName() + "'s bazaar", 0);

        this.owner = owner;

        ItemStack glassInfo = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = glassInfo.getItemMeta();

        itemMeta.setDisplayName(ChatColor.GOLD + "Double click to buy an item");
        glassInfo.setItemMeta(itemMeta);

        for (int i = 18; i <= 26; i++) {
            setItem(i, glassInfo);
        }
    }

    public Player getOwner() {
        return owner;
    }

    public Bazaar getBazaar() {
        if (GuardianDataManager.hasGuardianData(owner)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner);
            if (guardianData.hasBazaar()) {
                return guardianData.getBazaar();
            }
        }
        return null;
    }
}
