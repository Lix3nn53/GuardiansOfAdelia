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
        int finalDamage = (int) (statPassive.getBonusDamage() * bonusPercent);
        int finalDefense = (int) (statPassive.getBonusDefense() * bonusPercent);
        int finalHealth = (int) (statPassive.getBonusMaxHealth() * bonusPercent);
        int finalMana = (int) (statPassive.getBonusMaxMana() * bonusPercent);
        int finalCriticalChance = (int) (statPassive.getBonusCriticalChance() * bonusPercent);

        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Passive Gear");
        lore.add("");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        if (!statPassive.isEmpty()) {
            lore.add("");
            if (finalDamage != 0) {
                lore.add(AttributeType.BONUS_ELEMENT_DAMAGE.getCustomName() + ": " + ChatColor.GRAY + "+" + finalDamage);
            }
            if (finalDefense != 0) {
                lore.add(AttributeType.BONUS_ELEMENT_DEFENSE.getCustomName() + ": " + ChatColor.GRAY + "+" + finalDefense);
            }
            if (finalHealth != 0) {
                lore.add(AttributeType.BONUS_MAX_HEALTH.getCustomName() + ": " + ChatColor.GRAY + "+" + finalHealth);
            }
            if (finalMana != 0) {
                lore.add(AttributeType.BONUS_MAX_MANA.getCustomName() + ": " + ChatColor.GRAY + "+" + finalMana);
            }
            if (finalCriticalChance != 0) {
                lore.add(AttributeType.BONUS_CRITICAL_CHANCE.getCustomName() + ": " + ChatColor.GRAY + "+" + finalCriticalChance);
            }
        }
        lore.add("");
        lore.add(tier.getTierString());

        this.itemStack = new ItemStack(Material.SHEARS);
        PersistentDataContainerUtil.putInteger("reqLevel", level, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("passive", passiveType.name(), this.itemStack);

        if (finalDamage != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DAMAGE.name(), finalDamage, this.itemStack);
        }
        if (finalDefense != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DEFENSE.name(), finalDefense, this.itemStack);
        }
        if (finalHealth != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_HEALTH.name(), finalHealth, this.itemStack);
        }
        if (finalMana != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_MANA.name(), finalMana, this.itemStack);
        }
        if (finalCriticalChance != 0) {
            PersistentDataContainerUtil.putInteger(AttributeType.BONUS_CRITICAL_CHANCE.name(), finalCriticalChance, this.itemStack);
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
