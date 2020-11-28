package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Shield implements RPGGear {

    private final ShieldGearType gearType;
    private final String itemTag;
    private final int level;
    private final ItemStack itemStack;

    public Shield(String name, ItemTier tier, String itemTag, Material material, int customModelDataId, int level, ShieldGearType gearType, int health,
                  int defense, int magicDefense, int minStatValue, int maxStatValue, int minNumberOfStats) {
        name = tier.getTierColor() + name;
        if (itemTag != null && !itemTag.equals("")) {
            name = tier.getTierColor() + itemTag + " " + name;
        }

        double bonusPercent = tier.getBonusMultiplier();

        health = (int) ((health * bonusPercent) + 0.5);

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Gear Type: " + gearType.getDisplayName());
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        lore.add("");
        lore.add(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+" + health);
        lore.add(ChatColor.AQUA + "■ Defense: " + ChatColor.GRAY + "+" + defense);
        lore.add(ChatColor.BLUE + "✦ Magic Defense: " + ChatColor.GRAY + "+" + magicDefense);
        if (!statPassive.isEmpty()) {
            lore.add("");
            if (statPassive.getFire() != 0) {
                lore.add(ChatColor.RED + "☄ " + ChatColor.RED + "Fire: " + ChatColor.GRAY + "+" + statPassive.getFire());
            }
            if (statPassive.getWater() != 0) {
                lore.add(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Water: " + ChatColor.GRAY + "+" + statPassive.getWater());
            }
            if (statPassive.getEarth() != 0) {
                lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Earth: " + ChatColor.GRAY + "+" + statPassive.getEarth());
            }
            if (statPassive.getLightning() != 0) {
                lore.add(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Lightning: " + ChatColor.GRAY + "+" + statPassive.getLightning());
            }
            if (statPassive.getWind() != 0) {
                lore.add(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Wind: " + ChatColor.GRAY + "+" + statPassive.getWind());
            }
        }
        lore.add("");
        lore.add(tier.getTierString());

        this.itemStack = new ItemStack(material);
        RPGItemUtils.resetArmor(this.itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("gearType", gearType.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
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
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(customModelDataId);
        this.itemStack.setItemMeta(itemMeta);

        this.itemTag = itemTag;
        this.level = level;
        this.gearType = gearType;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    public ShieldGearType getGearType() {
        return gearType;
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
