package io.github.lix3nn53.guardiansofadelia.items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSet;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetManager;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GearArmor implements RPGGear {

    private final ItemStack itemStack;

    public GearArmor(String name, ItemTier tier, Material material, int level, ArmorGearType gearType, int health,
                     int defense, int minElemValue, int maxElemValue, int minNumberOfElements, boolean withGearSet) {
        name = tier.getTierColor() + name;

        String gearSetStr = null;
        if (withGearSet) {
            GearSet random = GearSetManager.getRandom(tier);
            gearSetStr = random != null ? random.getName() : null;
        }

        float bonusPercent = tier.getBonusMultiplier();

        health = (int) ((health * bonusPercent) + 0.5);

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(0, 0, 0, minElemValue, maxElemValue, minNumberOfElements);

        lore.add(ChatColor.RESET.toString() + ChatPalette.GOLD + gearType.getDisplayName());
        if (gearSetStr != null) {
            lore.add(ChatPalette.RED + gearSetStr);
        }
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatPalette.PURPLE + "Required Level: " + ChatPalette.GRAY + level);
        lore.add("");
        lore.add(GearArmor.getLoreHealthPrefix() + health);
        lore.add(ChatPalette.BLUE_LIGHT + "■ Element Defense: " + ChatPalette.GRAY + "+" + defense);
        if (!statPassive.isEmpty(false, true)) {
            lore.add("");
            /*for (AttributeType attributeType : AttributeType.values()) {
                if (statPassive.getAttributeValue(attributeType) != 0) {
                    lore.add(attributeType.getCustomName() + ": " + ChatPalette.GRAY + "+" + statPassive.getAttributeValue(attributeType));
                }
            }*/
            for (ElementType elementType : ElementType.values()) {
                if (statPassive.getElementValue(elementType) != 0) {
                    lore.add(elementType.getFullName(Translation.DEFAULT_LANG) + ": " + ChatPalette.GRAY + "+" + statPassive.getElementValue(elementType));
                }
            }
        }
        lore.add("");
        lore.add(tier.getTierString());
        lore.add("");
        GearSetEffect setEffect = gearType.getSetEffect();
        lore.add(ChatPalette.GRAY + "-- " + ChatPalette.GOLD + gearType.getDisplayName() + ChatPalette.GRAY + " [4 pieces] --");
        lore.add("      " + setEffect.toString());
        if (gearSetStr != null) {
            for (int i = 1; i < 6; i++) {
                GearSet gearSet = new GearSet(gearSetStr, i);
                if (GearSetManager.hasEffect(gearSet)) {
                    lore.add(ChatPalette.GRAY + "-- " + ChatPalette.RED + gearSetStr + ChatPalette.GRAY + " [" + i + " pieces] --");
                    List<GearSetEffect> effects = GearSetManager.getEffectsWithoutLower(gearSet);
                    for (GearSetEffect gearSetEffect : effects) {
                        lore.add("      " + gearSetEffect.toString());
                    }
                }
            }
        }

        this.itemStack = new ItemStack(material);
        RPGItemUtils.resetArmor(this.itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putInteger("health", health, this.itemStack);
        PersistentDataContainerUtil.putInteger("defense", defense, this.itemStack);

        /*for (AttributeType attributeType : AttributeType.values()) {
            if (statPassive.getAttributeValue(attributeType) != 0) {
                PersistentDataContainerUtil.putInteger(attributeType.name(), statPassive.getAttributeValue(attributeType), this.itemStack);
            }
        }*/
        for (ElementType elementType : ElementType.values()) {
            if (statPassive.getElementValue(elementType) != 0) {
                PersistentDataContainerUtil.putInteger(elementType.name(), statPassive.getElementValue(elementType), this.itemStack);
            }
        }
        if (gearSetStr != null) {
            PersistentDataContainerUtil.putString("gearSet", gearSetStr, this.itemStack);
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        this.itemStack.setItemMeta(itemMeta);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    public static String getLoreHealthPrefix() {
        return ChatPalette.GREEN_DARK + "❤ Health: " + ChatPalette.GRAY + "+";
    }
}
