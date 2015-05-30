package com.github.pocketkid2.announce.tasks;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.pocketkid2.announce.AnnouncePlugin;

public class AnnounceTask extends BukkitRunnable {

	private AnnouncePlugin plugin;

	private int count = 0;
	private Random random = null;

	public AnnounceTask(AnnouncePlugin pl) {
		plugin = pl;
		count = 0;
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public void run() {
		// Start with the prefix
		String message = plugin.prefix;

		// If we are doing random order
		if (plugin.random) {
			// TODO change this to be like a shuffle bag
			// Set the count to a random number that is within the size
			count = random.nextInt(plugin.messages.size());
			// And then grab it
			message += plugin.messages.get(count);
		} else {
			// Otherwise, we need to grab it first
			message += plugin.messages.get(count);
			// And then increment or reset the counter
			count++;
			if (count == plugin.messages.size()) {
				count = 0;
			}
		}

		// Display it
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("iannounce.recieve")) {
				p.sendMessage(message);
			}
		}
	}

}
