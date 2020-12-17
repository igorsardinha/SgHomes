package br.com.sgcraft.homes.comandos;

import org.bukkit.event.player.*;

import br.com.sgcraft.homes.MySQL.*;

import org.bukkit.entity.*;
import java.sql.*;
import org.bukkit.event.*;

public class Evento_Entrar implements Listener {
	@EventHandler
	public static void aoEntrar(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if (!Manager.checarContaExiste(p.getName())) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("INSERT INTO SgHOMES_Contas (Nome,Nick,Homes) VALUES (?,?,?);");
				ps.setString(1, p.getName().toLowerCase());
				ps.setString(2, p.getName());
				ps.setInt(3, 0);
				ps.executeUpdate();
			} catch (SQLException ev) {
				ev.printStackTrace();
			}
		}
	}

	@EventHandler
	private void onCmd(final PlayerCommandPreprocessEvent e) {
		final Player player = e.getPlayer();
		if ((e.getMessage().toLowerCase().startsWith("/sethome")
				|| e.getMessage().toLowerCase().startsWith("/sghomes:sethome"))) {
			if (!player.getWorld().getName().equalsIgnoreCase("terrenos")) {
				e.setCancelled(true);
				player.sendMessage(" §cVocê não pode usar isso nesse mundo!!");
			}
		}
	}
}
