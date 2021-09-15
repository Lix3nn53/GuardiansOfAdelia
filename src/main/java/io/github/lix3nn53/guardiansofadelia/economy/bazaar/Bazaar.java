package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Bazaar {

    private final Player owner;
    private final BazaarCustomerGui customerGui;
    private final List<Player> customers = new ArrayList<>();
    private ArmorStand bazaarModel;
    private boolean open = false;
    private int moneyEarned = 0;
    private final Location baseLocation;

    public Bazaar(Player owner) {
        this.owner = owner;
        this.customerGui = new BazaarCustomerGui(owner);
        this.baseLocation = owner.getLocation().clone();
        this.baseLocation.setYaw(0f);
        this.baseLocation.setPitch(0f);
        BazaarManager.onBazaarCreate(this.baseLocation, this);
    }

    public boolean addItem(ItemStack itemStack, int price) {
        if (GuardianDataManager.hasGuardianData(owner)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner);
            if (guardianData.bazaarStorageIsEmpty()) {
                if (customerGui.anyEmpty()) {
                    itemStack = EconomyUtils.setShopPrice(itemStack, price);
                    guardianData.addToBazaarStorage(itemStack);
                    customerGui.addItem(itemStack);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeItem(ItemStack itemStack) {
        if (GuardianDataManager.hasGuardianData(owner)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner);
            if (getItemsOnSale().contains(itemStack)) {
                guardianData.removeFromBazaarStorage(itemStack);
                customerGui.removeItem(itemStack, itemStack.getAmount());
                return true;
            }
        }
        return false;
    }

    public List<ItemStack> getItemsOnSale() {
        List<ItemStack> itemsOnSale = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            if (customerGui.getItem(i) != null) {
                ItemStack item = customerGui.getItem(i);
                if (!item.getType().equals(Material.AIR)) {
                    itemsOnSale.add(item);
                }
            }
        }
        return itemsOnSale;
    }

    public boolean buyItem(Player buyer, ItemStack itemToBuy) {
        if (GuardianDataManager.hasGuardianData(owner)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner);

            if (getItemsOnSale().contains(itemToBuy) && !buyer.equals(owner)) {

                boolean pay = EconomyUtils.pay(buyer, itemToBuy);
                if (pay) {
                    guardianData.removeFromBazaarStorage(itemToBuy);
                    removeItem(itemToBuy);

                    ItemStack clone = EconomyUtils.removeShopPrice(itemToBuy);
                    InventoryUtils.giveItemToPlayer(buyer, clone);

                    int price = EconomyUtils.getItemPrice(itemToBuy);
                    moneyEarned += price;

                    int[] coins = EconomyUtils.priceToCoins(price);

                    if (coins[0] > 0) {
                        ItemStack coin = new Coin(CoinType.COPPER, coins[0]).getCoin();
                        boolean addedToBazaarStorage = guardianData.addToBazaarStorage(coin);
                        if (!addedToBazaarStorage) {
                            InventoryUtils.giveItemToPlayer(owner, coin);
                        }
                    }
                    if (coins[1] > 0) {
                        ItemStack coin = new Coin(CoinType.SILVER, coins[1]).getCoin();
                        boolean addedToBazaarStorage = guardianData.addToBazaarStorage(coin);
                        if (!addedToBazaarStorage) {
                            InventoryUtils.giveItemToPlayer(owner, coin);
                        }
                    }
                    if (coins[2] > 0) {
                        ItemStack coin = new Coin(CoinType.GOLD, coins[2]).getCoin();
                        boolean addedToBazaarStorage = guardianData.addToBazaarStorage(coin);
                        if (!addedToBazaarStorage) {
                            InventoryUtils.giveItemToPlayer(owner, coin);
                        }
                    }

                    owner.sendMessage(ChatPalette.WHITE + buyer.getName() + ChatPalette.GOLD + " purchased this item " + itemToBuy.getItemMeta().getDisplayName() +
                            ChatPalette.GOLD + " from your bazaar. " +
                            ChatPalette.BROWN + coins[0] + " " + ChatPalette.GRAY + coins[1] + " " + ChatPalette.GOLD + coins[2]
                            + ChatPalette.GOLD + " coins added to your bazaar storage");
                    buyer.sendMessage(ChatPalette.GOLD + "You purchased this item " + itemToBuy.getItemMeta().getDisplayName() + " from " + ChatPalette.WHITE + owner.getName()
                            + ChatPalette.GOLD + " for " + ChatPalette.BROWN + coins[0] + " " + ChatPalette.GRAY + coins[1] + " " +
                            ChatPalette.GOLD + coins[2]
                            + ChatPalette.GOLD + " coins");
                }
            }
        }
        return false;
    }

    public void createModel() {
        this.bazaarModel = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation, EntityType.ARMOR_STAND);
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(3);
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        EntityEquipment equipment = this.bazaarModel.getEquipment();
        equipment.setHelmet(itemStack);
        this.bazaarModel.setVisible(false);
        this.bazaarModel.setCustomName(ChatPalette.GOLD + "< Bazaar " + ChatPalette.YELLOW + owner.getName() + ChatPalette.GOLD + " >");
        this.bazaarModel.setCustomNameVisible(true);
        this.bazaarModel.setInvulnerable(true);
        this.bazaarModel.setGravity(false);

        BazaarManager.putBazaarToPlayer(owner, bazaarModel);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean isOpen) {
        this.open = isOpen;
    }

    public void edit() {
        List<Player> copy = new ArrayList<>();
        copy.addAll(customers);
        for (Player customer : copy) {
            customer.closeInventory();
        }

        GuiGeneric customerGui = new GuiGeneric(27, ChatPalette.GOLD + "Edit your bazaar", 0);

        ItemStack glassInfo = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glassInfo.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GOLD + "Click an item in your inventory to add");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GOLD + "to your bazaar. Click an item in your bazaar");
        lore.add(ChatPalette.GOLD + "to remove from your bazaar.");
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "Click green wool to set up your bazaar");
        lore.add("");
        lore.add(ChatPalette.RED + "Click red wool to destroy your bazaar");
        itemMeta.setLore(lore);
        glassInfo.setItemMeta(itemMeta);
        for (int i = 18; i <= 26; i++) {
            customerGui.setItem(i, glassInfo);
        }

        ItemStack redWool = new ItemStack(Material.RED_WOOL);
        ItemMeta redMeta = redWool.getItemMeta();
        redMeta.setDisplayName(ChatPalette.RED + "Click to destroy your bazaar");
        redWool.setItemMeta(redMeta);
        customerGui.setItem(18, redWool);

        ItemStack greenWool = new ItemStack(Material.LIME_WOOL);
        ItemMeta greenMeta = greenWool.getItemMeta();
        greenMeta.setDisplayName(ChatPalette.GREEN_DARK + "Click to set up your bazaar");
        greenWool.setItemMeta(greenMeta);
        customerGui.setItem(26, greenWool);

        int i = 0;
        for (ItemStack itemStack : getItemsOnSale()) {
            customerGui.setItem(i, itemStack);
            i++;
        }

        setOpen(false);
        new BukkitRunnable() {
            @Override
            public void run() {
                customerGui.openInventory(owner);
            }
        }.runTask(GuardiansOfAdelia.getInstance());
    }

    public void showToCustomer(Player customer) {
        if (open) {
            customerGui.openInventory(customer);
            customers.add(customer);
        }
    }

    public void remove() {
        List<Player> copy = new ArrayList<>();
        copy.addAll(customers);
        for (Player customer : copy) {
            customer.closeInventory();
        }

        BazaarManager.clearBazaarToPlayer(bazaarModel);
        if (this.bazaarModel != null) {
            this.bazaarModel.remove();
        }

        this.open = false;

        BazaarManager.onBazaarRemove(this);
    }

    public void setUp() {
        if (!getItemsOnSale().isEmpty()) {
            if (this.bazaarModel == null) {
                createModel();
            }
            setOpen(true);
            owner.closeInventory();
        }
    }

    public List<Player> getCustomers() {
        return customers;
    }

    public int getMoneyEarned() {
        return moneyEarned;
    }

    public void removeCustomer(Player player) {
        customers.remove(player);
    }

    public Location getBaseLocation() {
        return baseLocation;
    }
}
