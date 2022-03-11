package io.github.lix3nn53.guardiansofadelia.rewards.chest;

import io.github.lix3nn53.guardiansofadelia.items.Consumable;
import io.github.lix3nn53.guardiansofadelia.items.enchanting.EnchantStone;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum LootChestTier {
    TIER_ONE,
    TIER_TWO,
    TIER_THREE,
    TIER_FOUR;

    private static final Random chanceRand = new Random();
    private static final Random amountRand = new Random();
    private static final Random enumRand = new Random();
    private static final Random skillLevelRand = new Random();
    private static final Random usesRand = new Random();
    private static final Random tierRand = new Random();

    public List<ItemStack> getLoot() {
        List<ItemStack> list = new ArrayList<>();

        float random = (float) chanceRand.nextDouble();

        //CONSUMABLES
        float consumableChance = 0.7f;
        if (random < consumableChance) {
            int minSkillLevel = getMinSkillLevel();
            int maxSkillLevel = getMaxSkillLevel();
            int minUses = 4;
            int maxUses = 10;

            int amountRandom = amountRand.nextInt(3);

            Consumable[] values = Consumable.values();
            for (int i = 0; i < amountRandom; i++) {
                int enumRandom = enumRand.nextInt(values.length);
                int skillLevelRandom = skillLevelRand.nextInt(maxSkillLevel - minSkillLevel) + minSkillLevel;

                int usesRandom = usesRand.nextInt(maxUses - minUses) + minUses;
                ItemStack itemStack = values[enumRandom].getItemStack(skillLevelRandom, usesRandom);
                list.add(itemStack);
            }
        }

        //ENCHANT_STONES
        float enchStoneChance = 0.4f;
        random = (float) Math.random();
        if (random < enchStoneChance) {
            int minTierIndex = getMinTierIndex();
            int maxTierIndex = getMaxTierIndex();
            int minUses = 1;
            int maxUses = 3;

            int amountRandom = amountRand.nextInt(3);

            EnchantStone[] values = EnchantStone.values();
            for (int i = 0; i < amountRandom; i++) {
                int tierRandom = 0;
                if (maxTierIndex != 0) {
                    tierRandom = tierRand.nextInt(maxTierIndex - minTierIndex) + minTierIndex;
                }

                int usesRandom = usesRand.nextInt(maxUses - minUses) + minUses;
                ItemStack itemStack = values[tierRandom].getItemStack(usesRandom);
                list.add(itemStack);
            }
        }

        //INGREDIENTS

        if (list.isEmpty()) {
            return getLoot();
        }

        return list;
    }

    public int getMinSkillLevel() {
        switch (this) {
            case TIER_ONE:
                return 1;
            case TIER_TWO:
                return 3;
            case TIER_THREE:
                return 5;
            case TIER_FOUR:
                return 7;
        }

        return -1;
    }

    public int getMaxSkillLevel() {
        switch (this) {
            case TIER_ONE:
                return 4;
            case TIER_TWO:
                return 6;
            case TIER_THREE:
                return 8;
            case TIER_FOUR:
                return 10;
        }

        return -1;
    }

    public int getMinTierIndex() {
        switch (this) {
            case TIER_ONE:
            case TIER_TWO:
                return 0;
            case TIER_THREE:
                return 1;
            case TIER_FOUR:
                return 2;
        }

        return -1;
    }

    public int getMaxTierIndex() {
        switch (this) {
            case TIER_ONE:
                return 0;
            case TIER_TWO:
                return 1;
            case TIER_THREE:
                return 2;
            case TIER_FOUR:
                return 3;
        }

        return -1;
    }
}
