package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.jobs.JobType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum Ingredient {
    HUNTING_LEATHER_WORN,
    HUNTING_LEATHER_HEAVY,
    HUNTING_BEEF,
    FISHING_COD,
    FISHING_SALMON,
    HARVESTING_STRING,
    HARVESTING_SILK,
    HARVESTING_SOFT_SILK,
    HARVESTING_ROSE,
    HARVESTING_CHERRY,
    HARVESTING_PLUM_FLOWER,
    MINING_ORE_COPPER,
    MINING_ORE_IRON,
    MINING_ORE_STEEL,
    MINING_ORE_DIAMOND,
    MINING_ORE_TITANIUM,
    MINING_JEWEL_GOLD_DUST,
    MINING_JEWEL_JADE,
    MINING_JEWEL_AMETHYST,
    MINING_JEWEL_SAPPHIRE,
    MINING_JEWEL_RUBY,
    WOODCUTTING_PLANK,
    WOODCUTTING_LOG;

    public ItemStack getItemStack(int amount) {
        ItemStack itemStack = null;
        switch (this) {
            case HUNTING_LEATHER_WORN:
                itemStack = new ItemStack(Material.LEATHER, amount);
                ItemMeta im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Worn Leather");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HUNTING_LEATHER_HEAVY:
                itemStack = new ItemStack(Material.RABBIT_HIDE, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Heavy Leather");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HUNTING_BEEF:
                itemStack = new ItemStack(Material.BEEF, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Raw Beef");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case FISHING_COD:
                itemStack = new ItemStack(Material.SUGAR, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Raw Cod");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case FISHING_SALMON:
                itemStack = new ItemStack(Material.NETHER_BRICKS, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Raw Salmon");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HARVESTING_STRING:
                itemStack = new ItemStack(Material.STRING, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "String");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HARVESTING_SILK:
                itemStack = new ItemStack(Material.GHAST_TEAR, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Silk");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HARVESTING_SOFT_SILK:
                itemStack = new ItemStack(Material.MAGMA_CREAM, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Soft Silk");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HARVESTING_ROSE:
                itemStack = new ItemStack(Material.PRISMARINE_SHARD, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Rose");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HARVESTING_CHERRY:
                itemStack = new ItemStack(Material.RABBIT_FOOT, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Cherry");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case HARVESTING_PLUM_FLOWER:
                itemStack = new ItemStack(Material.PRISMARINE_CRYSTALS, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Plum Flower");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_ORE_COPPER:
                itemStack = new ItemStack(Material.BONE, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Copper Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_ORE_IRON:
                itemStack = new ItemStack(Material.CHORUS_FRUIT, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Iron Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_ORE_STEEL:
                itemStack = new ItemStack(Material.IRON_NUGGET, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Steel Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_ORE_DIAMOND:
                itemStack = new ItemStack(Material.FERMENTED_SPIDER_EYE, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Diamond Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_ORE_TITANIUM:
                itemStack = new ItemStack(Material.NETHER_STAR, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Titanium Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_JEWEL_GOLD_DUST:
                itemStack = new ItemStack(Material.GLOWSTONE_DUST, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Gold Dust");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_JEWEL_JADE:
                itemStack = new ItemStack(Material.EMERALD, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Jade Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_JEWEL_AMETHYST:
                itemStack = new ItemStack(Material.SPIDER_EYE, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Amethyst Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_JEWEL_SAPPHIRE:
                itemStack = new ItemStack(Material.POPPED_CHORUS_FRUIT, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Sapphire Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case MINING_JEWEL_RUBY:
                itemStack = new ItemStack(Material.BLAZE_ROD, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Ruby Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case WOODCUTTING_PLANK:
                itemStack = new ItemStack(Material.CYAN_DYE, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Wooden Plank");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                itemStack.setItemMeta(im);
                break;
            case WOODCUTTING_LOG:
                itemStack = new ItemStack(Material.GRAY_DYE, amount);
                im = itemStack.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Wood Log");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                itemStack.setItemMeta(im);
                break;
        }
        return itemStack;
    }

    public Material getMaterial() {
        switch (this) {
            case HUNTING_LEATHER_WORN:
                return Material.LEATHER;
            case HUNTING_LEATHER_HEAVY:
                return Material.RABBIT_HIDE;
            case HUNTING_BEEF:
                return Material.BEEF;
            case FISHING_COD:
                return Material.SUGAR;
            case FISHING_SALMON:
                return Material.NETHER_BRICKS;
            case HARVESTING_STRING:
                return Material.STRING;
            case HARVESTING_SILK:
                return Material.GHAST_TEAR;
            case HARVESTING_SOFT_SILK:
                return Material.MAGMA_CREAM;
            case HARVESTING_ROSE:
                return Material.PRISMARINE_SHARD;
            case HARVESTING_CHERRY:
                return Material.RABBIT_FOOT;
            case HARVESTING_PLUM_FLOWER:
                return Material.PRISMARINE_CRYSTALS;
            case MINING_ORE_COPPER:
                return Material.BONE;
            case MINING_ORE_IRON:
                return Material.CHORUS_FRUIT;
            case MINING_ORE_STEEL:
                return Material.IRON_NUGGET;
            case MINING_ORE_DIAMOND:
                return Material.FERMENTED_SPIDER_EYE;
            case MINING_ORE_TITANIUM:
                return Material.NETHER_STAR;
            case MINING_JEWEL_GOLD_DUST:
                return Material.GLOWSTONE_DUST;
            case MINING_JEWEL_JADE:
                return Material.EMERALD;
            case MINING_JEWEL_AMETHYST:
                return Material.SPIDER_EYE;
            case MINING_JEWEL_SAPPHIRE:
                return Material.POPPED_CHORUS_FRUIT;
            case MINING_JEWEL_RUBY:
                return Material.BLAZE_ROD;
            case WOODCUTTING_PLANK:
                return Material.CYAN_DYE;
            case WOODCUTTING_LOG:
                return Material.GRAY_DYE;
        }
        return Material.AIR;
    }
}
