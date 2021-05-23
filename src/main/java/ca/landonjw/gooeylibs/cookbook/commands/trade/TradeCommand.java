package ca.landonjw.gooeylibs.cookbook.commands.trade;

import ca.landonjw.gooeylibs2.api.UIManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TradeCommand extends CommandBase {

    @Override
    public String getName() {
        return "trade";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/trade <player>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String targetName = args[0];
        EntityPlayerMP target = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(targetName);

        if(target != null) {
            TradeController controller = new TradeController((EntityPlayerMP) sender, target);
            UIManager.openUIForcefully((EntityPlayerMP) sender, new TradePage(controller, (EntityPlayerMP) sender));
            UIManager.openUIForcefully(target, new TradePage(controller, target));
        }
    }

}
