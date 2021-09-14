package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithLocationManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.CheckpointManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.HologramManager;
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
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.EntitiesLoadEvent;

import java.util.ArrayList;
import java.util.List;

public class MyChunkEvents implements Listener {

    public static final List<Entity> DO_NOT_DELETE = new ArrayList<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntitiesLoadEvent(EntitiesLoadEvent event) {

        List<Entity> chunkEntities = event.getEntities();
        for (Entity chunkEntity : chunkEntities) {
            if (shouldChunkEventRemove(chunkEntity)) {
                chunkEntity.remove();
            }
        }

        customEventsOnChunkLoad(event.getChunk());
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

    private boolean shouldChunkEventRemove(Entity chunkEntity) {
        if (DO_NOT_DELETE.contains(chunkEntity)) return false;

        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        if (npcRegistry.isNPC(chunkEntity)) {
            return false;
        }
        EntityType type = chunkEntity.getType();

        if (type.equals(EntityType.ARMOR_STAND)) {
            boolean questIcon = QuestNPCManager.isQuestIcon((ArmorStand) chunkEntity);

            return !questIcon;
        }

        return !(type.equals(EntityType.PLAYER) || type.equals(EntityType.ITEM_FRAME)
                || type.equals(EntityType.PAINTING) || type.equals(EntityType.DROPPED_ITEM));
    }

    private void customEventsOnChunkLoad(Chunk chunk) {
        String chunkKey = LocationUtils.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());

        BazaarManager.onChunkLoad(chunkKey);
        TombManager.onChunkLoad(chunkKey);
        PortalManager.onChunkLoad(chunkKey);
        CheckpointManager.onChunkLoad(chunkKey);
        HologramManager.onChunkLoad(chunkKey);
        LootChestManager.onChunkLoad(chunkKey);
        GatheringManager.onChunkLoad(chunkKey);
        SkillOnGroundWithLocationManager.onChunkLoad(chunkKey);
    }

    private void customEventsOnChunkUnload(Chunk chunk) {
        String chunkKey = LocationUtils.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
        LootChestManager.onChunkUnload(chunkKey);
    }
}
