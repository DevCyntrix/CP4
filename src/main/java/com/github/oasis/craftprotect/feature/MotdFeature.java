package com.github.oasis.craftprotect.feature;

import com.github.oasis.craftprotect.CraftProtectPlugin;
import com.github.oasis.craftprotect.api.Feature;
import com.github.oasis.craftprotect.controller.MotdController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

@Singleton
public class MotdFeature implements Feature {

    @Inject
    private MotdController controller;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Component messageOfTheDay = controller.getMessageOfTheDay();
        if (messageOfTheDay != null) {
            player.sendMessage(messageOfTheDay);
        }
    }

    @Override
    public void close() throws IOException {

    }
}
