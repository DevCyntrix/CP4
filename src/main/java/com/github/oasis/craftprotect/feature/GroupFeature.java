package com.github.oasis.craftprotect.feature;

import com.github.oasis.craftprotect.CraftProtectPlugin;
import com.github.oasis.craftprotect.api.Feature;
import com.github.oasis.craftprotect.controller.PlayerDisplayController;
import com.github.oasis.craftprotect.controller.PlaytimeController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Singleton
public class GroupFeature implements Feature {

    private final ScheduledTask updaterTask;
    @Inject
    private PlayerDisplayController controller;

    @Inject
    private PlaytimeController playtimeController;

    @Inject
    public GroupFeature(CraftProtectPlugin plugin) {
        this.updaterTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, task -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playtimeController.getPlaytime(onlinePlayer)
                        .thenAccept(time -> controller.updateGroup(onlinePlayer, time));
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        playtimeController.getPlaytime(player)
                .thenAccept(time -> controller.updateGroup(player, time));
    }

    @Override
    public void close() throws IOException {
        this.updaterTask.cancel();
    }
}
