package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacter;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterCompass;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class CompassManager {

    private static final HashMap<Player, CustomCharacter> playerToCompassDirection = new HashMap<>();

    public static void setCompassItemNPC(Player player, int npcNo) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        NPC npc = npcRegistry.getById(npcNo);

        /*ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE + "Compass " + ChatPalette.WHITE + "( " + npc.getName() + ChatPalette.WHITE + " )");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Right click while holding to select compass target");
        lore.add(ChatPalette.GRAY_DARK + "NPC: #" + npcNo);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);
        player.getInventory().addItem(itemStack);*/

        player.setCompassTarget(npc.getStoredLocation());
        player.sendMessage(ChatPalette.BLUE + "New compass target: " + ChatPalette.WHITE + npc.getName());
        onCompassTargetSelect(player);
    }

    public static void setCompassItemLocation(Player player, String locationName, Location location) {
        /*ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE + "Compass " + ChatPalette.WHITE + "( " + locationName + ChatPalette.WHITE + " )");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Right click while holding this item");
        lore.add(ChatPalette.GRAY + "to change compass target.");
        lore.add("");
        lore.add(ChatPalette.GRAY_DARK + "Target: " + locationName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);
        player.getInventory().addItem(itemStack);*/

        player.setCompassTarget(location);
        player.sendMessage(ChatPalette.BLUE + "New compass target: " + ChatPalette.WHITE + locationName);
        onCompassTargetSelect(player);
    }

    private static CustomCharacter getDirection(Player player) {
        Location eyeLocation = player.getEyeLocation();

        Vector eyeVector = eyeLocation.toVector().setY(0);
        Vector target = player.getCompassTarget().toVector().setY(0);

        Vector subtract = target.subtract(eyeVector).normalize();

        Vector directionBase = eyeLocation.getDirection().setY(0);
        Vector direction = directionBase.normalize();
        float angle = direction.angle(subtract);

        float degrees = (float) Math.toDegrees(angle);

        Vector crossProduct = direction.crossProduct(subtract);
        float dot = (float) crossProduct.dot(new Vector(0, 1, 0));
        if (dot > 0) { // Or < 0
            degrees = -degrees;
        }

        float degrees360 = degrees + 180;

        float unit = 11.25f;
        float half = unit / 2f;

        float start = -half;
        float end = 360f - half;

        int resultIndex = 0;
        if (degrees360 < end) {
            float counter = start;
            for (int i = 0; i < 32; i++) {
                counter += unit;
                if (degrees360 < counter) {
                    resultIndex = i;
                    break;
                }
            }
        }// else last half unit is same with first half unit = compass_00.png


        return CustomCharacterCompass.ARRAY[resultIndex];
    }

    public static void update(Player player) {
        if (!playerToCompassDirection.containsKey(player)) return;


        CustomCharacter compassDirection = getDirection(player);
        playerToCompassDirection.put(player, compassDirection);
    }

    public static CustomCharacter getCompassDirection(Player player) {
        if (playerToCompassDirection.containsKey(player)) return playerToCompassDirection.get(player);

        return CustomCharacterCompass.EMPTY;
    }

    public static void onCompassTargetSelect(Player player) {
        CustomCharacter compassDirection = getDirection(player);
        playerToCompassDirection.put(player, compassDirection);
    }

    public static void clearCompassTarget(Player player) {
        playerToCompassDirection.remove(player);
    }
}
