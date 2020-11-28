package io.github.lix3nn53.guardiansofadelia.rewards.chest;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum LootChestTier {
    TIER_ONE,
    TIER_TWO,
    TIER_THREE,
    TIER_FOUR;

    public List<ItemStack> getLoot() {
        List<ItemStack> list = new ArrayList<>();

        Random rand = new Random();
        double random = Math.random();

        //CONSUMABLES
        double consumableChance = 0.7;
        if (random < consumableChance) {
            int minSkillLevel = getMinSkillLevel();
            int maxSkillLevel = getMaxSkillLevel();
            int minUses = 4;
            int maxUses = 10;

            int amountRandom = rand.nextInt(3);

            Consumable[] values = Consumable.values();
            for (int i = 0; i < amountRandom; i++) {
                int enumRandom = rand.nextInt(values.length);
                int skillLevelRandom = rand.nextInt(maxSkillLevel - minSkillLevel) + minSkillLevel;

                int usesRandom = rand.nextInt(maxUses - minUses) + minUses;
                ItemStack itemStack = values[enumRandom].getItemStack(skillLevelRandom, usesRandom);
                list.add(itemStack);
            }
        }

        //ENCHANT_STONES
        double enchStoneChance = 0.4;
        random = Math.random();
        if (random < enchStoneChance) {
            int minTierIndex = getMinTierIndex();
            int maxTierIndex = getMaxTierIndex();
            int minUses = 1;
            int maxUses = 3;

            int amountRandom = rand.nextInt(3);

            EnchantStone[] values = EnchantStone.values();
            for (int i = 0; i < amountRandom; i++) {
                int tierRandom = 0;
                if (maxTierIndex != 0) {
                    tierRandom = rand.nextInt(maxTierIndex - minTierIndex) + minTierIndex;
                }

                int usesRandom = rand.nextInt(maxUses - minUses) + minUses;
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
