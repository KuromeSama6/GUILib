package moe.hiktal.guilib;

import moe.hiktal.guilib.builder.PageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GUILib extends JavaPlugin {
    static GUILib i;

    @Override
    public void onEnable() {
        i = this;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        if (!(sender instanceof Player)) {
            sender.sendMessage("Unavailable for console.");
            return true;
        }

        GUIMenu menu = new GUIMenu("Test", 6);

        switch (args[0]) {
            case "A":
                menu.AddPage(
                        new PageBuilder(menu)
                                .addItem(new ItemBuilder(Material.PAPER, 1)
                                        .disp("&aSimple Page")
                                        .lore("&7Click on a item to have that item announced in chat.")
                                        .build())

                                .setCursor(9)
                                .addItem(new ItemStack(Material.GRASS))
                                .addItem(new ItemStack(Material.DIRT))
                                .addItem(new ItemStack(Material.GOLD_BLOCK))
                                .addItem(new ItemStack(Material.DIAMOND_BLOCK))

                                .addItemsMasked((a, b) -> b == null, new ItemStack(Material.STAINED_GLASS_PANE))

                                .validResultHandler((p, res) -> {
                                    p.sendMessage(String.format("You clicked %s @ slot %s!", res.clicked.getType(), res.clickedIndex));
                                    p.closeInventory();
                                })

                                .build()
                );
                break;
        }

        menu.Open((Player)sender);

        return true;
    }
}
