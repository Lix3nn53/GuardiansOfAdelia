package io.github.lix3nn53.guardiansofadelia.items.enchanting;

import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearWeapon;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatOneType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Enchant {

    private final Player player;
    private final ItemStack itemStack;
    private final GearLevel gearLevel;
    private int currentEnchantLevel;

    public Enchant(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack.clone();

        this.currentEnchantLevel = EnchantManager.getEnchantLevel(itemStack);
        this.gearLevel = GearLevel.getGearLevel(itemStack);
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

    private void successItem() {
        if (currentEnchantLevel == 12) {
            return;
        }
        ItemMeta itemMeta = this.itemStack.getItemMeta();

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
        this.itemStack.setItemMeta(itemMeta);

        applyChange(true);

        currentEnchantLevel++;
    }

    private void failItem() {
        if (currentEnchantLevel == 0) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();

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
        this.itemStack.setItemMeta(itemMeta);

        applyChange(false);

        currentEnchantLevel--;
    }

    private void applyChange(boolean success) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        GearStatType type = StatUtils.getStatType(itemStack.getType());

        int enchantLevel = currentEnchantLevel;
        if (success) enchantLevel = currentEnchantLevel + 1;

        if (type.equals(GearStatType.ARMOR_GEAR) || type.equals(GearStatType.WEAPON_GEAR)) {
            int baseValue;
            String statString;

            int bonus;
            if (type.equals(GearStatType.WEAPON_GEAR)) {
                statString = GearWeapon.getLoreDamagePrefix();
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();

                WeaponGearType weaponGearType = WeaponGearType.fromMaterial(itemStack.getType());
                bonus = EnchantManager.getBonusWeapon(weaponGearType, gearLevel, enchantLevel);
            } else {
                statString = GearArmor.getLoreHealthPrefix();
                StatOneType stat = (StatOneType) StatUtils.getStat(itemStack);
                baseValue = stat.getValue();

                ArmorGearType armorGearType = ArmorGearType.fromMaterial(itemStack.getType());
                ArmorSlot armorSlot = ArmorSlot.getArmorSlot(itemStack.getType());
                bonus = EnchantManager.getBonusArmor(armorGearType, armorSlot, gearLevel, enchantLevel);
            }

            if (!success) bonus = -bonus;
            int nextValue = baseValue + bonus;
            if (CommandAdmin.DEBUG_MODE) {
                player.sendMessage("SUCC:" + success);
                player.sendMessage("gearLevel: " + gearLevel);
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
                if (this.currentEnchantLevel == 1 && !success) {
                    lore.set(lineToChange, statString + nextValue);
                } else {
                    String[] split = line.split("\\[\\+");
                    int oldBonus = 0;
                    int rootValue = baseValue;
                    if (split.length == 2) {
                        String oldBonusStr = split[1].substring(0, split[1].length() - 1);
                        oldBonus = Integer.parseInt(oldBonusStr);

                        String[] splitAgain = split[0].split("\\+");
                        rootValue = Integer.parseInt(splitAgain[1]);
                    }
                    lore.set(lineToChange, statString + rootValue + "[+" + (oldBonus + bonus) + "]");
                }
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
                player.sendMessage("SUCC:" + success);
                player.sendMessage("gearLevel: " + gearLevel);
                player.sendMessage("PASSIVE");
            }

            int bonus = EnchantManager.getBonusPassive(gearLevel, enchantLevel);
            if (!success) bonus = -bonus;

            // Attributes start
            HashMap<AttributeType, Integer> attributeTypeToLineToChange = new HashMap<>();
            HashMap<AttributeType, String> attributeTypeToPrefix = new HashMap<>();

            int changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                for (AttributeType attributeType : AttributeType.values()) {
                    if (stat.getAttributeValue(attributeType) != 0) {
                        String prefix = attributeType.getCustomName() + ": " + ChatPalette.GRAY + "+";
                        if (line.contains(prefix)) {
                            attributeTypeToLineToChange.put(attributeType, i);
                            attributeTypeToPrefix.put(attributeType, prefix);
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
                int baseValue = stat.getAttributeValue(attributeType);
                float decreaseOfAttribute = stat.getDecreaseOfAttribute(attributeType);
                int currentBonus = (int) (bonus * decreaseOfAttribute + 0.5);
                int nextValue = baseValue + currentBonus;
                if (attributeTypeToLineToChange.containsKey(attributeType)) {
                    Integer lineToChange = attributeTypeToLineToChange.get(attributeType);
                    String line = lore.get(lineToChange);

                    String statString = attributeTypeToPrefix.get(attributeType);
                    if (this.currentEnchantLevel == 1 && !success) {
                        lore.set(lineToChange, statString + nextValue);
                    } else {
                        String[] split = line.split("\\[\\+");
                        int oldBonus = 0;
                        int rootValue = baseValue;
                        if (split.length == 2) {
                            String oldBonusStr = split[1].substring(0, split[1].length() - 1);
                            oldBonus = Integer.parseInt(oldBonusStr);

                            String[] splitAgain = split[0].split("\\+");
                            rootValue = Integer.parseInt(splitAgain[1]);
                        }
                        lore.set(lineToChange, statString + rootValue + "[+" + (oldBonus + currentBonus) + "]");
                    }

                    attributeTypeToNextValues.put(attributeType, nextValue);
                }
            }

            for (AttributeType attributeType : AttributeType.values()) {
                if (attributeTypeToNextValues.containsKey(attributeType)) {
                    int nextValue = attributeTypeToNextValues.get(attributeType);
                    PersistentDataContainerUtil.putInteger(attributeType.name(), nextValue, this.itemStack);
                }
            }

            // Elements start
            HashMap<ElementType, Integer> elementTypeToLineToChange = new HashMap<>();
            HashMap<ElementType, String> elementTypeToPrefix = new HashMap<>();

            changeCounter = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                for (ElementType elementType : ElementType.values()) {
                    if (stat.getElementValue(elementType) != 0) {
                        String prefix = elementType.getFullName(Translation.DEFAULT_LANG) + ": " + ChatPalette.GRAY + "+";
                        if (line.contains(prefix)) {
                            elementTypeToLineToChange.put(elementType, i);
                            elementTypeToPrefix.put(elementType, prefix);
                            changeCounter++;
                        }
                    }
                }
                if (changeCounter == 5) {
                    break;
                }
            }

            HashMap<ElementType, Integer> elementTypeToNextValues = new HashMap<>();

            for (ElementType elementType : ElementType.values()) {
                int baseValue = stat.getElementValue(elementType);
                float decreaseOfAttribute = stat.getDecreaseOfElement(elementType);
                int currentBonus = (int) (bonus * decreaseOfAttribute + 0.5);
                int nextValue = baseValue + currentBonus;
                if (elementTypeToLineToChange.containsKey(elementType)) {
                    Integer lineToChange = elementTypeToLineToChange.get(elementType);
                    String line = lore.get(lineToChange);

                    String statString = elementTypeToPrefix.get(elementType);
                    if (this.currentEnchantLevel == 1 && !success) {
                        lore.set(lineToChange, statString + nextValue);
                    } else {
                        String[] split = line.split("\\[\\+");
                        int oldBonus = 0;
                        int rootValue = baseValue;
                        if (split.length == 2) {
                            String oldBonusStr = split[1].substring(0, split[1].length() - 1);
                            oldBonus = Integer.parseInt(oldBonusStr);

                            String[] splitAgain = split[0].split("\\+");
                            rootValue = Integer.parseInt(splitAgain[1]);
                        }
                        lore.set(lineToChange, statString + rootValue + "[+" + (oldBonus + currentBonus) + "]");
                    }

                    elementTypeToNextValues.put(elementType, nextValue);
                }
            }
            itemMeta.setLore(lore);
            this.itemStack.setItemMeta(itemMeta);

            for (ElementType elementType : ElementType.values()) {
                if (elementTypeToNextValues.containsKey(elementType)) {
                    int nextValue = elementTypeToNextValues.get(elementType);
                    PersistentDataContainerUtil.putInteger(elementType.name(), nextValue, this.itemStack);
                }
            }
        }
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
