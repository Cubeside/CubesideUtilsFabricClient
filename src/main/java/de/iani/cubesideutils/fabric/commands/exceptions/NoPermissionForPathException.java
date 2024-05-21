package de.iani.cubesideutils.fabric.commands.exceptions;

import de.iani.cubesideutils.fabric.commands.CommandRouter;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class NoPermissionForPathException extends CommandRouterException {

    private static final long serialVersionUID = 1295353884134111903L;

    public NoPermissionForPathException(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args, String message) {
        super(router, sender, alias, args, message);
    }

    public NoPermissionForPathException(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args) {
        this(router, sender, alias, args, "No permission!");
    }

}
