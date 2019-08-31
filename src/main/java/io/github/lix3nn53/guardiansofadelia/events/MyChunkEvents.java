package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
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
import org.bukkit.scheduler.BukkitRunnable;

public class MyChunkEvents implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoadEvent(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        new BukkitRunnable() {
            @Override
            public void run() {
                Entity[] chunkEntities = chunk.getEntities();
                for (Entity chunkEntity : chunkEntities) {
                    if (shouldChunkEventRemove(chunkEntity)) {
                        SpawnerManager.onMobDeath(chunkEntity);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                chunkEntity.remove();
                            }
                        }.runTask(GuardiansOfAdelia.getInstance());
                    }
                }

                SpawnerManager.activateSpawnersOnChunk(chunk);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        createCustomEntitiesOnChunkLoad(chunk);
                    }
                }.runTask(GuardiansOfAdelia.getInstance());
            }
        }.runTaskAsynchronously(GuardiansOfAdelia.getInstance());
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
        new BukkitRunnable() {
            @Override
            public void run() {
                Entity[] chunkEntities = chunk.getEntities();
                for (Entity chunkEntity : chunkEntities) {
                    if (shouldChunkEventRemove(chunkEntity)) {
                        SpawnerManager.onMobDeath(chunkEntity);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                chunkEntity.remove();
                            }
                        }.runTask(GuardiansOfAdelia.getInstance());
                    }
                }
                SpawnerManager.deactivateSpawnersOnChunk(chunk);
            }
        }.runTaskAsynchronously(GuardiansOfAdelia.getInstance());
    }

    private boolean shouldChunkEventRemove(Entity chunkEntity) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        if (npcRegistry.isNPC(chunkEntity)) {
            return false;
        }
        EntityType type = chunkEntity.getType();

        if (type.equals(EntityType.ARMOR_STAND)) {
            boolean questIcon = QuestNPCManager.isQuestIcon((ArmorStand) chunkEntity);
            if (questIcon) return false;
        }

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
}
