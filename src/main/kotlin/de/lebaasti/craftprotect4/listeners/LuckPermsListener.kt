package de.lebaasti.craftprotect4.listeners

import de.lebaasti.craftprotect4.functions.updateTablist
import de.lebaasti.craftprotect4.instance
import net.luckperms.api.LuckPerms
import net.luckperms.api.event.log.LogPublishEvent
import net.luckperms.api.event.node.NodeMutateEvent
import net.luckperms.api.event.user.UserLoadEvent
import net.luckperms.api.event.user.track.UserPromoteEvent
import org.bukkit.Bukkit


fun luckPermsListener(luckPerms: LuckPerms) {
    val eventBus = luckPerms.eventBus

    eventBus.subscribe(instance, NodeMutateEvent::class.java) { event: NodeMutateEvent -> onNodeMutate(event) }
}

private fun onNodeMutate(event: NodeMutateEvent) {
    Bukkit.getOnlinePlayers().forEach { it.updateTablist() }
}
