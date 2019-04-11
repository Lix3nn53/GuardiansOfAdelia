import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        final DatabaseQueries databaseQueries = new DatabaseQueries();

        databaseQueries.createTables();

        List<Integer> playersInGame = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            playersInGame.add(i);
        }

        for (int i = 4; i < playersInGame.size(); i+=4) {
            List<Integer> players = playersInGame.subList(i - 4, i);
            System.out.println(players);
        }
    }
}
