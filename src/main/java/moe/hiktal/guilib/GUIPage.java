package moe.hiktal.guilib;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.internal.runners.statements.RunAfters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GUIPage {
    public final GUIMenu menu;
    public final ItemStack[] items;
    private HashMap<Integer, BiConsumer<Player, ItemClickData>> individualCallbacks = new HashMap<>();
    private BiConsumer<Player, ItemClickResult> validHandler = (a, b) -> {};
    private Consumer<Player> invalidHandler = (p) -> {};

    public GUIPage(GUIMenu menu) {
        this.menu = menu;

        items = new ItemStack[menu.size];
    }

    public void Apply(Inventory inventory) {
        for (int i = 0; i < menu.size; i++) {
            inventory.setItem(i, items[i]);
        }
    }

    public void setInvalidHandler(Consumer<Player> invalidHandler) {
        this.invalidHandler = invalidHandler;
    }

    public void setValidHandler(BiConsumer<Player, ItemClickResult> validHandler) {
        this.validHandler = validHandler;
    }

    public void setIndividualCallbacks(HashMap<Integer, BiConsumer<Player, ItemClickData>> individualCallbacks) {
        this.individualCallbacks = individualCallbacks;
    }

    public HashMap<Integer, BiConsumer<Player, ItemClickData>> getIndividualCallbacks() {
        return individualCallbacks;
    }

    void ProcessClick(InventoryClickEvent e, ItemClickData data) {
        Player player = (Player) e.getWhoClicked();

        int slot = e.getSlot();

        ItemStack clickedItem = items[slot];
        ItemClickResult result = new ItemClickResult(items[slot], slot, data);
        if (individualCallbacks.containsKey(slot)) individualCallbacks.get(slot).accept(player, data);
        validHandler.accept(player, result);

    }

    void ProcessClosed(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        invalidHandler.accept(player);
    }

}
