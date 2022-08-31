package cp4.status;

import com.hakan.core.HCore;
import com.hakan.core.message.title.HTitle;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;


import static cp4.status.listeners.prefixPlugin;


public class Sub implements CommandExecutor {

    private String subtitle = "TEST";

    public Sub(Main main) {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        Player player = (Player) sender;
        Location location = player.getLocation();


        player.sendTitle("Hello!", "This is a test.", 1, 20, 1);

        if (player.hasPermission("cp4.sub")) {
            player.setGlowing(true);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 0.5f);
            player.sendMessage(prefixPlugin + "§3Du leuchtest jetzt");

            Firework fireWork = location.getWorld().spawn(location, Firework.class);
            FireworkMeta fireworkMeta = fireWork.getFireworkMeta();
            fireworkMeta.addEffect(FireworkEffect.builder().flicker(true).withTrail().withColor(Color.PURPLE).build());
            fireworkMeta.setPower(1);
            fireWork.setFireworkMeta(fireworkMeta);


        }
        else player.sendMessage(prefixPlugin + "§3Du musst Sub bei Oasis4_0 oder Oreocast sein");
                return true;

                }
            }






