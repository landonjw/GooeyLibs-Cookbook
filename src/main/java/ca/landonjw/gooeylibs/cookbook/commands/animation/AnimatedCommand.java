package ca.landonjw.gooeylibs.cookbook.commands.animation;

import ca.landonjw.gooeylibs2.api.UIManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class AnimatedCommand extends CommandBase {

    private final AnimatedPage page;

    public AnimatedCommand() {
        this.page = new AnimatedPage();
    }

    @Override
    public String getName() {
        return "animinv";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/animinv";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        UIManager.openUIForcefully((EntityPlayerMP) sender, page);
    }

}
