package de.iani.cubesideutils.fabric.commands;

import de.iani.cubesideutils.commands.ArgsParser;
import de.iani.cubesideutils.commands.PermissionRequirer;
import de.iani.cubesideutils.fabric.CubesideUtilsFabricClientMod;
import de.iani.cubesideutils.fabric.commands.exceptions.DisallowsCommandBlockException;
import de.iani.cubesideutils.fabric.commands.exceptions.IllegalSyntaxException;
import de.iani.cubesideutils.fabric.commands.exceptions.InternalCommandException;
import de.iani.cubesideutils.fabric.commands.exceptions.NoPermissionException;
import de.iani.cubesideutils.fabric.commands.exceptions.RequiresPlayerException;
import java.util.Collection;
import java.util.Collections;

import de.iani.cubesideutils.fabric.permission.PermissionHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BaseCommandBlock;

public abstract class SubCommand implements PermissionRequirer {

    // Overwrite these as necessarry

    public boolean requiresPlayer() {
        return false;
    }

    public boolean allowsCommandBlock() {
        return false;
    }

    @Override
    public String getRequiredPermission() {
        return null;
    }

    public boolean isAvailable(CommandSourceStack sender) {
        return true;
    }

    public boolean isVisible(CommandSourceStack sender) {
        return true;
    }

    public abstract boolean onCommand(CommandSourceStack sender, String alias, String commandString, ArgsParser args) throws DisallowsCommandBlockException, RequiresPlayerException, NoPermissionException, IllegalSyntaxException, InternalCommandException;

    public Collection<String> onTabComplete(CommandSourceStack sender, String alias, ArgsParser args) {
        return isDisplayable(sender) ? null : Collections.emptyList();
    }

    public String getUsage(CommandSourceStack sender) {
        return getUsage();
    }

    public String getUsage() {
        return "";
    }

    // For convenience

    public boolean hasRequiredPermission(CommandSourceStack sender) {
        if (getRequiredPermission() == null) {
            return true;
        }
        if (!(sender.source instanceof Player player)) {
            return true;
        }
        return PermissionHandler.hasPermission(getRequiredPermission());
    }

    public boolean isExecutable(CommandSourceStack sender) {
        if (sender.source instanceof BaseCommandBlock) {
            if (!allowsCommandBlock()) {
                return false;
            }
        }
        if (!(sender.source instanceof Player) && requiresPlayer()) {
            return false;
        }
        return hasRequiredPermission(sender) && isAvailable(sender);
    }

    public boolean isDisplayable(CommandSourceStack sender) {
        return isExecutable(sender) && isVisible(sender);
    }
}
