package br.com.sgcraft.homes.comandos;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import br.com.sgcraft.homes.*;
import br.com.sgcraft.homes.MySQL.*;

import org.bukkit.*;
import java.sql.*;
import java.util.*;

public class Comando_Home implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        command.getName().equalsIgnoreCase("home");
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVoc\u00ea n\u00e3o pode executar este comando.");
            return true;
        }
        final Player p = (Player)sender;
        if (args.length == 0) {
        	p.sendMessage("");
        	p.sendMessage(  "§e------- §6§lHOMES §e-------");
        	p.sendMessage(" §f/sethome [home] §8- §7Seta uma nova Home.");
        	p.sendMessage(" §f/home [home] §8- §7Vai para uma home sua.");
        	p.sendMessage(" §f/home [nick]:<home> §8- §7Ir para uma Home de outro Player.");
        	p.sendMessage(" §f/listhomes §8- §7Mostra as homes que você tem.");
        	p.sendMessage(" §f/publica [home] §8- §7Define home como pública.");
        	p.sendMessage(" §f/particular [home] §8- §7Define home como particular.");
        	p.sendMessage(" §f/delhome [home] §8- §7Apagar uma Home.");
        	p.sendMessage("");
        }
        if (args.length > 1) {
        	p.sendMessage("");
            p.sendMessage("§cUtilize /home [player:]<home>.");
            p.sendMessage("§1");
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
            }
            catch (SQLException ev) {
                ev.printStackTrace();
            }
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
            }
            catch (SQLException ev2) {
                ev2.printStackTrace();
            }
        }
        if (args.length != 1) {
            return false;
        }
        final String home = args[0];
        if (home.contains(":")) {
            if (args[0].endsWith(":")) {
                final int tem = home.length() - 1;
                final String at = new StringBuilder().append(home.charAt(tem)).toString();
                final String player = home.replace(at, "");
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
                                    homes3 = String.valueOf(String.valueOf(homes3.replace("Nenhuma.", ""))) + ", " + homess3;
                                }
                            }
                        }
                    }
                    catch (SQLException ev3) {
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
                                if (dono4.equals(player.toLowerCase()) && !Manager.ePublicaHome(homess4, player)) {
                                    homes4 = String.valueOf(String.valueOf(homes4.replace("Nenhuma.", ""))) + ", " + homess4;
                                }
                            }
                        }
                    }
                    catch (SQLException ev4) {
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
                }
                catch (SQLException ev3) {
                    ev3.printStackTrace();
                }
                String b2 = "  §6Homes p\u00fablicas: §f" + homes3;
                if (homes3.equals("Nenhuma.")) {
                    b2 = "  §6Homes p\u00fablicas: §fNenhuma.";
                }
                p.sendMessage(b2.replace("  §6Homes p\u00fablicas: §f,", "  §6Homes p\u00fablicas: §f"));
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
                if (!Manager.ePublicaHome(casa, player2)) {
                    final String nomerealp = Manager.pegarNomePlayer(player2);
                    if (p.hasPermission("sgcraft.homes.admin")) {
                        final Location loc = Manager.pegarHomePlayer(casa, player2);
                        p.teleport(loc);
                        p.sendMessage("§aTeleportado com sucesso.");
                        return true;
                    }
                    p.sendMessage("§cHome '" + casa + "' de " + nomerealp + " n\u00e3o \u00e9 p\u00fablica.");
                    return true;
                }
                else {
                    if (!p.hasPermission("sgcraft.homes.nodelay")) {
                        p.sendMessage("§eTeleportando em 3 segundos...");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                final Location loc = Manager.pegarHomePlayer(casa, player2);
                                p.teleport(loc);
                                p.sendMessage("§aTeleportado com sucesso.");
                            }
                        }, 60L);
                        return true;
                    }
                    final Location loc2 = Manager.pegarHomePlayer(casa, player2);
                    p.teleport(loc2);
                    p.sendMessage("§aTeleportado com sucesso.");
                    return true;
                }
            }
        }
        else {
            if (!Manager.checarHomeExiste(home, p.getName())) {
                p.sendMessage("§cHome '" + home + "' n\u00e3o existe.");
                return true;
            }
            if (!p.hasPermission("sgcraft.homes.nodelay")) {
                p.sendMessage("§eTeleportando em 3 segundos...");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        final Location loc = Manager.pegarHomePlayer(home, p.getName());
                        p.teleport(loc);
                        p.sendMessage("§aTeleportado com sucesso.");
                    }
                }, 60L);
                return true;
            }
            final Location loc3 = Manager.pegarHomePlayer(home, p.getName());
            p.teleport(loc3);
            p.sendMessage("§aTeleportado com sucesso.");
            return true;
        }
    }
}
