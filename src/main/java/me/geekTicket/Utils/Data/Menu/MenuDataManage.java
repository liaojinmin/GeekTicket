package me.geekTicket.Utils.Data.Menu;

import me.geekTicket.GeekTicketMain;
import me.geekTicket.Obj.MenuButtonsObj;
import me.geekTicket.Obj.MenuKey;
import me.geekTicket.Obj.MenuSettingsObj;
import me.geekTicket.Utils.Menu.MenuConfigManage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 老廖
 * @date : 2022-04-29 17:45
 **/
public class MenuDataManage {
    public static Map<String, MenuSettingsObj> MenuSettings = new HashMap<>();
    public static Map<MenuKey, MenuButtonsObj> MenuButtons = new HashMap<>();
    public static Map<String, String> MenuCommand = new HashMap<>();

    public static void LoadAllMenu() {
        MenuConfigManage.saveDefaultMenu();
        List<File> list = new ArrayList<>();
        ForFile(new File(GeekTicketMain.INSTANCE.getInstance().getDataFolder(),"menu"), list);
        for (File f : list) {
            String var100 = f.getName();
            String MenuFileName = var100.substring(0, var100.indexOf("."));
            new MenuConfigManage(MenuFileName);
        }

    }
    private static void ForFile(File f, List<File> list) {
        if (f.isDirectory()) {
            File[] amt = f.listFiles();
            for (File tmp : amt) {
                ForFile(tmp, list);
            }
        } else {
            if (f.getAbsolutePath().endsWith(".yml")) {
                list.add(f);
            }
        }
    }
}
