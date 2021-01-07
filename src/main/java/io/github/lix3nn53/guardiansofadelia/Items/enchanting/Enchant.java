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
                RPGItemUtils.setDamage(itemStack, nextValue);
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

            int lineToChangeStr = -1;
            int lineToChangeSpr = -1;
            int lineToChangeEnd = -1;
            int lineToChangeInt = -1;
            int lineToChangeDex = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getStrength() != 0) {
                    if (line.contains(ChatColor.RED + "☄ " + ChatColor.RED + "Strength: " + ChatColor.GRAY + "+")) {
                        lineToChangeStr = i;
                        changeCounter++;
                    }
                }
                if (stat.getSpirit() != 0) {
                    if (line.contains(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Spirit: " + ChatColor.GRAY + "+")) {
                        lineToChangeSpr = i;
                        changeCounter++;
                    }
                }
                if (stat.getEndurance() != 0) {
                    if (line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Endurance: " + ChatColor.GRAY + "+")) {
                        lineToChangeEnd = i;
                        changeCounter++;
                    }
                }
                if (stat.getIntelligence() != 0) {
                    if (line.contains(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Intelligence: " + ChatColor.GRAY + "+")) {
                        lineToChangeInt = i;
                        changeCounter++;
                    }
                }
                if (stat.getDexterity() != 0) {
                    if (line.contains(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Dexterity: " + ChatColor.GRAY + "+")) {
                        lineToChangeDex = i;
                        changeCounter++;
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            int nextStrValue = getNextValue(stat.getStrength());
            if (lineToChangeStr != -1) {
                String line = lore.get(lineToChangeStr);
                String newLine = line.replace(stat.getStrength() + "", nextStrValue + "");
                lore.set(lineToChangeStr, newLine);
            }
            int nextSprValue = getNextValue(stat.getSpirit());
            if (lineToChangeSpr != -1) {
                String line = lore.get(lineToChangeSpr);
                String newLine = line.replace(stat.getSpirit() + "", nextSprValue + "");
                lore.set(lineToChangeSpr, newLine);
            }
            int nextEndValue = getNextValue(stat.getEndurance());
            if (lineToChangeEnd != -1) {
                String line = lore.get(lineToChangeEnd);
                String newLine = line.replace(stat.getEndurance() + "", nextEndValue + "");
                lore.set(lineToChangeEnd, newLine);
            }
            int nextIntValue = getNextValue(stat.getIntelligence());
            if (lineToChangeInt != -1) {
                String line = lore.get(lineToChangeInt);
                String newLine = line.replace(stat.getIntelligence() + "", nextIntValue + "");
                lore.set(lineToChangeInt, newLine);
            }
            int nextDexValue = getNextValue(stat.getDexterity());
            if (lineToChangeDex != -1) {
                String line = lore.get(lineToChangeDex);
                String newLine = line.replace(stat.getDexterity() + "", nextDexValue + "");
                lore.set(lineToChangeDex, newLine);
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            if (lineToChangeStr != -1) {
                PersistentDataContainerUtil.putInteger("strength", nextStrValue, this.itemStack);
            }
            if (lineToChangeSpr != -1) {
                PersistentDataContainerUtil.putInteger("spirit", nextSprValue, this.itemStack);
            }
            if (lineToChangeEnd != -1) {
                PersistentDataContainerUtil.putInteger("endurance", nextEndValue, this.itemStack);
            }
            if (lineToChangeInt != -1) {
                PersistentDataContainerUtil.putInteger("intelligence", nextIntValue, this.itemStack);
            }
            if (lineToChangeDex != -1) {
                PersistentDataContainerUtil.putInteger("dexterity", nextDexValue, this.itemStack);
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
                RPGItemUtils.setDamage(itemStack, nextValue);
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

            int lineToChangeStr = -1;
            int lineToChangeSpr = -1;
            int lineToChangeEnd = -1;
            int lineToChangeInt = -1;
            int lineToChangeDex = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getStrength() != 0) {
                    if (line.contains(ChatColor.RED + "☄ " + ChatColor.RED + "Strength: " + ChatColor.GRAY + "+")) {
                        lineToChangeStr = i;
                        changeCounter++;
                    }
                }
                if (stat.getSpirit() != 0) {
                    if (line.contains(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Spirit: " + ChatColor.GRAY + "+")) {
                        lineToChangeSpr = i;
                        changeCounter++;
                    }
                }
                if (stat.getEndurance() != 0) {
                    if (line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Endurance: " + ChatColor.GRAY + "+")) {
                        lineToChangeEnd = i;
                        changeCounter++;
                    }
                }
                if (stat.getIntelligence() != 0) {
                    if (line.contains(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Intelligence: " + ChatColor.GRAY + "+")) {
                        lineToChangeInt = i;
                        changeCounter++;
                    }
                }
                if (stat.getDexterity() != 0) {
                    if (line.contains(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Dexterity: " + ChatColor.GRAY + "+")) {
                        lineToChangeDex = i;
                        changeCounter++;
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            int nextStrValue = getPreviousValue(stat.getStrength());
            if (lineToChangeStr != -1) {
                String line = lore.get(lineToChangeStr);
                String newLine = line.replace(stat.getStrength() + "", nextStrValue + "");
                lore.set(lineToChangeStr, newLine);
            }
            int nextSprValue = getPreviousValue(stat.getSpirit());
            if (lineToChangeSpr != -1) {
                String line = lore.get(lineToChangeSpr);
                String newLine = line.replace(stat.getSpirit() + "", nextSprValue + "");
                lore.set(lineToChangeSpr, newLine);
            }
            int nextEndValue = getPreviousValue(stat.getEndurance());
            if (lineToChangeEnd != -1) {
                String line = lore.get(lineToChangeEnd);
                String newLine = line.replace(stat.getEndurance() + "", nextEndValue + "");
                lore.set(lineToChangeEnd, newLine);
            }
            int nextIntValue = getPreviousValue(stat.getIntelligence());
            if (lineToChangeInt != -1) {
                String line = lore.get(lineToChangeInt);
                String newLine = line.replace(stat.getIntelligence() + "", nextIntValue + "");
                lore.set(lineToChangeInt, newLine);
            }
            int nextDexValue = getPreviousValue(stat.getDexterity());
            if (lineToChangeDex != -1) {
                String line = lore.get(lineToChangeDex);
                String newLine = line.replace(stat.getDexterity() + "", nextDexValue + "");
                lore.set(lineToChangeDex, newLine);
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            if (lineToChangeStr != -1) {
                PersistentDataContainerUtil.putInteger("strength", nextStrValue, this.itemStack);
            }
            if (lineToChangeSpr != -1) {
                PersistentDataContainerUtil.putInteger("spirit", nextSprValue, this.itemStack);
            }
            if (lineToChangeEnd != -1) {
                PersistentDataContainerUtil.putInteger("endurance", nextEndValue, this.itemStack);
            }
            if (lineToChangeInt != -1) {
                PersistentDataContainerUtil.putInteger("intelligence", nextIntValue, this.itemStack);
            }
            if (lineToChangeDex != -1) {
                PersistentDataContainerUtil.putInteger("dexterity", nextDexValue, this.itemStack);
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
