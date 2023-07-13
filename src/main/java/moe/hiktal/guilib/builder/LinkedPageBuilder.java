package moe.hiktal.guilib.builder;

import moe.hiktal.guilib.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A page that is of size 6x9, and has the bottom row reserved for page icons and a toolbar.
 * The toolbar is automatically centered.
 */
public class LinkedPageBuilder extends PageBuilder {
    private static final int PREV_SLOT = 45;
    private static final int NEXT_SLOT = 53;
    private int toolbarItemsCount;

    public LinkedPageBuilder(GUIMenu menu, ItemStack next, ItemStack prev) {
        super(menu);
        if (menu.height != 6) throw new IllegalStateException(String.format("Expected height 6 for LinkedPage, received %s", menu.height));

        // menu items
        setCursor(PREV_SLOT).addItem(prev).addCallback(PREV_SLOT, (player, data) -> {
            menu.Prev(player);
        });
        setCursor(NEXT_SLOT).addItem(next).addCallback(NEXT_SLOT, (player, data) -> {
            menu.Next(player);
        });

    }

    public LinkedPageBuilder addToolbarItem(ItemStack item) {
        if (toolbarItemsCount >= NEXT_SLOT - PREV_SLOT) throw new IllegalStateException("Toolbar is full!");
        addItem(PREV_SLOT + toolbarItemsCount + 1, item);
        ++toolbarItemsCount;
        return this;
    }

    @Override
    public LinkedPageBuilder addItem(int pos, ItemStack item) {
        if (pos > PREV_SLOT && pos < NEXT_SLOT) throw new IllegalArgumentException(String.format("Slots %s through %s must be set using addToolbarItem.", PREV_SLOT, NEXT_SLOT));
        super.addItem(pos, item);
        return this;
    }


    public static ItemStack DefaultPrev() {
        return new ItemBuilder(Material.ARROW, 1).disp("Previous Page").build();
    }

    public static ItemStack DefaultNext() {
        return new ItemBuilder(Material.ARROW, 1).disp("Next Page").build();
    }

}
