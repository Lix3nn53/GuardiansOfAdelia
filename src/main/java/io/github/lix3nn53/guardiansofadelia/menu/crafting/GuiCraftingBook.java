package io.github.lix3nn53.guardiansofadelia.menu.crafting;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetManager;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingLine;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiCraftingBook extends GuiBookGeneric {

    public GuiCraftingBook(CraftingType craftingType, int craftingLevel) {
        super(craftingType.toString() + " Crafting Level " + craftingLevel, 0);

        List<GuiPage> guiPageList = new ArrayList<>();
        guiPageList.add(new GuiPage());

        List<CraftingLine> craftingLines = CraftingManager.getCraftingTypeAndLevelToCraftingLines(craftingType, craftingLevel);

        int i = 0;
        for (CraftingLine craftingLine : craftingLines) {
            //add lines to page, if full create new page and add it to the pageList
            if (guiPageList.get(i).isEmpty()) {
                guiPageList.get(i).addLine(craftingLine);
            } else {
                guiPageList.add(new GuiPage());
                i++;
                guiPageList.get(i).addLine(craftingLine);
            }
        }

        //add pages from temporary list to book
        this.setPages(guiPageList);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
            if (rpgCharacter != null) {
                ItemStack item = clickedInventory.getItem(7);
                ItemMeta craftingGuideGlass = item.getItemMeta();
                if (craftingGuideGlass.hasDisplayName()) {
                    String displayName = craftingGuideGlass.getDisplayName();
                    if (displayName.equals(ChatPalette.GOLD + "Crafting Guide")) {
                        int slot = event.getSlot();

                        if (slot == 8 || slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                            List<ItemStack> ingredients = new ArrayList<>();

                            for (int i = slot - 8; i < slot - 1; i++) {
                                ItemStack ingredient = clickedInventory.getItem(i);
                                if (ingredient != null) {
                                    if (!ingredient.getType().equals(Material.AIR)) {
                                        ingredients.add(ingredient);
                                    }
                                }
                            }

                            boolean hasIngredients = true;
                            int jobExpToGive = 0;
                            for (ItemStack ingredient : ingredients) {
                                jobExpToGive += ingredient.getAmount();
                                boolean inventoryContains = InventoryUtils.inventoryContains(player.getInventory(), ingredient.getType(), ingredient.getAmount());
                                if (!inventoryContains) {
                                    hasIngredients = false;
                                    break;
                                }
                            }

                            if (hasIngredients) {
                                ItemStack current = event.getCurrentItem();
                                ItemStack clone = current.clone();

                                String title = event.getView().getTitle();
                                String[] split = title.split(" Crafting Level ");

                                String levelStrWithPage = split[1];

                                String[] split2 = levelStrWithPage.split(" Page");

                                String levelStr = split2[0];
                                int craftingLevel = Integer.parseInt(levelStr);
                                jobExpToGive = jobExpToGive * craftingLevel;
                                GearLevel gearLevel = GearLevel.values()[craftingLevel];

                                StatUtils.addRandomPassiveStats(clone, gearLevel, ItemTier.MYSTIC);
                                GearSetManager.addRandomGearEffect(current);

                                for (ItemStack ingredient : ingredients) {
                                    InventoryUtils.removeItemFromInventory(player.getInventory(), ingredient, ingredient.getAmount());
                                }
                                InventoryUtils.giveItemToPlayer(player, clone);

                                CraftingType craftingType = CraftingType.valueOf(split[0]);

                                rpgCharacter.getCraftingStats().addExperience(player, craftingType, jobExpToGive);

                                CustomSound customSound = GoaSound.ANVIL.getCustomSound();
                                customSound.play(player.getLocation());

                                // Quest TaskCrafting
                                List<Quest> questList = rpgCharacter.getQuestList();
                                for (Quest quest : questList) {
                                    quest.progressCraftingTasks(player, craftingType, clone);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
