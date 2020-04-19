/*
 * Copyright (c) BlackScarx
 */

package net.blackscarx.betterchairs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateManager implements Listener {

    /**
     * Update checker
     * @return
     */

    public String getLastSpigotVersion() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://BlackScarx.github.io/BetterChairs/version.txt").openConnection();
            return new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        } catch (Exception ex) {
            System.out.println("[BetterChairs] Failed to check for updates.");
        }
        return null;
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (!p.hasPermission("betterchairs.update"))
            return;
        Bukkit.getScheduler().runTaskAsynchronously(ChairsPlugin.getPlugin(ChairsPlugin.class), new Runnable() {
            @Override
            public void run() {
                String version = getLastSpigotVersion();
                if (version == null)
                    return;
                if (!version.equals(ChairsPlugin.getPlugin(ChairsPlugin.class).getDescription().getVersion())) {
                    p.sendMessage("§a[BetterChairs] New update available! §6Version: " + version
                            + "(current " + ChairsPlugin.getPlugin(ChairsPlugin.class).getDescription().getVersion() + ")");
                    ChairsPlugin.getNMS().sendUpdate(p);
                }
            }
        });
    }

}
