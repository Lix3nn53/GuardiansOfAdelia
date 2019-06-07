package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.Items.list.Ingredient;
import org.bukkit.inventory.ItemStack;

public enum GatheringType {
    WOODCUTTING,
    HARVESTING_FLOWER,
    FISHING,
    MINING_ORE,
    MINING_JEWELRY,
    HUNTING;

    public ItemStack getGatheredIngredient(int amount) {
        double random = Math.random();
        switch (this) {
            case WOODCUTTING:
                if (random < 0.7) {
                    return Ingredient.WOODCUTTING_LOG.getItemStack(amount);
                } else {
                    return Ingredient.WOODCUTTING_PLANK.getItemStack(amount);
                }
            case HARVESTING_FLOWER:
                if (random < 0.25) {
                    return Ingredient.HARVESTING_ROSE.getItemStack(amount);
                } else if (random < 0.5) {
                    return Ingredient.HARVESTING_STRING.getItemStack(amount);
                } else if (random < 0.65) {
                    return Ingredient.HARVESTING_CHERRY.getItemStack(amount);
                } else if (random < 0.8) {
                    return Ingredient.HARVESTING_SILK.getItemStack(amount);
                } else if (random < 0.9) {
                    return Ingredient.HARVESTING_PLUM_FLOWER.getItemStack(amount);
                } else if (random < 1D) {
                    return Ingredient.HARVESTING_SOFT_SILK.getItemStack(amount);
                }
            case FISHING:
                if (random < 0.7) {
                    return Ingredient.FISHING_COD.getItemStack(amount);
                } else {
                    return Ingredient.FISHING_SALMON.getItemStack(amount);
                }
            case MINING_ORE:
                if (random < 0.25) {
                    return Ingredient.MINING_ORE_COPPER.getItemStack(amount);
                } else if (random < 0.55) {
                    return Ingredient.MINING_ORE_IRON.getItemStack(amount);
                } else if (random < 0.75) {
                    return Ingredient.MINING_ORE_STEEL.getItemStack(amount);
                } else if (random < 0.9) {
                    return Ingredient.MINING_ORE_DIAMOND.getItemStack(amount);
                } else if (random < 1D) {
                    return Ingredient.MINING_ORE_TITANIUM.getItemStack(amount);
                }
            case MINING_JEWELRY:
                if (random < 0.25) {
                    return Ingredient.MINING_JEWEL_GOLD_DUST.getItemStack(amount);
                } else if (random < 0.55) {
                    return Ingredient.MINING_JEWEL_JADE.getItemStack(amount);
                } else if (random < 0.75) {
                    return Ingredient.MINING_JEWEL_AMETHYST.getItemStack(amount);
                } else if (random < 0.9) {
                    return Ingredient.MINING_JEWEL_SAPPHIRE.getItemStack(amount);
                } else if (random < 1D) {
                    return Ingredient.MINING_JEWEL_RUBY.getItemStack(amount);
                }
            case HUNTING:
                if (random < 0.4) {
                    return Ingredient.HUNTING_LEATHER_WORN.getItemStack(amount);
                } if (random < 0.7) {
                    return Ingredient.HUNTING_BEEF.getItemStack(amount);
                } else {
                    return Ingredient.HUNTING_LEATHER_HEAVY.getItemStack(amount);
                }
        }
        return Ingredient.WOODCUTTING_PLANK.getItemStack(amount);
    }
}

