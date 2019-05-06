package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.*;
import me.libraryaddict.disguise.disguisetypes.watchers.LlamaWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.OcelotWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.RabbitWatcher;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;


public class PetList {

    public static Wolf getCompanion(Player owner, String petCode, int currentHealth, int petLevel) {
        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);

        String petName = "NULL";
        int petMaxHP = 200;
        int damage = 10;

        switch (petCode) {
            case "cat1":
                petName = ChatColor.YELLOW + "Baby Ocelot";
                break;
            case "cat2":
                petName = ChatColor.GRAY + "Baby Panther";
                break;
            case "cat3":
                petName = ChatColor.GOLD + "Baby Tiger";
                break;
            case "cat4":
                petName = ChatColor.AQUA + "Baby Cat";
                break;
            case "rabbit1":
                damage = 20;
                petMaxHP = 420;
                petName = ChatColor.GRAY + "Black Rabbit";
                break;
            case "rabbit2":
                damage = 20;
                petMaxHP = 420;
                petName = ChatColor.YELLOW + "Brown Rabbit";
                break;
            case "rabbit3":
                damage = 20;
                petMaxHP = 420;
                petName = ChatColor.YELLOW + "Yellow Rabbit";
                break;
            case "rabbit4":
                damage = 20;
                petMaxHP = 420;
                petName = ChatColor.WHITE + "White Rabbit";
                break;
            case "bear":
                damage = 120;
                petMaxHP = 2400;
                petName = ChatColor.AQUA + "Baby PolarBear";
                break;
            case "sheep":
                damage = 34;
                petMaxHP = 680;
                petName = ChatColor.YELLOW + "Baby Sheep";
                break;
            case "cow":
                damage = 34;
                petMaxHP = 680;
                petName = ChatColor.YELLOW + "Baby Cow";
                break;
            case "chocolate":
                damage = 120;
                petMaxHP = 2400;
                petName = ChatColor.GOLD + "Baby Chocolate";
                break;
            case "llama1":
                damage = 90;
                petMaxHP = 1780;
                petName = ChatColor.YELLOW + "Brown Llama";
                break;
            case "llama2":
                damage = 90;
                petMaxHP = 1780;
                petName = ChatColor.WHITE + "White Llama";
                break;
            case "guard1":
                damage = 150;
                petMaxHP = 3000;
                petName = ChatColor.LIGHT_PURPLE + "Warrior";
                break;
            case "guard2":
                damage = 150;
                petMaxHP = 3000;
                petName = ChatColor.AQUA + "Warrior";
                break;
            case "dragon":
                damage = 150;
                petMaxHP = 3000;
                petName = ChatColor.RED + "Baby Dragon";
                break;
            case "vex":
                damage = 150;
                petMaxHP = 3000;
                petName = ChatColor.AQUA + "Vex";
                break;
            case "chicken":
                damage = 50;
                petMaxHP = 1020;
                petName = ChatColor.YELLOW + "Chicken";
                break;
            case "iceCream":
                damage = 200;
                petMaxHP = 4000;
                petName = ChatColor.LIGHT_PURPLE + "IceCream";
                break;
            case "pig":
                damage = 50;
                petMaxHP = 1020;
                petName = ChatColor.LIGHT_PURPLE + "Piggy";
                break;
            case "fox":
                damage = 90;
                petMaxHP = 1780;
                petName = ChatColor.YELLOW + "Fox";
                break;
        }

        if (currentHealth <= 0) {
            currentHealth = (int) (petMaxHP * 0.4 + 0.5);
        }

        int bonus = 0;
        if (petLevel > 1) {
            for (int i = 1; i < petLevel; i++) {
                int bonusTemp = (int) ((damage * 0.1) + 0.5);
                if (bonusTemp <= 1) {
                    bonusTemp = 1;
                }
                bonus += bonusTemp;
            }
        }
        damage += bonus;

