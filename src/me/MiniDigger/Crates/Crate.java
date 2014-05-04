package me.MiniDigger.Crates;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@SerializableAs("Crate")
public class Crate implements ConfigurationSerializable {

	private Location loc;
	private Inventory inv;

	public Crate(Location loc) {
		this.loc = loc;
		inv = Bukkit.createInventory(null, Crates.getInstance().getConfig()
				.getInt("crate.size") * 9, Crates.getInstance().getConfig()
				.getString("crate.display-name"));
	}

	public Crate() {
		inv = Bukkit.createInventory(null, Crates.getInstance().getConfig()
				.getInt("crate.size") * 9, Crates.getInstance().getConfig()
				.getString("crate.display-name"));
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new HashMap<>();

		result.put("loc", Utils.LocationToString(loc));
		result.put("inv", Utils.InventoryToString(inv));

		return result;
	}

	public static Crate deserialize(Map<String, Object> arg) {
		Crate result = new Crate();

		result.loc = Utils.StringToLocation((String) arg.get("loc"));
		result.inv = Utils.StringToInventory((String) arg.get("inv"));

		return result;
	}

	public void open(Player p) {
		if (!Crates.getInstance().getConfig()
				.getBoolean("use-perms-for-opening")) {
			p.openInventory(getInv());
		} else if (p.hasPermission("crate.open")) {
			p.openInventory(getInv());
		} else {
			Crates.getInstance().getPrefix().then("You don't have the ")
					.color(ChatColor.RED).then("permission ")
					.color(ChatColor.RED).tooltip("crate.open")
					.then(" to open this crate!").color(ChatColor.RED);
		}
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
}
