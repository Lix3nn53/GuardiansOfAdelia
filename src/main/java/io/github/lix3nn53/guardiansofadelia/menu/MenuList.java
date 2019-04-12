package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuList {

    public static GuiGeneric mainMenu() {
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.GREEN + "Menu", 0);

        ItemStack guide = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = guide.getItemMeta();
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(13);
        }
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.GREEN + "Guides");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "All about Guardians of Adelia!");

        }});
        guide.setItemMeta(itemMeta);
        guiGeneric.setItem(10, guide);

        ItemStack compass = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(12);
        }
        itemMeta.setDisplayName(ChatColor.AQUA + "Compass");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Choose where you want to go and");
            add(ChatColor.GRAY + "let the compass lead you!");
        }});
        compass.setItemMeta(itemMeta);
        guiGeneric.setItem(12, compass);

        ItemStack map = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(22);
        }
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Maps");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Explore the Adelia!");
        }});
        map.setItemMeta(itemMeta);
        guiGeneric.setItem(14, map);

        ItemStack news = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(8);
        }
        itemMeta.setDisplayName(ChatColor.AQUA + "Announcements and News");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Read Announcements and News about Guardians of Adelia");
        }});
        news.setItemMeta(itemMeta);
        guiGeneric.setItem(16, news);

        ItemStack character = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) character.getItemMeta();
        skullMeta.setDisplayName(ChatColor.GOLD + "Character");
        skullMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your character");
        }});
        character.setItemMeta(skullMeta);
        guiGeneric.setItem(28, character);

        ItemStack guild = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(14);
        }
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Guild");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "United we stand divided we fall!");
        }});
        guild.setItemMeta(itemMeta);
        guiGeneric.setItem(30, guild);

        ItemStack minigames = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(25);
        }
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Minigames");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Have fun in different game modes!");
        }});
        minigames.setItemMeta(itemMeta);
        guiGeneric.setItem(32, minigames);

        ItemStack bazaar = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(23);
        }
        itemMeta.setDisplayName(ChatColor.YELLOW + "Bazaar");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your bazaar!");
        }});
        bazaar.setItemMeta(itemMeta);
        guiGeneric.setItem(34, bazaar);

        ItemStack donation = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(24);
        }
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Donation ♥");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Donations are not required but helps me so");
            add(ChatColor.GRAY + "I can keep working on this project. Thanks <3");
        }});
        donation.setItemMeta(itemMeta);
        guiGeneric.setItem(49, donation);

        return guiGeneric;
    }

    public static GuiGeneric character() {
        GuiGeneric guiGeneric = new GuiGeneric(27, "Character", 0);

        ItemStack skills = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = skills.getItemMeta();
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(27);
        }
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Skills");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Learn and master skills!");

        }});
        skills.setItemMeta(itemMeta);
        guiGeneric.setItem(9, skills);

        ItemStack elements = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(10);
        }
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Elements");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Choose where you want to go and");
            add(ChatColor.GRAY + "let the compass lead you!");
        }});
        elements.setItemMeta(itemMeta);
        guiGeneric.setItem(11, elements);

        ItemStack job = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(26);
        }
        itemMeta.setDisplayName(ChatColor.YELLOW + "Job");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your character's job!");
        }});
        job.setItemMeta(itemMeta);
        guiGeneric.setItem(13, job);

        ItemStack chat = new ItemStack(Material.STONE_PICKAXE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(31);
        }
        itemMeta.setDisplayName(ChatColor.AQUA + "Chat Tag");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Select your chat tag!");
        }});
        chat.setItemMeta(itemMeta);
        guiGeneric.setItem(15, chat);

        return guiGeneric;
    }

    public static GuiGeneric skill() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.LIGHT_PURPLE + "Skills", 0);


        return guiGeneric;
    }

    public static GuiGeneric element() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.AQUA + "Elements", 0);


        return guiGeneric;
    }

    public static GuiGeneric job() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.YELLOW + "Job", 0);


        return guiGeneric;
    }

    public static GuiGeneric chatTag() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.AQUA + "Chat Tag", 0);


        return guiGeneric;
    }

    public static GuiGeneric guide() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GREEN + "Guides", 0);


        return guiGeneric;
    }

    public static GuiGeneric compass() {
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.AQUA + "Compass", 0);

        ItemStack city = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = city.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "Roumen #1");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to select your compass target!");
            add("");
            add(ChatColor.GRAY + "If you dont have a compass this will give you one.");
        }});
        city.setItemMeta(itemMeta);
        guiGeneric.setItem(0, city);

        itemMeta.setDisplayName(ChatColor.AQUA + "Port Veloa #2");
        city.setItemMeta(itemMeta);
        guiGeneric.setItem(2, city);

        itemMeta.setDisplayName(ChatColor.AQUA + "Elderine #3");
        city.setItemMeta(itemMeta);
        guiGeneric.setItem(4, city);

        itemMeta.setDisplayName(ChatColor.AQUA + "Uruga #4");
        city.setItemMeta(itemMeta);
        guiGeneric.setItem(6, city);

        itemMeta.setDisplayName(ChatColor.AQUA + "Arberstol Ruins #5");
        city.setItemMeta(itemMeta);
        guiGeneric.setItem(8, city);

        ItemStack npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GOLD + "King of Roumen #19");
        npc.setItemMeta(itemMeta);
        guiGeneric.setItem(18, npc);

        itemMeta.setDisplayName(ChatColor.GREEN + "Hogpen #20");
        npc.setItemMeta(itemMeta);
        guiGeneric.setItem(19, npc);

        return guiGeneric;
    }

    public static GuiGeneric guild() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.DARK_PURPLE + "Guild", 0);


        return guiGeneric;
    }

    public static GuiGeneric bazaar(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "Bazaar", 0);

        ItemStack info = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta itemMeta = info.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Your Bazaar");

        boolean hasBazaar = false;
        UUID uniqueId = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uniqueId)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                if (bazaar.isOpen()) {
                    hasBazaar = true;
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.GREEN + "OPEN");
                    lore.add("");
                    lore.add(ChatColor.GOLD + "Money earned: " + EconomyUtils.priceToString(bazaar.getMoneyEarned()));
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "Current Customers");
                    for (Player customer : bazaar.getCustomers()) {
                        lore.add(customer.getDisplayName());
                    }
                    itemMeta.setLore(lore);
                }
            }
        }
        if (!hasBazaar) {
            itemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "CLOSED");
            }});
        }
        info.setItemMeta(itemMeta);
        guiGeneric.setItem(12, info);

        ItemStack open = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Open/Edit your bazaar");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Set up a bazaar in your current location");
            add("");
            add(ChatColor.GRAY + "Edit your bazaar if you already have one");
        }});
        open.setItemMeta(itemMeta);
        guiGeneric.setItem(14, open);

        return guiGeneric;
    }

    public static GuiGeneric minigames() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "MiniGames", 0);

        ItemStack pvp = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pvp.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Last One Standing");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to join this minigame");
        }});
        pvp.setItemMeta(itemMeta);
        guiGeneric.setItem(0, pvp);

        itemMeta.setDisplayName(ChatColor.RED + "Most kills");
        pvp.setItemMeta(itemMeta);
        guiGeneric.setItem(2, pvp);

        return guiGeneric;
    }
}
