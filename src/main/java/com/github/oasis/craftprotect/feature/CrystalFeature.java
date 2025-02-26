package com.github.oasis.craftprotect.feature;

import com.github.oasis.craftprotect.api.Feature;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.io.IOException;

public class CrystalFeature implements Feature {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (event.getEntity().getType() != EntityType.ENDER_CRYSTAL)
            return;

        event.blockList().clear();
    }

    @Override
    public void close() throws IOException {

    }
}
