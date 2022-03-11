package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.menu.guild.GuiGuild;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
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
        super(54, CustomCharacterGui.MENU_54.toString() + CustomCharacterGui.LOGO, 0);

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

        ItemStack character = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(2);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + Translation.t(guardianData, "character.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.l1"));
        itemMeta.setLore(lore);
        character.setItemMeta(itemMeta);

        ItemStack guild = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(9);
        itemMeta.setDisplayName(ChatPalette.PURPLE + Translation.t(guardianData, "menu.guild.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.guild.l1"));
        itemMeta.setLore(lore);
        guild.setItemMeta(itemMeta);

        /*ItemStack bazaar = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(3);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.bazaar.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.bazaar.l1"));
        itemMeta.setLore(lore);
        bazaar.setItemMeta(itemMeta);
        this.setItem(31, bazaar);*/

        ItemStack minigames = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(25);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.minigames.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.minigames.l1"));
        itemMeta.setLore(lore);
        minigames.setItemMeta(itemMeta);

        ItemStack teleport = new ItemStack(Material.WOODEN_SHOVEL);
        itemMeta.setCustomModelData(6);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.teleportation.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.teleportation.l1"));
        itemMeta.setLore(lore);
        teleport.setItemMeta(itemMeta);

        ItemStack activeBoosts = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(28);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.boost.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.boost.l1"));
        itemMeta.setLore(lore);
        activeBoosts.setItemMeta(itemMeta);

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

        ItemStack daily = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(38);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.daily.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.daily.l1"));
        itemMeta.setLore(lore);
        daily.setItemMeta(itemMeta);

        GuiHelper.form54Big(this, new ItemStack[]{character, compass, guild, minigames, activeBoosts, donation, daily, teleport}, null);

        ItemStack settings = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(39);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "menu.settings.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.settings.l1"));
        itemMeta.setLore(lore);
        settings.setItemMeta(itemMeta);

        this.setItem(22, settings);
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
        if (GuiHelper.get54BigButtonIndexes(0).contains(slot)) {
            GuiCharacter gui = new GuiCharacter(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(1).contains(slot)) {
            GuiCompass gui = new GuiCompass(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(2).contains(slot)) {
            if (GuildManager.inGuild(player)) {
                GuiGuild gui = new GuiGuild();
                gui.openInventory(player);
            }
        } else if (GuiHelper.get54BigButtonIndexes(3).contains(slot)) {
            GuiMinigames gui = new GuiMinigames(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(4).contains(slot)) {
            GuiServerBoost gui = new GuiServerBoost();
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(5).contains(slot)) {
            player.closeInventory();
            TextComponent message = new TextComponent(" Donation ♥ ! (Click Me)");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://guardiansofadelia.com/store"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatPalette.PURPLE_LIGHT + "Click to donate ♥")));
            message.setColor(ChatPalette.PURPLE_LIGHT.toChatColor());
            message.setBold(true);
            player.spigot().sendMessage(message);
        } else if (GuiHelper.get54BigButtonIndexes(6).contains(slot)) {
            GuiDailyRewardClaim gui = new GuiDailyRewardClaim(player);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(7).contains(slot)) {
            GuiTeleportation gui = new GuiTeleportation(guardianData, player.getLevel());
            gui.openInventory(player);
        } else if (slot == 22) {
            GuiSettings gui = new GuiSettings(guardianData);
            gui.openInventory(player);
        }
    }
}
