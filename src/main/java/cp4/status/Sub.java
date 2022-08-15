package cp4.status;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;


import java.util.List;

import static cp4.status.listeners.prefixPlugin;


public class Sub implements CommandExecutor {

    private String subtitle = "TEST";

    public Sub(Main main) {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Location location = player.getLocation();

        if (player.hasPermission("cp4.sub")) {
            player.sendMessage("Du leuchtest jetzt");
            player.setGlowing(true);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 0.5f);
            player.sendMessage(prefixPlugin + "§3Du leuchtest jetzt");

            Firework fireWork = location.getWorld().spawn(location, Firework.class);
            FireworkMeta fireworkMeta = fireWork.getFireworkMeta();
            List<FireworkEffect> effects = fireworkMeta.getEffects();
            effects.add(FireworkEffect.builder().flicker(true).withColor(Color.AQUA).build());
           ;
            fireWork.setFireworkMeta(fireworkMeta);
            fireWork.detonate();



             //   }
           // }
        }
        else player.sendMessage(prefixPlugin + "§3Du musst Sub bei Oasis4_0 oder Oreocast sein");


            return true;


        }

    }
