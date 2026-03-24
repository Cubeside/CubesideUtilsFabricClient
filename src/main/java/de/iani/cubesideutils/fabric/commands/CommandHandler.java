package de.iani.cubesideutils.fabric.commands;

import java.util.List;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public interface CommandHandler {
    public boolean checkPermission(FabricClientCommandSource source);

    public int onCommand(FabricClientCommandSource source, String label, String[] args);

    public List<String> onTabComplete(FabricClientCommandSource source, String label, String[] args);
}
