package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetExperienceManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Egg implements RPGGear {

    private final ItemStack itemStack;

    public Egg(String petKey, ItemTier tier, Material material, int customModelData, int reqLevel, int petLevel, String gearSetStr) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(petKey);

        String name = mythicMob.getDisplayName().get();
        if (gearSetStr != null && !gearSetStr.equals("")) {
            name = tier.getTierColor() + gearSetStr + " " + name;
        }
        int health = PetManager.getHealth(petKey, petLevel);
        GuardiansOfAdelia.getInstance().getLogger().info("egg health: " + health);
        int damage = PetManager.getDamage(petKey, petLevel);
        GuardiansOfAdelia.getInstance().getLogger().info("egg damage: " + damage);

        int petExp = 0;
        for (int i = 1; i < petLevel; i++) {
            petExp += PetExperienceManager.getNextExperienceTarget(i);
        }

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Egg");
        lore.add("");

        /*if (damage > 0) {
            lore.add(ChatColor.YELLOW + "Type: " + ChatColor.GRAY + "Companion");
        } else {
            lore.add(ChatColor.YELLOW + "Type: " + ChatColor.GRAY + "Mount");
        }*/

        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + reqLevel);
        lore.add("");
        lore.add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + petLevel);
        lore.add(ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GRAY + petExp + " / " + PetExperienceManager.getNextExperienceTarget(petLevel));
        lore.add("");
        lore.add(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + health);

        if (damage > 0) {
            lore.add(ChatColor.RED + "✦ Element Damage: " + ChatColor.GRAY + damage);
        } else {
            double movementSpeed = PetManager.getMovementSpeed(petKey, petLevel);
            lore.add(ChatColor.AQUA + "⇨ Speed: " + ChatColor.GRAY + movementSpeed);
        }

        this.itemStack = new ItemStack(material);
        PersistentDataContainerUtil.putInteger("reqLevel", reqLevel, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("petCode", petKey, this.itemStack);
        PersistentDataContainerUtil.putInteger("petExp", petExp, this.itemStack);
        PersistentDataContainerUtil.putInteger("petCurrentHealth", health, this.itemStack);

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(customModelData);

        this.itemStack.setItemMeta(itemMeta);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

}
