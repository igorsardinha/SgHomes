package br.com.sgcraft.homes.MySQL;

import java.sql.*;
import org.bukkit.*;

public class Manager
{
    public static boolean checarContaExiste(final String nome) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES_Contas WHERE Nome=?");
            ps.setString(1, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean checarHomeExiste(final String nome, final String dono) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES WHERE Dono=? AND Nome=?");
            ps.setString(1, dono.toLowerCase());
            ps.setString(2, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String pegarNomePlayer(final String nome) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES_Contas WHERE Nome=?");
            ps.setString(1, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final String nick = rs.getString("Nick");
                return nick;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String pegarNomeHome(final String nome, final String dono) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES WHERE Dono=? AND Nome=?");
            ps.setString(1, dono.toLowerCase());
            ps.setString(2, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final String nomereal = rs.getString("Nomereal");
                return nomereal;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean ePublicaHome(final String nome, final String dono) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES WHERE Dono=? AND Nome=?");
            ps.setString(1, dono.toLowerCase());
            ps.setString(2, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final String nomereal = rs.getString("Publica");
                if (nomereal.equals("SIM")) {
                    return true;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Integer pegarNumeroHomes(final String nome) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES_Contas WHERE Nome=?");
            ps.setString(1, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int n = rs.getInt("Homes");
                return n;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Location pegarHomePlayer(final String nome, final String dono) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgHOMES WHERE Dono=? AND Nome=?");
            ps.setString(1, dono.toLowerCase());
            ps.setString(2, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final String home = rs.getString("Location");
                final String[] parts = home.split("@");
                final String world = parts[0];
                final double x = Double.parseDouble(parts[1]);
                final double y = Double.parseDouble(parts[2]);
                final double z = Double.parseDouble(parts[3]);
                final float yaw = Float.parseFloat(parts[4]);
                final float pitch = Float.parseFloat(parts[5]);
                final Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                return loc;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void addHome(final String nome, final String dono, final Location loc) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("INSERT INTO SgHOMES (Dono,Nome,Nomereal,Publica,Location) VALUES (?,?,?,?,?);");
            ps.setString(1, dono.toLowerCase());
            ps.setString(2, nome.toLowerCase());
            ps.setString(3, nome);
            ps.setString(4, "NAO");
            final String a = String.valueOf(String.valueOf(loc.getWorld().getName())) + "@" + loc.getX() + "@" + loc.getY() + "@" + loc.getZ() + "@" + loc.getYaw() + "@" + loc.getPitch();
            ps.setString(5, a);
            ps.executeUpdate();
            final int jatem = pegarNumeroHomes(dono);
            final int dps = jatem + 1;
            final PreparedStatement ps2 = MySQL.connection.prepareStatement("UPDATE SgHOMES_Contas SET Homes = ? WHERE Nome = ?;");
            ps2.setInt(1, dps);
            ps2.setString(2, dono.toLowerCase());
            ps2.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void attHome(final String nome, final String dono, final Location loc) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SgHOMES SET Location = ? WHERE Dono = ? AND Nome = ?;");
            final String a = String.valueOf(String.valueOf(loc.getWorld().getName())) + "@" + loc.getX() + "@" + loc.getY() + "@" + loc.getZ() + "@" + loc.getYaw() + "@" + loc.getPitch();
            ps.setString(1, a);
            ps.setString(2, dono.toLowerCase());
            ps.setString(3, nome.toLowerCase());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void setarPublica(final String nome, final String dono, final String publica) {
        try {
            final PreparedStatement ps1 = MySQL.connection.prepareStatement("UPDATE SgHOMES SET Publica = ? WHERE Dono = ? AND Nome = ?;");
            ps1.setString(1, publica);
            ps1.setString(2, dono.toLowerCase());
            ps1.setString(3, nome.toLowerCase());
            ps1.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deletarHome(final String nome, final String dono) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("DELETE FROM SgHOMES WHERE Dono=? AND Nome=?");
            ps.setString(1, dono.toLowerCase());
            ps.setString(2, nome.toLowerCase());
            ps.executeUpdate();
            final int jatem = pegarNumeroHomes(dono);
            final int dps = jatem - 1;
            final PreparedStatement ps2 = MySQL.connection.prepareStatement("UPDATE SgHOMES_Contas SET Homes = ? WHERE Nome = ?;");
            ps2.setInt(1, dps);
            ps2.setString(2, dono.toLowerCase());
            ps2.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
