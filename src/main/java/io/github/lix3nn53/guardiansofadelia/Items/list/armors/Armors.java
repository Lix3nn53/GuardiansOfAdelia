package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Armors {

    private final static HashMap<String, List<ArmorItemTemplate>> rpgClassToArmorTemplate = new HashMap<>();

    public static ItemStack getArmor(ArmorType armorType, RPGClass rpgClass, int gearLevel, int itemIndex, ItemTier tier, String itemTag, int minStatValue,
                                     int maxStatValue, int minNumberOfStats) {

        String key = rpgClass.toString() + armorType.toString() + gearLevel;
        List<ArmorItemTemplate> templates = rpgClassToArmorTemplate.get(key);

        if (templates == null || templates.size() <= itemIndex) {
            GuardiansOfAdelia.getInstance().getLogger().info("NULL ARMOR");
            GuardiansOfAdelia.getInstance().getLogger().info("key: " + key);
            GuardiansOfAdelia.getInstance().getLogger().info("itemIndex: " + itemIndex);
            return null;
        }

        ArmorItemTemplate template = templates.get(itemIndex);

        String name = template.getName();
        Material material = template.getMaterial();
        int level = template.getLevel();
        int health = template.getHealth();
        int defense = template.getDefense();
        int magicDefense = template.getMagicDefense();

        final GearArmor gearArmor = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        if (material.toString().contains("LEATHER_") && !rpgClass.equals(RPGClass.NO_CLASS)) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) gearArmor.getItemStack().getItemMeta();
            if (rpgClass.equals(RPGClass.ARCHER)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 188, 0));
            } else if (rpgClass.equals(RPGClass.KNIGHT)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 184, 230));
            } else if (rpgClass.equals(RPGClass.MAGE)) {
                leatherArmorMeta.setColor(Color.fromRGB(153, 0, 115));
            } else if (rpgClass.equals(RPGClass.ROGUE)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 0, 26));
            } else if (rpgClass.equals(RPGClass.PALADIN)) {
                leatherArmorMeta.setColor(Color.fromRGB(230, 230, 0));
            } else if (rpgClass.equals(RPGClass.WARRIOR)) {
                leatherArmorMeta.setColor(Color.fromRGB(180, 0, 0));
            } else if (rpgClass.equals(RPGClass.MONK)) {
                leatherArmorMeta.setColor(Color.fromRGB(230, 140, 0));
            } else if (rpgClass.equals(RPGClass.HUNTER)) {
                leatherArmorMeta.setColor(Color.fromRGB(35, 140, 35));
            }
            gearArmor.getItemStack().setItemMeta(leatherArmorMeta);
        }

        return gearArmor.getItemStack();
    }

    public static void put(RPGClass rpgClass, ArmorType armorType, ArmorItemTemplate armorItemTemplate) {
        int gearLevel = GearLevel.getGearLevel(armorItemTemplate.getLevel());
        String key = rpgClass.toString() + armorType.toString() + gearLevel;

        List<ArmorItemTemplate> list = new ArrayList<>();
        if (rpgClassToArmorTemplate.containsKey(key)) {
            list = rpgClassToArmorTemplate.get(key);
        }
        list.add(armorItemTemplate);
        rpgClassToArmorTemplate.put(key, list);
    }
}
