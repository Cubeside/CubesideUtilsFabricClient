package de.iani.cubesideutils.fabric.commands;

import de.iani.cubesideutils.fabric.commands.exceptions.IllegalSyntaxException;
import de.iani.cubesideutils.fabric.commands.exceptions.InternalCommandException;
import de.iani.cubesideutils.fabric.commands.exceptions.NoPermissionException;
import de.iani.cubesideutils.fabric.commands.exceptions.NoPermissionForPathException;
import java.awt.Color;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public interface CommandExceptionHandler {

    public static final CommandExceptionHandler DEFAULT_HANDLER = new CommandExceptionHandler() {};

    public default int handleNoPermission(NoPermissionException thrown) {
        Minecraft.getInstance().getChatListener().handleSystemMessage(Component.literal(getErrorMessagePrefix() + thrown.getMessage()).withColor(Color.RED.getRGB()), false);
        return 0;
    }

    public default int handleNoPermissionForPath(NoPermissionForPathException thrown) {
        Minecraft.getInstance().getChatListener().handleSystemMessage(Component.literal(getErrorMessagePrefix() + thrown.getMessage()).withColor(Color.RED.getRGB()), false);
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
            Minecraft.getInstance().getChatListener().handleSystemMessage(Component.literal(getErrorMessagePrefix() + thrown.getMessage()).withColor(Color.RED.getRGB()), false);
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
