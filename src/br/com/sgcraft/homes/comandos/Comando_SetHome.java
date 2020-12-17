package br.com.sgcraft.homes.comandos;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import br.com.sgcraft.homes.MySQL.*;
import br.com.sgcraft.homes.Main;

public class Comando_SetHome implements CommandExecutor {
	private Main plugin;

	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		command.getName().equalsIgnoreCase("sethome");
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cVoc\u00ea n\u00e3o pode executar este comando.");
			return true;
		}
		final Player p = (Player) sender;
		if (args.length == 0 || args.length > 1) {
			p.sendMessage("§cUtilize /sethome <nome> para setar uma home.");
			return true;
		}
		if (args.length != 1) {
			return false;
		}

		final String home = args[0];
		if (p.hasPermission("sgcraft.homes.admin")) {
			if (home.contains(":")) {
				if (args[0].endsWith(":")) {
					final int tem = home.length() - 1;
					final String at = new StringBuilder().append(home.charAt(tem)).toString();
					final String player = home.replace(at, "");
					if (!Manager.checarContaExiste(player)) {
						p.sendMessage("§cEste usu\u00e1rio n\u00e3o existe.");
						return true;
					}
					p.sendMessage("§cUtilize /sethome [player:]<home>.");
					return true;
				} else {
					final String[] parts = home.split(":");
					final String player2 = parts[0];
					if (!Manager.checarContaExiste(player2)) {
						p.sendMessage("§cEste usu\u00e1rio n\u00e3o existe.");
						return true;
					}
					final String casa = parts[1];
					if (Manager.checarHomeExiste(casa, player2)) {
						final String nomerealp = Manager.pegarNomePlayer(player2);
						p.sendMessage("§aHome '" + casa + "' de " + nomerealp + " foi atualizada.");
						Manager.attHome(casa, player2, p.getLocation());
						return true;
					}
					final String nomerealp = Manager.pegarNomePlayer(player2);
					p.sendMessage("§aHome '" + casa + "' de " + nomerealp + " criada com sucesso.");
					Manager.addHome(casa, player2, p.getLocation());
					return true;
				}
			} else {
				if (Manager.checarHomeExiste(home, p.getName())) {
					p.sendMessage("§aHome '" + home + "' foi atualizada.");
					Manager.attHome(home, p.getName(), p.getLocation());
					return true;
				} else if (home.contains(":")) {
					p.sendMessage("§cVoc\u00ea inseriu um caractere inv\u00e1lido.");
					return true;
				} else if (home.length() > 16) {
					p.sendMessage("§cUtilize no m\u00e1ximo 16 caracteres.");
					return true;
				} else {
					p.sendMessage("§aHome '" + home + "' criada com sucesso.");
					Manager.addHome(home, p.getName(), p.getLocation());
					Main.sendJSONMsg(p, "§r", "§e§lCLIQUE AQUI PARA TORNAR PÚBLICA", "§r",
							"§aTornar uma home publica.", "/publica " + home);
					return true;
				}
			}
		} else {
			if (p.hasPermission("sgcraft.homes.50") && Manager.pegarNumeroHomes(p.getName()) >= 50) {
				p.sendMessage("§cN\u00famero de homes m\u00e1ximo atingido.");
				return true;
			}
			if (p.hasPermission("sgcraft.homes.40") && Manager.pegarNumeroHomes(p.getName()) >= 40) {
				p.sendMessage("§cN\u00famero de homes m\u00e1ximo atingido.");
				return true;
			}
			if (p.hasPermission("sgcraft.homes.30") && Manager.pegarNumeroHomes(p.getName()) >= 30) {
				p.sendMessage("§cN\u00famero de homes m\u00e1ximo atingido.");
				return true;
			}
			if (p.hasPermission("sgcraft.homes.20") && Manager.pegarNumeroHomes(p.getName()) >= 20) {
				p.sendMessage("§cN\u00famero de homes m\u00e1ximo atingido.");
				return true;
			}
			if (!p.hasPermission("sgcraft.homes.20") && !p.hasPermission("sgcraft.homes.30") && !p.hasPermission("sgcraft.homes.40") && !p.hasPermission("sgcraft.homes.50") && Manager.pegarNumeroHomes(p.getName()) >= 10) {
				p.sendMessage("§cN\u00famero de homes m\u00e1ximo atingido.");
				return true;
			}
			if (home.contains(":")) {
				p.sendMessage("§cVoc\u00ea inseriu um caractere inv\u00e1lido.");
				return true;
			}
			if (home.length() > 16) {
				p.sendMessage("§cUtilize no m\u00e1ximo 16 caracteres.");
				return true;
			}
			if (Manager.checarHomeExiste(home, p.getName())) {
				p.sendMessage("§aHome '" + home + "' foi atualizada.");
				Manager.attHome(home, p.getName(), p.getLocation());
			} else {
				p.sendMessage("§aHome '" + home + "' criada com sucesso.");
				Manager.addHome(home, p.getName(), p.getLocation());
				plugin.sendJSONMsg(p, "§r", "§e§lCLIQUE AQUI PARA TORNAR PÚBLICA", "§r", "§aTornar uma home publica.",
						"/publica " + home);
				return true;
			}
		}
		return false;
	}
}
