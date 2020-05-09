/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 17:50.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.misc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MojangUUIDResolve {

    /*
     *
     * MojangUUIDResolve made by @yanjulang
     *
     *
     */

    public static final LoadingCache<String, ProxyCacheResult> nameCache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<String, ProxyCacheResult>() {

                @Override
                public ProxyCacheResult load(String uuid) throws Exception {
                    uuid = uuid.replaceAll("-", "");
                    try {
                        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                        InputStream stream = url.openStream();
                        InputStreamReader inr = new InputStreamReader(stream);
                        BufferedReader reader = new BufferedReader(inr);
                        String s = null;
                        StringBuilder sb = new StringBuilder();
                        while ((s = reader.readLine()) != null) {
                            sb.append(s);
                        }
                        String result = sb.toString();
                        JsonArray array = new JsonParser().parse(result).getAsJsonArray();

                        if (array.size() == 0)
                            return new ProxyCacheResult();

                        return new ProxyCacheResult(InfoType.NAME,
                                array.get(array.size() - 1).getAsJsonObject().get("name").getAsString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ProxyCacheResult();
                }
            });
    static Gson gson = new Gson();
    public static final LoadingCache<String, ProxyCacheResult> uuidCache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<String, ProxyCacheResult>() {
                @Override
                public ProxyCacheResult load(String name) {
                    BufferedReader reader = null;
                    try {
                        URL url = new URL(new StringBuilder().append("https://api.mojang.com/users/profiles/minecraft/")
                                .append(name).toString());
                        URLConnection conn = url.openConnection();

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line);
                        }

                        System.out.println("Content " + content.toString());

                        MojangUUIDProfile p = gson.fromJson(content.toString(),
                                MojangUUIDProfile.class);

                        if ((p != null) && (!p.id.isEmpty())) {
                            String uuidStr = p.id;
                            String[] uuidComponents = new String[]{uuidStr.substring(0, 8), uuidStr.substring(8, 12),
                                    uuidStr.substring(12, 16), uuidStr.substring(16, 20),
                                    uuidStr.substring(20)};

                            // Combine components with a dash
                            StringBuilder builder = new StringBuilder();
                            for (String component : uuidComponents) {
                                builder.append(component).append('-');
                            }

                            // Correct uuid length, remove last dash
                            builder.setLength(builder.length() - 1);
                            return new ProxyCacheResult(InfoType.UUID, builder.toString());
                        }

                    } catch (IOException e) {
                        ProxyServer.getInstance().getConsole()
                                .sendMessage(new TextComponent("Can't retrieve UUID from mojang server!"));
                    } finally {
                        if (reader != null)
                            try {
                                reader.close();
                            } catch (IOException ignored) {
                            }
                    }
                    return new ProxyCacheResult();
                }
            });

    public static ProxyCacheResult getNameResult(String uuid) {
        try {
            return nameCache.get(uuid);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProxyCacheResult getUUIDResult(String name) {
        try {
            return uuidCache.get(name);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUUID(String name) {
        return getUUIDResult(name).getValue();
    }

    public String getName(String uuid) {
        return getNameResult(uuid).getValue();
    }

    public enum InfoType {
        NAME, UUID;

        public static InfoType fromString(String type) {
            for (InfoType t : values())
                if (t.name().equalsIgnoreCase(type))
                    return t;
            return null;
        }
    }

    public static class ProxyCacheResult {

        private InfoType type;

        private String value;

        public ProxyCacheResult() {
            this.type = null;
            this.value = null;
        }

        public ProxyCacheResult(InfoType type, String value) {
            this.type = type;
            this.value = value;
        }

        public InfoType getType() {
            return this.type;
        }

        public void setType(InfoType type) {
            this.type = type;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isNull() {
            return this.type == null || this.value == null;
        }
    }

    public class MojangUUIDProfile {
        String id;
        String name;

        private MojangUUIDProfile() {
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }
    }
}