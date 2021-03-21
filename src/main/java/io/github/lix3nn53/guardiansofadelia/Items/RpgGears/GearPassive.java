package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
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
        HashMap<AttributeType, Integer> finalValues = new HashMap<>();
        for (AttributeType attributeType : AttributeType.values()) {
            int finalValue = (int) (statPassive.getAttributeValue(attributeType) * bonusPercent);
            finalValues.put(attributeType, finalValue);
        }

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Passive Gear");
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        if (!statPassive.isEmpty()) {
            lore.add("");
            for (AttributeType attributeType : AttributeType.values()) {
                int finalValue = finalValues.get(attributeType);
                if (finalValue != 0) {
                    lore.add(attributeType.getCustomName() + ": " + ChatColor.GRAY + "+" + finalValue);
                }
            }
        }
        lore.add("");
        lore.add(tier.getTierString());

        this.itemStack = new ItemStack(Material.SHEARS);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("passive", passiveType.name(), this.itemStack);

        for (AttributeType attributeType : AttributeType.values()) {
            int finalValue = finalValues.get(attributeType);
            if (finalValue != 0) {
                PersistentDataContainerUtil.putInteger(attributeType.name(), finalValue, this.itemStack);
            }
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
