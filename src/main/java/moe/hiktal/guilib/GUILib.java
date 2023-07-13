package moe.hiktal.guilib;

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

}
