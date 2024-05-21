package de.iani.cubesideutils.fabric.commands.exceptions;

import de.iani.cubesideutils.fabric.commands.CommandRouter;
import de.iani.cubesideutils.fabric.commands.SubCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class NoPermissionException extends SubCommandException {

    private static final long serialVersionUID = 426296281527518966L;

    private String permission;

    public NoPermissionException(CommandRouter router, FabricClientCommandSource sender, String alias, SubCommand subCommand, String[] args, String permission, String message) {
        super(router, sender, alias, subCommand, args, message);

        this.permission = permission;
    }

    public NoPermissionException(CommandRouter router, FabricClientCommandSource sender, String alias, SubCommand subCommand, String[] args, String permission) {
        this(router, sender, alias, subCommand, args, permission, "No permission!");
    }

    public NoPermissionException(CommandRouter router, FabricClientCommandSource sender, String alias, SubCommand subCommand, String[] args) {
        this(router, sender, alias, subCommand, args, null, "No permission!");
    }

    public String getPermission() {
        return permission;
    }

}
