package com.serversigma.sigmaevolutions.command;

import com.serversigma.sigmaevolutions.manager.ItemManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class PickaxeGiveCommand implements CommandExecutor {

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
            p.sendMessage("§c[SigmaEvolutions] Seu inventário precisa estar vazio.");
            return true;
        }

        if (args.length != 1) {
            p.sendMessage("§cUso incorreto, utilize §7/picareta [blocos]");
            return true;
        }

        int blocks;

        try {
            blocks = Integer.parseInt(args[0].replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            p.sendMessage("§cA quantidade inserida é inválida.");
            return true;
        }

        itemManager.givePickaxe(p, blocks);
        p.sendMessage("§a[SigmaEvolutions] Você pegou uma picareta com sucesso.");
        return true;
    }
}