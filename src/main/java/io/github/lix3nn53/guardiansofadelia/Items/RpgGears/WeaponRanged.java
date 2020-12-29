package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSet;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetManager;
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

public class WeaponRanged implements RPGGear {

    private final String itemTag;
    private final int level;
    private final WeaponGearType gearType;
    private final ItemStack itemStack;

    public WeaponRanged(String name, ItemTier tier, String itemTag, Material material, int customModelDataId, int level, WeaponGearType gearType,
                        int rangedDamage, AttackSpeed attackSpeed, int minStatValue,
                        int maxStatValue, int minNumberOfStats, boolean reduceMeleeDamage, String gearSetStr) {
        name = tier.getTierColor() + name;
        if (itemTag != null && !itemTag.equals("")) {
            name = tier.getTierColor() + itemTag + " " + name;
        }

        double bonusPercent = tier.getBonusMultiplier();
        double meleeDamagePercent = 0.4;
        if (!reduceMeleeDamage) {
            meleeDamagePercent = 1;
        }

        rangedDamage = (int) ((rangedDamage * bonusPercent) + 0.5);
        int meleeDamage = (int) ((rangedDamage * meleeDamagePercent) + 0.5);

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + gearType.getDisplayName());
        if (gearSetStr != null) {
            lore.add(ChatColor.RED + gearSetStr);
        }
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        lore.add("");
        lore.add(ChatColor.RED + "⸸ Damage: " + ChatColor.GRAY + "+" + meleeDamage);
        lore.add(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+" + rangedDamage);
        lore.add(ChatColor.AQUA + "ø Attack Speed: " + attackSpeed.getLoreString());
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
        if (gearSetStr != null) {
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
        RPGItemUtils.setDamage(this.itemStack, meleeDamage);

        PersistentDataContainerUtil.putInteger("meleeDamage", meleeDamage, this.itemStack);
        PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, this.itemStack);

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
        if (gearSetStr != null) {
            PersistentDataContainerUtil.putString("gearSet", gearSetStr, this.itemStack);
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
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

    public WeaponGearType getGearType() {
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
