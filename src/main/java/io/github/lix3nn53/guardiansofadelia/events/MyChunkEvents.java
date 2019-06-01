package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Chunk;
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
                SpawnerManager.onMobDeath(chunkEntity);
                chunkEntity.remove();
            } else {
                createQuestIconBase(chunkEntity);
            }
        }
        SpawnerManager.activateSpawnersOnChunk(chunk);
        createCustomEntitiesOnChunkLoad(chunk);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        Entity[] chunkEntities = chunk.getEntities();
        for (Entity chunkEntity : chunkEntities) {
            if (shouldChunkEventRemove(chunkEntity)) {
                SpawnerManager.onMobDeath(chunkEntity);
                chunkEntity.remove();
            }
        }
        SpawnerManager.deactivateSpawnersOnChunk(chunk);
    }

    private boolean shouldChunkEventRemove(Entity chunkEntity) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        if (npcRegistry.isNPC(chunkEntity)) {
            return false;
        }
        EntityType type = chunkEntity.getType();
        return !(type.equals(EntityType.PLAYER) || type.equals(EntityType.ITEM_FRAME)
                || type.equals(EntityType.PAINTING) || type.equals(EntityType.DROPPED_ITEM));
    }

    private void createCustomEntitiesOnChunkLoad(Chunk chunk) {
        String chunkKey = SpawnerManager.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
        //create bazaar models
        BazaarManager.onChunkLoad(chunkKey);
        TombManager.onChunkLoad(chunkKey);
        PortalManager.onChunkLoad(chunkKey);
    }

    private void createQuestIconBase(Entity chunkEntity) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        if (npcRegistry.isNPC(chunkEntity)) {
            //create quest icon on
            QuestNPCManager.onChunkLoad(chunkEntity);
        }
    }
}
