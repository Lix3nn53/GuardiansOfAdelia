package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.Mount;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetExperienceManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PetMount implements RPGGear {

    private final ItemTier tier;
    private final String itemTag;
    private final int level;
    private ItemStack itemStack;
    private RPGGearType gearType = RPGGearType.EGG_MOUNT;

    public PetMount(Mount mount, ItemTier tier, String itemTag, Material material, int customModelData, int reqLevel, int petBaseHealth, double petBaseSpeed, double petBaseJump) {
        String name = tier.getTierColor() + mount.getName();
        if (itemTag != null && !itemTag.equals("")) {
            name = tier.getTierColor() + itemTag + " " + mount.getName();
        }
        int mountHealth = PetManager.getMountHealth(1, petBaseHealth);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "Gear Type: " + gearType.getDisplayName());
        lore.add("");
        lore.add(ChatColor.YELLOW + "Type: " + ChatColor.GRAY + "Mount");
        lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + reqLevel);
        lore.add("");
        lore.add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + 1);
        lore.add(ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GRAY + "0 / " + PetExperienceManager.getNextExperienceTarget(1));
        lore.add("");
        lore.add(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + mountHealth);
        lore.add(ChatColor.AQUA + "⇨ Speed: " + ChatColor.GRAY + petBaseSpeed);
        lore.add(ChatColor.YELLOW + "⇪ Jump: " + ChatColor.GRAY + petBaseJump);

        this.itemStack = new ItemStack(material);
        PersistentDataContainerUtil.putInteger("reqLevel", reqLevel, this.itemStack);
        PersistentDataContainerUtil.putString("petCode", mount.toString(), this.itemStack);
        PersistentDataContainerUtil.putInteger("petExp", 0, this.itemStack);
        PersistentDataContainerUtil.putInteger("petCurrentHealth", mountHealth, this.itemStack);
        PersistentDataContainerUtil.putInteger("petBaseHealth", petBaseHealth, this.itemStack);
        PersistentDataContainerUtil.putDouble("petBaseSpeed", petBaseSpeed, this.itemStack);
        PersistentDataContainerUtil.putDouble("petBaseJump", petBaseJump, this.itemStack);

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(customModelData);
        this.itemStack.setItemMeta(itemMeta);

        this.tier = tier;
        this.itemTag = itemTag;
        this.level = reqLevel;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public RPGGearType getGearType() {
        return gearType;
    }

    @Override
    public ItemTier getTier() {
        return tier;
    }

    @Override
    public String getItemTag() {
        return itemTag;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
