package de.iani.cubesideutils.fabric.commands.exceptions;

import de.iani.cubesideutils.fabric.commands.CommandRouter;
import java.util.Objects;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public abstract class CommandRouterException extends Exception {

    private static final long serialVersionUID = 3550234682652991485L;

    private CommandRouter router;
    private FabricClientCommandSource sender;
    private String alias;
    private String[] args;

    public CommandRouterException(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args, String message, Throwable cause) {
        super(message, cause);
        init(router, sender, alias, args);
    }

    public CommandRouterException(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args, String message) {
        super(message);
        init(router, sender, alias, args);
    }

    public CommandRouterException(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args, Throwable cause) {
        super(cause);
        init(router, sender, alias, args);
    }

    public CommandRouterException(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args) {
        super();
        init(router, sender, alias, args);
    }

    private void init(CommandRouter router, FabricClientCommandSource sender, String alias, String[] args) {
        this.router = router;
        this.sender = Objects.requireNonNull(sender);
        this.alias = Objects.requireNonNull(alias);
        this.args = args.clone();
    }

    public CommandRouter getRouter() {
        return router;
    }

    public FabricClientCommandSource getSender() {
        return sender;
    }

    public String getAlias() {
        return alias;
    }

    public String[] getArgs() {
        return args.clone();
    }

}
