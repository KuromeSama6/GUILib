package moe.hiktal.guilib;

public class ItemClickResult {
    public final int clickedIndex;
    public final ItemClickData result;

    public ItemClickResult(int clickedIndex, ItemClickData result) {
        this.clickedIndex = clickedIndex;
        this.result = result;
    }
}
