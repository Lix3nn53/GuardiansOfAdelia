package io.github.lix3nn53.guardiansofadelia.sounds;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * How to create soundtrack files:
 * <p>
 * Export soundtrack as datapack using https://github.com/HielkeMinecraft/OpenNoteBlockStudio then
 * get playing.mcfunction folder, remove unnecessary lines and save as text file.
 * <p>
 * How to add created text file:
 * Add it to soundtracks folder in project resources and add it's name to songNames list below
 */
public class CustomSoundtrack {

    private static final List<String> songNames = new ArrayList<>();
    private static final RadioSongPlayer radioSongPlayer;
    private static int CURRENT_SONG_INDEX = 0;

    /*
    private static final HashMap<String, Set<Integer>> songToTick = new HashMap<>();
    private static final HashMap<String, HashMap<Integer, List<Float>>> songToPitch = new HashMap<>();
    private static final HashMap<String, HashMap<Integer, List<Sound>>> songToSound = new HashMap<>();
    */

    static {
        songNames.add("01 - Zelda - Ocarina of Time - Song of Storms");
        songNames.add("02 - Zelda - Ocarina of Time - Zelda Medley");

        String filePath = GuardiansOfAdelia.getInstance().getDataFolder() + File.separator + "soundtracks";
        File folder = new File(filePath);

        Song[] songs = new Song[songNames.size()];

        for (int i = 0; i < songNames.size(); i++) {
            String songName = songNames.get(i);
            File songFile = new File(folder, songName + ".nbs");

            if (!songFile.exists()) {
                GuardiansOfAdelia.getInstance().saveResource(songName + ".nbs", false);
            }

            Song song = NBSDecoder.parse(songFile);
            songs[i] = song;
        }

        Playlist playlist = new Playlist(songs);

        radioSongPlayer = new RadioSongPlayer(playlist, SoundCategory.BLOCKS);
        radioSongPlayer.setRepeatMode(RepeatMode.ALL);
    }

