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

public class Shield implements RPGGear {

    private final ItemStack itemStack;

    public Shield(String name, ItemTier tier, Material material, int customModelDataId, int level, ShieldGearType gearType, int health,
                  int defense, int magicDefense, int minStatValue, int maxStatValue, int minNumberOfStats, String gearSetStr) {
        name = tier.getTierColor() + name;
        boolean gearSetExist = false;
        if (gearSetStr != null && !gearSetStr.equals("")) {
            name = tier.getTierColor() + gearSetStr + " " + name;
            gearSetExist = true;
        }

        double bonusPercent = tier.getBonusMultiplier();

        health = (int) ((health * bonusPercent) + 0.5);

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + gearType.getDisplayName());
        if (gearSetExist) {
            lore.add(ChatColor.RED + gearSetStr);
        }
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        lore.add("");
        lore.add(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+" + health);
        lore.add(ChatColor.AQUA + "■ Defense: " + ChatColor.GRAY + "+" + defense);
        lore.add(ChatColor.BLUE + "✦ Magic Defense: " + ChatColor.GRAY + "+" + magicDefense);
        if (!statPassive.isEmpty()) {
            lore.add("");
            if (statPassive.getBonusDamage() != 0) {
                lore.add(AttributeType.BONUS_ELEMENT_DAMAGE.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getBonusDamage());
            }
            if (statPassive.getBonusDefense() != 0) {
                lore.add(AttributeType.BONUS_ELEMENT_DEFENSE.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getBonusDefense());
            }
            if (statPassive.getBonusMaxHealth() != 0) {
                lore.add(AttributeType.BONUS_MAX_HEALTH.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getBonusMaxHealth());
            }
            if (statPassive.getBonusMaxMana() != 0) {
                lore.add(AttributeType.BONUS_MAX_MANA.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getBonusMaxMana());
            }
            if (statPassive.getBonusCriticalChance() != 0) {
                lore.add(AttributeType.BONUS_CRITICAL_CHANCE.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getBonusCriticalChance());
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
        RPGItemUtils.resetArmor(this.itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("gearType", gearType.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putInteger("health", health, this.itemStack);
        PersistentDataContainerUtil.putInteger("defense", defense, this.itemStack);
        PersistentDataContainerUtil.putInteger("magicDefense", magicDefense, this.itemStack);

        if (statPassive.getBonusDamage() != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DAMAGE.name(), statPassive.getBonusDamage(), this.itemStack);
        }
        if (statPassive.getBonusDefense() != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DEFENSE.name(), statPassive.getBonusDefense(), this.itemStack);
        }
        if (statPassive.getBonusMaxHealth() != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_HEALTH.name(), statPassive.getBonusMaxHealth(), this.itemStack);
        }
        if (statPassive.getBonusMaxMana() != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_MANA.name(), statPassive.getBonusMaxMana(), this.itemStack);
        }
        if (statPassive.getBonusCriticalChance() != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_CRITICAL_CHANCE.name(), statPassive.getBonusCriticalChance(), this.itemStack);
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
