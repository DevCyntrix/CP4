package cp4.status;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

import static org.bukkit.Bukkit.*;

public class listeners implements Listener {

    public static String prefixPlugin = "§b[CP4 Plugin§b] ";

    private Main pl;

    public listeners(Main pl) {
        this.pl = pl;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {


        Player p = e.getPlayer();
        World world = p.getWorld();
        Location locationfire = p.getLocation().clone();
        p.setGlowing(false);

        // Math.cos(0) = 1
        // Math.sin(0) = 0

        // x = 1
        // z = 0

        File userDataFolder = pl.getUserDataFolder();
        File userData = new File(userDataFolder, p.getUniqueId() + ".yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(userData);
        long onlineTime = configuration.getLong("online-time", 0L);

        p.sendMessage(prefixPlugin + p.getDisplayName() + " §e hat das Spiel betreten");
        if(onlineTime >= 18000000){

            p.setPlayerListName("§7Neu " + p.getDisplayName());
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 0.5f);

           Bukkit.broadcastMessage(prefixPlugin + p.getDisplayName() + " hat nun den Prefix §7Neu");

        }
        else if (onlineTime >= 86400000){

            p.setPlayerListName("§eAktiv " + p.getDisplayName());
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 0.5f);
            e.setJoinMessage(prefixPlugin + p.getDisplayName() + " hat nun den Prefix §eAktiv");

        }
        else if(onlineTime >= 604800000){

            p.setPlayerListName("§6Sehr Aktiv  " + p.getDisplayName());
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 0.5f);
            e.setJoinMessage(prefixPlugin + p.getDisplayName() + " hat nun den Prefix §6 Sehr Aktiv");
        }



        double seconds = 3.0;
        double segments = 16;
        double radius = 1;

        new BukkitRunnable() {
            final long created = System.currentTimeMillis();


            @Override
            public void run() {
                if ((created + seconds * 1000) < System.currentTimeMillis()) {
                    cancel();
                    return;
                }
                for (double pa = 0.0; pa < 2 * Math.PI; pa += 2 * Math.PI / segments) {
                    Location l = locationfire.clone()
                            .add(
                                    Math.cos(pa) * radius, // X
                                    0.3, // Y
                                    Math.sin(pa) * radius // Z
                            );
                    world.spawnParticle(Particle.FLAME, l, 1, 0, 0, 0, 0);

                    Location l2 = l.clone().add(0, 0.6, 0);

                    world.spawnParticle(Particle.FLAME, l2, 1, 0, 0, 0, 0);
                }
            }
        }.runTaskTimerAsynchronously(pl, 10, 10);

        // if (p.isOp()) {
        //p.sendMessage("§7Willkommen Admin");

        e.setJoinMessage(prefixPlugin + p.getDisplayName() + " §e hat das Spiel betreten");

    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        e.setQuitMessage(prefixPlugin + e.getPlayer().getDisplayName() + " §ehat das Spiel verlassen");
    }


}