        petName += " " + ChatColor.WHITE + "<" + owner.getName() + ">" + ChatColor.GREEN + " " + currentHealth + "/" + petMaxHP + "❤";
        Wolf wolf = (Wolf) EntityUtils.create(spawnLoc, petName, petMaxHP, EntityType.WOLF);
        wolf.setAdult();
        wolf.setTamed(true);
        wolf.setOwner(owner);
        wolf.setHealth(currentHealth);
        wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.75D);
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        //disguise the wolf
        switch (petCode) {
            case "cat1": {
                MobDisguise disguise = new MobDisguise(DisguiseType.OCELOT, false);
                OcelotWatcher ocelotWatcher = (OcelotWatcher) disguise.getWatcher();
                ocelotWatcher.setType(Ocelot.Type.WILD_OCELOT);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "cat2": {
                MobDisguise disguise = new MobDisguise(DisguiseType.OCELOT, false);
                OcelotWatcher ocelotWatcher = (OcelotWatcher) disguise.getWatcher();
                ocelotWatcher.setType(Ocelot.Type.BLACK_CAT);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "cat3": {
                MobDisguise disguise = new MobDisguise(DisguiseType.OCELOT, false);
                OcelotWatcher ocelotWatcher = (OcelotWatcher) disguise.getWatcher();
                ocelotWatcher.setType(Ocelot.Type.RED_CAT);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "cat4": {
                MobDisguise disguise = new MobDisguise(DisguiseType.OCELOT, false);
                OcelotWatcher ocelotWatcher = (OcelotWatcher) disguise.getWatcher();
                ocelotWatcher.setType(Ocelot.Type.SIAMESE_CAT);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "rabbit1": {
                MobDisguise disguise = new MobDisguise(DisguiseType.RABBIT, false);
                RabbitWatcher rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.BLACK);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "rabbit2": {
                MobDisguise disguise = new MobDisguise(DisguiseType.RABBIT, false);
                RabbitWatcher rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.BROWN);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "rabbit3": {
                MobDisguise disguise = new MobDisguise(DisguiseType.RABBIT, false);
                RabbitWatcher rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.GOLD);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "rabbit4": {
                MobDisguise disguise = new MobDisguise(DisguiseType.RABBIT, false);
                RabbitWatcher rabbitWatcher = (RabbitWatcher) disguise.getWatcher();
                rabbitWatcher.setType(RabbitType.WHITE);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "bear": {
                MobDisguise disguise = new MobDisguise(DisguiseType.POLAR_BEAR, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "sheep": {
                MobDisguise disguise = new MobDisguise(DisguiseType.SHEEP, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "cow": {
                MobDisguise disguise = new MobDisguise(DisguiseType.COW, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "chocolate": {
                MobDisguise disguise = new MobDisguise(DisguiseType.MUSHROOM_COW, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "llama1": {
                MobDisguise disguise = new MobDisguise(DisguiseType.LLAMA, false);
                LlamaWatcher llamaWatcher = (LlamaWatcher) disguise.getWatcher();
                llamaWatcher.setCarpet(AnimalColor.ORANGE);
                llamaWatcher.setColor(Llama.Color.BROWN);
                disguise.setReplaceSounds(true);
                break;
            }
            case "llama2": {
                MobDisguise disguise = new MobDisguise(DisguiseType.LLAMA, false);
                LlamaWatcher llamaWatcher = (LlamaWatcher) disguise.getWatcher();
                llamaWatcher.setCarpet(AnimalColor.LIME);
                llamaWatcher.setColor(Llama.Color.CREAMY);
                disguise.setReplaceSounds(true);
                break;
            }
            case "guard1": {
                PlayerDisguise disguise = new PlayerDisguise(petName);
                PlayerWatcher pWatcher = disguise.getWatcher();
                ItemStack weapon = Weapons.getWeapon(RPGClass.ROGUE, 8, ItemTier.COMMON, "", 0, 0, 0);
                pWatcher.setItemInMainHand(weapon);
                pWatcher.setItemInOffHand(weapon);
                pWatcher.setSkin("JJesi");
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "guard2": {
                PlayerDisguise disguise = new PlayerDisguise(petName);
                PlayerWatcher pWatcher = disguise.getWatcher();
                ItemStack weapon = Weapons.getWeapon(RPGClass.ROGUE, 4, ItemTier.COMMON, "", 0, 0, 0);
                pWatcher.setItemInMainHand(weapon);
                pWatcher.setSkin("Stegoleopluradon");
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "dragon": {
                MobDisguise disguise = new MobDisguise(DisguiseType.BAT, true);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "vex": {
                MobDisguise disguise = new MobDisguise(DisguiseType.VEX, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "chicken": {
                MobDisguise disguise = new MobDisguise(DisguiseType.CHICKEN, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "iceCream": {
                MobDisguise disguise = new MobDisguise(DisguiseType.SNOWMAN, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
            case "pig": {
                MobDisguise disguise = new MobDisguise(DisguiseType.PIG, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            }
        }

        return wolf;
    }

    public static Vehicle getMount(Player owner, String petCode, int currentHealth) {
        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);

        String petName = "NULL";
        int petMaxHP = 240;
        EntityType type = EntityType.HORSE;
        Horse.Color color = Horse.Color.BLACK;
        ItemStack armor = new ItemStack(Material.AIR);

        double movementSpeed = 0.5;
        double jumpStrength = 0.5;

        switch (petCode) {
            case "creamHorse":
                petMaxHP = 240;
                petName = ChatColor.YELLOW + "Cream Horse";
                color = Horse.Color.CREAMY;
                break;
            case "brownHorse":
                petMaxHP = 240;
                petName = ChatColor.YELLOW + "Brown Horse";
                color = Horse.Color.DARK_BROWN;
                break;
            case "vizz":
                petMaxHP = 240;
                petName = ChatColor.GOLD + "Vizz";
                color = Horse.Color.GRAY;
                break;
            case "unicorn":
                petMaxHP = 3000;
                petName = ChatColor.LIGHT_PURPLE + "Rainbow Horse";
                armor = new ItemStack(Material.DIAMOND_HORSE_ARMOR);
                break;
            case "lava":
                petMaxHP = 3000;
                petName = ChatColor.RED + "Lava Horse";
                armor = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
                break;
            case "knight":
                petMaxHP = 3000;
                petName = ChatColor.WHITE + "Knight Horse";
                armor = new ItemStack(Material.IRON_HORSE_ARMOR);
                break;
            case "darkKnight":
                petMaxHP = 3000;
                petName = ChatColor.GRAY + "DarkKnight Horse";
                color = Horse.Color.WHITE;
                break;
            case "zombie":
                petMaxHP = 530;
                petName = ChatColor.GRAY + "Zombie Horse";
                color = Horse.Color.CHESTNUT;
                break;
            case "skeleton":
                petMaxHP = 3000;
                petName = ChatColor.RED + "Skeleton Horse";
                color = Horse.Color.BLACK;
                break;
            case "dino":
                petMaxHP = 2000;
                type = EntityType.DONKEY;
                petName = ChatColor.DARK_GREEN + "Dino";
                break;
            case "car":
                petMaxHP = 2000;
                type = EntityType.MULE;
                petName = ChatColor.RED + "Car";
                break;
        }

        if (currentHealth <= 0) {
            currentHealth = (int) (petMaxHP * 0.4 + 0.5);
        }

        petName += " " + ChatColor.WHITE + "<" + owner.getName() + ">" + ChatColor.GREEN + " " + currentHealth + "/" + petMaxHP + "❤";
        if (type.equals(EntityType.HORSE)) {
            Horse horse = (Horse) EntityUtils.create(spawnLoc, petName, petMaxHP, type);
            horse.setTamed(true);
            horse.setAdult();
            horse.setStyle(Horse.Style.NONE);
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            horse.setColor(color);
            horse.setOwner(owner);
            horse.setHealth(currentHealth);
            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
            horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jumpStrength);
            if (!armor.getType().equals(Material.ANVIL)) {
                horse.getInventory().setArmor(armor);
            }
            return horse;
        } else if (type.equals(EntityType.DONKEY)) {
            Donkey donkey = (Donkey) EntityUtils.create(spawnLoc, petName, petMaxHP, type);
            donkey.setTamed(true);
            donkey.setAdult();
            donkey.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            donkey.setOwner(owner);
            donkey.setHealth(currentHealth);
            donkey.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
            donkey.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jumpStrength);
            return donkey;
        } else {
            Mule mule = (Mule) EntityUtils.create(spawnLoc, petName, petMaxHP, type);
            mule.setTamed(true);
            mule.setAdult();
            mule.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            mule.setOwner(owner);
            mule.setHealth(currentHealth);
            mule.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
            mule.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jumpStrength);
            return mule;
        }
    }
}
