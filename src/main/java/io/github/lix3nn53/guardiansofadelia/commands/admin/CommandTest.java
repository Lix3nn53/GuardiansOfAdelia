package io.github.lix3nn53.guardiansofadelia.commands.admin;

import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.FlagSetMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGround;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementSingle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
                player.sendMessage(ChatColor.BLUE + "/test test");
                player.sendMessage(ChatColor.BLUE + "/test model portal<1-5>");
                player.sendMessage(ChatColor.BLUE + "/test sound <code> - play custom sounds");
                player.sendMessage(ChatColor.BLUE + "/test damage <amount> - damage self");
            } else if (args[0].equals("test")) {
                if (this.test) {
                    test = false;
                    skillOnGround.deactivate();
                    return true;
                }
                test = true;

                SelfTarget selfTarget = new SelfTarget();

                ArrangementSingle arrangementSingle = new ArrangementSingle(Particle.FLAME, null, 0.1, 0.5, 0.1);
                /* ParticleMechanic particleMechanic = new ParticleMechanic(false, arrangementSingle, new ArrayList<>(), null, 0, 0,
                        false, true, true, true, 0, 0, 0, 0, 0);*/

                ArrayList<Double> radiusList = new ArrayList<>();
                radiusList.add(5d);
                ArrayList<Integer> amountList = new ArrayList<>();
                amountList.add(20);
                AreaTarget areaTarget = new AreaTarget(false, true, true, false, 10, false, false,
                        false, radiusList, amountList, null, null, null, 0.2, arrangementSingle);


                ArrayList<Double> damages = new ArrayList<>();
                damages.add(1d);
                DamageMechanic damageMechanic = new DamageMechanic(false, damages, null, ElementType.FIRE, null);

                FlagCondition flagCondition = new FlagCondition("trapKey", false, false);
                SelfTarget selfTargetForFlag = new SelfTarget();
                FlagCondition flagConditionForFlag = new FlagCondition("trapKey", false, false);

                List<Integer> ticks = new ArrayList<>();
                ticks.add(40);
                ticks.add(40);
                FlagSetMechanic FlagSetMechanic = new FlagSetMechanic("trapKey", ticks, false, null);

                selfTarget.addChildren(flagCondition);
                flagCondition.addChildren(areaTarget);
                areaTarget.addChildren(damageMechanic);
                areaTarget.addChildren(selfTargetForFlag);
                selfTargetForFlag.addChildren(flagConditionForFlag);
                flagConditionForFlag.addChildren(FlagSetMechanic);

                skillOnGround = new SkillOnGround("command", 20L, selfTarget);

                skillOnGround.activate(player.getLocation(), 40L);
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
                double damage = Double.parseDouble(args[1]);

                player.damage(damage);
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
