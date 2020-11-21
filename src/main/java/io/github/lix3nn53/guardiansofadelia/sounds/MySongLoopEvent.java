package io.github.lix3nn53.guardiansofadelia.sounds;

import com.xxmicloxx.NoteBlockAPI.event.SongLoopEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MySongLoopEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(SongLoopEvent event) {
        CustomSoundtrack.onSongEnd();
    }
}
