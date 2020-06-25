package io.github.lix3nn53.guardiansofadelia.utilities;

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
        net.minecraft.server.v1_16_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", NBTTagString.a("generic.attack_damage"));
        damage.set("Name", NBTTagString.a("generic.attack_damage"));
        damage.set("Amount", NBTTagInt.a(attack));
        damage.set("Operation", NBTTagInt.a(0));
        damage.set("UUIDLeast", NBTTagInt.a(5));
        damage.set("UUIDMost", NBTTagInt.a(16));
        damage.set("Slot", NBTTagString.a("mainhand"));

        Iterator it = modifiers.iterator();
        while (it.hasNext()) {
            NBTTagCompound nbtTagCompound = (NBTTagCompound) it.next();
            if (nbtTagCompound.getString("AttributeName").equals("generic.attack_damage")) {
                it.remove();
            }
        }

        modifiers.add(damage);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }

    public static ItemStack resetArmor(ItemStack item) {
        net.minecraft.server.v1_16_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound toughness = new NBTTagCompound();
        toughness.set("AttributeName", NBTTagString.a("generic.armor_toughness"));
        toughness.set("Name", NBTTagString.a("generic.armor_toughness"));
        toughness.set("Amount", NBTTagInt.a(0));
        toughness.set("Operation", NBTTagInt.a(0));
        toughness.set("UUIDLeast", NBTTagInt.a(894654));
        toughness.set("UUIDMost", NBTTagInt.a(2872));
        modifiers.add(toughness);
        NBTTagCompound armor = new NBTTagCompound();
        armor.set("AttributeName", NBTTagString.a("generic.armor"));
        armor.set("Name", NBTTagString.a("generic.armor"));
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
        speed.set("AttributeName", NBTTagString.a("generic.attack_speed"));
        speed.set("Name", NBTTagString.a("generic.attack_speed"));
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
