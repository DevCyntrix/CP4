package com.github.oasis.craftprotect.command;

import com.github.oasis.craftprotect.api.CraftProtect;
import com.github.oasis.craftprotect.api.CraftProtectCommand;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static org.bukkit.Bukkit.getServer;

@Singleton
public class ClearItemCommand implements CraftProtectCommand {


    @Inject
    private CraftProtect plugin;

    @Override
    public @Nullable String getPermission() {
        return "cp4.admin";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        int countdownTime = 10;

        Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, new Consumer<>() {
            int countdown = countdownTime;

            @Override
            public void accept(ScheduledTask scheduledTask) {

                if (countdown == 0) {
                    clearItems();
                    scheduledTask.cancel();
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Alle Item-Drops wurden gelöscht!");
                } else {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Die Item-Drops werden in " + countdown + " Sekunden gelöscht.");
                    countdown--;
                }
            }
        }, 1, 20);// Führe die Aufgabe alle 20 Ticks (1 Sekunde) aus
        return true;
    }

    public long clearItems() {
        return getServer().getWorlds()
                .stream()
                .flatMap(world -> world.getEntitiesByClass(Item.class).stream())
                .mapToLong(value -> {
                    value.remove();
                    return 1;
                }).sum();
    }
}