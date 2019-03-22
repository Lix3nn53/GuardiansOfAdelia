package io.github.lix3nn53.guardiansofadelia.utilities;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.inventory.ItemStack;

public class NBTTagUtils {

    public ProtocolManager pm = ProtocolLibrary.getProtocolManager();

    public static ItemStack putString(String key, String value, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.put(key, value);
        NbtFactory.setItemTag(ret, tag);
        return ret;
    }

    public static boolean hasTag(ItemStack item, String key) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        return tag.containsKey(key);
    }

    public static String getString(ItemStack item, String key) {
        if (hasTag(item, key)) {
            ItemStack ret = item.clone();
            ret = MinecraftReflection.getBukkitItemStack(ret);
            NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
            return tag.getString(key);
        }
        return null;
    }

    public static ItemStack putDouble(String key, Double value, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.put(key, value);
        NbtFactory.setItemTag(ret, tag);
        return ret;
    }

    public static double getDouble(ItemStack item, String key) {
        if (hasTag(item, key)) {
            ItemStack ret = item.clone();
            ret = MinecraftReflection.getBukkitItemStack(ret);
            NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
            return tag.getDouble(key);
        }
        return 0.0;
    }

    public static ItemStack putInteger(String key, int i, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.put(key, i);
        NbtFactory.setItemTag(ret, tag);
        return ret;
    }

    public static Integer getInteger(ItemStack item, String key) {
        if (hasTag(item, key)) {
            ItemStack ret = item.clone();
            ret = MinecraftReflection.getBukkitItemStack(ret);
            NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
            return tag.getInteger(key);
        }
        return 0;
    }

    public static ItemStack removeTag(String key, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.remove(key);
        NbtFactory.setItemTag(ret, tag);
        return ret;
    }

}
