package io.github.lix3nn53.guardiansofadelia.sounds;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CustomSound {

    Sound sound;
    String soundString;
    float volume;
    float pitch;

    public CustomSound(String soundString, float volume, float pitch) {
        this.soundString = soundString;
        this.volume = volume;
        this.pitch = pitch;
    }

    public CustomSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Location location) {
        World world = location.getWorld();
        if (this.sound == null) {
            world.playSound(location, soundString, SoundCategory.RECORDS, volume, pitch);
        } else {
            world.playSound(location, sound, SoundCategory.RECORDS, volume, pitch);
        }
    }

    public void play(Player player) {
        if (this.sound == null) {
            player.playSound(player.getLocation(), soundString, SoundCategory.RECORDS, volume, pitch);
        } else {
            player.playSound(player.getLocation(), sound, SoundCategory.RECORDS, volume, pitch);
        }
    }

}
