package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class RPGItemUtils {

    /*public static ItemStack setDamageWhenInMainHand(ItemStack item, int attack) {
        net.minecraft.server.v1_16_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", new NBTTagString("generic.attack_damage"));
        damage.set("Name", new NBTTagString("generic.attack_damage"));
        damage.set("Amount", new NBTTagInt(attack));
        damage.set("Operation", new NBTTagInt(0));
        damage.set("UUIDLeast", new NBTTagInt(894654));
        damage.set("UUIDMost", new NBTTagInt(2872));
        damage.set("Slot", new NBTTagString("mainhand"));
        modifiers.add(damage);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }*/

    public static ItemStack clearThenSetDamageWhenInMainHand(ItemStack item, int attack) {
        GuardiansOfAdelia.getInstance().getLogger().info("clearThenSetDamageWhenInMainHand");
        net.minecraft.server.v1_16_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        GuardiansOfAdelia.getInstance().getLogger().info("1");
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        GuardiansOfAdelia.getInstance().getLogger().info("2");
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        GuardiansOfAdelia.getInstance().getLogger().info("3");
        NBTTagCompound damage = new NBTTagCompound();
        GuardiansOfAdelia.getInstance().getLogger().info("4");
        damage.set("AttributeName", NBTTagString.a("minecraft:generic.attack_damage"));
        GuardiansOfAdelia.getInstance().getLogger().info("5");
        damage.set("Name", NBTTagString.a("minecraft:generic.attack_damage"));
        GuardiansOfAdelia.getInstance().getLogger().info("6");
        damage.set("Amount", NBTTagInt.a(attack));
        GuardiansOfAdelia.getInstance().getLogger().info("7");
        damage.set("Operation", NBTTagInt.a(0));
        GuardiansOfAdelia.getInstance().getLogger().info("8");
        damage.set("UUIDLeast", NBTTagInt.a(5));
        GuardiansOfAdelia.getInstance().getLogger().info("9");
        damage.set("UUIDMost", NBTTagInt.a(16));
        GuardiansOfAdelia.getInstance().getLogger().info("10");
        damage.set("Slot", NBTTagString.a("mainhand"));
        GuardiansOfAdelia.getInstance().getLogger().info("11");

        Iterator it = modifiers.iterator();
        while (it.hasNext()) {
            NBTTagCompound nbtTagCompound = (NBTTagCompound) it.next();
            if (nbtTagCompound.getString("AttributeName").equals("minecraft:generic.attack_damage")) {
                it.remove();
            }
        }
        GuardiansOfAdelia.getInstance().getLogger().info("12");

        modifiers.add(damage);
        GuardiansOfAdelia.getInstance().getLogger().info("13");
        compound.set("AttributeModifiers", modifiers);
        GuardiansOfAdelia.getInstance().getLogger().info("14");
        nmsStack.setTag(compound);
        GuardiansOfAdelia.getInstance().getLogger().info("15");
        item = CraftItemStack.asBukkitCopy(nmsStack);
        GuardiansOfAdelia.getInstance().getLogger().info("16");
        return item;
    }

    public static ItemStack resetArmor(ItemStack item) {
        GuardiansOfAdelia.getInstance().getLogger().info("resetArmor");
        net.minecraft.server.v1_16_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound toughness = new NBTTagCompound();
        toughness.set("AttributeName", NBTTagString.a("minecraft:generic.armor_toughness"));
        toughness.set("Name", NBTTagString.a("minecraft:generic.armor_toughness"));
        toughness.set("Amount", NBTTagInt.a(0));
        toughness.set("Operation", NBTTagInt.a(0));
        toughness.set("UUIDLeast", NBTTagInt.a(894654));
        toughness.set("UUIDMost", NBTTagInt.a(2872));
        modifiers.add(toughness);
        NBTTagCompound armor = new NBTTagCompound();
        armor.set("AttributeName", NBTTagString.a("minecraft:generic.armor"));
        armor.set("Name", NBTTagString.a("minecraft:generic.armor"));
        armor.set("Amount", NBTTagInt.a(0));
        armor.set("Operation", NBTTagInt.a(0));
        armor.set("UUIDLeast", NBTTagInt.a(894654));
        armor.set("UUIDMost", NBTTagInt.a(2872));
        modifiers.add(armor);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }

    public static ItemStack setAttackSpeed(ItemStack item, double attackSpeed) {
        net.minecraft.server.v1_16_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        NBTTagCompound speed = new NBTTagCompound();
        speed.set("AttributeName", NBTTagString.a("minecraft:generic.attack_speed"));
        speed.set("Name", NBTTagString.a("minecraft:generic.attack_speed"));
        speed.set("Amount", NBTTagDouble.a(attackSpeed));
        speed.set("Operation", NBTTagInt.a(0));
        speed.set("UUIDLeast", NBTTagInt.a(894654));
        speed.set("UUIDMost", NBTTagInt.a(2872));
        speed.set("Slot", NBTTagString.a("mainhand"));
        modifiers.add(speed);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }

}
