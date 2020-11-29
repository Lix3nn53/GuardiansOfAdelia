package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.CheckpointManager;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.HologramManager;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class MyChunkEvents implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoadEvent(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        Entity[] chunkEntities = chunk.getEntities();
        for (Entity chunkEntity : chunkEntities) {
            if (shouldChunkEventRemove(chunkEntity)) {
                chunkEntity.remove();
            }
        }

        customEventsOnChunkLoad(chunk);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onNPCSpawnEvent(NPCSpawnEvent event) {
        NPC npc = event.getNPC();
        QuestNPCManager.onNPCSpawn(npc);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onNPCDespawnEvent(NPCDespawnEvent event) {
        NPC npc = event.getNPC();
        QuestNPCManager.onNPCDespawn(npc);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        Entity[] chunkEntities = chunk.getEntities();
        for (Entity chunkEntity : chunkEntities) {
            if (shouldChunkEventRemove(chunkEntity)) {
                chunkEntity.remove();
            }
        }

        customEventsOnChunkUnload(chunk);
    }

    /**
     * Since mythic mobs manages mobs, we only need to manage armor stands for custom models
     *
     * @param chunkEntity
     * @return
     */
    private boolean shouldChunkEventRemove(Entity chunkEntity) {
        EntityType type = chunkEntity.getType();

        if (type.equals(EntityType.ARMOR_STAND)) {
            boolean questIcon = QuestNPCManager.isQuestIcon((ArmorStand) chunkEntity);
            return !questIcon;
        }

        return false;
    }

    private void customEventsOnChunkLoad(Chunk chunk) {
        String chunkKey = LocationUtils.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());

        BazaarManager.onChunkLoad(chunkKey);
        TombManager.onChunkLoad(chunkKey);
        PortalManager.onChunkLoad(chunkKey);
        CheckpointManager.onChunkLoad(chunkKey);
        HologramManager.onChunkLoad(chunkKey);
        LootChestManager.onChunkLoad(chunkKey);
    }

    private void customEventsOnChunkUnload(Chunk chunk) {
        String chunkKey = LocationUtils.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
        LootChestManager.onChunkUnload(chunkKey);
    }
}
