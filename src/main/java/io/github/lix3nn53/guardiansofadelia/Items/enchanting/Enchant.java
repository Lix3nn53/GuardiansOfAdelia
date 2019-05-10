package io.github.lix3nn53.guardiansofadelia.Items.enchanting;

import io.github.lix3nn53.guardiansofadelia.Items.stats.*;
import io.github.lix3nn53.guardiansofadelia.utilities.persistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Enchant {

    private final Player player;
    private final double multiplier = 0.05;
    private ItemStack itemStack;
    private int currentEnchantLevel;

    public Enchant(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack.clone();

        this.currentEnchantLevel = EnchantManager.getEnchantLevel(itemStack);
    }

    public boolean enchantItem() {
        double roll = Math.random();
        double chance = getChance();
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

    private double getChance() {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return 1;
        }
        if (this.currentEnchantLevel == 0) {
            return 0.84;
        } else if (this.currentEnchantLevel == 1) {
            return 0.8;
        } else if (this.currentEnchantLevel == 2) {
            return 0.76;
        } else if (this.currentEnchantLevel == 3) {
            return 0.72;
        } else if (this.currentEnchantLevel == 4) {
            return 0.67;
        } else if (this.currentEnchantLevel == 5) {
            return 0.6;
        } else if (this.currentEnchantLevel == 6) {
            return 0.53;
        } else if (this.currentEnchantLevel == 7) {
            return 0.47;
        } else if (this.currentEnchantLevel == 8) {
            return 0.4;
        } else if (this.currentEnchantLevel == 9) {
            return 0.32;
        } else if (this.currentEnchantLevel == 10) {
            return 0.26;
        } else if (this.currentEnchantLevel == 11) {
            return 0.2;
        }
        return 0;
    }

    private int getBonusValue(int value) {
        int bonus = (int) ((value * multiplier) + 0.5);
        if (bonus <= 1) {
            return 1;
        }
        return bonus;
    }

    private int getDecreaseValue(int value) {
        int bonus = (int) (value - ((value / (1 + multiplier)) + 0.5));
        if (bonus <= 1) {
            return 1;
        }
        return bonus;
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
            name = name.replace("\\+1", "\\+2");
        } else if (this.currentEnchantLevel == 2) {
            name = name.replace("\\+2", "\\+3");
        } else if (this.currentEnchantLevel == 3) {
            name = name.replace("\\+3", "\\+4");
        } else if (this.currentEnchantLevel == 4) {
            name = name.replace("\\+4", "\\+5");
        } else if (this.currentEnchantLevel == 5) {
            name = name.replace("\\+5", "\\+6");
        } else if (this.currentEnchantLevel == 6) {
            name = name.replace("\\+6", "\\+7");
        } else if (this.currentEnchantLevel == 7) {
            name = name.replace("\\+7", "\\+8");
        } else if (this.currentEnchantLevel == 8) {
            startGlowing();
            name = name.replace("\\+8", "\\+9");
        } else if (this.currentEnchantLevel == 9) {
            name = name.replace("\\+9", "\\+10");
        } else if (this.currentEnchantLevel == 10) {
            name = name.replace("\\+10", "\\+11");
        } else if (this.currentEnchantLevel == 11) {
            name = name.replace("\\+11", "\\+12");
        }
        itemMeta.setDisplayName(name);

        StatType type = StatUtils.getStatType(itemStack.getType());

        if (type.equals(StatType.HEALTH) || type.equals(StatType.MAGICAL) ||
                type.equals(StatType.MELEE) || type.equals(StatType.RANGED)) {
            StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
            int nextValue = stat.getValue() + getBonusValue(stat.getValue());

            String statString = ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+";
            if (type.equals(StatType.MAGICAL)) {
                statString = ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "+";
            } else if (type.equals(StatType.MELEE)) {
                statString = ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+";
            } else if (type.equals(StatType.RANGED)) {
                statString = ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+";
            }
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
                String newLine = line.replace(stat.getValue() + "", nextValue + "");
                lore.set(lineToChange, newLine);
                itemMeta.setLore(lore);
            }

            if (type.equals(StatType.MELEE)) {
                itemStack = RPGItemUtils.setDamageWhenInMainHand(this.itemStack, nextValue);
            } else if (type.equals(StatType.RANGED)) {
                itemStack = persistentDataContainerUtil.putInteger("rangedDamage", nextValue, this.itemStack);
            }
        } else if (type.equals(StatType.HYBRID)) {
            StatHybrid stat = (StatHybrid) StatUtils.getStat(itemStack);
            int nextDamageValue = stat.getDamage() + getBonusValue(stat.getDamage());
            int nextRangedDamageValue = stat.getRangedDamage() + getBonusValue(stat.getRangedDamage());
            int lineToChange = -1;
            int lineToChangeRanged = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+")) {
                    lineToChange = i;
                    changeCounter++;
                } else if (line.contains(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+")) {
                    lineToChangeRanged = i;
                    changeCounter++;
                }
                if (changeCounter == 2) {
                    break;
                }
            }
            if (lineToChange != -1 && lineToChangeRanged != -1) {
                String line = lore.get(lineToChange);
                String newLine = line.replace(stat.getDamage() + "", nextDamageValue + "");
                lore.set(lineToChange, newLine);

                String lineRanged = lore.get(lineToChangeRanged);
                String newLineRanged = lineRanged.replace(stat.getRangedDamage() + "", nextRangedDamageValue + "");
                lore.set(lineToChangeRanged, newLineRanged);
                itemMeta.setLore(lore);
            }
            itemStack = RPGItemUtils.setDamageWhenInMainHand(this.itemStack, nextDamageValue);
        } else if (type.equals(StatType.PASSIVE)) {
            StatPassive stat = (StatPassive) StatUtils.getStat(itemStack);

            int lineToChangeFire = -1;
            int lineToChangeWater = -1;
            int lineToChangeEarth = -1;
            int lineToChangeLightning = -1;
            int lineToChangeAir = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getFire() != 0 && line.contains(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+")) {
                    lineToChangeFire = i;
                    changeCounter++;
                } else if (stat.getWater() != 0 && line.contains(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+")) {
                    lineToChangeWater = i;
                    changeCounter++;
                } else if (stat.getEarth() != 0 && line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+")) {
                    lineToChangeEarth = i;
                    changeCounter++;
                } else if (stat.getLightning() != 0 && line.contains(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+")) {
                    lineToChangeLightning = i;
                    changeCounter++;
                } else if (stat.getAir() != 0 && line.contains(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + ChatColor.GRAY + "+")) {
                    lineToChangeAir = i;
                    changeCounter++;
                }
                if (changeCounter == 5) {
                    break;
                }
            }
            if (lineToChangeFire != -1) {
                int nextFireValue = stat.getFire() + getBonusValue(stat.getFire());
                String line = lore.get(lineToChangeFire);
                String newLine = line.replace(stat.getFire() + "", nextFireValue + "");
                lore.set(lineToChangeFire, newLine);
            } else if (lineToChangeWater != -1) {
                int nextWaterValue = stat.getWater() + getBonusValue(stat.getWater());
                String line = lore.get(lineToChangeWater);
                String newLine = line.replace(stat.getWater() + "", nextWaterValue + "");
                lore.set(lineToChangeWater, newLine);
            } else if (lineToChangeEarth != -1) {
                int nextEarthValue = stat.getEarth() + getBonusValue(stat.getEarth());
                String line = lore.get(lineToChangeEarth);
                String newLine = line.replace(stat.getEarth() + "", nextEarthValue + "");
                lore.set(lineToChangeEarth, newLine);
            } else if (lineToChangeLightning != -1) {
                int nextLightningValue = stat.getLightning() + getBonusValue(stat.getLightning());
                String line = lore.get(lineToChangeLightning);
                String newLine = line.replace(stat.getLightning() + "", nextLightningValue + "");
                lore.set(lineToChangeLightning, newLine);
            } else if (lineToChangeAir != -1) {
                int nextAirValue = stat.getAir() + getBonusValue(stat.getAir());
                String line = lore.get(lineToChangeAir);
                String newLine = line.replace(stat.getAir() + "", nextAirValue + "");
                lore.set(lineToChangeAir, newLine);
            }
            itemMeta.setLore(lore);
        }

        this.itemStack.setItemMeta(itemMeta);
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
            name = name.replace(ChatColor.AQUA + " \\[ ", "");
            name = name.replace("]", "");
            name = name.replace("\\+1 ", "");
        } else if (this.currentEnchantLevel == 2) {
            name = name.replace("\\+2", "\\+1");
        } else if (this.currentEnchantLevel == 3) {
            name = name.replace("\\+3", "\\+2");
        } else if (this.currentEnchantLevel == 4) {
            name = name.replace("\\+4", "\\+3");
        } else if (this.currentEnchantLevel == 5) {
            name = name.replace("\\+5", "\\+4");
        } else if (this.currentEnchantLevel == 6) {
            name = name.replace("\\+6", "\\+5");
        } else if (this.currentEnchantLevel == 7) {
            name = name.replace("\\+7", "\\+6");
        } else if (this.currentEnchantLevel == 8) {
            name = name.replace("\\+8", "\\+7");
        } else if (this.currentEnchantLevel == 9) {
            extinguish();
            name = name.replace("\\+9", "\\+8");
        } else if (this.currentEnchantLevel == 10) {
            name = name.replace("\\+10", "\\+9");
        } else if (this.currentEnchantLevel == 11) {
            name = name.replace("\\+11", "\\+10");
        } else if (this.currentEnchantLevel == 12) {
            name = name.replace("\\+12", "\\+11");
        }
        itemMeta.setDisplayName(name);

        StatType type = StatUtils.getStatType(itemStack.getType());

        if (type.equals(StatType.HEALTH) || type.equals(StatType.MAGICAL) ||
                type.equals(StatType.MELEE) || type.equals(StatType.RANGED)) {
            StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
            int nextValue = stat.getValue() - getDecreaseValue(stat.getValue());

            String statString = ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+";
            if (type.equals(StatType.MAGICAL)) {
                statString = ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "+";
            } else if (type.equals(StatType.MELEE)) {
                statString = ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+";
            } else if (type.equals(StatType.RANGED)) {
                statString = ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+";
            }
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
                String newLine = line.replace(stat.getValue() + "", nextValue + "");
                lore.set(lineToChange, newLine);
                itemMeta.setLore(lore);
            }

            if (type.equals(StatType.MELEE)) {
                itemStack = RPGItemUtils.setDamageWhenInMainHand(this.itemStack, nextValue);
            } else if (type.equals(StatType.RANGED)) {
                itemStack = persistentDataContainerUtil.putInteger("rangedDamage", nextValue, this.itemStack);
            }
        } else if (type.equals(StatType.HYBRID)) {
            StatHybrid stat = (StatHybrid) StatUtils.getStat(itemStack);
            int nextDamageValue = stat.getDamage() - getDecreaseValue(stat.getDamage());
            int nextRangedDamageValue = stat.getRangedDamage() - getDecreaseValue(stat.getRangedDamage());
            int lineToChange = -1;
            int lineToChangeRanged = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+")) {
                    lineToChange = i;
                    changeCounter++;
                } else if (line.contains(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+")) {
                    lineToChangeRanged = i;
                    changeCounter++;
                }
                if (changeCounter == 2) {
                    break;
                }
            }
            if (lineToChange != -1 && lineToChangeRanged != -1) {
                String line = lore.get(lineToChange);
                String newLine = line.replace(stat.getDamage() + "", nextDamageValue + "");
                lore.set(lineToChange, newLine);

                String lineRanged = lore.get(lineToChangeRanged);
                String newLineRanged = lineRanged.replace(stat.getRangedDamage() + "", nextRangedDamageValue + "");
                lore.set(lineToChangeRanged, newLineRanged);
                itemMeta.setLore(lore);
            }
            itemStack = RPGItemUtils.setDamageWhenInMainHand(this.itemStack, nextDamageValue);
        } else if (type.equals(StatType.PASSIVE)) {
            StatPassive stat = (StatPassive) StatUtils.getStat(itemStack);

            int lineToChangeFire = -1;
            int lineToChangeWater = -1;
            int lineToChangeEarth = -1;
            int lineToChangeLightning = -1;
            int lineToChangeAir = -1;
            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (stat.getFire() != 0 && line.contains(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+")) {
                    lineToChangeFire = i;
                    changeCounter++;
                } else if (stat.getWater() != 0 && line.contains(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+")) {
                    lineToChangeWater = i;
                    changeCounter++;
                } else if (stat.getEarth() != 0 && line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+")) {
                    lineToChangeEarth = i;
                    changeCounter++;
                } else if (stat.getLightning() != 0 && line.contains(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+")) {
                    lineToChangeLightning = i;
                    changeCounter++;
                } else if (stat.getAir() != 0 && line.contains(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + ChatColor.GRAY + "+")) {
                    lineToChangeAir = i;
                    changeCounter++;
                }
                if (changeCounter == 5) {
                    break;
                }
            }
            if (lineToChangeFire != -1) {
                int nextFireValue = stat.getFire() - getDecreaseValue(stat.getFire());
                String line = lore.get(lineToChangeFire);
                String newLine = line.replace(stat.getFire() + "", nextFireValue + "");
                lore.set(lineToChangeFire, newLine);
            } else if (lineToChangeWater != -1) {
                int nextWaterValue = stat.getWater() - getDecreaseValue(stat.getWater());
                String line = lore.get(lineToChangeWater);
                String newLine = line.replace(stat.getWater() + "", nextWaterValue + "");
                lore.set(lineToChangeWater, newLine);
            } else if (lineToChangeEarth != -1) {
                int nextEarthValue = stat.getEarth() - getDecreaseValue(stat.getEarth());
                String line = lore.get(lineToChangeEarth);
                String newLine = line.replace(stat.getEarth() + "", nextEarthValue + "");
                lore.set(lineToChangeEarth, newLine);
            } else if (lineToChangeLightning != -1) {
                int nextLightningValue = stat.getLightning() - getDecreaseValue(stat.getLightning());
                String line = lore.get(lineToChangeLightning);
                String newLine = line.replace(stat.getLightning() + "", nextLightningValue + "");
                lore.set(lineToChangeLightning, newLine);
            } else if (lineToChangeAir != -1) {
                int nextAirValue = stat.getAir() - getDecreaseValue(stat.getAir());
                String line = lore.get(lineToChangeAir);
                String newLine = line.replace(stat.getAir() + "", nextAirValue + "");
                lore.set(lineToChangeAir, newLine);
            }
            itemMeta.setLore(lore);
        }

        this.itemStack.setItemMeta(itemMeta);
        currentEnchantLevel--;
    }

    private void startGlowing() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData >= 6) {
                customModelData++;
                itemMeta.setCustomModelData(customModelData);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }

    private void extinguish() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData >= 7) {
                customModelData--;
                itemMeta.setCustomModelData(customModelData);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }
}
