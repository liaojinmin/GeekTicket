package me.geekTicket;

import me.arasple.mc.trmenu.TrMenu;
import me.geekTicket.Command.CommandCore;
import me.geekTicket.Events.Join_event;
import me.geekTicket.Libs.LibsManager;
import me.geekTicket.Metrics.Metrics;
import me.geekTicket.RollAction.ActionManager;
import me.geekTicket.RollAction.Trmenu.GiveTicket;
import me.geekTicket.RollAction.Trmenu.TakeTicket;
import me.geekTicket.Utils.CheckUpdate;
import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Placeholder.Papi_Hook;
import me.geekTicket.Utils.Task.Task;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public final class GeekTicketMain extends JavaPlugin {

    public static GeekTicketMain instance;
    public static final String Version = String.valueOf(1.7);
    public static final String BukkitVersion = Bukkit.getBukkitVersion();


    @Override
    public void onEnable() {
        new GiveTicket(TrMenu.INSTANCE.getActionHandle()).register();
        instance = this;
        long stime = System.currentTimeMillis();
        say("");
        say("     §aGeekTicket §bv"+Version+" §7by §awww.geekcraft.ink");
        say("     §8适用于Bukkit: §71.7.10-1.18.1 §8当前: §8"+ Bukkit.getVersion());
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

        say("§8[§3§lGeekTicket§8] §8加载配置文件...");
        saveDefaultConfig();
        ConfigManager.saveLangConfig();
        say("§8[§3§lGeekTicket§8] §8注册指令...");
        Objects.requireNonNull(getCommand("geekt")).setExecutor(new CommandCore());
        getServer().getPluginManager().registerEvents(new Join_event(),this);
        new ActionManager();
        new DataManager();
        Task.getLeaderboard();
        new Metrics(this, 14521);
        new CheckUpdate();
        if (Integer.parseInt(CheckUpdate.getNewVersion.get("Int")) > Integer.parseInt(Version.replace(".",""))) {
            say("§8[§3§lGeekTicket§8] §8发现新版本待更新, 当前版本:§f " + Version + " §8最新版本:§f " + CheckUpdate.getNewVersion.get("New"));
            say("§8[§3§lGeekTicket§8] §8Found a new version to be updated, the current version:§f " + Version + " §8New:§f " + CheckUpdate.getNewVersion.get("New"));
        } else {
            say("§8[§3§lGeekTicket§8] §8你使用的是最新版本§f "+Version);
        }
        say("§8[§3§lGeekTicket§8] §b启动完成 §8(" + (System.currentTimeMillis() - stime) + " ms)");

    }

    @Override
    public void onDisable() {
        say("§8[§3§lGeekTicket§8] §A再见！");
        DataManager.closeData();
    }

    public static void say(String s) {
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(s);
    }
}
