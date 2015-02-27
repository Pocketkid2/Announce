package com.github.pocketkid2.announce.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.announce.AnnouncePlugin;

public class BroadcastMessageTask extends BukkitRunnable {

	public AnnouncePlugin plugin;

	public BroadcastMessageTask(AnnouncePlugin pl) {
		plugin = pl;
	}

	@Override
	public void run() {
		// Get message using index
		String message = plugin.messages.get(plugin.count);

		// Broadcast it by looping through all players
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			// Check for permission
			if (p.hasPermission("iannounce.recieve")) {
				// Send the message
				p.sendMessage(message);
			}
		}

		// When we're done, we need to cycle
		plugin.cycle();
	}

}
