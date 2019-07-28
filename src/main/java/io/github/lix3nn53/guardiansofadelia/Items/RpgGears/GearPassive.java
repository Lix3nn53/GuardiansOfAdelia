package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GearPassive implements RPGGear {

    private final ItemTier tier;
    private final String itemTag;
    private final int level;
    private ItemStack itemStack;

    public GearPassive(String name, ItemTier tier, String itemTag, Material material, int customModelDataId, int passiveType, int level, RPGClass rpgClass,
                       int minStatValue, int maxStatValue, int minNumberOfStats, double bonusPercent) {
        name = tier.getTierColor() + name;
        if (itemTag != null && !itemTag.equals("")) {
            name = tier.getTierColor() + itemTag + " " + name;
        }

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);
        int finalFire = (int) (statPassive.getFire() * bonusPercent);
        int finalWater = (int) (statPassive.getWater() * bonusPercent);
        int finalEarth = (int) (statPassive.getEarth() * bonusPercent);
        int finalLightning = (int) (statPassive.getLightning() * bonusPercent);
        int finalWind = (int) (statPassive.getWind() * bonusPercent);

        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        if (!rpgClass.equals(RPGClass.NO_CLASS)) {
            lore.add(ChatColor.DARK_PURPLE + "Required Class: " + rpgClass.getClassString());
        }
        lore.add(ChatColor.YELLOW + "----------------");
        lore.add(tier.getTierString());
        lore.add(ChatColor.YELLOW + "----------------");
        if (finalFire != 0) {
            lore.add(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+" + finalFire);
        }
        if (finalWater != 0) {
            lore.add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+" + finalWater);
        }
        if (finalEarth != 0) {
            lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+" + finalEarth);
        }
        if (finalLightning != 0) {
            lore.add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+" + finalLightning);
        }
        if (finalWind != 0) {
            lore.add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Wind: " + ChatColor.GRAY + "+" + finalWind);
        }

        this.itemStack = new ItemStack(material);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putInteger("passive", passiveType, this.itemStack);

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
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(customModelDataId);
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
