package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.events.MyPlayerJoinEvent;
import io.github.lix3nn53.guardiansofadelia.events.MyPlayerQuitEvent;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.menu.main.settings.GuiLanguage;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.transportation.TeleportationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GuiSettings extends GuiGeneric {

    public GuiSettings(GuardianData guardianData) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK + Translation.t(guardianData, "menu.settings.name"), 0);

        ItemStack language = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = language.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Language");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select server language.");
        itemMeta.setLore(lore);
        language.setItemMeta(itemMeta);

        ItemStack characterSelect = new ItemStack(Material.BARRIER);
        itemMeta = characterSelect.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Character selection");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Go back to character selection.");
        lore.add("");
        lore.add("Caution!");
        lore.add("- Your bazaar will be destroyed");
        lore.add("- You will leave your party");
        itemMeta.setLore(lore);
        characterSelect.setItemMeta(itemMeta);

        GuiHelper.form27Small(this, new ItemStack[]{language, characterSelect}, "Main Menu");
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        RPGCharacter rpgCharacter;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            } else {
                return;
            }
        } else {
            return;
        }

        int slot = event.getSlot();
        if (slot == 0) {
            GuiMain gui = new GuiMain(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(0) == slot) {
            GuiLanguage gui = new GuiLanguage(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(1) == slot) {
            if (guardianData.isFreeToAct()) {
                player.closeInventory();

                String destination = "Character selection";
                int stepCount = 5;
                guardianData.setTeleporting(true);
                final float startPosX = (float) player.getLocation().getX();
                final float startPosY = (float) player.getLocation().getY();
                final float startPosZ = (float) player.getLocation().getZ();

                ArmorStand hologramTop = new Hologram(player.getLocation().add(0.0, 2.6, 0.0),
                        ChatPalette.BLUE + "< " + ChatPalette.YELLOW + destination + ChatPalette.BLUE + " >").getArmorStand();
                ArmorStand hologramBottom = new Hologram(player.getLocation().add(0.0, 2.3, 0.0),
                        ChatPalette.BLUE_LIGHT + "Teleporting.. " + stepCount).getArmorStand();
                player.sendTitle(ChatPalette.BLUE + "Teleporting..", ChatPalette.BLUE_LIGHT.toString() + stepCount, 5, 20, 5);

                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int ticksRun;

                    @Override
                    public void run() {
                        ticksRun++;

                        boolean doesDivide = ticksRun % 4 == 0;
                        if (doesDivide) {
                            int currentStep = ticksRun / 4;

                            float differenceX = Math.abs(startPosX - (float) player.getLocation().getX());
                            float differenceY = Math.abs(startPosY - (float) player.getLocation().getY());
                            float differenceZ = Math.abs(startPosZ - (float) player.getLocation().getZ());

                            if (currentStep < stepCount) {
                                if (TeleportationUtils.isTeleportCanceled(differenceX, differenceY, differenceZ)) {
                                    TeleportationUtils.cancelTeleportation(this, guardianData, hologramTop, hologramBottom, player);
                                } else {
                                    TeleportationUtils.nextStep(player, hologramTop, hologramBottom, destination, stepCount - currentStep);
                                }
                            } else {
                                if (TeleportationUtils.isTeleportCanceled(differenceX, differenceY, differenceZ)) {
                                    TeleportationUtils.cancelTeleportation(this, guardianData, hologramTop, hologramBottom, player);
                                } else {
                                    TeleportationUtils.finishTeleportation(this, guardianData, hologramTop, hologramBottom,
                                            player, CharacterSelectionScreenManager.characterSelectionCenter, destination, null, 0);

                                    MyPlayerQuitEvent.onPlayerBackToCharacterSelection(player);
                                    MyPlayerJoinEvent.onPlayerBackToCharacterSelection(player);
                                }
                            }
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 5L);
            }
        }
    }
}
