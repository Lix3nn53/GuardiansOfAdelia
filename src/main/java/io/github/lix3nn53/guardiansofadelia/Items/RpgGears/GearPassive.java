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

    private final ItemStack itemStack;

    public GearPassive(String name, ItemTier tier, int customModelData, RPGSlotType passiveType, int level,
                       int minStatValue, int maxStatValue, int minNumberOfStats, String GearSetStr) {
        name = tier.getTierColor() + name;
        if (GearSetStr != null && !GearSetStr.equals("")) {
            name = tier.getTierColor() + GearSetStr + " " + name;
        }

        double bonusPercent = tier.getBonusMultiplier();

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);
        int finalStr = (int) (statPassive.getStrength() * bonusPercent);
        int finalSpr = (int) (statPassive.getSpirit() * bonusPercent);
        int finalEnd = (int) (statPassive.getEndurance() * bonusPercent);
        int finalInt = (int) (statPassive.getIntelligence() * bonusPercent);
        int finalDex = (int) (statPassive.getDexterity() * bonusPercent);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Passive Gear");
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        if (!statPassive.isEmpty()) {
            lore.add("");
            if (finalStr != 0) {
                lore.add(ChatColor.RED + "☄ " + ChatColor.RED + "Strength: " + ChatColor.GRAY + "+" + finalStr);
            }
            if (finalSpr != 0) {
                lore.add(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Spirit: " + ChatColor.GRAY + "+" + finalSpr);
            }
            if (finalEnd != 0) {
                lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Endurance: " + ChatColor.GRAY + "+" + finalEnd);
            }
            if (finalInt != 0) {
                lore.add(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Intelligence: " + ChatColor.GRAY + "+" + finalInt);
            }
            if (finalDex != 0) {
                lore.add(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Dexterity: " + ChatColor.GRAY + "+" + finalDex);
            }
        }
        lore.add("");
        lore.add(tier.getTierString());

        this.itemStack = new ItemStack(Material.SHEARS);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("passive", passiveType.name(), this.itemStack);

        if (finalStr != 0) {
            PersistentDataContainerUtil.putInteger("strength", finalStr, this.itemStack);
        }
        if (finalSpr != 0) {
            PersistentDataContainerUtil.putInteger("spirit", finalSpr, this.itemStack);
        }
        if (finalEnd != 0) {
            PersistentDataContainerUtil.putInteger("endurance", finalEnd, this.itemStack);
        }
        if (finalInt != 0) {
            PersistentDataContainerUtil.putInteger("intelligence", finalInt, this.itemStack);
        }
        if (finalDex != 0) {
            PersistentDataContainerUtil.putInteger("dexterity", finalDex, this.itemStack);
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(customModelData);

        this.itemStack.setItemMeta(itemMeta);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

}
