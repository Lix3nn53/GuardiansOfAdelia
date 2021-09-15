package io.github.lix3nn53.guardiansofadelia.items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetData;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetExperienceManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetSkillManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Egg implements RPGGear {

    private final ItemStack itemStack;

    public Egg(String petKey, ItemTier tier, Material material, int customModelData, int reqLevel, int petLevel, String gearSetStr) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(petKey);

        String name = mythicMob.getDisplayName().get();
        if (gearSetStr != null && !gearSetStr.equals("")) {
            name = tier.getTierColor() + gearSetStr + " " + name;
        }
        PetData petData = PetSkillManager.getPetData(petKey);
        int speed = petData.getSpeed();

        int petExp = 0;
        for (int i = 1; i < petLevel; i++) {
            petExp += PetExperienceManager.getNextExperienceTarget(i);
        }

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET.toString() + ChatPalette.YELLOW + "Egg");
        lore.add("");

        /*if (damage > 0) {
            lore.add(ChatPalette.YELLOW + "Type: " + ChatPalette.GRAY + "Companion");
        } else {
            lore.add(ChatPalette.YELLOW + "Type: " + ChatPalette.GRAY + "Mount");
        }*/

        lore.add(ChatColor.RESET.toString() + ChatPalette.PURPLE + "Required Level: " + ChatPalette.GRAY + reqLevel);
        lore.add("");
        lore.add(ChatPalette.GOLD + "Level: " + ChatPalette.GRAY + petLevel);
        lore.add(ChatPalette.PURPLE_LIGHT + "Experience: " + ChatPalette.GRAY + petExp + " / " + PetExperienceManager.getNextExperienceTarget(petLevel));
        lore.add("");
        lore.add(ChatPalette.BLUE_LIGHT + "⇨ Speed: " + ChatPalette.GRAY + speed);
        lore.add("");
        HashMap<Integer, Skill> skills = petData.getSkills();
        if (skills.isEmpty()) {
            lore.add(ChatPalette.GRAY + "This pet has no skills");
        } else {
            for (int i : skills.keySet()) {
                Skill skill = skills.get(i);
                lore.add(ChatPalette.GRAY + "---- " + ChatPalette.YELLOW + "Skill at level " + ChatPalette.GOLD + i + ChatPalette.GRAY + " ----");
                List<String> description = skill.getDescription();
                lore.addAll(description);
            }
        }

        this.itemStack = new ItemStack(material);
        PersistentDataContainerUtil.putInteger("reqLevel", reqLevel, this.itemStack);
        PersistentDataContainerUtil.putString("itemTier", tier.toString(), this.itemStack);
        PersistentDataContainerUtil.putString("petCode", petKey, this.itemStack);
        PersistentDataContainerUtil.putInteger("petExp", petExp, this.itemStack);
        // PersistentDataContainerUtil.putInteger("petCurrentHealth", health, this.itemStack);

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
