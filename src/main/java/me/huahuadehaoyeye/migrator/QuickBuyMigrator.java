package me.huahuadehaoyeye.migrator;

import com.andrei1058.bedwars.api.BedWars;
import lombok.Getter;
import me.huahuadehaoyeye.migrator.config.FileManager;
import me.huahuadehaoyeye.migrator.core.LayoutMigrator;
import me.huahuadehaoyeye.migrator.listeners.InventoryListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class QuickBuyMigrator extends JavaPlugin {

    @Getter
    private static QuickBuyMigrator instance;
    private FileManager fileManager;
    private LayoutMigrator migrator;
    private BedWars bedWars;

    @Override
    public void onEnable() {
       instance = this;
       fileManager = new FileManager(this);
       if (fileManager.getConfig().getString("hypixel-api-key").isEmpty()) {
           getLogger().severe("Hypixel的API密钥没有设置! 请在config.yml中设置!");
           getLogger().severe("要获得你的API密钥，请加入mc.hypixel.net并使用/api new命令");
           getServer().getPluginManager().disablePlugin(this);
           return;
       }
       bedWars = getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
       migrator = new LayoutMigrator(this);
       getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
       new Metrics(this, 16340);
    }

}
