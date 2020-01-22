package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.Items.Ingredient;

public enum GatheringType {
    WOODCUTTING,
    HARVESTING_FLOWER,
    FISHING,
    MINING_ORE,
    MINING_JEWELRY,
    HUNTING;

    public Ingredient getGatheredIngredient() {
        double random = Math.random();
        switch (this) {
            case WOODCUTTING:
                if (random < 0.4) {
                    return Ingredient.WOODCUTTING_LOG;
                } else {
                    return Ingredient.WOODCUTTING_PLANK;
                }
            case HARVESTING_FLOWER:
                if (random < 0.25) {
                    return Ingredient.HARVESTING_ROSE;
                } else if (random < 0.5) {
                    return Ingredient.HARVESTING_STRING;
                } else if (random < 0.65) {
                    return Ingredient.HARVESTING_CHERRY;
                } else if (random < 0.8) {
                    return Ingredient.HARVESTING_SILK;
                } else if (random < 0.9) {
                    return Ingredient.HARVESTING_PLUM_FLOWER;
                } else if (random < 1D) {
                    return Ingredient.HARVESTING_SOFT_SILK;
                }
            case FISHING:
                if (random < 0.4) {
                    return Ingredient.FISHING_COD;
                } else {
                    return Ingredient.FISHING_SALMON;
                }
            case MINING_ORE:
                if (random < 0.25) {
                    return Ingredient.MINING_ORE_COPPER;
                } else if (random < 0.55) {
                    return Ingredient.MINING_ORE_IRON;
                } else if (random < 0.75) {
                    return Ingredient.MINING_ORE_STEEL;
                } else if (random < 0.9) {
                    return Ingredient.MINING_ORE_DIAMOND;
                } else if (random < 1D) {
                    return Ingredient.MINING_ORE_TITANIUM;
                }
            case MINING_JEWELRY:
                if (random < 0.25) {
                    return Ingredient.MINING_JEWEL_GOLD_DUST;
                } else if (random < 0.55) {
                    return Ingredient.MINING_JEWEL_JADE;
                } else if (random < 0.75) {
                    return Ingredient.MINING_JEWEL_AMETHYST;
                } else if (random < 0.9) {
                    return Ingredient.MINING_JEWEL_SAPPHIRE;
                } else if (random < 1D) {
                    return Ingredient.MINING_JEWEL_RUBY;
                }
            case HUNTING:
                if (random < 0.4) {
                    return Ingredient.HUNTING_LEATHER_WORN;
                } else if (random < 0.7) {
                    return Ingredient.HUNTING_BEEF;
                } else {
                    return Ingredient.HUNTING_LEATHER_HEAVY;
                }
        }
        return Ingredient.WOODCUTTING_PLANK;
    }
}

