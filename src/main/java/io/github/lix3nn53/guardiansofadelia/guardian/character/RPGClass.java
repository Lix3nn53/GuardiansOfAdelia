package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.list.*;
import org.bukkit.ChatColor;

import java.util.List;

public enum RPGClass {
    NO_CLASS,
    ARCHER,
    KNIGHT,
    MAGE,
    MONK,
    ROGUE,
    PALADIN,
    WARRIOR,
    HUNTER;

    public String getClassString() {
        ChatColor color = ChatColor.GREEN;
        String name = "Archer";
        if (this == RPGClass.KNIGHT) {
            color = ChatColor.AQUA;
            name = "Knight";
        } else if (this == RPGClass.MAGE) {
            color = ChatColor.LIGHT_PURPLE;
            name = "Mage";
        } else if (this == RPGClass.MONK) {
            color = ChatColor.GOLD;
            name = "Monk";
        } else if (this == RPGClass.ROGUE) {
            color = ChatColor.BLUE;
            name = "Rogue";
        } else if (this == RPGClass.PALADIN) {
            color = ChatColor.YELLOW;
            name = "Paladin";
        } else if (this == RPGClass.WARRIOR) {
            color = ChatColor.RED;
            name = "Warrior";
        } else if (this == RPGClass.HUNTER) {
            color = ChatColor.DARK_GREEN;
            name = "Hunter";
        }
        return color + name;
    }

    public ChatColor getClassColor() {
        ChatColor color = ChatColor.GREEN;
        if (this == RPGClass.KNIGHT) {
            color = ChatColor.AQUA;
        } else if (this == RPGClass.MAGE) {
            color = ChatColor.LIGHT_PURPLE;
        } else if (this == RPGClass.MONK) {
            color = ChatColor.GOLD;
        } else if (this == RPGClass.ROGUE) {
            color = ChatColor.BLUE;
        } else if (this == RPGClass.PALADIN) {
            color = ChatColor.YELLOW;
        } else if (this == RPGClass.WARRIOR) {
            color = ChatColor.RED;
        } else if (this == RPGClass.HUNTER) {
            color = ChatColor.DARK_GREEN;
        }
        return color;
    }

    public String getClassStringNoColor() {
        String name = "Archer";
        if (this == RPGClass.KNIGHT) {
            name = "Knight";
        } else if (this == RPGClass.MAGE) {
            name = "Mage";
        } else if (this == RPGClass.MONK) {
            name = "Monk";
        } else if (this == RPGClass.ROGUE) {
            name = "Rogue";
        } else if (this == RPGClass.PALADIN) {
            name = "Paladin";
        } else if (this == RPGClass.WARRIOR) {
            name = "Warrior";
        } else if (this == RPGClass.HUNTER) {
            name = "Hunter";
        }
        return name;
    }

    public int getClassIconCustomModelData() {
        if (this == RPGClass.KNIGHT) {
            return 9;
        } else if (this == RPGClass.MAGE) {
            return 10;
        } else if (this == RPGClass.MONK) {
            return 15;
        } else if (this == RPGClass.ROGUE) {
            return 11;
        } else if (this == RPGClass.PALADIN) {
            return 8;
        } else if (this == RPGClass.WARRIOR) {
            return 12;
        } else if (this == RPGClass.HUNTER) {
            return 16;
        }
        return 7;
    }

    public int getAttributeBonusForLevel(AttributeType attributeType, int level) {
        int tier = 1;
        if (this == RPGClass.ARCHER) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 5;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 0;
            }
        } else if (this == RPGClass.KNIGHT) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 9;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 0;
            }
        } else if (this == RPGClass.MAGE) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 5;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 0;
            }
        } else if (this == RPGClass.MONK) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 0;
            }
        } else if (this == RPGClass.ROGUE) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 5;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.PALADIN) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 7;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 2;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 0;
            }
        } else if (this == RPGClass.WARRIOR) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 7;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 0;
            }
        } else if (this == RPGClass.HUNTER) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        }

        double levelD = level;
        double tierD = tier;

        return (int) ((levelD * ((levelD * tierD) / 125)) + 0.5);
    }

    public List<Skill> getSkillSet() {
        if (this == RPGClass.KNIGHT) {
            return KnightSkills.getSet();
        } else if (this == RPGClass.MAGE) {
            return MageSkills.getSet();
        } else if (this == RPGClass.MONK) {
            return MonkSkills.getSet();
        } else if (this == RPGClass.ROGUE) {
            return RogueSkills.getSet();
        } else if (this == RPGClass.PALADIN) {
            return PaladinSkills.getSet();
        } else if (this == RPGClass.WARRIOR) {
            return WarriorSkills.getSet();
        } else if (this == RPGClass.HUNTER) {
            return HunterSkills.getSet();
        }
        return ArcherSkills.getSet();
    }

    public WeaponGearType getDefaultWeaponGearType() {
        if (this == RPGClass.KNIGHT) {
            return WeaponGearType.SWORD;
        } else if (this == RPGClass.MAGE) {
            return WeaponGearType.STAFF;
        } else if (this == RPGClass.MONK) {
            return WeaponGearType.SPEAR;
        } else if (this == RPGClass.ROGUE) {
            return WeaponGearType.DAGGER;
        } else if (this == RPGClass.PALADIN) {
            return WeaponGearType.MACE;
        } else if (this == RPGClass.WARRIOR) {
            return WeaponGearType.BATTLE_AXE;
        } else if (this == RPGClass.HUNTER) {
            return WeaponGearType.CROSSBOW;
        }

        return WeaponGearType.BOW;
    }

    public ArmorGearType getDefaultArmorGearType() {
        if (this == RPGClass.KNIGHT) {
            return ArmorGearType.HEAVY_ARMOR;
        } else if (this == RPGClass.MAGE) {
            return ArmorGearType.LIGHT_ARMOR;
        } else if (this == RPGClass.MONK) {
            return ArmorGearType.MEDIUM_ARMOR;
        } else if (this == RPGClass.ROGUE) {
            return ArmorGearType.LIGHT_ARMOR;
        } else if (this == RPGClass.PALADIN) {
            return ArmorGearType.HEAVY_ARMOR;
        } else if (this == RPGClass.WARRIOR) {
            return ArmorGearType.MEDIUM_ARMOR;
        } else if (this == RPGClass.HUNTER) {
            return ArmorGearType.LIGHT_ARMOR;
        }

        return ArmorGearType.LIGHT_ARMOR;
    }
}
