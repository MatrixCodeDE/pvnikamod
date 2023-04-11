package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.gui.*;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.collection.parallel.ParIterableLike;

import java.util.Collections;
import java.util.List;

public class Commands extends CommandBase {
    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    public Commands() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public String getCommandName() {
        return "pvnika";
    }

    public List<String> getCommandAliases() {
        return Collections.singletonList("pvnikamod");
    }

    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /" + this.getCommandName();
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            MinecraftForge.EVENT_BUS.register(this);
        } else {
            sender.addChatMessage(new ChatComponentText(this.getCommandUsage(sender)));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.mc.displayGuiScreen(new GuiClientOptions(null));
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
