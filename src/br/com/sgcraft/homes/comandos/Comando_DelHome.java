package br.com.sgcraft.homes.comandos;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import br.com.sgcraft.homes.MySQL.*;

public class Comando_DelHome implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        command.getName().equalsIgnoreCase("delhome");
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVoc\u00ea n\u00e3o pode executar este comando.");
            return true;
        }
        final Player p = (Player)sender;
        if (args.length == 0 || args.length > 1) {
            p.sendMessage("§cUtilize /delhome <nome> para deletar uma home.");
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        final String home = args[0];
        if (home.contains(":")) {
            if (!p.hasPermission("sgcraft.homes.admin")) {
                p.sendMessage("§cHome '" + home + "' n\u00e3o existe.");
                return true;
            }
            if (args[0].endsWith(":")) {
                final int tem = home.length() - 1;
                final String at = new StringBuilder().append(home.charAt(tem)).toString();
                final String player = home.replace(at, "");
                if (!Manager.checarContaExiste(player)) {
                    p.sendMessage("§cEste usu\u00e1rio n\u00e3o existe.");
                    return true;
                }
                p.sendMessage("§cUtilize /delhome [player:]<home>.");
                return true;
            }
            else {
                final String[] parts = home.split(":");
                final String player2 = parts[0];
                if (!Manager.checarContaExiste(player2)) {
                    p.sendMessage("§cEste usu\u00e1rio n\u00e3o existe.");
                    return true;
                }
                final String casa = parts[1];
                if (!Manager.checarHomeExiste(casa, player2)) {
                    final String nomerealp = Manager.pegarNomePlayer(player2);
                    p.sendMessage("§cHome '" + casa + "' de " + nomerealp + " n\u00e3o existe.");
                    return true;
                }
                final String nomerealp = Manager.pegarNomePlayer(player2);
                p.sendMessage("§aHome '" + casa + "' de " + nomerealp + " deletada com sucesso.");
                Manager.deletarHome(casa, player2);
                return true;
            }
        }
        else {
            if (!Manager.checarHomeExiste(home, p.getName())) {
                p.sendMessage("§cHome '" + home + "' n\u00e3o existe.");
                return true;
            }
            final String nomereal = Manager.pegarNomeHome(home, p.getName());
            p.sendMessage("§aHome '" + nomereal + "' deletada com sucesso.");
            Manager.deletarHome(home, p.getName());
            return true;
        }
    }
}
