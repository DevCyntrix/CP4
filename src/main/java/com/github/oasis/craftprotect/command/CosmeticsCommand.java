package com.github.oasis.craftprotect.command;

import com.github.oasis.craftprotect.api.CraftProtectCommand;
import com.github.oasis.craftprotect.controller.CosmeticsController;
import com.github.oasis.craftprotect.model.cosmetic.AttachedTo;
import com.github.oasis.craftprotect.model.cosmetic.CosmeticModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CosmeticsCommand implements CraftProtectCommand {

    @Inject
    private CosmeticsController controller;


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) {
            return true;
        }

        String id = args[0];
        CosmeticModel model = controller.getModelMap().get(id);

        if (!(sender instanceof Player player)) {
            return true;
        }

        Particle.DustOptions options = new Particle.DustOptions(Color.PURPLE, 0.3f);
        Location location = player.getLocation();
        for (Vector vector : model.getList()) {
            Vector v = vector.clone();
            v = controller.prepare(v, player, AttachedTo.HEAD);
            Location add = location.clone().add(v);
            add.getWorld().spawnParticle(Particle.REDSTONE, add, 1, 0, 0, 0, 0, options);
        }


        return true;
    }
}
