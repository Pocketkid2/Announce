package com.github.pocketkid2.announce;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.github.pocketkid2.announce.tasks.BroadcastMessageTask;

public class AnnouncePlugin extends JavaPlugin {

	// The message task that needs to be cancelled on plugin disable
	public BukkitTask task;

	// The list of messages defined in the config
	public List<String> messages;

	// The config value for the delay (in seconds) between messages
	public int delay;

	// The config value for whether messages should be chosen at random
	public boolean random;

	// The variable that represents the next message to be displayed (list
	// index)
	public int count;

	// The random number generator
	private Random r;

	@Override
	public void onEnable() {
		// Add config if it doesn't exist
		saveDefaultConfig();

		// Load values from config
		delay = getConfig().getInt("delay", 600);
		random = getConfig().getBoolean("random", false);

		// Initialize Random
		if (random) {
			r = new Random();
		} else {
			r = null;
		}

		// Load messages from config
		messages = getConfig().getStringList("messages");

		// Replace colors
		for (String s : messages) {
			s = ChatColor.translateAlternateColorCodes('&', s);
		}

		// Check for missing messages
		if (messages.isEmpty()) {
			getLogger().warning("No messages configured in config.yml!");
		} else {
			// Initialize the count variable
			count = -1;

			// Start the messages
			cycle();
			task = new BroadcastMessageTask(this).runTaskTimer(this, 0, delay * 20);
		}

		// Log status
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		// Cancel the message task
		task.cancel();

		// Log status
		getLogger().info("Done!");
	}

	// Modify the variable 'count', either cycle through, or choose random ones
	public void cycle() {
		// If we chose random
		if (random) {
			// Make it a random number
			count = r.nextInt(messages.size());
			// Otherwise, let's do a numeric order cycle
		} else {
			// Increase by one
			count++;

			// Move to 0 if at end
			if (count >= messages.size()) {
				count = 0;
			}
		}
	}

}
