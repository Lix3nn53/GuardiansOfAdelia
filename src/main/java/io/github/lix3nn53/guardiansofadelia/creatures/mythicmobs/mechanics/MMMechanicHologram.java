package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderString;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class MMMechanicHologram extends SkillMechanic implements ITargetedEntitySkill {

    protected final PlaceholderString message;

    public MMMechanicHologram(MythicLineConfig config) {
        super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.message = config.getPlaceholderString(new String[]{"message", "m"}, "CASTING SKILL?");
    }

    public static void chatHologram(LivingEntity entity, String message) {
        float height = (float) entity.getHeight();
        Location location = entity.getLocation().clone().add(0, height + 0.4, 0);

        new BukkitRunnable() {

            final int ticksLimit = 15;
            ArmorStand armorStand;
            int ticksPass = 0;

            @Override
            public void run() {
                if (ticksPass == ticksLimit) {
                    cancel();
                    armorStand.remove();
                } else {
                    if (ticksPass == 0) {
                        armorStand = new Hologram(location, ChatPalette.YELLOW + "< " + ChatPalette.GRAY + message + ChatPalette.YELLOW + " >").getArmorStand();
                    }
                    Location location = entity.getLocation().clone().add(0, height + 0.4, 0);
                    armorStand.teleport(location);
                    ticksPass++;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 5L);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        chatHologram(target, message.get(data, abstractEntity));

        return true;
    }
}
