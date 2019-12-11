package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.RabbitType;
import me.libraryaddict.disguise.disguisetypes.watchers.CatWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.FoxWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.RabbitWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SnowmanWatcher;
import org.bukkit.ChatColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Wolf;


public enum Companion {
    CHICKEN,
    SHEEP,
    COW,
    PIG,
    CAT_ALL_BLACK,
    CAT_BLACK,
    CAT_BRITISH_SHORTHAIR,
    CAT_CALICO,
    CAT_JELLIE,
    CAT_PERSIAN,
    CAT_RAGDOLL,
    CAT_RED,
    CAT_SIAMESE,
    CAT_TABBY,
    CAT_WHITE,
    RABBIT_BLACK,
    RABBIT_BLACK_AND_WHITE,
    RABBIT_BROWN,
    RABBIT_GOLD,
    RABBIT_SALT_AND_PEPPER,
    RABBIT_WHITE,
    CHOCOLATE,
    WOLF,
    TURTLE,
    POLAR_BEAR,
    PANDA,
    ICE_CREAM,
    FOX_RED,
    FOX_SNOW,
    VEX,
    MINI_DRAGON;

    public String getName() {
        switch (this) {
            case CHICKEN:
                return ChatColor.AQUA + "Chicken";
            case SHEEP:
                return ChatColor.WHITE + "Baby Sheep";
            case COW:
                return ChatColor.YELLOW + "Baby Cow";
            case PIG:
                return ChatColor.LIGHT_PURPLE + "Baby Pig";
            case CAT_ALL_BLACK:
                return ChatColor.GRAY + "AllBlack Cat";
            case CAT_BLACK:
                return ChatColor.GRAY + "Black Cat";
            case CAT_BRITISH_SHORTHAIR:
                return ChatColor.WHITE + "BritishShorthair Cat";
            case CAT_CALICO:
                return ChatColor.YELLOW + "Calico Cat";
            case CAT_JELLIE:
                return ChatColor.YELLOW + "Jellie Cat";
            case CAT_PERSIAN:
                return ChatColor.YELLOW + "Persian Cat";
            case CAT_RAGDOLL:
                return ChatColor.WHITE + "Ragdoll Cat";
            case CAT_RED:
                return ChatColor.GOLD + "Red Cat";
            case CAT_SIAMESE:
                return ChatColor.GRAY + "Siamese Cat";
            case CAT_TABBY:
                return ChatColor.YELLOW + "Tabby Cat";
            case CAT_WHITE:
                return ChatColor.WHITE + "White Cat";
            case RABBIT_BLACK:
                return ChatColor.GRAY + "Black Rabbit";
            case RABBIT_BLACK_AND_WHITE:
                return ChatColor.WHITE + "BlackAndWhite Rabbit";
            case RABBIT_BROWN:
                return ChatColor.YELLOW + "Brown Rabbit";
            case RABBIT_GOLD:
                return ChatColor.GOLD + "Gold Rabbit";
            case RABBIT_SALT_AND_PEPPER:
                return ChatColor.YELLOW + "SaltAndPepper Rabbit";
            case RABBIT_WHITE:
                return ChatColor.WHITE + "White Rabbit";
            case CHOCOLATE:
                return ChatColor.YELLOW + "Baby Chocolate";
            case WOLF:
                return ChatColor.WHITE + "Wolf";
            case TURTLE:
                return ChatColor.GREEN + "Turtle";
            case POLAR_BEAR:
                return ChatColor.AQUA + "Baby PolarBear";
            case PANDA:
                return ChatColor.GREEN + "Baby Panda";
            case ICE_CREAM:
                return ChatColor.LIGHT_PURPLE + "Ice Cream";
            case FOX_RED:
                return ChatColor.GOLD + "Red Fox";
            case FOX_SNOW:
                return ChatColor.AQUA + "Snow Fox";
            case VEX:
                return ChatColor.AQUA + "Vex";
            case MINI_DRAGON:
                return ChatColor.RED + "Baby Dragon";
        }
        return "NULL_COMPANION";
    }

