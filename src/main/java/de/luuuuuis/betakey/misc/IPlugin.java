/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey.misc;

import java.io.File;

public interface IPlugin {

    String getVersion();

    File getFolder();

    void sendMessageConsole(String message);

}
