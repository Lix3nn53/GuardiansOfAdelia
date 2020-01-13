package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
    private Location baseLocation;

    public Bazaar(Player owner) {
        this.owner = owner;
        this.customerGui = new BazaarCustomerGui(owner);
        this.baseLocation = owner.getLocation().clone();
        this.baseLocation.setYaw(0f);
        this.baseLocation.setPitch(0f);
        BazaarManager.onBazaarCreate(this.baseLocation, this);
    }

    public boolean addItem(ItemStack itemStack, int price) {
        if (GuardianDataManager.hasGuardianData(owner.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner.getUniqueId());
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
        if (GuardianDataManager.hasGuardianData(owner.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner.getUniqueId());
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
        if (GuardianDataManager.hasGuardianData(owner.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner.getUniqueId());

            if (getItemsOnSale().contains(itemToBuy) && !buyer.equals(owner)) {

                boolean pay = EconomyUtils.pay(buyer, itemToBuy);
                if (pay) {
                    guardianData.removeFromBazaarStorage(itemToBuy);
                    removeItem(itemToBuy);

                    ItemStack clone = EconomyUtils.removeShopPrice(itemToBuy);
                    InventoryUtils.giveItemToPlayer(buyer, clone);

                    int price = EconomyUtils.getItemPrice(itemToBuy);
                    moneyEarned += price;

                    List<Coin> coins = EconomyUtils.priceToCoins(price);
                    for (Coin coin : coins) {
                        if (coin.getAmount() > 0) {
                            boolean addedToBazaarStorage = guardianData.addToBazaarStorage(coin.getCoin());
                            if (!addedToBazaarStorage) {
                                InventoryUtils.giveItemToPlayer(owner, coin.getCoin());
                            }
                        }
                    }

                    owner.sendMessage(ChatColor.WHITE + buyer.getName() + ChatColor.GOLD + " purchased this item " + itemToBuy.getItemMeta().getDisplayName() +
                            ChatColor.GOLD + " from your bazaar. " +
                            ChatColor.GREEN + coins.get(0).getAmount() + " " + ChatColor.WHITE + coins.get(1).getAmount() + " " + ChatColor.YELLOW + coins.get(2).getAmount()
                            + ChatColor.GOLD + " coins added to your bazaar storage");
                    buyer.sendMessage(ChatColor.GOLD + "You purchased this item " + itemToBuy.getItemMeta().getDisplayName() + " from " + ChatColor.WHITE + owner.getName()
                            + ChatColor.GOLD + " for " + ChatColor.GREEN + coins.get(0).getAmount() + " " + ChatColor.WHITE + coins.get(1).getAmount() + " " +
                            ChatColor.YELLOW + coins.get(2).getAmount()
                            + ChatColor.GOLD + " coins");
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
        this.bazaarModel.setHelmet(itemStack);
        this.bazaarModel.setVisible(false);
        this.bazaarModel.setCustomName(ChatColor.GOLD + "< Bazaar " + ChatColor.YELLOW + owner.getName() + ChatColor.GOLD + " >");
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

        GuiGeneric customerGui = new GuiGeneric(27, ChatColor.GOLD + "Edit your bazaar", 0);

        ItemStack glassInfo = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glassInfo.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Click an item in your inventory to add");
        itemMeta.setLore(new ArrayList() {{
            add(ChatColor.GOLD + "to your bazaar. Click an item in your bazaar");
            add(ChatColor.GOLD + "to remove from your bazaar.");
            add("");
            add(ChatColor.GREEN + "Click green wool to set up your bazaar");
            add("");
            add(ChatColor.RED + "Click red wool to destroy your bazaar");
        }});
        glassInfo.setItemMeta(itemMeta);
        for (int i = 18; i <= 26; i++) {
            customerGui.setItem(i, glassInfo);
        }

        ItemStack redWool = new ItemStack(Material.RED_WOOL);
        ItemMeta redMeta = redWool.getItemMeta();
        redMeta.setDisplayName(ChatColor.RED + "Click to destroy your bazaar");
        redWool.setItemMeta(redMeta);
        customerGui.setItem(18, redWool);

        ItemStack greenWool = new ItemStack(Material.LIME_WOOL);
        ItemMeta greenMeta = greenWool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GREEN + "Click to set up your bazaar");
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
        this.bazaarModel.remove();

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
