package io.github.lix3nn53.guardiansofadelia.utilities;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class RPGItemUtils {

    public static ItemStack setDamageWhenInMainHand(ItemStack item, int attack) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage.set("Name", new NBTTagString("generic.attackDamage"));
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
    }

    public static ItemStack clearThenSetDamageWhenInMainHand(ItemStack item, int attack) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage.set("Name", new NBTTagString("generic.attackDamage"));
        damage.set("Amount", new NBTTagInt(attack));
        damage.set("Operation", new NBTTagInt(0));
        damage.set("UUIDLeast", new NBTTagInt(5));
        damage.set("UUIDMost", new NBTTagInt(16));
        damage.set("Slot", new NBTTagString("mainhand"));

        int size = modifiers.size();
        for (int i = 0; i < size; ++i) {
            NBTTagCompound nbtTagCompound = (NBTTagCompound) modifiers.get(i);
            if (nbtTagCompound.getString("AttributeName").equals("generic.attackDamage")) {
                //noinspection SuspiciousListRemoveInLoop
                modifiers.remove(i);
            }
        }
        modifiers.add(damage);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }

    public static ItemStack resetArmor(ItemStack item) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound toughness = new NBTTagCompound();
        toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
        toughness.set("Name", new NBTTagString("generic.armorToughness"));
        toughness.set("Amount", new NBTTagInt(0));
        toughness.set("Operation", new NBTTagInt(0));
        toughness.set("UUIDLeast", new NBTTagInt(894654));
        toughness.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(toughness);
        NBTTagCompound armor = new NBTTagCompound();
        armor.set("AttributeName", new NBTTagString("generic.armor"));
        armor.set("Name", new NBTTagString("generic.armor"));
        armor.set("Amount", new NBTTagInt(0));
        armor.set("Operation", new NBTTagInt(0));
        armor.set("UUIDLeast", new NBTTagInt(894654));
        armor.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(armor);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }

    public static ItemStack setAttackSpeed(ItemStack item, double attackSpeed) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers")
                ? compound.getList("AttributeModifiers", 10)
                : new NBTTagList();
        NBTTagCompound speed = new NBTTagCompound();
        speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        speed.set("Name", new NBTTagString("generic.attackSpeed"));
        speed.set("Amount", new NBTTagDouble(attackSpeed));
        speed.set("Operation", new NBTTagInt(0));
        speed.set("UUIDLeast", new NBTTagInt(894654));
        speed.set("UUIDMost", new NBTTagInt(2872));
        speed.set("Slot", new NBTTagString("mainhand"));
        modifiers.add(speed);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }

}
