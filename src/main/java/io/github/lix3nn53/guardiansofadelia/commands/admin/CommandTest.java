package io.github.lix3nn53.guardiansofadelia.commands.admin;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGround;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacter;
import io.github.lix3nn53.guardiansofadelia.text.font.NegativeSpace;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandTest implements CommandExecutor {

    boolean test = false;
    SkillOnGround skillOnGround;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("test")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.BLUE + "/test test");
                player.sendMessage(ChatPalette.BLUE + "/test color <code>");
                player.sendMessage(ChatPalette.BLUE + "/test hex <code>");
                player.sendMessage(ChatPalette.BLUE + "/test palette");
                player.sendMessage(ChatPalette.BLUE + "/test model portal<1-5>");
                player.sendMessage(ChatPalette.BLUE + "/test sound <code> - play custom sounds");
                player.sendMessage(ChatPalette.BLUE + "/test damage <amount> - damage self");
                player.sendMessage(ChatPalette.BLUE + "/test custom <custom> [sBefore] [sAfter] <negative> <positive> - test custom char");
            } else if (args[0].equals("test")) {
                /*CustomCharacter menuContent = CustomCharacterGui.MENU_54;
                CustomCharacter logo = CustomCharacterGui.LOGO;
                logo.setNegativeSpacesBefore(Integer.parseInt(args[1]));
                logo.setNegativeSpacesAfter(Integer.parseInt(args[2]));
                logo.setNegativeSpace(NegativeSpace.valueOf(args[3]));
                logo.setPositiveSpace(NegativeSpace.valueOf(args[4]));

                String s1 = menuContent.toString();
                String s2 = logo.toString();

                GuiGeneric guiGeneric = new GuiGeneric(54, s1 + s2 + args[3], 0);
                guiGeneric.openInventory(player);*/

                StatusEffectManager.addStatus(player, StatusEffectType.ROOT, 100);
            } else if (args[0].equals("custom")) {
                CustomCharacter chara = CustomCharacter.valueOf(args[1]);
                chara.setNegativeSpacesBefore(Integer.parseInt(args[2]));
                chara.setNegativeSpacesAfter(Integer.parseInt(args[3]));
                chara.setNegativeSpace(NegativeSpace.valueOf(args[4]));
                chara.setPositiveSpace(NegativeSpace.valueOf(args[5]));

                player.sendMessage("BEFORE" + chara + "AFTER");
                //test custom LOGO 1 20 NEGATIVE_8 POSITIVE_8
                //test custom COMPASS_EMPTY 1 1 NEGATIVE_8 POSITIVE_8
            } else if (args[0].equals("customgui")) {
                CustomCharacter menuContent = CustomCharacter.valueOf(args[1]);
                CustomCharacter logo = CustomCharacter.valueOf(args[2]);
                menuContent.setNegativeSpacesBefore(Integer.parseInt(args[3]));
                menuContent.setNegativeSpacesAfter(Integer.parseInt(args[4]));
                menuContent.setNegativeSpace(NegativeSpace.valueOf(args[5]));
                menuContent.setPositiveSpace(NegativeSpace.valueOf(args[6]));

                logo.setNegativeSpacesBefore(Integer.parseInt(args[7]));
                logo.setNegativeSpacesAfter(Integer.parseInt(args[8]));
                logo.setNegativeSpace(NegativeSpace.valueOf(args[9]));
                logo.setPositiveSpace(NegativeSpace.valueOf(args[10]));

                String s1 = menuContent.toString();
                String s2 = logo.toString();

                GuiGeneric guiGeneric = new GuiGeneric(54, s1 + s2 + ChatColor.DARK_GRAY + "TEST TITLE", 0);
                guiGeneric.openInventory(player);
                //test customgui MENU_54 LOGO 1 41 NEGATIVE_4 POSITIVE_4 -5 37 NEGATIVE_4 POSITIVE_4
                //test customgui MENU_54_FLAT LOGO 5 41 NEGATIVE_4 POSITIVE_4 -5 37 NEGATIVE_4 POSITIVE_4
            } else if (args[0].equals("palette")) {
                StringBuilder msg = new StringBuilder();
                for (ChatPalette chatPalette : ChatPalette.values()) {
                    msg.append(chatPalette).append("⬛");
                }

                player.sendMessage(msg.toString());
            } else if (args[0].equals("color")) {
                ChatPalette chatPalette = ChatPalette.valueOf(args[1].toUpperCase());

                player.sendMessage(chatPalette + "TEST COLOR");
            } else if (args[0].equals("hex")) {
                ChatColor chatPalette = ChatColor.of(args[1].toUpperCase());

                player.sendMessage(chatPalette + "TEST COLOR");
            } else if (args[0].equals("sound")) {
                if (args.length == 2) {
                    GoaSound goaSound = GoaSound.valueOf(args[1]);
                    CustomSound customSound = goaSound.getCustomSound();
                    customSound.play(player);
                }
            } else if (args[0].equals("model")) {
                if (args.length == 2) {
                    Location location = player.getLocation();
                    ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (args[1].equals("portal1")) {
                        itemMeta.setCustomModelData(6);
                    } else if (args[1].equals("portal2")) {
                        itemMeta.setCustomModelData(7);
                    } else if (args[1].equals("portal3")) {
                        itemMeta.setCustomModelData(8);
                    } else if (args[1].equals("portal4")) {
                        itemMeta.setCustomModelData(9);
                    } else if (args[1].equals("portal5")) {
                        itemMeta.setCustomModelData(10);
                    }
                    itemMeta.setUnbreakable(true);
                    itemStack.setItemMeta(itemMeta);
                    EntityEquipment equipment = armorStand.getEquipment();
                    equipment.setHelmet(itemStack);
                    armorStand.setVisible(false);
                    armorStand.setInvulnerable(true);
                    armorStand.setGravity(false);
                }
            } else if (args[0].equals("damage")) {
                float damage = Float.parseFloat(args[1]);

                player.damage(damage);
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
