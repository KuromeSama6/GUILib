package moe.hiktal.guilib;

import org.bukkit.inventory.ItemStack;

public class ItemClickResult {
    public final int clickedIndex;
    public final ItemClickData result;
    public final ItemStack clicked;

    public ItemClickResult(ItemStack clicked, int clickedIndex, ItemClickData result) {
        this.clicked = clicked;
        this.clickedIndex = clickedIndex;
        this.result = result;
    }
}
