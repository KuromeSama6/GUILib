package moe.hiktal.guilib.builder;

import moe.hiktal.guilib.GUIMenu;
import moe.hiktal.guilib.GUIPage;
import moe.hiktal.guilib.ItemClickData;
import moe.hiktal.guilib.ItemClickResult;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PageBuilder {
    public final GUIMenu menu;
    private final GUIPage page;
    private final int max;
    private int cursor = 0;
    private final HashMap<Integer, BiConsumer<Player, ItemClickData>> individualCallbacks = new HashMap<>();

    public PageBuilder(GUIMenu menu) {
        this.menu = menu;
        max = menu.size - 1;
        page = new GUIPage(menu);
    }

    public int getCursor() {return cursor;}
    public PageBuilder setCursor() {

        return this;
    }
    public PageBuilder incrementCursor() {
        cursor++;
        if (cursor > max) cursor = 0;
        return this;
    }

    public PageBuilder setCursor(int pos) {
        if (cursor > max) throw new IllegalArgumentException(String.format("Cursor pos %s larger than max pos of %s", pos, max));
        cursor = pos;
        return this;
    }

    public PageBuilder addItem(ItemStack item) {
        page.items[cursor] = item;
        return this;
    }

    public PageBuilder addItem(int pos, ItemStack item) {
        setCursor(pos);
        addItem(item);
        return this;
    }

    public PageBuilder addCallback(int pos, BiConsumer<Player, ItemClickData> callback) {
        setCursor(pos);
        individualCallbacks.put(pos, callback);
        return this;
    }

    public PageBuilder validResultHandler(BiConsumer<Player, ItemClickResult> handler) {
        page.setValidHandler(handler);
        return this;
    }

    public PageBuilder invalidResultHandler(Consumer<Player> handler) {
        page.setInvalidHandler(handler);
        return this;
    }

    public GUIPage build() {
        page.setIndividualCallbacks(individualCallbacks);
        return page;
    }

}
