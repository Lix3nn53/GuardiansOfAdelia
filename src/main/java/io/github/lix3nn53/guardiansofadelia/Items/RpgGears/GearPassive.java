package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GearPassive implements RPGGear {

    private final String itemTag;
    private final int level;
    private final ItemStack itemStack;

    public GearPassive(String name, ItemTier tier, String itemTag, int customModelData, RPGSlotType passiveType, int level,
                       int minStatValue, int maxStatValue, int minNumberOfStats) {
        name = tier.getTierColor() + name;
        if (itemTag != null && !itemTag.equals("")) {
            name = tier.getTierColor() + itemTag + " " + name;
        }

        double bonusPercent = tier.getBonusMultiplier();

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);
        int finalFire = (int) (statPassive.getFire() * bonusPercent);
        int finalWater = (int) (statPassive.getWater() * bonusPercent);
        int finalEarth = (int) (statPassive.getEarth() * bonusPercent);
        int finalLightning = (int) (statPassive.getLightning() * bonusPercent);
        int finalWind = (int) (statPassive.getWind() * bonusPercent);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Passive Gear");
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        if (!statPassive.isEmpty()) {
            lore.add("");
            if (finalFire != 0) {
                lore.add(ChatColor.RED + "☄ " + ChatColor.RED + "Fire: " + ChatColor.GRAY + "+" + finalFire);
            }
            if (finalWater != 0) {
                lore.add(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Water: " + ChatColor.GRAY + "+" + finalWater);
            }
            if (finalEarth != 0) {
                lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Earth: " + ChatColor.GRAY + "+" + finalEarth);
            }
            if (finalLightning != 0) {
                lore.add(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Lightning: " + ChatColor.GRAY + "+" + finalLightning);
            }
            if (finalWind != 0) {
                lore.add(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Wind: " + ChatColor.GRAY + "+" + finalWind);
            }
        }
        lore.add("");
        lore.add(tier.getTierString());

        this.itemStack = new ItemStack(Material.SHEARS);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("passive", passiveType.name(), this.itemStack);

        if (finalFire != 0) {
            PersistentDataContainerUtil.putInteger("fire", finalFire, this.itemStack);
        }
        if (finalWater != 0) {
            PersistentDataContainerUtil.putInteger("water", finalWater, this.itemStack);
        }
        if (finalEarth != 0) {
            PersistentDataContainerUtil.putInteger("earth", finalEarth, this.itemStack);
        }
        if (finalLightning != 0) {
            PersistentDataContainerUtil.putInteger("lightning", finalLightning, this.itemStack);
        }
        if (finalWind != 0) {
            PersistentDataContainerUtil.putInteger("wind", finalWind, this.itemStack);
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(customModelData);
        this.itemStack.setItemMeta(itemMeta);

        this.itemTag = itemTag;
        this.level = level;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
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
