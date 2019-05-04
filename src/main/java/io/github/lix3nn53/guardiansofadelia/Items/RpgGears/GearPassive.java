package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GearPassive implements RPGGear {

    private final int itemID;
    private final ItemTier tier;
    private final String itemTag;
    private final int level;
    private ItemStack itemStack;

    public GearPassive(String name, ItemTier tier, String itemTag, Material material, int customModelDataId, int passiveType, int level, RPGClass rpgClass,
                       int minStatValue, int maxStatValue, int minNumberOfStats, double bonusPercent, int itemID) {
        name = tier.getTierColor() + itemTag + " " + name;

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        lore.add(ChatColor.DARK_PURPLE + "Required Class: " + rpgClass.getClassString());
        lore.add(ChatColor.YELLOW + "----------------");
        lore.add(tier.getTierString());
        lore.add(ChatColor.YELLOW + "----------------");
        if (statPassive.getFire() != 0) {
            lore.add(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+" + (int) (statPassive.getFire() * bonusPercent));
        }
        if (statPassive.getWater() != 0) {
            lore.add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+" + (int) (statPassive.getWater() * bonusPercent));
        }
        if (statPassive.getEarth() != 0) {
            lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+" + (int) (statPassive.getEarth() * bonusPercent));
        }
        if (statPassive.getLightning() != 0) {
            lore.add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+" + (int) (statPassive.getLightning() * bonusPercent));
        }
        if (statPassive.getAir() != 0) {
            lore.add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + ChatColor.GRAY + "+" + (int) (statPassive.getAir() * bonusPercent));
        }
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "#" + itemID);

        this.itemStack = new ItemStack(material);
        this.itemStack = NBTTagUtils.putInteger("reqLevel", level, this.itemStack);
        this.itemStack = NBTTagUtils.putInteger("passive", passiveType, this.itemStack);

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(customModelDataId);
        this.itemStack.setItemMeta(itemMeta);

        this.itemID = itemID;
        this.tier = tier;
        this.itemTag = itemTag;
        this.level = level;
    }

    @Override
    public int getItemID() {
        return itemID;
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
