package com.github.oasis.craftprotect.feature;

import com.github.oasis.craftprotect.api.Feature;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.annotation.Nullable;
import java.io.IOException;

@Singleton
public class ChatFeature implements Feature {

    @Inject
    @Nullable
    private LuckPerms luckPerms;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (luckPerms == null)
            return;

        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        String playerPrefix = user.getCachedData().getMetaData().getPrefix();
        if(playerPrefix == null)
            playerPrefix = "";

        String playerSuffix = user.getCachedData().getMetaData().getSuffix();
        if(playerSuffix == null)
            playerPrefix = "";

        String messagePrefix = user.getCachedData().getMetaData().getMetaValue("message-prefix");
        if (messagePrefix == null)
            messagePrefix = "";

        event.setFormat(ChatColor.translateAlternateColorCodes('&', playerPrefix + "%s" + playerSuffix + " §8»§r " + messagePrefix + "%s"));
    }

    @Override
    public void close() throws IOException {

    }
}
