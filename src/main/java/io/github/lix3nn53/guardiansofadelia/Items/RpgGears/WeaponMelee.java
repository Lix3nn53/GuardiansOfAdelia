package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSet;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetManager;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponMelee implements RPGGear {

    private final ItemStack itemStack;

    public WeaponMelee(String name, ItemTier tier, Material material, int customModelDataId, int level, WeaponGearType gearType, int elementDamage,
                       AttackSpeed attackSpeed, int minStatValue, int maxStatValue, int minNumberOfStats, String gearSetStr) {
        name = tier.getTierColor() + name;
        boolean gearSetExist = false;
        if (gearSetStr != null && !gearSetStr.equals("")) {
            name = tier.getTierColor() + gearSetStr + " " + name;
            gearSetExist = true;
        }

        double bonusPercent = tier.getBonusMultiplier();

        elementDamage = (int) ((elementDamage * bonusPercent) + 0.5);

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + gearType.getDisplayName());
        if (gearSetExist) {
            lore.add(ChatColor.RED + gearSetStr);
        }
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        lore.add("");
        lore.add(ChatColor.RED + "✦ Element Damage: " + ChatColor.GRAY + "+" + elementDamage);
        lore.add(ChatColor.AQUA + "ø Attack Speed: " + attackSpeed.getLoreString());
        if (!statPassive.isEmpty()) {
            lore.add("");
            for (AttributeType attributeType : AttributeType.values()) {
                if (statPassive.getAttributeValue(attributeType) != 0) {
                    lore.add(attributeType.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getAttributeValue(attributeType));
                }
            }
        }
        lore.add("");
        lore.add(tier.getTierString());
        if (gearSetExist) {
            lore.add("");
            for (int i = 2; i < 6; i++) {
                GearSet gearSet = new GearSet(gearSetStr, i);
                if (GearSetManager.hasEffect(gearSet)) {
                    lore.add(ChatColor.GRAY + "-- " + ChatColor.RED + gearSetStr + ChatColor.GRAY + " [" + i + " pieces] --");
                    List<GearSetEffect> effects = GearSetManager.getEffectsWithoutLower(gearSet);
                    for (GearSetEffect gearSetEffect : effects) {
                        lore.add("      " + gearSetEffect.toString());
                    }
                }
            }
        }

        this.itemStack = new ItemStack(material);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("gearType", gearType.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);

        RPGItemUtils.setAttackSpeed(this.itemStack, attackSpeed.getSpeedValue());
        RPGItemUtils.setDamage(this.itemStack, elementDamage);

        PersistentDataContainerUtil.putInteger("elementDamage", elementDamage, this.itemStack);

        for (AttributeType attributeType : AttributeType.values()) {
            if (statPassive.getAttributeValue(attributeType) != 0) {
                PersistentDataContainerUtil.putInteger(attributeType.name(), statPassive.getAttributeValue(attributeType), this.itemStack);
            }
        }
        if (gearSetExist) {
            PersistentDataContainerUtil.putString("gearSet", gearSetStr, this.itemStack);
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(customModelDataId);

        this.itemStack.setItemMeta(itemMeta);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
}
