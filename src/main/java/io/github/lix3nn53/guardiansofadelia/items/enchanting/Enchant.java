package io.github.lix3nn53.guardiansofadelia.items.enchanting;

import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearWeapon;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatOneType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Enchant {

    private final Player player;
    private final float MULTIPLIER = 1.05f;
    private final ItemStack itemStack;
    private int currentEnchantLevel;

    public Enchant(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack.clone();

        this.currentEnchantLevel = EnchantManager.getEnchantLevel(itemStack);
    }

    public boolean enchantItem() {
        float roll = (float) Math.random();
        float chance = EnchantManager.getChance(player, currentEnchantLevel);
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
            name = name + ChatPalette.BLUE_LIGHT + " [ +1 ]";
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
                statString = GearWeapon.getLoreDamagePrefix();
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            } else {
                statString = GearArmor.getLoreHealthPrefix();
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            }

            int nextValue = getNextValue(baseValue);
            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("SUCC");
                player.sendMessage("statString: " + statString);
                player.sendMessage("baseValue: " + baseValue);
                player.sendMessage("nextValue: " + nextValue);
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

            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("SUCC");
                player.sendMessage("PASSIVE");
            }

            HashMap<AttributeType, Integer> attributeTypeToLineToChange = new HashMap<>();

            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                for (AttributeType attributeType : AttributeType.values()) {
                    if (stat.getAttributeValue(attributeType) != 0) {
                        if (line.contains(attributeType.getCustomName() + ": " + ChatPalette.GRAY + "+")) {
                            attributeTypeToLineToChange.put(attributeType, i);
                            changeCounter++;
                        }
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            HashMap<AttributeType, Integer> attributeTypeToNextValues = new HashMap<>();

            for (AttributeType attributeType : AttributeType.values()) {
                int nextValue = getNextValue(stat.getAttributeValue(attributeType));
                if (attributeTypeToLineToChange.containsKey(attributeType)) {
                    Integer lineToChange = attributeTypeToLineToChange.get(attributeType);
                    String line = lore.get(lineToChange);
                    String newLine = line.replace(stat.getAttributeValue(attributeType) + "", nextValue + "");
                    lore.set(lineToChange, newLine);
                    attributeTypeToNextValues.put(attributeType, nextValue);
                }
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            for (AttributeType attributeType : AttributeType.values()) {
                if (attributeTypeToNextValues.containsKey(attributeType)) {
                    Integer nextValue = attributeTypeToNextValues.get(attributeType);
                    PersistentDataContainerUtil.putInteger(attributeType.name(), nextValue, this.itemStack);
                }
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
            name = name.replace(ChatPalette.BLUE_LIGHT + " [ ", "");
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
                statString = GearWeapon.getLoreDamagePrefix();
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            } else {
                statString = GearArmor.getLoreHealthPrefix();
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();
            }

            int nextValue = getPreviousValue(baseValue);
            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("FAIL");
                player.sendMessage("statString: " + statString);
                player.sendMessage("baseValue: " + baseValue);
                player.sendMessage("nextValue: " + nextValue);
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

            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("SUCC");
                player.sendMessage("PASSIVE");
            }

            HashMap<AttributeType, Integer> attributeTypeToLineToChange = new HashMap<>();

            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                for (AttributeType attributeType : AttributeType.values()) {
                    if (stat.getAttributeValue(attributeType) != 0) {
                        if (line.contains(attributeType.getCustomName() + ": " + ChatPalette.GRAY + "+")) {
                            attributeTypeToLineToChange.put(attributeType, i);
                            changeCounter++;
                        }
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            HashMap<AttributeType, Integer> attributeTypeToNextValues = new HashMap<>();

            for (AttributeType attributeType : AttributeType.values()) {
                int nextValue = getPreviousValue(stat.getAttributeValue(attributeType));
                if (attributeTypeToLineToChange.containsKey(attributeType)) {
                    Integer lineToChange = attributeTypeToLineToChange.get(attributeType);
                    String line = lore.get(lineToChange);
                    String newLine = line.replace(stat.getAttributeValue(attributeType) + "", nextValue + "");
                    lore.set(lineToChange, newLine);
                    attributeTypeToNextValues.put(attributeType, nextValue);
                }
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            for (AttributeType attributeType : AttributeType.values()) {
                if (attributeTypeToNextValues.containsKey(attributeType)) {
                    Integer nextValue = attributeTypeToNextValues.get(attributeType);
                    PersistentDataContainerUtil.putInteger(attributeType.name(), nextValue, this.itemStack);
                }
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
            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("new customModelData: " + customModelData);
            }
        }
    }

    private void extinguish(ItemMeta itemMeta) {
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            customModelData--;
            itemMeta.setCustomModelData(customModelData);
            itemStack.setItemMeta(itemMeta);
            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("new customModelData: " + customModelData);
            }
        }
    }
}
