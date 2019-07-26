package io.github.lix3nn53.guardiansofadelia.sounds;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CustomSound {

    String sound;
    float volume;
    float pitch;

    public CustomSound(String sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Location location) {
        World world = location.getWorld();
        world.playSound(location, sound, SoundCategory.RECORDS, volume, pitch);
    }

    public void play(Player player) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

}
