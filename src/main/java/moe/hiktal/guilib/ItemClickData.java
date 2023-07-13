package moe.hiktal.guilib;

import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemClickData {
    public final boolean isRightClick, isLeftClick, isShiftClick;

    private final InventoryClickEvent event;

    public ItemClickData(InventoryClickEvent e) {
        event = e;

        isLeftClick = e.isLeftClick();
        isRightClick = e.isRightClick();
        isShiftClick = e.isShiftClick();

    }

}
