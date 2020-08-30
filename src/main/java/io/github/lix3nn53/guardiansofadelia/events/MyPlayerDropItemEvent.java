package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.commands.CommandDestroyItemManager;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class MyPlayerDropItemEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerDropItemEvent event) {
        Item itemDrop = event.getItemDrop();
        ItemStack itemStack = itemDrop.getItemStack();

        Player player = event.getPlayer();

        PlayerInventory playerInventory = player.getInventory();

        int heldItemSlot = playerInventory.getHeldItemSlot();
        if (heldItemSlot >= 0 && heldItemSlot <= 3) { //drop skill bar item
            event.setCancelled(true);
            return;
        } else if (!InventoryUtils.canTradeMaterial(itemStack.getType())) {
            event.setCancelled(true);
            CommandDestroyItemManager.addItemToDestroy(player, itemStack);

            player.sendMessage(ChatColor.RED + "You can't drop this item, are you sure you want to destroy it?");
            TextComponent messageMain = new TextComponent("Destroy Item");
            messageMain.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/destroyitem"));
            messageMain.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Destroy the item you tried to drop")));
            messageMain.setColor(ChatColor.RED);
            messageMain.setBold(true);
            player.spigot().sendMessage(messageMain);

        }

        ItemStack itemInMainHand = playerInventory.getItemInMainHand();

        if (itemInMainHand.getType().equals(Material.AIR)) {
            UUID uuid = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    rpgCharacterStats.clearMainHandBonuses();
                }
            }
        }

        DropProtectionManager.setItem(itemStack, player);
    }

}
