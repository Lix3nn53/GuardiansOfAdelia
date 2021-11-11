package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataContainerUtil {

    public static boolean hasString(ItemStack itemStack, String key) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            return persistentDataContainer.has(namespacedKey, PersistentDataType.STRING);
        }
        return false;
    }

    public static boolean hasInteger(ItemStack itemStack, String key) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            return persistentDataContainer.has(namespacedKey, PersistentDataType.INTEGER);
        }
        return false;
    }

    public static boolean hasFloat(ItemStack itemStack, String key) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            return persistentDataContainer.has(namespacedKey, PersistentDataType.FLOAT);
        }
        return false;
    }

    public static void putString(String key, String value, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
    }

    public static String getString(ItemStack itemStack, String key) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            return persistentDataContainer.get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }

    public static void putFloat(String key, float value, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.FLOAT, value);
        itemStack.setItemMeta(itemMeta);
    }

    public static float getFloat(ItemStack itemStack, String key) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            return persistentDataContainer.get(namespacedKey, PersistentDataType.FLOAT);
        }
        return 0;
    }

    public static void putInteger(String key, int value, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.INTEGER, value);
        itemStack.setItemMeta(itemMeta);
    }

    public static int getInteger(ItemStack itemStack, String key) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            return persistentDataContainer.get(namespacedKey, PersistentDataType.INTEGER);
        }
        return 0;
    }

    public static void removeTag(ItemStack itemStack, String key) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.remove(namespacedKey);
        itemStack.setItemMeta(itemMeta);
    }

    public static boolean hasInteger(Entity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.has(namespacedKey, PersistentDataType.INTEGER);
    }

    public static void putInteger(String key, int value, Entity entity) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.INTEGER, value);
    }

    public static int getInteger(Entity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.get(namespacedKey, PersistentDataType.INTEGER);
    }

    public static boolean hasFloat(Entity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.has(namespacedKey, PersistentDataType.FLOAT);
    }

    public static void putFloat(String key, float value, Entity entity) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.FLOAT, value);
    }

    public static float getFloat(Entity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.get(namespacedKey, PersistentDataType.FLOAT);
    }

    public static boolean hasString(Entity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.has(namespacedKey, PersistentDataType.STRING);
    }

    public static void putString(String key, String value, Entity entity) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, value);
    }

    public static String getString(Entity entity, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GuardiansOfAdelia.getInstance(), key);
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.get(namespacedKey, PersistentDataType.STRING);
    }
}
