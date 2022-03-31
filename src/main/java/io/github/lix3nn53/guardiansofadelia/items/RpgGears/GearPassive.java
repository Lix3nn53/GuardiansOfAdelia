package io.github.lix3nn53.guardiansofadelia.items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetManager;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import net.md_5.bungee.api.ChatColor;
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
                       int minAttrValue, int maxAttrValue, int minNumberOfAttr, int minElemValue, int maxElemValue,
                       int minNumberOfElement, boolean withGearSet) {
        name = tier.getTierColor() + name;

        String gearSetStr = null;
        if (withGearSet) {
            GuardiansOfAdelia.getInstance().getLogger().info("GearSetManager.getRandom from PASSIVE");
            gearSetStr = GearSetManager.getRandom(tier);
        }
        if (gearSetStr != null) {
            name = tier.getTierColor() + gearSetStr + " " + name;
        }

        float bonusPercent = tier.getBonusMultiplier();

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minAttrValue, maxAttrValue, minNumberOfAttr, minElemValue, maxElemValue, minNumberOfElement);
        HashMap<AttributeType, Integer> finalValuesAttr = new HashMap<>();
        HashMap<ElementType, Integer> finalValuesElem = new HashMap<>();


        for (AttributeType attributeType : AttributeType.values()) {
            int finalValue = (int) (statPassive.getAttributeValue(attributeType) * bonusPercent);
            finalValuesAttr.put(attributeType, finalValue);
        }
        for (ElementType elementType : ElementType.values()) {
            int finalValue = (int) (statPassive.getElementValue(elementType) * bonusPercent);
            finalValuesElem.put(elementType, finalValue);
        }

        lore.add(ChatColor.RESET.toString() + ChatPalette.GOLD + "Passive Gear");
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatPalette.PURPLE + "Required Level: " + ChatPalette.GRAY + level);
        if (!statPassive.isEmpty(true, false)) {
            lore.add("");
            for (AttributeType attributeType : AttributeType.values()) {
                int finalValue = finalValuesAttr.get(attributeType);
                if (finalValue != 0) {
                    lore.add(attributeType.getCustomName() + ": " + ChatPalette.GRAY + "+" + attributeType.getIncrementLore(finalValue));
                }
            }
        }

        if (!statPassive.isEmpty(false, true)) {
            lore.add("");
            for (ElementType elementType : ElementType.values()) {
                int finalValue = finalValuesElem.get(elementType);
                if (finalValue != 0) {
                    lore.add(elementType.getFullName(Translation.DEFAULT_LANG) + ": " + ChatPalette.GRAY + "+" + finalValue);
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
            int finalValue = finalValuesAttr.get(attributeType);
            if (finalValue != 0) {
                PersistentDataContainerUtil.putInteger(attributeType.name(), finalValue, this.itemStack);
            }
        }
        for (ElementType elementType : ElementType.values()) {
            int finalValue = finalValuesElem.get(elementType);
            if (finalValue != 0) {
                PersistentDataContainerUtil.putInteger(elementType.name(), finalValue, this.itemStack);
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
