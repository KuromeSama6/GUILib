package moe.hiktal.guilib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import sun.security.krb5.internal.crypto.Des;

import java.util.*;

/**
 * Representation of an inventory displayed to a player.
 * An GUIMenu may contain many pages (GUIPage).
 */
public class GUIMenu implements Listener {
    private static final int WIDTH = 9;

    /**
     * The total number of slots in this GUIMenu.
     */
    public final int size, height, width;
    /**
     * The title (shown in the top-left corner) of this GUIMenu.
     */
    public final String title;

    private final List<GUIPage> pages = new ArrayList<>();
    private final HashMap<Player, Inventory> inventories = new HashMap<>();
    private GUIPage activePage;
    private boolean destroyed;

    public GUIMenu(String title, int height) {
        this.height = height;
        this.width = WIDTH;
        this.size = height * width;
        this.title = ChatColor.translateAlternateColorCodes('&', title);

        Bukkit.getPluginManager().registerEvents(this, GUILib.i);
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void AddPage(GUIPage... pages) {
        EnsureNotDestroyed();
        if (pages.length == 0) throw new IllegalArgumentException("At least one GUIPage must be passed.");
        activePage = pages[0];
        this.pages.addAll(Arrays.asList(pages));
    }

    public void setPage(int page) {
        EnsureNotDestroyed();
        activePage = pages.get(page);
    }

    public GUIPage getActivePage() {
        EnsureNotDestroyed();
        return activePage;
    }

    public int GetActivePageIndex() {
        return pages.indexOf(activePage);
    }

    public void Next(Player player) {
        int current = GetActivePageIndex();
        final int max = pages.size() - 1;

        if (current == max) current = 0;
        else ++current;

        activePage = pages.get(current);
        Open(player);
    }

    public void Prev(Player player) {
        int current = GetActivePageIndex();
        final int max = pages.size() - 1;

        if (current == 0) current = max;
        else --current;

        activePage = pages.get(current);
        Open(player);
    }

    public void Open(Player player) {
        EnsureNotDestroyed();

        if (inventories.containsKey(player)) player.closeInventory();

        Inventory inventory = Bukkit.createInventory(player, size, title);
        activePage.Apply(inventory);
        player.openInventory(inventory);
        inventories.put(player, inventory);
    }

    public void Destroy() {
        EnsureNotDestroyed();

        destroyed = true;
        for (Player player : inventories.keySet()) player.closeInventory();
        inventories.clear();
        HandlerList.unregisterAll(this);
    }

    private void EnsureNotDestroyed() {
        if (destroyed) throw new IllegalStateException("GUIMenu is destroyed and cannot be accessed.");
    }

    @EventHandler
    private void OnInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player) || !inventories.containsKey(e.getWhoClicked())) return;

        if (!e.getClickedInventory().getTitle().equals(inventories.get(e.getWhoClicked()).getTitle())) {
            e.setCancelled(true);
            return;
        }

        Player player = (Player) e.getWhoClicked();

        ItemClickData data = new ItemClickData(e);
        e.setCancelled(true);
        activePage.ProcessClick(e, data);
    }

    @EventHandler
    private void OnInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player) || !inventories.containsKey(e.getPlayer())) return;
        inventories.remove(e.getPlayer());
        if (inventories.size() == 0) Destroy();
        activePage.ProcessClosed(e);
    }

}
