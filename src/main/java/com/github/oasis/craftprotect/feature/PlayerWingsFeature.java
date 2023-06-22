package com.github.oasis.craftprotect.feature;

import com.github.oasis.craftprotect.CraftProtectPlugin;
import com.github.oasis.craftprotect.api.Feature;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;

@Singleton
public class PlayerWingsFeature implements Feature {

    @Inject
    private CraftProtectPlugin plugin;

    public PlayerWingsFeature() throws IOException {
    }




/*    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        plugin.attachAsyncRepeaterTask(event.getPlayer(), "wings", () -> {
            float rotation = event.getPlayer().getLocation().getYaw();
            try {
                Object handle = event.getPlayer().getClass().getMethod("getHandle").invoke(event.getPlayer());
                rotation = (float) handle.getClass().getField("aV").get(handle);
            } catch (Exception ignored) {
                System.out.println("Not found");
            }

            double radians = Math.toRadians(rotation);

            double cos = Math.cos(radians);
            double sin = Math.sin(radians);

            Vector directionVector = new Vector();
            //directionVector.setX(-Math.sin(Math.toRadians(rotation)));
            //directionVector.setZ(Math.cos(Math.toRadians(rotation)));
            //directionVector = directionVector.multiply(0.2F);
            directionVector.setY(-0.3);

            Location clone = event.getPlayer().getEyeLocation().clone().subtract(directionVector);

            Particle.DustOptions options = new Particle.DustOptions(Color.PURPLE, 0.3f);

            for (Vector vector : vectors) {
                Vector rotatedVector = new Vector(cos * vector.getX() - sin * vector.getZ(), vector.getY(), sin * vector.getX() + cos * vector.getZ());
                Location add = clone.clone().add(rotatedVector);
                add.getWorld().spawnParticle(Particle.REDSTONE, add, 1, 0, 0, 0, 0, options);
            }


        }, 2, 2);

    }*/

    @Override
    public void close() throws IOException {

    }
}
