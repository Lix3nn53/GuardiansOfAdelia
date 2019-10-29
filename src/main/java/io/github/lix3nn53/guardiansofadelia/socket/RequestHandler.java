package io.github.lix3nn53.guardiansofadelia.socket;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RequestHandler {

    private final PrintWriter out;

    public RequestHandler(PrintWriter out) {
        this.out = out;
    }

    public void onRequest(String inputLine) throws ParseException {
        GuardiansOfAdelia.getInstance().getLogger().info("Request" + inputLine);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(inputLine);

        String minecraftUsername = (String) json.get("minecraftUsername");
        int productId = Math.toIntExact((Long) json.get("productId"));
        int credits = Math.toIntExact((Long) json.get("credits"));

        GuardiansOfAdelia.getInstance().getLogger().info("minecraftUsername: " + minecraftUsername);
        GuardiansOfAdelia.getInstance().getLogger().info("productId: " + productId);
        GuardiansOfAdelia.getInstance().getLogger().info("credits: " + credits);

        ItemStack weapon = Weapons.getWeapon(RPGClass.WARRIOR, 1, ItemTier.COMMON, "", 1, 1, 1);

        boolean success = false;

        Player playerExact = Bukkit.getPlayerExact(minecraftUsername);
        if (playerExact != null) {
            UUID uuid = playerExact.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                success = guardianData.addToPremiumStorage(weapon);
            }
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(minecraftUsername);
            UUID uuid = offlinePlayer.getUniqueId();
            success = addItemToPremiumStorage(uuid, weapon);
        }

        JSONObject jsonResponse = new JSONObject();
        if (!success) {
            jsonResponse.put("error", "");
        } else {
            jsonResponse.put("success", "");
        }

        sendResponse(jsonResponse.toJSONString());
    }

    private void sendResponse(String response) {
        out.println(response);
    }

    private boolean addItemToPremiumStorage(UUID uuid, ItemStack itemStack) {
        if (!uuidExists(uuid)) return false;

        try {
            ItemStack[] premiumStorage = DatabaseQueries.getPremiumStorage(uuid);

            List<ItemStack> list = new ArrayList<>();

            if (premiumStorage != null) list = new ArrayList<>(Arrays.asList(premiumStorage));

            if (list.size() >= 54) return false;

            list.add(itemStack);
            ItemStack[] newPremiumStorage = list.toArray(new ItemStack[0]);
            DatabaseQueries.setPremiumStorage(uuid, newPremiumStorage);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean uuidExists(UUID uuid) {
        return DatabaseQueries.uuidExists(uuid);
    }
}
