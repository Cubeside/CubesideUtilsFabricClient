package de.iani.cubesideutils.fabric.commands.exceptions;

import de.iani.cubesideutils.fabric.commands.CommandRouter;
import de.iani.cubesideutils.fabric.commands.SubCommand;
import net.minecraft.commands.CommandSourceStack;

public class InternalCommandException extends SubCommandException {

    private static final long serialVersionUID = -6856078921802113528L;

    public InternalCommandException(CommandRouter router, CommandSourceStack sender, String alias, SubCommand subCommand, String[] args, String message, Throwable cause) {
        super(router, sender, alias, subCommand, args, message, cause);
    }

    public InternalCommandException(CommandRouter router, CommandSourceStack sender, String alias, SubCommand subCommand, String[] args, Throwable cause) {
        this(router, sender, alias, subCommand, args, null, cause);
    }

}
