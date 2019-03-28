package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.quests.QuestHologram;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MyChunkEvents implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoadEvent(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        Entity[] chunkEntities = chunk.getEntities();
        for (int i = 0; i < chunkEntities.length; i++) {
            Entity chunkEntity = chunkEntities[i];
            if (!isBoundEntity(chunkEntity, true)) {
                SpawnerManager.onMobDeath(chunkEntity);
                chunkEntity.remove();
            }
        }
        SpawnerManager.activateSpawnersOnChunk(chunk);
        createCustomEntitiesOnChunkLoad(chunk);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        Entity[] chunkEntities = chunk.getEntities();
        for (int i = 0; i < chunkEntities.length; i++) {
            Entity chunkEntity = chunkEntities[i];
            if (!isBoundEntity(chunkEntity, false)) {
                SpawnerManager.onMobDeath(chunkEntity);
                chunkEntity.remove();
            }
        }
        SpawnerManager.deactivateSpawnersOnChunk(chunk);
    }

    private boolean isBoundEntity(Entity chunkEntity, boolean isChunkLoad) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        if (npcRegistry.isNPC(chunkEntity)) {
            if (isChunkLoad) {
                //create quest icons
                QuestNPCManager.onChunkLoad(chunkEntity);
            }
            return true;
        } else if (chunkEntity.getType().equals(EntityType.PLAYER) || chunkEntity.getType().equals(EntityType.ITEM_FRAME)
                || chunkEntity.getType().equals(EntityType.PAINTING)) {
            return true;
        } else if (chunkEntity.getType().equals(EntityType.WOLF) || chunkEntity.getType().equals(EntityType.HORSE)
                || chunkEntity.getType().equals(EntityType.DONKEY) || chunkEntity.getType().equals(EntityType.MULE)) {
            return PetManager.isPet(chunkEntity);
        } else return chunkEntity.getType().equals(EntityType.DROPPED_ITEM);
    }

    private void createCustomEntitiesOnChunkLoad(Chunk chunk) {
        String chunkKey = SpawnerManager.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
        //create bazaar models
        BazaarManager.onChunkLoad(chunkKey);
    }
}
