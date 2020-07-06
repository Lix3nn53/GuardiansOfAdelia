package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.chat.PremiumRank;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class CommandChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("chat")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/chat item - show item in chat");
            } else if (args[0].equals("item")) {

                UUID uuid = player.getUniqueId();
                if (!GuardianDataManager.hasGuardianData(uuid)) return false;

                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

                PremiumRank premiumRank = guardianData.getPremiumRank();

                if (!(premiumRank.equals(PremiumRank.LEGEND) || premiumRank.equals(PremiumRank.TITAN))) {
                    player.sendMessage(ChatColor.RED + "Required premium rank to display items in chat is " + ChatColor.GOLD + "LEGEND");
                    return false;
                }

                GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.DARK_GRAY + "Select item to show in chat", 0);

                ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta itemMeta = filler.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GRAY + "Items in inventory");
                filler.setItemMeta(itemMeta);
                guiGeneric.addItem(filler);

                PlayerInventory inventory = player.getInventory();

                for (ItemStack itemStack : inventory.getContents()) {
                    if (itemStack == null) continue;
                    Material type = itemStack.getType();
                    if (type.equals(Material.BOOK) || type.equals(Material.IRON_HOE)) continue;
                    guiGeneric.addItem(itemStack);
                }

                filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                itemMeta = filler.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GRAY + "Equipped armors");
                filler.setItemMeta(itemMeta);
                guiGeneric.addItem(filler);

                ItemStack[] armorContents = inventory.getArmorContents();

                for (ItemStack itemStack : armorContents) {
                    if (itemStack == null) continue;
                    guiGeneric.addItem(itemStack);
                }

                if (guardianData.hasActiveCharacter()) {
                    filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    itemMeta = filler.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GRAY + "Equipped passive items");
                    filler.setItemMeta(itemMeta);
                    guiGeneric.addItem(filler);

                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    RPGInventory rpgInventory = activeCharacter.getRpgInventory();

                    if (!rpgInventory.getRingSlot().isEmpty()) {
                        guiGeneric.addItem(rpgInventory.getRingSlot().getItemOnSlot());
                    }

                    if (!rpgInventory.getEarringSlot().isEmpty()) {
                        guiGeneric.addItem(rpgInventory.getEarringSlot().getItemOnSlot());
                    }

                    if (!rpgInventory.getNecklaceSlot().isEmpty()) {
                        guiGeneric.addItem(rpgInventory.getNecklaceSlot().getItemOnSlot());
                    }

                    if (!rpgInventory.getGloveSlot().isEmpty()) {
                        guiGeneric.addItem(rpgInventory.getGloveSlot().getItemOnSlot());
                    }

                    if (!rpgInventory.getParrotSlot().isEmpty()) {
                        guiGeneric.addItem(rpgInventory.getParrotSlot().getItemOnSlot());
                    }

                    if (!rpgInventory.getEggSlot().isEmpty()) {
                        guiGeneric.addItem(rpgInventory.getEggSlot().getItemOnSlot());
                    }
                }

                guiGeneric.openInventory(player);
            }
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
