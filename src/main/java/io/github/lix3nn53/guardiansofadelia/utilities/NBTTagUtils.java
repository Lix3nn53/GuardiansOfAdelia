package io.github.lix3nn53.guardiansofadelia.utilities;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.BukkitConverters;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static com.comphenix.protocol.wrappers.nbt.NbtFactory.fromBase;

public class NBTTagUtils {

    // Item stack trickery
    private static StructureModifier<Object> itemStackModifier;
    public ProtocolManager pm = ProtocolLibrary.getProtocolManager();

    public static ItemStack putString(String key, String value, ItemStack item) {
        if (item.getType().equals(Material.AIR)) {
            GuardiansOfAdelia.getInstance().getLogger().info("AIR INTEGER");
        }
        if (item.getType().equals(Material.TRIDENT)) {
            GuardiansOfAdelia.getInstance().getLogger().info("TRIDENT INTEGER");
        }
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.put(key, value);
        setItemTag(ret, tag);
        return ret;
    }

    public static boolean hasTag(ItemStack item, String key) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        return tag.containsKey(key);
    }

    public static String getString(ItemStack item, String key) {
        if (hasTag(item, key)) {
            ItemStack ret = item.clone();
            ret = MinecraftReflection.getBukkitItemStack(ret);
            NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
            return tag.getString(key);
        }
        return null;
    }

    public static ItemStack putDouble(String key, Double value, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.put(key, value);
        setItemTag(ret, tag);
        return ret;
    }

    public static double getDouble(ItemStack item, String key) {
        if (hasTag(item, key)) {
            ItemStack ret = item.clone();
            ret = MinecraftReflection.getBukkitItemStack(ret);
            NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
            return tag.getDouble(key);
        }
        return 0.0;
    }

    public static ItemStack putInteger(String key, int i, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        ItemStack bukkitItemStack = MinecraftReflection.getBukkitItemStack(item);
        NbtCompound tag = (NbtCompound) fromItemTag(bukkitItemStack);
        tag.put(key, i);
        setItemTag(ret, tag);
        return ret;
    }

    public static Integer getInteger(ItemStack item, String key) {
        if (hasTag(item, key)) {
            ItemStack ret = item.clone();
            ret = MinecraftReflection.getBukkitItemStack(ret);
            NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
            return tag.getInteger(key);
        }
        return 0;
    }

    public static ItemStack removeTag(String key, ItemStack item) {
        ItemStack ret = item.clone();
        ret = MinecraftReflection.getBukkitItemStack(ret);
        NbtCompound tag = (NbtCompound) fromItemTag(MinecraftReflection.getBukkitItemStack(item));
        tag.remove(key);
        setItemTag(ret, tag);
        return ret;
    }

    /**
     * Construct a wrapper for an NBT tag stored (in memory) in an item stack. This is where
     * auxillary data such as enchanting, name and lore is stored. It doesn't include the items
     * material, damage value or count.
     * <p>
     * The item stack must be a wrapper for a CraftItemStack. Use
     * {@link MinecraftReflection#getBukkitItemStack(Object)} if not.
     *
     * @param stack - the item stack.
     * @return A wrapper for its NBT tag.
     */
    public static NbtWrapper<?> fromItemTag(ItemStack stack) {
        checkItemStack(stack);

        StructureModifier<NbtBase<?>> modifier = getStackModifier(stack);
        NbtBase<?> result = modifier.read(0);

        // Create the tag if it doesn't exist
        if (result == null) {
            result = NbtFactory.ofCompound("tag");
            modifier.write(0, result);
        }
        return fromBase(result);
    }

    /**
     * Ensure that the given stack can store arbitrary NBT information.
     *
     * @param stack - the stack to check.
     */
    private static void checkItemStack(ItemStack stack) {
        if (stack == null)
            throw new IllegalArgumentException("Stack cannot be NULL.");
        if (!MinecraftReflection.isCraftItemStack(stack))
            throw new IllegalArgumentException("Stack must be a CraftItemStack.");
        if (stack.getType() == Material.AIR)
            throw new IllegalArgumentException("ItemStacks representing air cannot store NMS information.");
    }

    /**
     * Retrieve a structure modifier that automatically marshalls between NBT wrappers and their NMS counterpart.
     *
     * @param stack - the stack that will store the NBT compound.
     * @return The structure modifier.
     */
    private static StructureModifier<NbtBase<?>> getStackModifier(ItemStack stack) {
        Object nmsStack = MinecraftReflection.getMinecraftItemStack(stack);

        if (itemStackModifier == null) {
            itemStackModifier = new StructureModifier<Object>(nmsStack.getClass(), Object.class, false);
        }

        // Use the first and best NBT tag
        return itemStackModifier.
                withTarget(nmsStack).
                withType(MinecraftReflection.getNBTBaseClass(),
                        BukkitConverters.getNbtConverter());
    }

    /**
     * Set the NBT compound tag of a given item stack.
     * <p>
     * The item stack must be a wrapper for a CraftItemStack. Use
     * {@link MinecraftReflection#getBukkitItemStack(Object)} if not.
     *
     * @param stack    - the item stack, cannot be air.
     * @param compound - the new NBT compound, or NULL to remove it.
     * @throws IllegalArgumentException If the stack is not a CraftItemStack, or it represents air.
     */
    public static void setItemTag(ItemStack stack, NbtCompound compound) {
        checkItemStack(stack);

        StructureModifier<NbtBase<?>> modifier = getStackModifier(stack);
        modifier.write(0, compound);
    }
}
