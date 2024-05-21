package de.iani.cubesideutils.fabric.commands;

import de.iani.cubesideutils.commands.ArgsParser;
import de.iani.cubesideutils.commands.PermissionRequirer;
import de.iani.cubesideutils.fabric.commands.exceptions.IllegalSyntaxException;
import de.iani.cubesideutils.fabric.commands.exceptions.InternalCommandException;
import de.iani.cubesideutils.fabric.commands.exceptions.NoPermissionException;

import java.util.Collection;
import java.util.Collections;

import de.iani.cubesideutils.fabric.permission.PermissionHandler;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public abstract class SubCommand implements PermissionRequirer {

    // Overwrite these as necessarry

    @Override
    public String getRequiredPermission() {
        return null;
    }

    public boolean isAvailable(FabricClientCommandSource sender) {
        return true;
    }

    public boolean isVisible(FabricClientCommandSource sender) {
        return true;
    }

    public abstract boolean onCommand(FabricClientCommandSource sender, String alias, String commandString, ArgsParser args) throws NoPermissionException, IllegalSyntaxException, InternalCommandException;

    public Collection<String> onTabComplete(FabricClientCommandSource sender, String alias, ArgsParser args) {
        return isDisplayable(sender) ? null : Collections.emptyList();
    }

    public String getUsage(FabricClientCommandSource sender) {
        return getUsage();
    }

    public String getUsage() {
        return "";
    }

    // For convenience

    public boolean hasRequiredPermission(FabricClientCommandSource sender) {
        if (getRequiredPermission() == null) {
            return true;
        }
        return PermissionHandler.hasPermission(getRequiredPermission());
    }

    public boolean isExecutable(FabricClientCommandSource sender) {
        return hasRequiredPermission(sender) && isAvailable(sender);
    }

    public boolean isDisplayable(FabricClientCommandSource sender) {
        return isExecutable(sender) && isVisible(sender);
    }
}
