package com.serversigma.sigmalevels.command;

import com.serversigma.sigmalevels.manager.ItemManager;
import com.serversigma.sigmalevels.model.LevelType;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class ArmorGiveCommand implements CommandExecutor {

    private final ItemManager itemManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cO console não pode executar esse comando.");
            return true;
        }

        Player p = (Player) sender;

        int slots = 0;

        for (ItemStack slot : p.getInventory().getContents()) {
            if (slot == null) slots++;
        }

        if (slots < 4) {
            p.sendMessage("§cSeu inventário precisa estar vazio.");
            return true;
        }

        if (args.length != 1) {
            itemManager.giveItem(p, 0, LevelType.ARMOR_ALL);
            p.sendMessage("§aVocê pegou uma armadura com sucesso.");
            return true;
        }

        double gems;

        try {
            gems = Double.parseDouble(args[0].replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            p.sendMessage("§cA quantidade inserida é inválida.");
            return true;
        }

        itemManager.giveItem(p, gems, LevelType.ARMOR_ALL);
        p.sendMessage("§aVocê pegou uma armadura com sucesso.");
        return true;
    }

}
