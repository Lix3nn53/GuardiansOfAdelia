package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.jobs.JobType;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Ingredients {

    public static ItemStack getIngredient(GatheringType gatheringType, int ingredientLevel, int amount) {
        ItemStack item = null;
        if (gatheringType.equals(GatheringType.HUNTING)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.LEATHER, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Worn Leather");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.RABBIT_HIDE, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Heavy Leather");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                item.setItemMeta(im);
            }
        } else if (gatheringType.equals(GatheringType.FISHING)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.SUGAR, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Anchovy");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.NETHER_BRICKS, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Clownfish");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            }
        } else if (gatheringType.equals(GatheringType.HARVESTING_FLOWER)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.PRISMARINE_SHARD, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Rose");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.RABBIT_FOOT, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Cherry");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 3) {
                item = new ItemStack(Material.PRISMARINE_CRYSTALS, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Plum Flower");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            }
        } else if (gatheringType.equals(GatheringType.HARVESTING_GRASS)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.STRING, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "String");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.GHAST_TEAR, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Silk");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 3) {
                item = new ItemStack(Material.MAGMA_CREAM, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Soft Silk");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());

                }});
                item.setItemMeta(im);
            }
        } else if (gatheringType.equals(GatheringType.MINING_ORE)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.BONE, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Copper Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.CHORUS_FRUIT, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Iron Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 3) {
                item = new ItemStack(Material.IRON_NUGGET, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Steel Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 4) {
                item = new ItemStack(Material.FERMENTED_SPIDER_EYE, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Diamond Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 5) {
                item = new ItemStack(Material.NETHER_STAR, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Titanium Mine");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            }
        } else if (gatheringType.equals(GatheringType.MINING_JEWELRY)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.GLOWSTONE_DUST, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Gold Dust");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.EMERALD, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Jade Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 2");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 3) {
                item = new ItemStack(Material.SPIDER_EYE, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Amethyst Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 4) {
                item = new ItemStack(Material.POPPED_CHORUS_FRUIT, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Sapphire Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 4");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 5) {
                item = new ItemStack(Material.BLAZE_ROD, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Ruby Stone");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 5");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ALCHEMIST.getName());

                }});
                item.setItemMeta(im);
            }
        } else if (gatheringType.equals(GatheringType.WOODCUTTING)) {
            if (ingredientLevel == 1) {
                item = new ItemStack(Material.CYAN_DYE, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Wooden Plank");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 1");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            } else if (ingredientLevel == 2) {
                item = new ItemStack(Material.GRAY_DYE, amount);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW + "Wood Log");
                im.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material Level: 3");
                    add("");
                    add(ChatColor.GREEN + "✔ " + JobType.WEAPONSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.ARMORSMITH.getName());
                    add(ChatColor.GREEN + "✔ " + JobType.JEWELLER.getName());

                }});
                item.setItemMeta(im);
            }
        }
        return item;
    }

    public static List<ItemStack> getReqIngredients(ItemStack itemStack) {
        List<ItemStack> reqIngredients = new ArrayList<>();

        Material material = itemStack.getType();
        GearLevel gearLevel = GearLevel.getGearLevelOfItem(itemStack);

        if (material.equals(Material.DIAMOND_LEGGINGS) ||
                material.equals(Material.GOLDEN_LEGGINGS) ||
                material.equals(Material.IRON_LEGGINGS) ||
                material.equals(Material.CHAINMAIL_LEGGINGS) ||
                material.equals(Material.LEATHER_LEGGINGS) ||
                material.equals(Material.DIAMOND_BOOTS) ||
                material.equals(Material.GOLDEN_BOOTS) ||
                material.equals(Material.IRON_BOOTS) ||
                material.equals(Material.CHAINMAIL_BOOTS) ||
                material.equals(Material.LEATHER_BOOTS) ||
                material.equals(Material.DIAMOND_HELMET) ||
                material.equals(Material.GOLDEN_HELMET) ||
                material.equals(Material.IRON_HELMET) ||
                material.equals(Material.CHAINMAIL_HELMET) ||
                material.equals(Material.LEATHER_HELMET) ||
                material.equals(Material.DIAMOND_CHESTPLATE) ||
                material.equals(Material.GOLDEN_CHESTPLATE) ||
                material.equals(Material.IRON_CHESTPLATE) ||
                material.equals(Material.CHAINMAIL_CHESTPLATE) ||
                material.equals(Material.LEATHER_CHESTPLATE) ||
                material.equals(Material.IRON_SWORD)) {
            String materialString = itemStack.getType().toString();

            //amount for shields and chestplates
            List<Integer> amountList = new ArrayList<>();
            switch (gearLevel) {
                case ONE:
                    amountList.add(4);
                    amountList.add(6);
                case TWO:
                    amountList.add(8);
                    amountList.add(8);
                case THREE:
                    amountList.add(12);
                    amountList.add(12);
                case FOUR:
                    amountList.add(8);
                    amountList.add(24);
                    amountList.add(12);
                case FIVE:
                    amountList.add(12);
                    amountList.add(24);
                    amountList.add(12);
                case SIX:
                    amountList.add(8);
                    amountList.add(24);
                    amountList.add(12);
                case SEVEN:
                    amountList.add(8);
                    amountList.add(32);
                    amountList.add(38);
                case EIGHT:
                    amountList.add(12);
                    amountList.add(32);
                    amountList.add(18);
                case NINE:
                    amountList.add(12);
                    amountList.add(64);
                    amountList.add(32);
            }

            //decrease amount for leggings, helmets and boots
            if (materialString.contains("LEGGINGS")) {
                List<Integer> temp = new ArrayList<>();
                for (int i : amountList) {
                    temp.add((int) (i * 0.65));
                }
                amountList = temp;
            } else if (materialString.contains("HELMET") || materialString.contains("BOOTS")) {
                List<Integer> temp = new ArrayList<>();
                for (int i : amountList) {
                    temp.add((int) (i * 0.4));
                }
                amountList = temp;
            }

            if (materialString.contains("DIAMOND") || materialString.contains("SWORD") || materialString.contains("IRON")) {
                switch (gearLevel) {
                    case ONE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, amountList.get(1)));
                    case TWO:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, amountList.get(1)));
                    case THREE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, amountList.get(1)));
                    case FOUR:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, amountList.get(2)));
                    case FIVE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, amountList.get(2)));
                    case SIX:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, amountList.get(2)));
                    case SEVEN:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, amountList.get(2)));
                    case EIGHT:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, amountList.get(2)));
                    case NINE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, amountList.get(2)));
                }
            } else { //gold, chainmail, leather
                switch (gearLevel) {
                    case ONE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, amountList.get(1)));
                    case TWO:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, amountList.get(1)));
                    case THREE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, amountList.get(1)));
                    case FOUR:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, amountList.get(2)));
                    case FIVE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 2, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, amountList.get(2)));
                    case SIX:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 2, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, amountList.get(2)));
                    case SEVEN:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 2, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, amountList.get(2)));
                    case EIGHT:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 3, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, amountList.get(2)));
                    case NINE:
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 3, amountList.get(0)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, amountList.get(1)));
                        reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, amountList.get(2)));
                }
            }
        } else if (material.equals(Material.COAL)) {
            //hp pot
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 2));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 2));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 4));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 6));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 8));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 8));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 8));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 16));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 12));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 16));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 12));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 16));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 16));
            }
        } else if (material.equals(Material.GLISTERING_MELON_SLICE)) {
            //mana pot
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 1));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 1));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 2));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 2));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 2));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 3));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 3));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 4));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 4));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 4));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 6));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 6));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 8));
            }
        } else if (material.equals(Material.WHEAT)) {
            //purple pot
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 3));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 3));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 6));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 9));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 9));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 12));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 12));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 12));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 18));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 18));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 36));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 24));
            }
        } else if (material.equals(Material.BOWL)) {
            //regen pot
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 3));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 3));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 6));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 9));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 9));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 1, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 12));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 2, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 12));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 12));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 3, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 18));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 18));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 4, 36));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_FLOWER, 5, 24));
            }
        } else if (material.equals(Material.DIAMOND_AXE) || material.equals(Material.DIAMOND_PICKAXE) ||
                material.equals(Material.DIAMOND_SWORD) || material.equals(Material.DIAMOND_HOE)) {
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 6));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 8));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 12));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 16));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 12));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 12));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 12));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 28));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 18));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 18));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 48));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 64));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 32));
            }
        } else if (material.equals(Material.BOW) || material.equals(Material.CROSSBOW)) {
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, 2));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, 4));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 1, 8));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 16));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 2, 8));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 2, 8));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 2, 12));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 28));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 10));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 3, 16));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 22));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 3, 20));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 48));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 52));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.HARVESTING_GRASS, 3, 24));
            }
        } else if (material.equals(Material.TRIDENT) || material.equals(Material.DIAMOND_SHOVEL)) {
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 2));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 4));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 8));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 16));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 1, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 8));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 8));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 18));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 6));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 12));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 28));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 10));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 16));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 22));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 20));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 48));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 52));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 20));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 24));
            }
        } else if (material.equals(Material.SHEARS)) {
            //jewels
            switch (gearLevel) {
                case ONE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 4));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 6));
                case TWO:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 8));
                case THREE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 1, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 12));
                case FOUR:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 1, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 12));
                case FIVE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.WOODCUTTING, 2, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 2, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 12));
                case SIX:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 2, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 24));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 12));
                case SEVEN:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 3, 8));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 3, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 18));
                case EIGHT:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 4, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 32));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 18));
                case NINE:
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_ORE, 5, 12));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 4, 64));
                    reqIngredients.add(Ingredients.getIngredient(GatheringType.MINING_JEWELRY, 5, 32));
            }
        }

        return reqIngredients;
    }
}
