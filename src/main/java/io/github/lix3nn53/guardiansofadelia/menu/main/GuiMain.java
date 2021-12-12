package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.guild.GuiGuild;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacter;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
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
        super(54, CustomCharacter.MENU_54.toString() + CustomCharacter.LOGO, 0);

        ItemStack compass = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(27);
        String name = ChatPalette.BLUE + Translation.t(guardianData, "menu.compass.name");
        itemMeta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.compass.l1"));
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.compass.l2"));
        itemMeta.setLore(lore);
        compass.setItemMeta(itemMeta);
        this.setItem(20, compass);
        ItemStack empty = OtherItems.getEmpty(name, lore);
        this.setItem(21, empty);
        this.setItem(11, empty);
        this.setItem(12, empty);

        ItemStack character = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(2);
        name = ChatPalette.GREEN_DARK + Translation.t(guardianData, "character.name");
        itemMeta.setDisplayName(name);
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.l1"));
        itemMeta.setLore(lore);
        character.setItemMeta(itemMeta);
        this.setItem(18, character);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(19, empty);
        this.setItem(9, empty);
        this.setItem(10, empty);

        ItemStack guild = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(9);
        name = ChatPalette.PURPLE + Translation.t(guardianData, "menu.guild.name");
        itemMeta.setDisplayName(name);
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.guild.l1"));
        itemMeta.setLore(lore);
        guild.setItemMeta(itemMeta);
        this.setItem(23, guild);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(24, empty);
        this.setItem(14, empty);
        this.setItem(15, empty);

        /*ItemStack bazaar = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(3);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.bazaar.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.bazaar.l1"));
        itemMeta.setLore(lore);
        bazaar.setItemMeta(itemMeta);
        this.setItem(31, bazaar);*/

        ItemStack settings = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(25);
        name = ChatPalette.GOLD + Translation.t(guardianData, "menu.minigames.name");
        itemMeta.setDisplayName(name);
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.minigames.l1"));
        itemMeta.setLore(lore);
        settings.setItemMeta(itemMeta);
        this.setItem(25, settings);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(26, empty);
        this.setItem(16, empty);
        this.setItem(17, empty);

        ItemStack teleport = new ItemStack(Material.WOODEN_SHOVEL);
        itemMeta.setCustomModelData(6);
        name = ChatPalette.GOLD + Translation.t(guardianData, "menu.teleportation.name");
        itemMeta.setDisplayName(name);
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.teleportation.l1"));
        itemMeta.setLore(lore);
        teleport.setItemMeta(itemMeta);
        this.setItem(52, teleport);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(53, empty);
        this.setItem(43, empty);
        this.setItem(44, empty);

        ItemStack activeBoosts = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(28);
        name = ChatPalette.GOLD + Translation.t(guardianData, "menu.boost.name");
        itemMeta.setDisplayName(name);
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.boost.l1"));
        itemMeta.setLore(lore);
        activeBoosts.setItemMeta(itemMeta);
        this.setItem(45, activeBoosts);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(46, empty);
        this.setItem(36, empty);
        this.setItem(37, empty);

        ItemStack donation = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(10);
        name = ChatPalette.PURPLE_LIGHT + Translation.t(guardianData, "menu.store.name");
        itemMeta.setDisplayName(name);
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
        this.setItem(47, donation);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(48, empty);
        this.setItem(38, empty);
        this.setItem(39, empty);

        ItemStack daily = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(38);
        name = ChatPalette.GOLD + Translation.t(guardianData, "menu.daily.name");
        itemMeta.setDisplayName(name);
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.daily.l1"));
        itemMeta.setLore(lore);
        daily.setItemMeta(itemMeta);
        this.setItem(50, daily);
        empty = OtherItems.getEmpty(name, lore);
        this.setItem(51, empty);
        this.setItem(41, empty);
        this.setItem(42, empty);
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
        if (slot == 18 || slot == 19 || slot == 9 || slot == 10) {
            GuiCharacter gui = new GuiCharacter(guardianData);
            gui.openInventory(player);
        } else if (slot == 20 || slot == 21 || slot == 11 || slot == 12) {
            GuiCompass gui = new GuiCompass();
            gui.openInventory(player);
        } else if (slot == 23 || slot == 24 || slot == 14 || slot == 15) {
            if (GuildManager.inGuild(player)) {
                GuiGuild gui = new GuiGuild();
                gui.openInventory(player);
            }
        } else if (slot == 25 || slot == 26 || slot == 16 || slot == 17) {
            GuiMinigames gui = new GuiMinigames();
            gui.openInventory(player);
        } else if (slot == 52 || slot == 53 || slot == 43 || slot == 44) {
            GuiTeleportation gui = new GuiTeleportation(guardianData);
            gui.openInventory(player);
        } else if (slot == 45 || slot == 46 || slot == 36 || slot == 37) {
            GuiServerBoost gui = new GuiServerBoost();
            gui.openInventory(player);
        } else if (slot == 47 || slot == 48 || slot == 38 || slot == 39) {
            player.closeInventory();
            TextComponent message = new TextComponent(" Donation ♥ ! (Click Me)");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://guardiansofadelia.com/store"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatPalette.PURPLE_LIGHT + "Click to donate ♥")));
            message.setColor(ChatPalette.PURPLE_LIGHT.toChatColor());
            message.setBold(true);
            player.spigot().sendMessage(message);
        } else if (slot == 50 || slot == 51 || slot == 41 || slot == 42) {
            GuiDailyRewardClaim gui = new GuiDailyRewardClaim(player);
            gui.openInventory(player);
        }
    }
}
