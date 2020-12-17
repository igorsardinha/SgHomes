package br.com.sgcraft.homes.comandos;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import br.com.sgcraft.homes.MySQL.*;

public class Comando_Particular implements CommandExecutor {
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		command.getName().equalsIgnoreCase("particular");
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cVoc\u00ea n\u00e3o pode executar este comando.");
			return true;
		}
		final Player p = (Player) sender;
		if (p.hasPermission("sgcraft.homes.publicar")) {
			if (args.length == 0 || args.length > 1) {
				p.sendMessage("§cUtilize /particular <home> para tornar uma home particular.");
				return true;
			}
			if (args.length != 1) {
				return false;
			}
			final String home = args[0];
			if (!Manager.checarHomeExiste(home, p.getName())) {
				p.sendMessage("§cHome '" + home + "' n\u00e3o existe.");
				return true;
			}
			if (!Manager.ePublicaHome(home, p.getName())) {
				p.sendMessage("§cHome '" + home + "' j\u00e1 \u00e9 particular.");
				return true;
			}
			final String nomereal = Manager.pegarNomeHome(home, p.getName());
			p.sendMessage("§aHome '" + nomereal + "' agora \u00e9 particular.");
			Manager.setarPublica(home, p.getName(), "NAO");
			return true;
		} else {
			p.sendMessage("§cVocê não tem permissão para fazer isto.");
		}
		return true;
	}
}
