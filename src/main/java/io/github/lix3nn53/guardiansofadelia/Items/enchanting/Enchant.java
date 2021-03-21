package io.github.lix3nn53.guardiansofadelia.Items.enchanting;

import io.github.lix3nn53.guardiansofadelia.Items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatOneType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Enchant {

    private final Player player;
    private final double MULTIPLIER = 1.05;
    private final ItemStack itemStack;
    private int currentEnchantLevel;

    public Enchant(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack.clone();

        this.currentEnchantLevel = EnchantManager.getEnchantLevel(itemStack);
    }

    public boolean enchantItem() {
        double roll = Math.random();
        double chance = EnchantManager.getChance(player, currentEnchantLevel);
        boolean isSuccess = roll <= chance;
        if (isSuccess) {
            successItem();
            return true;
        } else {
            failItem();
            return false;
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private int getNextValue(int value) {
        return (int) ((value * MULTIPLIER) + 0.5);
    }

    private int getPreviousValue(int value) {
        return (int) ((value / MULTIPLIER) + 0.5);
    }

    private void successItem() {
        if (currentEnchantLevel == 12) {
            return;
        }
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        String name = itemMeta.getDisplayName();
        if (this.currentEnchantLevel == 0) {
            name = name + ChatColor.AQUA + " [ +1 ]";
        } else if (this.currentEnchantLevel == 1) {
            name = name.replace("+1", "+2");
        } else if (this.currentEnchantLevel == 2) {
            name = name.replace("+2", "+3");
        } else if (this.currentEnchantLevel == 3) {
            name = name.replace("+3", "+4");
        } else if (this.currentEnchantLevel == 4) {
            name = name.replace("+4", "+5");
        } else if (this.currentEnchantLevel == 5) {
            name = name.replace("+5", "+6");
        } else if (this.currentEnchantLevel == 6) {
            name = name.replace("+6", "+7");
        } else if (this.currentEnchantLevel == 7) {
            name = name.replace("+7", "+8");
        } else if (this.currentEnchantLevel == 8) {
            startGlowing(itemMeta);
            name = name.replace("+8", "+9");
        } else if (this.currentEnchantLevel == 9) {
            name = name.replace("+9", "+10");
        } else if (this.currentEnchantLevel == 10) {
            name = name.replace("+10", "+11");
        } else if (this.currentEnchantLevel == 11) {
            name = name.replace("+11", "+12");
        }
        itemMeta.setDisplayName(name);

        GearStatType type = StatUtils.getStatType(itemStack.getType());

        if (type.equals(GearStatType.ARMOR_GEAR) || type.equals(GearStatType.WEAPON_GEAR)) {
            int baseValue;
            String statString;

            if (type.equals(GearStatType.WEAPON_GEAR)) {
                statString = ChatColor.RED + "⸸ Damage: " + ChatColor.GRAY + "+";
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            } else {
                statString = ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+";
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            }

            player.sendMessage("SUCC");
            player.sendMessage("statString: " + statString);
            player.sendMessage("baseValue: " + baseValue);
            int nextValue = getNextValue(baseValue);
            player.sendMessage("nextValue: " + nextValue);

            int lineToChange = -1;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(statString)) {
                    lineToChange = i;
                    break;
                }
            }
            if (lineToChange != -1) {
                String line = lore.get(lineToChange);
                String newLine = line.replace(baseValue + "", nextValue + "");
                lore.set(lineToChange, newLine);
                itemMeta.setLore(lore);
            }

            this.itemStack.setItemMeta(itemMeta);

            if (type.equals(GearStatType.WEAPON_GEAR)) {
                PersistentDataContainerUtil.putInteger("elementDamage", nextValue, this.itemStack);
                RPGItemUtils.setDamage(itemStack, nextValue);
            } else {
                PersistentDataContainerUtil.putInteger("health", nextValue, this.itemStack);
            }
        } else if (type.equals(GearStatType.PASSIVE_GEAR)) {
            StatPassive stat = (StatPassive) StatUtils.getStat(itemStack);

            player.sendMessage("SUCC");
            player.sendMessage("PASSIVE");

            int lineToChangeBonusDamage = -1;
            int lineToChangeBonusDefense = -1;
            int lineToChangeBonusHealth = -1;
            int lineToChangeBonusMana = -1;
            int lineToChangeBonusCriticalChance = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getBonusDamage() != 0) {
                    if (line.contains(AttributeType.BONUS_ELEMENT_DAMAGE.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeBonusDamage = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusDefense() != 0) {
                    if (line.contains(AttributeType.BONUS_ELEMENT_DEFENSE.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeBonusDefense = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusMaxHealth() != 0) {
                    if (line.contains(AttributeType.BONUS_MAX_HEALTH.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeBonusHealth = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusMaxMana() != 0) {
                    if (line.contains(AttributeType.BONUS_MAX_MANA.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeBonusMana = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusCriticalChance() != 0) {
                    if (line.contains(AttributeType.BONUS_CRITICAL_CHANCE.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeBonusCriticalChance = i;
                        changeCounter++;
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            int nextDamageValue = getNextValue(stat.getBonusDamage());
            if (lineToChangeBonusDamage != -1) {
                String line = lore.get(lineToChangeBonusDamage);
                String newLine = line.replace(stat.getBonusDamage() + "", nextDamageValue + "");
                lore.set(lineToChangeBonusDamage, newLine);
            }
            int nextDefenseValue = getNextValue(stat.getBonusDefense());
            if (lineToChangeBonusDefense != -1) {
                String line = lore.get(lineToChangeBonusDefense);
                String newLine = line.replace(stat.getBonusDefense() + "", nextDefenseValue + "");
                lore.set(lineToChangeBonusDefense, newLine);
            }
            int nextHealthValue = getNextValue(stat.getBonusMaxHealth());
            if (lineToChangeBonusHealth != -1) {
                String line = lore.get(lineToChangeBonusHealth);
                String newLine = line.replace(stat.getBonusMaxHealth() + "", nextHealthValue + "");
                lore.set(lineToChangeBonusHealth, newLine);
            }
            int nextManaValue = getNextValue(stat.getBonusMaxMana());
            if (lineToChangeBonusMana != -1) {
                String line = lore.get(lineToChangeBonusMana);
                String newLine = line.replace(stat.getBonusMaxMana() + "", nextManaValue + "");
                lore.set(lineToChangeBonusMana, newLine);
            }
            int nextCriticalValue = getNextValue(stat.getBonusCriticalChance());
            if (lineToChangeBonusCriticalChance != -1) {
                String line = lore.get(lineToChangeBonusCriticalChance);
                String newLine = line.replace(stat.getBonusCriticalChance() + "", nextCriticalValue + "");
                lore.set(lineToChangeBonusCriticalChance, newLine);
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            if (lineToChangeBonusDamage != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DAMAGE.name(), nextDamageValue, this.itemStack);
            }
            if (lineToChangeBonusDefense != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DEFENSE.name(), nextDefenseValue, this.itemStack);
            }
            if (lineToChangeBonusHealth != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_HEALTH.name(), nextHealthValue, this.itemStack);
            }
            if (lineToChangeBonusMana != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_MANA.name(), nextManaValue, this.itemStack);
            }
            if (lineToChangeBonusCriticalChance != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_CRITICAL_CHANCE.name(), nextCriticalValue, this.itemStack);
            }
        }

        currentEnchantLevel++;
    }

    private void failItem() {
        if (currentEnchantLevel == 0) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        String name = itemMeta.getDisplayName();
        if (this.currentEnchantLevel == 1) {
            name = name.replace(ChatColor.AQUA + " [ ", "");
            name = name.replace("]", "");
            name = name.replace("+1 ", "");
        } else if (this.currentEnchantLevel == 2) {
            name = name.replace("+2", "+1");
        } else if (this.currentEnchantLevel == 3) {
            name = name.replace("+3", "+2");
        } else if (this.currentEnchantLevel == 4) {
            name = name.replace("+4", "+3");
        } else if (this.currentEnchantLevel == 5) {
            name = name.replace("+5", "+4");
        } else if (this.currentEnchantLevel == 6) {
            name = name.replace("+6", "+5");
        } else if (this.currentEnchantLevel == 7) {
            name = name.replace("+7", "+6");
        } else if (this.currentEnchantLevel == 8) {
            name = name.replace("+8", "+7");
        } else if (this.currentEnchantLevel == 9) {
            extinguish(itemMeta);
            name = name.replace("+9", "+8");
        } else if (this.currentEnchantLevel == 10) {
            name = name.replace("+10", "+9");
        } else if (this.currentEnchantLevel == 11) {
            name = name.replace("+11", "+10");
        } else if (this.currentEnchantLevel == 12) {
            name = name.replace("+12", "+11");
        }
        itemMeta.setDisplayName(name);

        GearStatType type = StatUtils.getStatType(itemStack.getType());

        if (type.equals(GearStatType.ARMOR_GEAR) || type.equals(GearStatType.WEAPON_GEAR)) {
            int baseValue;
            String statString;

            if (type.equals(GearStatType.WEAPON_GEAR)) {
                statString = ChatColor.RED + "⸸ Damage: " + ChatColor.GRAY + "+";
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            } else {
                statString = ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+";
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            }

            player.sendMessage("FAIL");
            player.sendMessage("statString: " + statString);
            player.sendMessage("baseValue: " + baseValue);
            int nextValue = getPreviousValue(baseValue);
            player.sendMessage("nextValue: " + nextValue);

            int lineToChange = -1;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(statString)) {
                    lineToChange = i;
                    break;
                }
            }
            if (lineToChange != -1) {
                String line = lore.get(lineToChange);
                String newLine = line.replace(baseValue + "", nextValue + "");
                lore.set(lineToChange, newLine);
                itemMeta.setLore(lore);
            }

            this.itemStack.setItemMeta(itemMeta);

            if (type.equals(GearStatType.WEAPON_GEAR)) {
                PersistentDataContainerUtil.putInteger("elementDamage", nextValue, this.itemStack);
                RPGItemUtils.setDamage(itemStack, nextValue);
            } else {
                PersistentDataContainerUtil.putInteger("health", nextValue, this.itemStack);
            }
        } else if (type.equals(GearStatType.PASSIVE_GEAR)) {
            StatPassive stat = (StatPassive) StatUtils.getStat(itemStack);

            player.sendMessage("SUCC");
            player.sendMessage("PASSIVE");

            int lineToChangeDamage = -1;
            int lineToChangeMana = -1;
            int lineToChangeHealth = -1;
            int lineToChangeDefense = -1;
            int lineToChangeCriticalChance = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getBonusDamage() != 0) {
                    if (line.contains(AttributeType.BONUS_ELEMENT_DAMAGE.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeDamage = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusDefense() != 0) {
                    if (line.contains(AttributeType.BONUS_ELEMENT_DEFENSE.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeDefense = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusMaxHealth() != 0) {
                    if (line.contains(AttributeType.BONUS_MAX_HEALTH.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeHealth = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusMaxMana() != 0) {
                    if (line.contains(AttributeType.BONUS_MAX_MANA.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeMana = i;
                        changeCounter++;
                    }
                }
                if (stat.getBonusCriticalChance() != 0) {
                    if (line.contains(AttributeType.BONUS_CRITICAL_CHANCE.getCustomName() + ": " + ChatColor.GRAY + "+")) {
                        lineToChangeCriticalChance = i;
                        changeCounter++;
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            int nextDamageValue = getPreviousValue(stat.getBonusDamage());
            if (lineToChangeDamage != -1) {
                String line = lore.get(lineToChangeDamage);
                String newLine = line.replace(stat.getBonusDamage() + "", nextDamageValue + "");
                lore.set(lineToChangeDamage, newLine);
            }
            int nextDefenseValue = getPreviousValue(stat.getBonusDefense());
            if (lineToChangeDefense != -1) {
                String line = lore.get(lineToChangeDefense);
                String newLine = line.replace(stat.getBonusDefense() + "", nextDefenseValue + "");
                lore.set(lineToChangeDefense, newLine);
            }
            int nextHealthValue = getPreviousValue(stat.getBonusMaxHealth());
            if (lineToChangeHealth != -1) {
                String line = lore.get(lineToChangeHealth);
                String newLine = line.replace(stat.getBonusMaxHealth() + "", nextHealthValue + "");
                lore.set(lineToChangeHealth, newLine);
            }
            int nextManaValue = getPreviousValue(stat.getBonusMaxMana());
            if (lineToChangeMana != -1) {
                String line = lore.get(lineToChangeMana);
                String newLine = line.replace(stat.getBonusMaxMana() + "", nextManaValue + "");
                lore.set(lineToChangeMana, newLine);
            }
            int nextCriticalChanceValue = getPreviousValue(stat.getBonusCriticalChance());
            if (lineToChangeCriticalChance != -1) {
                String line = lore.get(lineToChangeCriticalChance);
                String newLine = line.replace(stat.getBonusCriticalChance() + "", nextCriticalChanceValue + "");
                lore.set(lineToChangeCriticalChance, newLine);
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            if (lineToChangeDamage != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DAMAGE.name(), nextDamageValue, this.itemStack);
            }
            if (lineToChangeDefense != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_ELEMENT_DEFENSE.name(), nextDefenseValue, this.itemStack);
            }
            if (lineToChangeHealth != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_HEALTH.name(), nextHealthValue, this.itemStack);
            }
            if (lineToChangeMana != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_MAX_MANA.name(), nextManaValue, this.itemStack);
            }
            if (lineToChangeCriticalChance != -1) {
                PersistentDataContainerUtil.putInteger(AttributeType.BONUS_CRITICAL_CHANCE.name(), nextCriticalChanceValue, this.itemStack);
            }
        }

        currentEnchantLevel--;
    }

    private void startGlowing(ItemMeta itemMeta) {
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            customModelData++;
            itemMeta.setCustomModelData(customModelData);
            itemStack.setItemMeta(itemMeta);
            player.sendMessage("new customModelData: " + customModelData);
        }
    }

    private void extinguish(ItemMeta itemMeta) {
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            customModelData--;
            itemMeta.setCustomModelData(customModelData);
            itemStack.setItemMeta(itemMeta);
            player.sendMessage("new customModelData: " + customModelData);
        }
    }
}
