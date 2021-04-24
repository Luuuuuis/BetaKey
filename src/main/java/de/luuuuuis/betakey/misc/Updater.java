/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey.misc;

import com.google.gson.JsonObject;
import de.luuuuuis.betakey.BetaKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {

    private String tag_name;
    //private String browser_download_url;

    public Updater() {
        try {
            JsonObject jsonObject = BetaKey.GSON.fromJson(readJSONFromURL(), JsonObject.class);
            this.tag_name = jsonObject.get("tag_name").getAsString();
            //browser_download_url = jsonObject.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString();

            if (isNew())
                report();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void report() {
        BetaKey.instance.getIPlugin().sendMessageConsole("§cA newer version of BetaKey is available. Please consider downloading the new version.\n" +
                "More information at https://github.com/Luuuuuis/BetaKey/releases/latest.");
    }

    private String readJSONFromURL() throws IOException {
        URL url = new URL("https://api.github.com/repos/Luuuuuis/BetaKey/releases/latest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            connection.disconnect();

            return content.toString();
        }
    }

//    private void download() {
//        try (InputStream in = new URL(browser_download_url).openStream()) {
//            Files.copy(in, Paths.get(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean isNew() {
        return !tag_name.equals(BetaKey.instance.getIPlugin().getVersion());
    }
}
