package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrizeChest {

    private final String name;

    public PrizeChest(String name) {
        this.name = ChatColor.GOLD + name + " Prize Chest";
    }

    /**
     * @param dungeonTheme
     * @param type         0 for Weapon, 1 for Jewelry, 2 for Armor
     */
    public PrizeChest(DungeonTheme dungeonTheme, int type) {
        String chestName = ChatColor.GOLD + dungeonTheme.getName() + " Prize Chest";

        if (type == 0) {
            chestName = chestName + " (Weapon)";
        } else if (type == 1) {
            chestName = chestName + " (Jewelry)";
        } else if (type == 2) {
            chestName = chestName + " (Armor)";
        }

        this.name = chestName;
    }

    public String getName() {
        return name;
    }

    public void play(Player player, List<ItemStack> prizePool) {
        GuiGeneric chestGui = new GuiGeneric(27, "Opening Prize-Chest " + getName(), 0);

        ItemStack fillGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fillGlass.getItemMeta();
        fillMeta.setDisplayName(ChatColor.DARK_GRAY + "-");
        fillGlass.setItemMeta(fillMeta);

        ItemStack prizeGlass = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta prizeMeta = prizeGlass.getItemMeta();
        prizeMeta.setDisplayName(ChatColor.GOLD + "Prize Slot");
        prizeGlass.setItemMeta(prizeMeta);

        for (int i = 0; i < 4; i++) {
            chestGui.setItem(i, fillGlass);
        }
        for (int i = 5; i < 9; i++) {
            chestGui.setItem(i, fillGlass);
        }
        for (int i = 18; i < 22; i++) {
            chestGui.setItem(i, fillGlass);
        }
        for (int i = 23; i < 27; i++) {
            chestGui.setItem(i, fillGlass);
        }

        chestGui.setItem(4, prizeGlass);
        chestGui.setItem(22, prizeGlass);

        Collections.shuffle(prizePool);

        CustomSound sound = GoaSound.WHEEL_OF_FORTUNE.getCustomSound();
        sound.play(player.getLocation());
        chestGui.openInventory(player);

        int size = prizePool.size();
        int slideCountRandom = new Random().nextInt(size / 2); //random start index
        int slideLimit = slideCountRandom + 12; //slide 12 times

        new BukkitRunnable() {

            int slideCount = slideCountRandom; //random start index

            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    int index = slideCount + i;

                    if (index >= size) index = index % size;

                    ItemStack item = prizePool.get(index);
                    chestGui.setItem(i + 9, item);
                }
                if (slideCount > slideLimit) {
                    cancel();

                    CustomSound sound = GoaSound.SUCCESS.getCustomSound();
                    sound.play(player.getLocation());

                    ItemStack[] content = chestGui.getContents();
                    GuiGeneric prizeGui = new GuiGeneric(27, ChatColor.YELLOW + "Result Prize-Chest " + getName(), 0);
                    prizeGui.setContents(content);
                    prizeGui.openInventory(player);

                    ItemStack item = prizeGui.getItem(13);
                    InventoryUtils.giveItemToPlayer(player, item);
                }
                slideCount++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 10L, 5L);
    }

    public ItemStack getChest() {
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(10000035);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Right click while holding to open!");

        itemMeta.setDisplayName(getName());
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
