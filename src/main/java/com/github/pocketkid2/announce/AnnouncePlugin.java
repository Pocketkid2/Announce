package com.github.pocketkid2.announce;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.github.pocketkid2.announce.tasks.AnnounceTask;

public class AnnouncePlugin extends JavaPlugin {

	private static final int TICKS_PER_SEC = 20;
	private static final int SECS_PER_MIN = 60;

	public BukkitTask task = null;

	public List<String> messages = null;

	public boolean random = false;
	public int delay = 0;
	public String prefix = null;

	@Override
	public void onEnable() {
		// Save the default config
		saveDefaultConfig();

		// Load options from config
		random = getConfig().getBoolean("random-order");
		delay = getConfig().getInt("message-delay");
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("message-prefix"));

		// Load messages from config
		messages = getConfig().getStringList("messages");

		// Check if there are no messages
		if (messages.isEmpty()) {
			getLogger().info("There are no valid messages defined in config.yml! Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			// Otherwise, colorize all the messages
			for (int i = 0; i < messages.size(); i++) {
				messages.set(i, ChatColor.translateAlternateColorCodes('&', messages.get(i)));
			}
		}

		// Schedule task
		task = new AnnounceTask(this).runTaskTimer(this, 0, delay * TICKS_PER_SEC * SECS_PER_MIN);

		// Log status
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		// Cancel task
		if (task != null) {
			task.cancel();
		}

		// Log status
		getLogger().info("Done!");
	}
}
