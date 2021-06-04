package com.serversigma.sigmalevels.command;

import com.serversigma.sigmalevels.manager.ItemManager;
import com.serversigma.sigmalevels.model.LevelType;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SwordGiveCommand implements CommandExecutor {

    private final ItemManager itemManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cO console não pode executar esse comando.");
            return true;
        }

        Player p = (Player) sender;

        int slot = p.getInventory().firstEmpty();
        if (slot == -1) {
            p.sendMessage("§cSeu inventário precisa estar vazio.");
            return true;
        }

        if (args.length != 1) {
            itemManager.giveItem(p, 0, LevelType.TOOLS_SWORD);
            p.sendMessage("§aVocê pegou uma espada com sucesso.");
            return true;
        }

        double entities;

        try {
            entities = Double.parseDouble(args[0].replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            p.sendMessage("§cA quantidade inserida é inválida.");
            return true;
        }

        itemManager.giveItem(p, entities, LevelType.TOOLS_SWORD);
        p.sendMessage("§aVocê pegou uma espada com sucesso.");
        return true;
    }

}