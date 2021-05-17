package com.serversigma.command;

import com.serversigma.manager.ItemManager;
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

        if (!(p.hasPermission("sigmaevolutions.pickaxe.give"))) {
            p.sendMessage("§cVocê não tem acesso a este comando.");
        }
        int slot = p.getInventory().firstEmpty();
        if (slot == -1) {
            p.sendMessage("§c[SigmaEvolutions] Seu inventário precisa estar vazio.");
            return false;
        }

        itemManager.giveSword(p);
        p.sendMessage("§a[SigmaEvolutions] Você pegou uma espada com sucesso.");
        return false;
    }
}

