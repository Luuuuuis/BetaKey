/*
 * Developed by Luuuuuis on 04.05.19 18:00.
 * Last modified 04.05.19 17:58.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.luuuuuis.betakey.BetaKey;
import net.md_5.bungee.api.ChatColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerConfig {

    private HashMap<String, Object> MySQLCredentials = new HashMap<>();
    private BetaKey betaKey;
    private String prefix;
    private String kickmessage;

    public ServerConfig(BetaKey betaKey) {
        this.betaKey = betaKey;
        query();
    }

    private void query() {
        Thread thread = new Thread(() -> {

            File file = new File(betaKey.getDataFolder().getPath(), "config.json");
            if (!file.exists()) {
                if (!betaKey.getDataFolder().exists())
                    betaKey.getDataFolder().mkdir();

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try (FileWriter fileWriter = new FileWriter(betaKey.getDataFolder().getPath() + "/config.json")) {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("prefix", "&9BetaKey &8>> &7");
                    jsonObject.put("kickmessage", "&bBetaKey\n\n&7You have to redeem your key on &cluis.team/BetaKeyV2&7.");

                    JSONObject MySQL = new JSONObject();
                    MySQL.put("active", true);
                    MySQL.put("Host", "localhost");
                    MySQL.put("Port", 3306);
                    MySQL.put("database", "beta");
                    MySQL.put("User", "root");
                    MySQL.put("Password", "yourPassword");
                    jsonObject.put("mysql", MySQL);

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    fileWriter.write(gson.toJson(jsonObject));
                    fileWriter.flush();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            try {
                Object object = new JSONParser().parse(new FileReader(betaKey.getDataFolder().getPath() + "/config.json"));
                JSONObject jsonObject = (JSONObject) object;

                prefix = ChatColor.translateAlternateColorCodes('&', jsonObject.get("prefix").toString());
                kickmessage = jsonObject.get("kickmessage").toString();

                Map MySQLJSON = (Map) jsonObject.get("mysql");
                for (Object o : MySQLJSON.entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    MySQLCredentials.put(pair.getKey().toString(), pair.getValue());
                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getKickmessage() {
        return kickmessage;
    }

    public HashMap<String, Object> getMySQLCredentials() {
        return MySQLCredentials;
    }
}
