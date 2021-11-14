package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.menu.bazaar.GuiBazaar;
import io.github.lix3nn53.guardiansofadelia.menu.guild.GuiGuild;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiMain extends GuiGeneric {

    public GuiMain(GuardianData guardianData) {
        super(54, ChatPalette.GRAY_DARK + "Guardians of Adelia", 0);

        ItemStack compass = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(27);
        itemMeta.setDisplayName(ChatPalette.BLUE + Translation.t(guardianData, "menu.compass.name"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.compass.l1"));
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.compass.l2"));
        itemMeta.setLore(lore);
        compass.setItemMeta(itemMeta);
        this.setItem(12, compass);

        ItemStack map = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(24);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + Translation.t(guardianData, "menu.map.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.map.l1"));
        itemMeta.setLore(lore);
        map.setItemMeta(itemMeta);
        this.setItem(14, map);

        ItemStack character = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(2);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + Translation.t(guardianData, "character.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.l1"));
        itemMeta.setLore(lore);
        character.setItemMeta(itemMeta);
        this.setItem(10, character);

        ItemStack guild = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(9);
        itemMeta.setDisplayName(ChatPalette.PURPLE + Translation.t(guardianData, "menu.guild.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.guild.l1"));
        itemMeta.setLore(lore);
        guild.setItemMeta(itemMeta);
        this.setItem(16, guild);

        ItemStack bazaar = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(3);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.bazaar.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.bazaar.l1"));
        itemMeta.setLore(lore);
        bazaar.setItemMeta(itemMeta);
        this.setItem(29, bazaar);

        ItemStack minigames = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(25);
        itemMeta.setDisplayName(ChatPalette.PURPLE + Translation.t(guardianData, "menu.minigames.name"));
        lore = new ArrayList<>();
        lore.add("");
        itemMeta.setLore(lore);
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.minigames.l1"));
        minigames.setItemMeta(itemMeta);
        this.setItem(31, minigames);

        ItemStack teleport = new ItemStack(Material.WOODEN_SHOVEL);
        itemMeta.setCustomModelData(6);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + Translation.t(guardianData, "menu.teleportation.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.teleportation.l1"));
        itemMeta.setLore(lore);
        teleport.setItemMeta(itemMeta);
        this.setItem(33, teleport);

        ItemStack activeBoosts = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(28);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.boost.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.boost.l1"));
        itemMeta.setLore(lore);
        activeBoosts.setItemMeta(itemMeta);
        this.setItem(47, activeBoosts);

        ItemStack donation = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + Translation.t(guardianData, "menu.store.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.store.l1"));
        lore.add(ChatPalette.GRAY + "");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.store.l2"));
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.store.l3"));
        lore.add(ChatPalette.GRAY + "");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.store.l4"));
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.store.l5"));
        itemMeta.setLore(lore);
        donation.setItemMeta(itemMeta);
        this.setItem(49, donation);

        ItemStack daily = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(38);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.daily.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.daily.l1"));
        itemMeta.setLore(lore);
        daily.setItemMeta(itemMeta);
        this.setItem(51, daily);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        RPGCharacter rpgCharacter;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            } else {
                return;
            }
        } else {
            return;
        }

        int slot = event.getSlot();
        if (slot == 10) {
            GuiCharacter gui = new GuiCharacter(guardianData);
            gui.openInventory(player);
        } else if (slot == 12) {
            GuiCompass gui = new GuiCompass();
            gui.openInventory(player);
        } else if (slot == 14) {
            player.closeInventory();
            TextComponent message = new TextComponent(" Maps ! (Click Me)");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t5"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatPalette.BLUE_LIGHT + "Click to see Maps from our website")));
            message.setColor(ChatPalette.GREEN_DARK.toChatColor());
            message.setBold(true);
            player.spigot().sendMessage(message);
        } else if (slot == 16) {
            if (GuildManager.inGuild(player)) {
                GuiGuild gui = new GuiGuild();
                gui.openInventory(player);
            }
        } else if (slot == 29) {
            GuiBazaar gui = new GuiBazaar(guardianData);
            gui.openInventory(player);
        } else if (slot == 31) {
            GuiMinigames gui = new GuiMinigames();
            gui.openInventory(player);
        } else if (slot == 33) {
            GuiTeleportation gui = new GuiTeleportation(guardianData);
            gui.openInventory(player);
        } else if (slot == 47) {
            GuiServerBoost gui = new GuiServerBoost();
            gui.openInventory(player);
        } else if (slot == 49) {
            player.closeInventory();
            TextComponent message = new TextComponent(" Donation ♥ ! (Click Me)");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://guardiansofadelia.com/store"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatPalette.PURPLE_LIGHT + "Click to donate ♥")));
            message.setColor(ChatPalette.PURPLE_LIGHT.toChatColor());
            message.setBold(true);
            player.spigot().sendMessage(message);
        } else if (slot == 51) {
            GuiDailyRewardClaim gui = new GuiDailyRewardClaim(player);
            gui.openInventory(player);
        }
    }
}
