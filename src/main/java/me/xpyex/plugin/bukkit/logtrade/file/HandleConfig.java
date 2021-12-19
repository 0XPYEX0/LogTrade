package me.xpyex.plugin.bukkit.logtrade.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class HandleConfig {
    public static final File CONFIG_FILE = new File("plugins/LogTrade/config.json");
    public static JSONObject config;

    public static boolean loadConfig() {
        try {
            if (!HandleAllFile.ROOT.exists()) {
                return HandleAllFile.loadAllFiles();
            }
            if (!CONFIG_FILE.exists()) {
                createConfigFile();
            }
            StringBuilder configText = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(CONFIG_FILE), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                configText.append(line);
            }
            in.close();
            config = JSON.parseObject(configText.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return config != null;
    }

    private static JSONObject getNewConfig() {
        JSONObject root = new JSONObject();
        JSONObject cfg = new JSONObject();
        cfg.put("LogDrop", false);
        cfg.put("LogPickUp", true);
        cfg.put("LogInvGet", true);
        cfg.put("LogInvDrop", true);
        cfg.put("LogTradeMe", true);
        cfg.put("LogCraft", true);
        root.put("LogConfig", cfg);
        root.put("ConsoleLog", true);

        JSONObject itemWL = new JSONObject();
        itemWL.put("Enabled", true);
        JSONArray items = new JSONArray();
        itemWL.put("Items", items);
        root.put("ItemWhiteList", itemWL);

        JSONObject typeWL = new JSONObject();
        typeWL.put("Enabled", true);
        JSONArray types = new JSONArray();
        typeWL.put("Types", types);
        root.put("TypeWhiteList", typeWL);

        return root;
    }

    private static void createConfigFile() {
        try {
            CONFIG_FILE.createNewFile();
            PrintWriter out = new PrintWriter(CONFIG_FILE, "UTF-8");
            out.print(JSON.toJSONString(getNewConfig(), true));
            out.flush();
            out.close();
        } catch (Exception ignored) {}
    }

    public static boolean reloadConfig() {
        config = null;
        return loadConfig();
    }
}