    public void disguise(Wolf wolf) {
        switch (this) {
            case CHICKEN:
                MobDisguise disguise = new MobDisguise(DisguiseType.CHICKEN, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case SHEEP:
                disguise = new MobDisguise(DisguiseType.SHEEP, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case COW:
                disguise = new MobDisguise(DisguiseType.COW, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case PIG:
                disguise = new MobDisguise(DisguiseType.PIG, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_ALL_BLACK:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                CatWatcher catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.ALL_BLACK);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_BLACK:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.BLACK);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_BRITISH_SHORTHAIR:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.BRITISH_SHORTHAIR);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_CALICO:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.CALICO);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_JELLIE:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.JELLIE);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_PERSIAN:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.PERSIAN);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_RAGDOLL:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.RAGDOLL);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_RED:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.RED);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_SIAMESE:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.SIAMESE);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_TABBY:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.TABBY);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CAT_WHITE:
                disguise = new MobDisguise(DisguiseType.CAT, false);
                catWatcher = (CatWatcher) disguise.getWatcher();
                catWatcher.setType(Cat.Type.WHITE);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case RABBIT_BLACK:
                disguise = new MobDisguise(DisguiseType.RABBIT, false);
                RabbitWatcher rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.BLACK);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case RABBIT_BLACK_AND_WHITE:
                disguise = new MobDisguise(DisguiseType.RABBIT, false);
                rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.PATCHES);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case RABBIT_BROWN:
                disguise = new MobDisguise(DisguiseType.RABBIT, false);
                rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.BROWN);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case RABBIT_GOLD:
                disguise = new MobDisguise(DisguiseType.RABBIT, false);
                rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.GOLD);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case RABBIT_SALT_AND_PEPPER:
                disguise = new MobDisguise(DisguiseType.RABBIT, false);
                rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.PEPPER);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case RABBIT_WHITE:
                disguise = new MobDisguise(DisguiseType.RABBIT, false);
                rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.WHITE);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case CHOCOLATE:
                disguise = new MobDisguise(DisguiseType.MUSHROOM_COW, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case WOLF:
                break;
            case TURTLE:
                disguise = new MobDisguise(DisguiseType.TURTLE, true);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case POLAR_BEAR:
                disguise = new MobDisguise(DisguiseType.POLAR_BEAR, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case PANDA:
                disguise = new MobDisguise(DisguiseType.PANDA, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case ICE_CREAM:
                disguise = new MobDisguise(DisguiseType.SNOWMAN, true);
                disguise = disguise.setReplaceSounds(true);
                SnowmanWatcher snowmanWatcher = (SnowmanWatcher) disguise.getWatcher();
                snowmanWatcher.setDerp(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case FOX_RED:
                disguise = new MobDisguise(DisguiseType.FOX, true);
                FoxWatcher foxWatcher = (FoxWatcher) disguise.getWatcher();
                foxWatcher.setType(Fox.Type.RED);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case FOX_SNOW:
                disguise = new MobDisguise(DisguiseType.FOX, true);
                foxWatcher = (FoxWatcher) disguise.getWatcher();
                foxWatcher.setType(Fox.Type.SNOW);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case VEX:
                disguise = new MobDisguise(DisguiseType.VEX, true);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case MINI_DRAGON:
                disguise = new MobDisguise(DisguiseType.BAT, true);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
        }
    }

    public int getEggCustomModelData() {
        switch (this) {
            case CHICKEN:
                return 10000009;
            case SHEEP:
                return 10000017;
            case COW:
                return 10000013;
            case PIG:
                return 10000005;
            case CAT_ALL_BLACK:
                return 10000006;
            case CAT_BLACK:
                return 10000006;
            case CAT_BRITISH_SHORTHAIR:
                return 10000006;
            case CAT_CALICO:
                return 10000006;
            case CAT_JELLIE:
                return 10000006;
            case CAT_PERSIAN:
                return 10000006;
            case CAT_RAGDOLL:
                return 10000006;
            case CAT_RED:
                return 10000006;
            case CAT_SIAMESE:
                return 10000006;
            case CAT_TABBY:
                return 10000006;
            case CAT_WHITE:
                return 10000006;
            case RABBIT_BLACK:
                return 10000018;
            case RABBIT_BLACK_AND_WHITE:
                return 10000018;
            case RABBIT_BROWN:
                return 10000018;
            case RABBIT_GOLD:
                return 10000018;
            case RABBIT_SALT_AND_PEPPER:
                return 10000018;
            case RABBIT_WHITE:
                return 10000018;
            case CHOCOLATE:
                return 10000013;
            case WOLF:
                return 10000016;
            case TURTLE:
                return 10000001;
            case POLAR_BEAR:
                return 10000004;
            case PANDA:
                return 10000014;
            case ICE_CREAM:
                return 10000015;
            case FOX_RED:
                return 10000011;
            case FOX_SNOW:
                return 10000004;
            case VEX:
                return 10000010;
            case MINI_DRAGON:
                return 10000003;
        }
        return 10000001;
    }

    public ItemTier getItemTier() {
        switch (this) {
            case CHICKEN:
                return ItemTier.COMMON;
            case SHEEP:
                return ItemTier.COMMON;
            case COW:
                return ItemTier.COMMON;
            case PIG:
                return ItemTier.COMMON;
            case CAT_ALL_BLACK:
                return ItemTier.COMMON;
            case CAT_BLACK:
                return ItemTier.COMMON;
            case CAT_BRITISH_SHORTHAIR:
                return ItemTier.COMMON;
            case CAT_CALICO:
                return ItemTier.COMMON;
            case CAT_JELLIE:
                return ItemTier.COMMON;
            case CAT_PERSIAN:
                return ItemTier.COMMON;
            case CAT_RAGDOLL:
                return ItemTier.COMMON;
            case CAT_RED:
                return ItemTier.COMMON;
            case CAT_SIAMESE:
                return ItemTier.COMMON;
            case CAT_TABBY:
                return ItemTier.COMMON;
            case CAT_WHITE:
                return ItemTier.COMMON;
            case RABBIT_BLACK:
                return ItemTier.COMMON;
            case RABBIT_BLACK_AND_WHITE:
                return ItemTier.COMMON;
            case RABBIT_BROWN:
                return ItemTier.COMMON;
            case RABBIT_GOLD:
                return ItemTier.COMMON;
            case RABBIT_SALT_AND_PEPPER:
                return ItemTier.COMMON;
            case RABBIT_WHITE:
                return ItemTier.COMMON;
            case CHOCOLATE:
                return ItemTier.COMMON;
            case WOLF:
                return ItemTier.COMMON;
            case TURTLE:
                return ItemTier.COMMON;
            case POLAR_BEAR:
                return ItemTier.COMMON;
            case PANDA:
                return ItemTier.COMMON;
            case ICE_CREAM:
                return ItemTier.COMMON;
            case FOX_RED:
                return ItemTier.COMMON;
            case FOX_SNOW:
                return ItemTier.COMMON;
            case VEX:
                return ItemTier.COMMON;
            case MINI_DRAGON:
                return ItemTier.COMMON;
        }
        return ItemTier.COMMON;
    }
}
