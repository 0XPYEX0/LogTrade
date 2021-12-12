package me.xpyex.plugin.bukkit.logtrade.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class HandleConfig {
    private static final File CONFIG_FILE = new File("plugins/LogTrade/config.json");
    public static JSONObject config;

    public static boolean loadConfig() {
        try {
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
        cfg.put("LogNormalItem", false);
        root.put("LogConfig", cfg);
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
}
