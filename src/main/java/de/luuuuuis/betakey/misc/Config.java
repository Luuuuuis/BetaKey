/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey.misc;

import de.luuuuuis.betakey.BetaKey;
import lombok.Cleanup;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    @SneakyThrows(IOException.class)
    public synchronized static void init(File dataFolder) {
        String path = dataFolder.getPath() + "/config.json";
        if (Files.notExists(Paths.get(path))) {

            // create DataFolder
            if (!dataFolder.exists())
                //noinspection ResultOfMethodCallIgnored
                dataFolder.mkdir();

            // copy config
            @Cleanup InputStream inputStream = BetaKey.class.getClassLoader().getResourceAsStream("config.json");
            Files.copy(Objects.requireNonNull(inputStream), Paths.get(path));
        }

        read(path);
    }

    @SneakyThrows(IOException.class)
    private static void read(String path) {
        @Cleanup FileReader fileReader = new FileReader(path, StandardCharsets.UTF_8);
        instance = BetaKey.GSON.fromJson(fileReader, Config.class);
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public String getKickMessage() {
        return kickMessage;
    }

    public HashMap<String, Object> getMySQLCredentials() {
        return mysql;
    }

}
