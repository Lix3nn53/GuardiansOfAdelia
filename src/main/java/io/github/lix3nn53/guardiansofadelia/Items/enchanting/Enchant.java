package io.github.lix3nn53.guardiansofadelia.Items.enchanting;

import io.github.lix3nn53.guardiansofadelia.Items.stats.*;
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
    private ItemStack itemStack;
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

        StatType type = StatUtils.getStatType(itemStack.getType());

        if (type.equals(StatType.HEALTH) || type.equals(StatType.MAGICAL) ||
                type.equals(StatType.MELEE) || type.equals(StatType.HYBRID)) {
            int baseValue;
            String statString;

            if (type.equals(StatType.MAGICAL)) {
                statString = ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "+";
                StatMagical stat = (StatMagical) StatUtils.getStat(itemStack);
                baseValue = stat.getMagicDamage();
            } else if (type.equals(StatType.MELEE)) {
                statString = ChatColor.RED + "⸸ Damage: " + ChatColor.GRAY + "+";
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            } else if (type.equals(StatType.HYBRID)) {
                statString = ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+";
                StatHybrid stat = (StatHybrid) StatUtils.getStat(itemStack);
                baseValue = stat.getRangedDamage();
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

            if (type.equals(StatType.MELEE)) {
                PersistentDataContainerUtil.putInteger("meleeDamage", nextValue, this.itemStack);
                this.itemStack = RPGItemUtils.clearThenSetDamageWhenInMainHand(itemStack, nextValue);
            } else if (type.equals(StatType.HYBRID)) {
                PersistentDataContainerUtil.putInteger("rangedDamage", nextValue, this.itemStack);
            } else if (type.equals(StatType.MAGICAL)) {
                PersistentDataContainerUtil.putInteger("magicDamage", nextValue, this.itemStack);
            } else {
                PersistentDataContainerUtil.putInteger("health", nextValue, this.itemStack);
            }
        } else if (type.equals(StatType.PASSIVE)) {
            StatPassive stat = (StatPassive) StatUtils.getStat(itemStack);

            player.sendMessage("SUCC");
            player.sendMessage("PASSIVE");

            int lineToChangeFire = -1;
            int lineToChangeWater = -1;
            int lineToChangeEarth = -1;
            int lineToChangeLightning = -1;
            int lineToChangeWind = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getFire() != 0) {
                    if (line.contains(ChatColor.RED + "☄ " + ChatColor.RED + "Fire: " + ChatColor.GRAY + "+")) {
                        lineToChangeFire = i;
                        changeCounter++;
                    }
                }
                if (stat.getWater() != 0) {
                    if (line.contains(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Water: " + ChatColor.GRAY + "+")) {
                        lineToChangeWater = i;
                        changeCounter++;
                    }
                }
                if (stat.getEarth() != 0) {
                    if (line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Earth: " + ChatColor.GRAY + "+")) {
                        lineToChangeEarth = i;
                        changeCounter++;
                    }
                }
                if (stat.getLightning() != 0) {
                    if (line.contains(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Lightning: " + ChatColor.GRAY + "+")) {
                        lineToChangeLightning = i;
                        changeCounter++;
                    }
                }
                if (stat.getWind() != 0) {
                    if (line.contains(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Wind: " + ChatColor.GRAY + "+")) {
                        lineToChangeWind = i;
                        changeCounter++;
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            int nextFireValue = getNextValue(stat.getFire());
            if (lineToChangeFire != -1) {
                String line = lore.get(lineToChangeFire);
                String newLine = line.replace(stat.getFire() + "", nextFireValue + "");
                lore.set(lineToChangeFire, newLine);
            }
            int nextWaterValue = getNextValue(stat.getWater());
            if (lineToChangeWater != -1) {
                String line = lore.get(lineToChangeWater);
                String newLine = line.replace(stat.getWater() + "", nextWaterValue + "");
                lore.set(lineToChangeWater, newLine);
            }
            int nextEarthValue = getNextValue(stat.getEarth());
            if (lineToChangeEarth != -1) {
                String line = lore.get(lineToChangeEarth);
                String newLine = line.replace(stat.getEarth() + "", nextEarthValue + "");
                lore.set(lineToChangeEarth, newLine);
            }
            int nextLightningValue = getNextValue(stat.getLightning());
            if (lineToChangeLightning != -1) {
                String line = lore.get(lineToChangeLightning);
                String newLine = line.replace(stat.getLightning() + "", nextLightningValue + "");
                lore.set(lineToChangeLightning, newLine);
            }
            int nextWindValue = getNextValue(stat.getWind());
            if (lineToChangeWind != -1) {
                String line = lore.get(lineToChangeWind);
                String newLine = line.replace(stat.getWind() + "", nextWindValue + "");
                lore.set(lineToChangeWind, newLine);
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            if (lineToChangeFire != -1) {
                PersistentDataContainerUtil.putInteger("fire", nextFireValue, this.itemStack);
            }
            if (lineToChangeWater != -1) {
                PersistentDataContainerUtil.putInteger("water", nextWaterValue, this.itemStack);
            }
            if (lineToChangeEarth != -1) {
                PersistentDataContainerUtil.putInteger("earth", nextEarthValue, this.itemStack);
            }
            if (lineToChangeLightning != -1) {
                PersistentDataContainerUtil.putInteger("lightning", nextLightningValue, this.itemStack);
            }
            if (lineToChangeWind != -1) {
                PersistentDataContainerUtil.putInteger("wind", nextWindValue, this.itemStack);
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

        StatType type = StatUtils.getStatType(itemStack.getType());

        if (type.equals(StatType.HEALTH) || type.equals(StatType.MAGICAL) ||
                type.equals(StatType.MELEE) || type.equals(StatType.HYBRID)) {
            int baseValue;
            String statString;

            if (type.equals(StatType.MAGICAL)) {
                statString = ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "+";
                StatMagical stat = (StatMagical) StatUtils.getStat(itemStack);
                baseValue = stat.getMagicDamage();
            } else if (type.equals(StatType.MELEE)) {
                statString = ChatColor.RED + "⸸ Damage: " + ChatColor.GRAY + "+";
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            } else if (type.equals(StatType.HYBRID)) {
                statString = ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+";
                StatHybrid stat = (StatHybrid) StatUtils.getStat(itemStack);
                baseValue = stat.getRangedDamage();
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

            if (type.equals(StatType.MELEE)) {
                PersistentDataContainerUtil.putInteger("meleeDamage", nextValue, this.itemStack);
                this.itemStack = RPGItemUtils.clearThenSetDamageWhenInMainHand(itemStack, nextValue);
            } else if (type.equals(StatType.HYBRID)) {
                PersistentDataContainerUtil.putInteger("rangedDamage", nextValue, this.itemStack);
            } else if (type.equals(StatType.MAGICAL)) {
                PersistentDataContainerUtil.putInteger("magicDamage", nextValue, this.itemStack);
            } else {
                PersistentDataContainerUtil.putInteger("health", nextValue, this.itemStack);
            }
        } else if (type.equals(StatType.PASSIVE)) {
            StatPassive stat = (StatPassive) StatUtils.getStat(itemStack);

            player.sendMessage("SUCC");
            player.sendMessage("PASSIVE");

            int lineToChangeFire = -1;
            int lineToChangeWater = -1;
            int lineToChangeEarth = -1;
            int lineToChangeLightning = -1;
            int lineToChangeWind = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getFire() != 0) {
                    if (line.contains(ChatColor.RED + "☄ " + ChatColor.RED + "Fire: " + ChatColor.GRAY + "+")) {
                        lineToChangeFire = i;
                        changeCounter++;
                    }
                }
                if (stat.getWater() != 0) {
                    if (line.contains(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Water: " + ChatColor.GRAY + "+")) {
                        lineToChangeWater = i;
                        changeCounter++;
                    }
                }
                if (stat.getEarth() != 0) {
                    if (line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Earth: " + ChatColor.GRAY + "+")) {
                        lineToChangeEarth = i;
                        changeCounter++;
                    }
                }
                if (stat.getLightning() != 0) {
                    if (line.contains(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Lightning: " + ChatColor.GRAY + "+")) {
                        lineToChangeLightning = i;
                        changeCounter++;
                    }
                }
                if (stat.getWind() != 0) {
                    if (line.contains(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Wind: " + ChatColor.GRAY + "+")) {
                        lineToChangeWind = i;
                        changeCounter++;
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            int nextFireValue = getPreviousValue(stat.getFire());
            if (lineToChangeFire != -1) {
                String line = lore.get(lineToChangeFire);
                String newLine = line.replace(stat.getFire() + "", nextFireValue + "");
                lore.set(lineToChangeFire, newLine);
            }
            int nextWaterValue = getPreviousValue(stat.getWater());
            if (lineToChangeWater != -1) {
                String line = lore.get(lineToChangeWater);
                String newLine = line.replace(stat.getWater() + "", nextWaterValue + "");
                lore.set(lineToChangeWater, newLine);
            }
            int nextEarthValue = getPreviousValue(stat.getEarth());
            if (lineToChangeEarth != -1) {
                String line = lore.get(lineToChangeEarth);
                String newLine = line.replace(stat.getEarth() + "", nextEarthValue + "");
                lore.set(lineToChangeEarth, newLine);
            }
            int nextLightningValue = getPreviousValue(stat.getLightning());
            if (lineToChangeLightning != -1) {
                String line = lore.get(lineToChangeLightning);
                String newLine = line.replace(stat.getLightning() + "", nextLightningValue + "");
                lore.set(lineToChangeLightning, newLine);
            }
            int nextWindValue = getPreviousValue(stat.getWind());
            if (lineToChangeWind != -1) {
                String line = lore.get(lineToChangeWind);
                String newLine = line.replace(stat.getWind() + "", nextWindValue + "");
                lore.set(lineToChangeWind, newLine);
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            if (lineToChangeFire != -1) {
                PersistentDataContainerUtil.putInteger("fire", nextFireValue, this.itemStack);
            }
            if (lineToChangeWater != -1) {
                PersistentDataContainerUtil.putInteger("water", nextWaterValue, this.itemStack);
            }
            if (lineToChangeEarth != -1) {
                PersistentDataContainerUtil.putInteger("earth", nextEarthValue, this.itemStack);
            }
            if (lineToChangeLightning != -1) {
                PersistentDataContainerUtil.putInteger("lightning", nextLightningValue, this.itemStack);
            }
            if (lineToChangeWind != -1) {
                PersistentDataContainerUtil.putInteger("wind", nextWindValue, this.itemStack);
            }
        }

        currentEnchantLevel--;
    }

    private void startGlowing(ItemMeta itemMeta) {
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData >= 6) {
                customModelData++;
                itemMeta.setCustomModelData(customModelData);
                itemStack.setItemMeta(itemMeta);
                player.sendMessage("new customModelData: " + customModelData);
            }
        }
    }

    private void extinguish(ItemMeta itemMeta) {
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData >= 7) {
                customModelData--;
                itemMeta.setCustomModelData(customModelData);
                itemStack.setItemMeta(itemMeta);
                player.sendMessage("new customModelData: " + customModelData);
            }
        }
    }
}
