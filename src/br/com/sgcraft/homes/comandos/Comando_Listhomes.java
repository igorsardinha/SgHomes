package br.com.sgcraft.homes.comandos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.com.sgcraft.homes.MySQL.Manager;
import br.com.sgcraft.homes.MySQL.MySQL;

public class Comando_Listhomes implements CommandExecutor {
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		command.getName().equalsIgnoreCase("listhomes");
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cVoc\u00ea n\u00e3o pode executar este comando.");
			return true;
		}
		final Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage("");
			String homes1 = "Nenhuma.";
			try {
				final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES;");
				final ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					final String dono = rs.getString("Dono");
					final String nome = rs.getString("Nome");
					final String nomereal = rs.getString("Nomereal");
					final HashMap<String, String> map = new HashMap<String, String>();
					map.put(nome, nomereal);
					for (final String homess : map.keySet()) {
						if (dono.equals(p.getName().toLowerCase()) && Manager.ePublicaHome(homess, p.getName())) {
							homes1 = String.valueOf(String.valueOf(homes1.replace("Nenhuma.", ""))) + ", " + homess;
						}
					}
				}
			} catch (SQLException ev) {
				ev.printStackTrace();
			}
			String b = "  §6Homes p\u00fablicas: §f" + homes1;
			if (homes1.equals("Nenhuma.")) {
				b = "  §6Homes p\u00fablicas: §fNenhuma.";
			}
			p.sendMessage("§eHomes que você possui:");
			p.sendMessage(b.replace("  §6Homes p\u00fablicas: §f,", "  §6Homes p\u00fablicas: §f"));
			String homes2 = "Nenhuma.";
			try {
				final PreparedStatement ps2 = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES;");
				final ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					final String dono2 = rs2.getString("Dono");
					final String nome2 = rs2.getString("Nome");
					final String nomereal2 = rs2.getString("Nomereal");
					final HashMap<String, String> map2 = new HashMap<String, String>();
					map2.put(nome2, nomereal2);
					for (final String homess2 : map2.keySet()) {
						if (dono2.equals(p.getName().toLowerCase()) && !Manager.ePublicaHome(homess2, p.getName())) {
							homes2 = String.valueOf(String.valueOf(homes2.replace("Nenhuma.", ""))) + ", " + homess2;
						}
					}
				}
			} catch (SQLException ev2) {
				ev2.printStackTrace();
			}
			String a = "  §6Homes particulares: §f" + homes2;
			if (homes2.equals("Nenhuma.")) {
				a = "  §6Homes particulares: §fNenhuma.";
			}
			p.sendMessage(a.replace("  §6Homes particulares: §f,",  "  §6Homes particulares: §f"));
			p.sendMessage("");
			return true;
		}
		if (args.length != 1) {
			return false;
		}
		final String home_player = args[0];
		if (home_player != null) {
			final String player = home_player;
			if (!Manager.checarContaExiste(player)) {
				p.sendMessage("§cEste usu\u00e1rio n\u00e3o existe.");
				return true;
			}
			if (p.hasPermission("sgcraft.homes.admin")) {
				final String nomerealp = Manager.pegarNomePlayer(player);
				p.sendMessage("§7Homes de §6" + nomerealp + "§7:");
				String homes3 = "Nenhuma.";
				try {
					final PreparedStatement ps3 = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES;");
					final ResultSet rs3 = ps3.executeQuery();
					while (rs3.next()) {
						final String dono3 = rs3.getString("Dono");
						final String nome3 = rs3.getString("Nome");
						final String nomereal3 = rs3.getString("Nomereal");
						final HashMap<String, String> map3 = new HashMap<String, String>();
						map3.put(nome3, nomereal3);
						for (final String homess3 : map3.keySet()) {
							if (dono3.equals(player.toLowerCase()) && Manager.ePublicaHome(homess3, player)) {
								homes3 = String.valueOf(String.valueOf(homes3.replace("Nenhuma.", ""))) + ", "
										+ homess3;
							}
						}
					}
				} catch (SQLException ev3) {
					ev3.printStackTrace();
				}
				String b2 = "  §6Homes p\u00fablicas: §f" + homes3;
				if (homes3.equals("Nenhuma.")) {
					b2 = "  §6Homes p\u00fablicas: §fNenhuma.";
				}
				p.sendMessage(b2.replace("  §6Homes p\u00fablicas: §f,", "  §6Homes p\u00fablicas: §f"));
				String homes4 = "Nenhuma.";
				try {
					final PreparedStatement ps4 = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES;");
					final ResultSet rs4 = ps4.executeQuery();
					while (rs4.next()) {
						final String dono4 = rs4.getString("Dono");
						final String nome4 = rs4.getString("Nome");
						final String nomereal4 = rs4.getString("Nomereal");
						final HashMap<String, String> map4 = new HashMap<String, String>();
						map4.put(nome4, nomereal4);
						for (final String homess4 : map4.keySet()) {
							if (dono4.equals(player.toLowerCase())
									&& !Manager.ePublicaHome(homess4, player)) {
								homes4 = String.valueOf(String.valueOf(homes4.replace("Nenhuma.", ""))) + ", "
										+ homess4;
							}
						}
					}
				} catch (SQLException ev4) {
					ev4.printStackTrace();
				}
				String a2 = "  §6Homes particulares: §f" + homes4;
				if (homes4.equals("Nenhuma.")) {
					a2 = "  §6Homes particulares: §fNenhuma.";
				}
				p.sendMessage(a2.replace("  §6Homes particulares: §f,", "  §6Homes particulares: §f"));
				return true;
			}
			final String nomerealp = Manager.pegarNomePlayer(player);
			p.sendMessage("§7Homes de §6" + nomerealp + "§7:");
			String homes3 = "Nenhuma.";
			try {
				final PreparedStatement ps3 = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES;");
				final ResultSet rs3 = ps3.executeQuery();
				while (rs3.next()) {
					final String dono3 = rs3.getString("Dono");
					final String nome3 = rs3.getString("Nome");
					final String nomereal3 = rs3.getString("Nomereal");
					final HashMap<String, String> map3 = new HashMap<String, String>();
					map3.put(nome3, nomereal3);
					for (final String homess3 : map3.keySet()) {
						if (dono3.equals(player.toLowerCase()) && Manager.ePublicaHome(homess3, player)) {
							homes3 = String.valueOf(String.valueOf(homes3.replace("Nenhuma.", ""))) + ", " + homess3;
						}
					}
				}
			} catch (SQLException ev3) {
				ev3.printStackTrace();
			}
			String b2 = "  §6Homes p\u00fablicas: §f" + homes3;
			if (homes3.equals("Nenhuma.")) {
				b2 = "  §6Homes p\u00fablicas: §fNenhuma.";
			}
			p.sendMessage(b2.replace("  §6Homes p\u00fablicas: §f,", "  §6Homes p\u00fablicas: §f"));
			return true;
		}
		return false;
	}
}
