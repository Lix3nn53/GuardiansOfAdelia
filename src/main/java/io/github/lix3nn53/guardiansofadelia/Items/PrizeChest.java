package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
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

public class PrizeChest {

    String name;
    List<ItemStack> prizePool = new ArrayList<>();

    public PrizeChest(String name) {
        this.name = name;
    }

    public void addItem(ItemStack itemStack, int amountToAdd) {
        for (int i = 0; i < amountToAdd; i++) {
            prizePool.add(itemStack);
        }
    }

    public void addItemList(List<ItemStack> itemStackList) {
        prizePool.addAll(itemStackList);
    }

    public void play(Player player) {
        GuiGeneric chestGui = new GuiGeneric(27, "Opening Prize-Chest " + name, 0);

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

        new BukkitRunnable() {

            int slideCount = 0;
            int size = prizePool.size();
            int slideLimit = Math.min(size / 2, 33);

            @Override
            public void run() {
                if (slideCount >= size) {
                    slideCount = 0;
                }
                for (int i = 0; i < 9; i++) {
                    ItemStack item = prizePool.get(slideCount + i);
                    chestGui.setItem(i + 9, item);
                }
                if (slideCount > slideLimit) {
                    cancel();

                    CustomSound sound = GoaSound.SUCCESS.getCustomSound();
                    sound.play(player.getLocation());

                    ItemStack[] content = chestGui.getContents();
                    GuiGeneric prizeGui = new GuiGeneric(27, ChatColor.YELLOW + "Result Prize-Chest " + name, 0);
                    prizeGui.setContents(content);
                    prizeGui.openInventory(player);
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
        lore.add(ChatColor.GOLD + "Number of items: " + ChatColor.GRAY + prizePool.size());
        lore.add("");

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
