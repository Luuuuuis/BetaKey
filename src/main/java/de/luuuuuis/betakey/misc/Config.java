/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 20:35.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.misc;

import de.luuuuuis.betakey.BetaKey;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class Config {

    private static Config instance;


    private final HashMap<String, Object> mysql;
    private final String prefix;
    private final String kickMessage;

    public Config(String prefix, String kickMessage, HashMap<String, Object> mysql) {
        this.prefix = prefix;
        this.kickMessage = kickMessage;
        this.mysql = mysql;
    }

    public static Config getInstance() {
        return instance;
    }

    public synchronized static void init(File dataFolder) {

        String path = dataFolder.getPath() + "/config.json";
        if (Files.notExists(Paths.get(path))) {

            // create DataFolder
            if (!dataFolder.exists())
                dataFolder.mkdir();

            // copy config
            try (InputStream in = BetaKey.class.getClassLoader().getResourceAsStream("config.json")) {
                Files.copy(Objects.requireNonNull(in), Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // read config
            try (FileReader fileReader = new FileReader(path)) {
                instance = BetaKey.GSON.fromJson(fileReader, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try (FileReader fileReader = new FileReader(path)) {
                instance = BetaKey.GSON.fromJson(fileReader, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getPrefix() {
        return prefix;
    }

    public String getKickMessage() {
        return kickMessage;
    }

    public HashMap<String, Object> getMySQLCredentials() {
        return mysql;
    }

}
