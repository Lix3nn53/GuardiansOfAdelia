package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GearArmor implements RPGGear {

    private final ItemTier tier;
    private final String itemTag;
    private final int level;
    private ItemStack itemStack;

    public GearArmor(String name, ItemTier tier, String itemTag, Material material, int level, RPGClass rpgClass, int health,
                     int defense, int magicDefense, int minStatValue, int maxStatValue, int minNumberOfStats) {
        name = tier.getTierColor() + name;
        if (itemTag != null && !itemTag.equals("")) {
            name = tier.getTierColor() + itemTag + " " + name;
        }

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        if (!rpgClass.equals(RPGClass.NO_CLASS)) {
            lore.add(ChatColor.DARK_PURPLE + "Required Class: " + rpgClass.getClassString());
        }
        lore.add(ChatColor.YELLOW + "----------------");
        lore.add(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+" + health);
        lore.add(ChatColor.AQUA + "■ Defense: " + ChatColor.GRAY + "+" + defense);
        lore.add(ChatColor.BLUE + "✦ Magic Defense: " + ChatColor.GRAY + "+" + magicDefense);
        lore.add(tier.getTierString());
        lore.add(ChatColor.YELLOW + "----------------");
        if (statPassive.getFire() != 0) {
            lore.add(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+" + statPassive.getFire());
        }
        if (statPassive.getWater() != 0) {
            lore.add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+" + statPassive.getWater());
        }
        if (statPassive.getEarth() != 0) {
            lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+" + statPassive.getEarth());
        }
        if (statPassive.getLightning() != 0) {
            lore.add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+" + statPassive.getLightning());
        }
        if (statPassive.getWind() != 0) {
            lore.add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Wind: " + ChatColor.GRAY + "+" + statPassive.getWind());
        }

        this.itemStack = new ItemStack(material);
        this.itemStack = RPGItemUtils.resetArmor(this.itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("reqClass", rpgClass.toString(), this.itemStack);
        PersistentDataContainerUtil.putInteger("health", health, this.itemStack);
        PersistentDataContainerUtil.putInteger("defense", defense, this.itemStack);
        PersistentDataContainerUtil.putInteger("magicDefense", magicDefense, this.itemStack);

        if (statPassive.getFire() != 0) {
            PersistentDataContainerUtil.putInteger("fire", statPassive.getFire(), this.itemStack);
        }
        if (statPassive.getWater() != 0) {
            PersistentDataContainerUtil.putInteger("water", statPassive.getWater(), this.itemStack);
        }
        if (statPassive.getEarth() != 0) {
            PersistentDataContainerUtil.putInteger("earth", statPassive.getEarth(), this.itemStack);
        }
        if (statPassive.getLightning() != 0) {
            PersistentDataContainerUtil.putInteger("lightning", statPassive.getLightning(), this.itemStack);
        }
        if (statPassive.getWind() != 0) {
            PersistentDataContainerUtil.putInteger("wind", statPassive.getWind(), this.itemStack);
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        this.itemStack.setItemMeta(itemMeta);

        this.tier = tier;
        this.itemTag = itemTag;
        this.level = level;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public ItemTier getTier() {
        return tier;
    }

    @Override
    public String getItemTag() {
        return itemTag;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
