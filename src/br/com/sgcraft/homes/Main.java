package br.com.sgcraft.homes;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.sgcraft.homes.MySQL.MySQL;
import br.com.sgcraft.homes.comandos.Comando_DelHome;
import br.com.sgcraft.homes.comandos.Comando_Home;
import br.com.sgcraft.homes.comandos.Comando_Listhomes;
import br.com.sgcraft.homes.comandos.Comando_Particular;
import br.com.sgcraft.homes.comandos.Comando_Publica;
import br.com.sgcraft.homes.comandos.Comando_SetHome;
import br.com.sgcraft.homes.comandos.Evento_Entrar;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class Main extends JavaPlugin {
	public static Plugin plugin;
	public static MySQL sql;
	public static String prefix;

	static {
		Main.prefix = "Config.";
	}
	public void onEnable() {
		if (this.getConfig().getString("Config") == null) {
			this.salvarConfiguracao();
			Bukkit.getConsoleSender().sendMessage("§cNecess\u00e1rio acesso \u00e0 MySQL para o plugin funcionar.");
			return;
		}
		if (!this.getConfig().getString("Config.MySQL").equalsIgnoreCase("true")) {
			Bukkit.getConsoleSender().sendMessage("§cNecess\u00e1rio acesso \u00e0 MySQL para o plugin funcionar.");
			return;
		}
		this.setupSQL();
		Main.plugin = (Plugin) this;
		Bukkit.getPluginManager().registerEvents((Listener) new Evento_Entrar(), (Plugin) this);
		this.onComandos();
		Bukkit.getConsoleSender().sendMessage("§1");
		Bukkit.getConsoleSender().sendMessage("§e[SgHomes] Carregado com Sucesso!");
		Bukkit.getConsoleSender().sendMessage("§2");
	}

	public void onDisable() {
		this.reloadConfig();
		this.saveConfig();
		Main.plugin = null;
		Bukkit.getConsoleSender().sendMessage("§1");
		Bukkit.getConsoleSender().sendMessage("§e[SgHomes] Desativado com Sucesso!");
		Bukkit.getConsoleSender().sendMessage("§2");
	}

	public void setupSQL() {
		final String user = this.getConfig().getString(String.valueOf(String.valueOf(Main.prefix)) + "Usuario");
		final String password = this.getConfig().getString(String.valueOf(String.valueOf(Main.prefix)) + "Senha");
		final String host = this.getConfig().getString(String.valueOf(String.valueOf(Main.prefix)) + "Host");
		final String database = this.getConfig().getString(String.valueOf(String.valueOf(Main.prefix)) + "Database");
		(Main.sql = new MySQL(user, password, host, database, this)).startConnection();
	}

	public void salvarConfiguracao() {
		this.getConfig().set(String.valueOf(String.valueOf(Main.prefix)) + "MySQL", (Object) false);
		this.getConfig().set(String.valueOf(String.valueOf(Main.prefix)) + "Usuario", (Object) "user");
		this.getConfig().set(String.valueOf(String.valueOf(Main.prefix)) + "Host", (Object) "host");
		this.getConfig().set(String.valueOf(String.valueOf(Main.prefix)) + "Database", (Object) "banco");
		this.getConfig().set(String.valueOf(String.valueOf(Main.prefix)) + "Senha", (Object) "senha");
		this.saveConfig();
	}

	public void onComandos() {
		this.getCommand("sethome").setExecutor((CommandExecutor) new Comando_SetHome());
		this.getCommand("delhome").setExecutor((CommandExecutor) new Comando_DelHome());
		this.getCommand("home").setExecutor((CommandExecutor) new Comando_Home());
		this.getCommand("listhomes").setExecutor((CommandExecutor) new Comando_Listhomes());
		this.getCommand("publica").setExecutor((CommandExecutor) new Comando_Publica());
		this.getCommand("particular").setExecutor((CommandExecutor) new Comando_Particular());
		
	}
	public static void sendJSONMsg(Player p, String text, String JSON, String text2, String hoverEvent, String command) {
		TextComponent msg = new TextComponent(text);
		TextComponent msg2 = new TextComponent(JSON);
		TextComponent msg3 = new TextComponent(text2);
		msg2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverEvent).create()));
		msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
		msg.addExtra(msg2);
		msg.addExtra(msg3);
		p.spigot().sendMessage(msg);
	}
	
	
}
