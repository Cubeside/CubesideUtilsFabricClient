package de.iani.cubesideutils.fabric.commands;

import de.iani.cubesideutils.fabric.commands.exceptions.IllegalSyntaxException;
import de.iani.cubesideutils.fabric.commands.exceptions.InternalCommandException;
import de.iani.cubesideutils.fabric.commands.exceptions.NoPermissionException;
import de.iani.cubesideutils.fabric.commands.exceptions.NoPermissionForPathException;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.awt.*;

public interface CommandExceptionHandler {

    public static final CommandExceptionHandler DEFAULT_HANDLER = new CommandExceptionHandler() {
    };

    public default int handleNoPermission(NoPermissionException thrown) {
        ClientPlayerEntity sender = thrown.getSender().getPlayer();
        sender.sendMessage(Text.literal(getErrorMessagePrefix() + thrown.getMessage()).withColor(Color.RED.getRGB()));
        return 0;
    }

    public default int handleNoPermissionForPath(NoPermissionForPathException thrown) {
        ClientPlayerEntity sender = thrown.getSender().getPlayer();
        sender.sendMessage(Text.literal(getErrorMessagePrefix() + thrown.getMessage()).withColor(Color.RED.getRGB()));
        return 0;
    }

    public default int handleIllegalSyntax(IllegalSyntaxException thrown) {
        CommandRouter router = thrown.getRouter();
        FabricClientCommandSource sender = thrown.getSender();
        String alias = thrown.getAlias();
        String[] args = thrown.getArgs();
        router.showHelp(sender, alias, args);
        return 0;
    }

    public default int handleInternalException(InternalCommandException thrown) {
        if (thrown.getMessage() != null) {
            ClientPlayerEntity sender = thrown.getSender().getPlayer();
            sender.sendMessage(Text.literal(getErrorMessagePrefix() + thrown.getMessage()).withColor(Color.RED.getRGB()));
        }

        Throwable cause = thrown.getCause();
        if (cause instanceof Error) {
            throw (Error) cause;
        } else if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        } else {
            throw new RuntimeException(cause);
        }
    }

    public default String getErrorMessagePrefix() {
        return "";
    }

    public default String getHelpMessagePrefix() {
        return "";
    }
}