    public static void startPlayLoopForEveryone() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            radioSongPlayer.addPlayer(player);
        }

        radioSongPlayer.playSong(CURRENT_SONG_INDEX);
        radioSongPlayer.setPlaying(true);
    }

    public static void addPlayer(Player player) {
        radioSongPlayer.addPlayer(player);
        sendCurrentSongMessage(player);
    }


    public static void sendCurrentSongMessage(Player player) {
        String songName = songNames.get(CURRENT_SONG_INDEX);
        player.sendMessage(ChatPalette.YELLOW + "Now playing: " + ChatPalette.GOLD + songName);
    }

    protected static void onSongEnd() {
        radioSongPlayer.setPlaying(false);

        //DELAYED TASKS
        new BukkitRunnable() {
            @Override
            public void run() {
                playNextForEveryone();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 25L);
    }

    private static void playNextForEveryone() {
        if (CURRENT_SONG_INDEX < songNames.size() - 1) {
            CURRENT_SONG_INDEX++;
        } else {
            CURRENT_SONG_INDEX = 0;
        }
        play(CURRENT_SONG_INDEX);
    }

    private static void play(int index) {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        String songName = songNames.get(index);
        for (Player player : onlinePlayers) {
            player.sendMessage(ChatPalette.YELLOW + "Now playing: " + ChatPalette.GOLD + songName);
        }

        radioSongPlayer.playSong(CURRENT_SONG_INDEX);
        radioSongPlayer.setPlaying(true);
    }

    /*
    private static void play(int index) {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        String songName = songNames.get(index);
        for (Player player : onlinePlayers) {
            player.sendMessage(ChatPalette.YELLOW + "Now playing: " + ChatPalette.GOLD + songName);
        }

        Set<Integer> ticks = songToTick.get(songName);
        HashMap<Integer, List<Float>> tickToPitchs = songToPitch.get(songName);
        HashMap<Integer, List<Sound>> tickToSongs = songToSound.get(songName);
        new BukkitRunnable() {

            int tick = 1;
            int noPlayedTickCount = 0;

            @Override
            public void run() {
                if (ticks.contains(tick)) {
                    List<Float> floats = tickToPitchs.get(tick);
                    List<Sound> sounds = tickToSongs.get(tick);
                    for (int i = 0; i < floats.size(); i++) {
                        Float aFloat = floats.get(i);
                        Sound sound = sounds.get(i);
                        for (Player player : onlinePlayers) {
                            player.playSound(player.getLocation(), sound, SoundCategory.BLOCKS, 1, aFloat);
                        }
                    }
                    noPlayedTickCount = 0;
                } else {
                    noPlayedTickCount++;
                }

                tick++;

                if (noPlayedTickCount > 200) {
                    cancel();
                    playNextForEveryone();
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 1L, 1L);
    }

    private static void loadSongs() {
        for (String songName : songNames) {
            loadSongFromFile(songName);
        }
    }
    */

    /**
     * convert playing.mcfunction file generated by NoteBlockStudio(https://github.com/HielkeMinecraft/OpenNoteBlockStudio)
     * to txt file then use it here
     */
    /*
    private static void loadSongFromFile(String songName) {
        List<String> strings = readTxtLines(songName + ".txt");

        HashSet<Integer> ticks = new HashSet<>();
        HashMap<Integer, List<Float>> tickToPitchs = new HashMap<>();
        HashMap<Integer, List<Sound>> tickToSounds = new HashMap<>();
        for (String command : strings) {
            int tick = getTick(command);
            ticks.add(tick);

            if (tickToPitchs.containsKey(tick)) {
                tickToPitchs.get(tick).add(getPitch(command));
            } else {
                List<Float> floats = new ArrayList<>();
                floats.add(getPitch(command));
                tickToPitchs.put(tick, floats);
            }

            if (tickToSounds.containsKey(tick)) {
                tickToSounds.get(tick).add(getSound(command));
            } else {
                List<Sound> sounds = new ArrayList<>();
                sounds.add(getSound(command));
                tickToSounds.put(tick, sounds);
            }
        }

        songToTick.put(songName, ticks);
        songToPitch.put(songName, tickToPitchs);
        songToSound.put(songName, tickToSounds);
    }

    private static int getTick(String vanillaMinecraftCommand) {
        Pattern p = Pattern.compile("=\\d+");//. represents single character
        Matcher m = p.matcher(vanillaMinecraftCommand);

        int pitch;

        if (m.find()) {
            String found = m.group();
            found = found.replaceFirst("=", "");
            pitch = Integer.parseInt(found);
        } else {
            return -1;
        }

        return pitch;
    }

    private static float getPitch(String vanillaMinecraftCommand) {

        Pattern p = Pattern.compile("1\\s(\\d?\\.?\\d+?)\\s1");//. represents single character
        Matcher m = p.matcher(vanillaMinecraftCommand);

        float pitch = 0;

        if (m.find()) {
            String found = m.group();
            found = found.replaceFirst("1 ", "");
            found = found.substring(0, found.length() - 1);
            pitch = Float.parseFloat(found);
        }

        return pitch;
    }

    private static Sound getSound(String vanillaMinecraftCommand) {
        Sound sound = Sound.BLOCK_NOTE_BLOCK_HARP;

        if (vanillaMinecraftCommand.contains(".banjo")) {
            return Sound.BLOCK_NOTE_BLOCK_BANJO;
        } else if (vanillaMinecraftCommand.contains(".basedrum")) {
            return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        } else if (vanillaMinecraftCommand.contains(".bass")) {
            return Sound.BLOCK_NOTE_BLOCK_BASS;
        } else if (vanillaMinecraftCommand.contains(".bell")) {
            return Sound.BLOCK_NOTE_BLOCK_BELL;
        } else if (vanillaMinecraftCommand.contains(".bit")) {
            return Sound.BLOCK_NOTE_BLOCK_BIT;
        } else if (vanillaMinecraftCommand.contains(".chime")) {
            return Sound.BLOCK_NOTE_BLOCK_CHIME;
        } else if (vanillaMinecraftCommand.contains(".cow_bell")) {
            return Sound.BLOCK_NOTE_BLOCK_COW_BELL;
        } else if (vanillaMinecraftCommand.contains(".didgeridoo")) {
            return Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
        } else if (vanillaMinecraftCommand.contains(".flute")) {
            return Sound.BLOCK_NOTE_BLOCK_FLUTE;
        } else if (vanillaMinecraftCommand.contains(".guitar")) {
            return Sound.BLOCK_NOTE_BLOCK_GUITAR;
        } else if (vanillaMinecraftCommand.contains(".harp")) {
            return Sound.BLOCK_NOTE_BLOCK_HARP;
        } else if (vanillaMinecraftCommand.contains(".hat")) {
            return Sound.BLOCK_NOTE_BLOCK_HAT;
        } else if (vanillaMinecraftCommand.contains(".iron_xylophone")) {
            return Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE;
        } else if (vanillaMinecraftCommand.contains(".pling")) {
            return Sound.BLOCK_NOTE_BLOCK_PLING;
        } else if (vanillaMinecraftCommand.contains(".snare")) {
            return Sound.BLOCK_NOTE_BLOCK_SNARE;
        } else if (vanillaMinecraftCommand.contains(".xylophone")) {
            return Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
        }

        return sound;
    }

    private static List<String> readTxtLines(String file) {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    DATA_FOLDER + "\\" + file));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
    */
}
