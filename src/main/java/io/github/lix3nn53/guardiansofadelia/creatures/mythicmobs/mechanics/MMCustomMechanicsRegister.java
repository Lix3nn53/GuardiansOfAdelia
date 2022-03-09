package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.core.skills.SkillManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MMCustomMechanicsRegister implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(MythicMechanicLoadEvent event) {
        SkillManager skillManager = MythicBukkit.inst().getSkillManager();

        if (event.getMechanicName().equalsIgnoreCase("GoaElementType")) {
            event.register(new MMMechanicElementType(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaElementResistance")) {
            event.register(new MMMechanicElementResistance(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaDisarm")) {
            event.register(new MMMechanicDisarm(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaRoot")) {
            event.register(new MMMechanicRoot(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaSilence")) {
            event.register(new MMMechanicSilence(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaStun")) {
            event.register(new MMMechanicStun(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaHologram")) {
            event.register(new MMMechanicHologram(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaGlow")) {
            event.register(new MMMechanicGlow(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaDisguise")) {
            event.register(new MMMechanicDisguise(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaProjectile")) {
            event.register(new MMMechanicProjectile(skillManager, event.getConfig()));
        } else if (event.getMechanicName().equalsIgnoreCase("GoaTargetPlayer")) {
            event.register(new MMMechanicTargetPlayer(skillManager, event.getConfig()));
        }
    }
}
