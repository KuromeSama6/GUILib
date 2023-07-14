package moe.hiktal.guilib;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(Material material, int qty) {
        item = new ItemStack(material, qty);
    }

    public ItemBuilder disp(String str) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', str));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lores) {
        ItemMeta meta = item.getItemMeta();
        List<String> current = meta.getLore();
        if (current == null) current = new ArrayList<>();
        current.addAll(Arrays.stream(lores).map(c -> ChatColor.translateAlternateColorCodes('&', c)).collect(Collectors.toList()));
        meta.setLore(current);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return item;
    }

}
