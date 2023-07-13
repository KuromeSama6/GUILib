package moe.hiktal.guilib;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(Material material, int qty) {
        item = new ItemStack(material, qty);
    }

    public ItemBuilder disp(String str) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(str);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lores) {
        ItemMeta meta = item.getItemMeta();
        List<String> current = meta.getLore();
        current.addAll(Arrays.asList(lores));
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return item;
    }

}
