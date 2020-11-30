package io.github.lix3nn53.guardiansofadelia.guardian.attribute;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class Attribute {
    private final AttributeType attributeType;
    private int invested;
    private int bonusFromHelmet;
    private int bonusFromChestplate;
    private int bonusFromLeggings;
    private int bonusFromBoots;
    private int bonusFromMainhand;
    private int bonusFromOffhand;

    private int bonusFromPassive;

    public Attribute(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public int getInvested() {
        return invested;
    }

    public void setInvested(int invested, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        this.invested = invested;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void investPoint(RPGCharacterStats rpgCharacterStats, int amount, boolean fixDisplay) {
        this.invested += amount;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void downgradePoint(RPGCharacterStats rpgCharacterStats, int amount, boolean fixDisplay) {
        this.invested -= amount;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public int getBonusFromEquipment() {
        return bonusFromHelmet + bonusFromChestplate + bonusFromLeggings + bonusFromBoots + bonusFromMainhand + bonusFromOffhand + bonusFromPassive;
    }

    public int getBonusFromLevel(int playerLevel, String playerClassStr) {
        if (RPGClassManager.hasClass(playerClassStr)) {
            RPGClass playerClass = RPGClassManager.getClass(playerClassStr);
            return playerClass.getAttributeBonusForLevel(this.attributeType, playerLevel);
        }
        return 0;
    }

    public void clearEquipment(RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        this.bonusFromHelmet = 0;
        this.bonusFromChestplate = 0;
        this.bonusFromLeggings = 0;
        this.bonusFromBoots = 0;
        this.bonusFromMainhand = 0;
        this.bonusFromOffhand = 0;

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void clearPassive(RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        this.bonusFromPassive = 0;

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void removeBonus(EquipmentSlot equipmentSlot, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        switch (equipmentSlot) {
            case HAND:
                this.bonusFromMainhand = 0;
                break;
            case OFF_HAND:
                this.bonusFromOffhand = 0;
                break;
            case FEET:
                this.bonusFromBoots = 0;
                break;
            case LEGS:
                this.bonusFromLeggings = 0;
                break;
            case CHEST:
                this.bonusFromChestplate = 0;
                break;
            case HEAD:
                this.bonusFromHelmet = 0;
                break;
        }

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void setBonus(Player player, EquipmentSlot equipmentSlot, RPGCharacterStats rpgCharacterStats, int bonus, boolean fixDisplay) {
        int bonusPointDifference = 0;

        switch (equipmentSlot) {
            case HAND:
                bonusPointDifference = bonus - this.bonusFromMainhand;
                this.bonusFromMainhand = bonus;
                break;
            case OFF_HAND:
                bonusPointDifference = bonus - this.bonusFromOffhand;
                this.bonusFromOffhand = bonus;
                break;
            case FEET:
                bonusPointDifference = bonus - this.bonusFromBoots;
                this.bonusFromBoots = bonus;
                break;
            case LEGS:
                bonusPointDifference = bonus - this.bonusFromLeggings;
                this.bonusFromLeggings = bonus;
                break;
            case CHEST:
                bonusPointDifference = bonus - this.bonusFromChestplate;
                this.bonusFromChestplate = bonus;
                break;
            case HEAD:
                bonusPointDifference = bonus - this.bonusFromHelmet;
                this.bonusFromHelmet = bonus;
                break;
        }

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
        //onBonusChangeFillEmpty(player, rpgCharacterStats, bonusPointDifference);
    }

    public void addBonusToPassive(int bonus, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        bonusFromPassive += bonus;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void removeBonusFromPassive(int remove, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        bonusFromPassive -= remove;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public double getIncrement(int playerLevel, String playerClass) {
        return (invested + getBonusFromEquipment() + getBonusFromLevel(playerLevel, playerClass)) * attributeType.getIncrementPerPoint();
    }

    private void onValueChange(RPGCharacterStats rpgCharacterStats) {
        switch (attributeType) {
            case FIRE:
                break;
            case LIGHTNING:
                break;
            case EARTH:
                rpgCharacterStats.onMaxHealthChange();
                break;
            case WATER:
                rpgCharacterStats.onCurrentManaChange();
                break;
            case WIND:
                break;
        }
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    /*public void onBonusChangeFillEmpty(Player player, RPGCharacterStats rpgCharacterStats, int bonusPointDifference) {
        if (bonusPointDifference > 0) {
            switch (attributeType) {
                case EARTH:
                    player.sendMessage("Bonus earth fix health");
                    double increment = attributeType.getIncrement();
                    double v = increment * bonusPointDifference;

                    double currentHealth = player.getHealth();
                    double maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();

                    if (currentHealth == maxHealth) break;

                    double nextHealth = currentHealth + v;

                    if (nextHealth > maxHealth) {
                        nextHealth = maxHealth;
                    }

                    player.setHealth(nextHealth);
                    break;
                case WATER:
                    player.sendMessage("Bonus water fix mana");
                    increment = attributeType.getIncrement();
                    v = increment * bonusPointDifference;

                    int currentMana = rpgCharacterStats.getCurrentMana();

                    double maxMana = rpgCharacterStats.getTotalMaxMana();

                    if (currentMana == maxMana) break;

                    double nextMana = currentMana + v;

                    if (nextMana > maxMana) {
                        nextMana = maxMana;
                    }

                    rpgCharacterStats.setCurrentMana((int) nextMana);
                    break;
            }
        }
    }*/
}
