package com.serversigma.command;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PickaxeGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender == null) {
            Bukkit.getConsoleSender().sendMessage("§cSomente jogadores podem executar esse comando.");
        }

        Player p = (Player) sender;

        return false;
    }
}
