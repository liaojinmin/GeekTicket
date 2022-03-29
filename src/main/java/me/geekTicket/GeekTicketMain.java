package me.geekTicket;

import me.arasple.mc.trmenu.TrMenu;
import me.geekTicket.Command.CommandCore;
import me.geekTicket.Libs.LibsManager;
import me.geekTicket.Metrics.Metrics;
import me.geekTicket.TicketAction.Trmenu.GiveTicket;
import me.geekTicket.TicketAction.Trmenu.TakeTicket;
import me.geekTicket.Utils.Bukkit.ConfigLoad;
import me.geekTicket.Utils.Bukkit.LangLoad;
import me.geekTicket.Utils.CheckUpdate;
import me.geekTicket.Utils.Data.DataBaseManager;
import me.geekTicket.Utils.Placeholder.Papi_Hook;
import me.geekTicket.Utils.Task.Task;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class GeekTicketMain extends JavaPlugin {

    public static GeekTicketMain instance;
    public static final String Version = String.valueOf(1.7);
    public static final String BukkitVersion = Bukkit.getBukkitVersion();

    @Override
    public void onLoad() {
        instance = this;
        say("");
        say("正在加载 §3§lGeekTicket §f...  §8"+ Bukkit.getVersion());
        say("");
        saveDefaultConfig();
        ConfigLoad.loadConfig();
        LangLoad.loadlang();
    }


    @Override
    public void onEnable() {
        long stime = System.currentTimeMillis();
        say("");
        say("     §aGeekTicket §bv"+Version+" §7by §awww.geekcraft.ink");
        say("     §8适用于Bukkit: §71.7.10-1.18.1 §8当前: §8" + getServer().getName());
        say("");
        LibsManager.loadMyLibs();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            say("§8[§3§lGeekTicket§8] §8找到 §7PlaceholderAPI §8启用变量功能");
            new Papi_Hook(this).register();
        }
        if (Bukkit.getPluginManager().getPlugin("TrMenu") != null) {
            int b = 0;  b = Integer.parseInt(TrMenu.INSTANCE.getPlugin().getDescription().getVersion().replace(".","").replace("RC", "").replace("-",""));
            if (b >= 317) {
                say("§8[§3§lGeekTicket§8] §8找到 §7TrMenu §8启用快捷动作");
                new GiveTicket(TrMenu.INSTANCE.getActionHandle()).register();
                new TakeTicket(TrMenu.INSTANCE.getActionHandle()).register();
            }
        }

        say("§8[§3§lGeekTicket§8] §8注册指令...");
        Objects.requireNonNull(getCommand("geekt")).setExecutor(new CommandCore());
       // getServer().getPluginManager().registerEvents(new Join_event(),this);

        new DataBaseManager();
        Task.getLeaderboard();
        new Metrics(this, 14521);
        new CheckUpdate();
        say("§8[§3§lGeekTicket§8] §b启动完成 §8(" + (System.currentTimeMillis() - stime) + " ms)");

    }

    @Override
    public void onDisable() {
        say("§8[§3§lGeekTicket§8] §A再见！");
        DataBaseManager.closeData();
    }

    public static void say(String s) {
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(s);
    }
}
