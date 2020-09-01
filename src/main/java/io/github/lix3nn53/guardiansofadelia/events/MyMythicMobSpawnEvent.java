package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MyMythicMobSpawnEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(MythicMobSpawnEvent e) {
        Entity entity = e.getEntity();
        MythicMob mythicMob = e.getMobType();
        String internalName = mythicMob.getInternalName();

        AdeliaEntity adeliaEntity = AdeliaEntityManager.getEntity(internalName);
        int experience = adeliaEntity.getExperience();

        AdeliaEntity.setEntityExperience(entity, experience);
    }
}
