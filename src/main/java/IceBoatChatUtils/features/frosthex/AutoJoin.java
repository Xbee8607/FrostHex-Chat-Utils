package IceBoatChatUtils.features.frosthex;

import IceBoatChatUtils.config.ModConfigScreen;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;

public class AutoJoin {
    static ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static int chatMessageTime;
    private static int timerCount;

    public static void Check(String rawMessage){
        if(rawMessage.contains("--> Click to join a race")){
            timerCount = 0;
            if(Config.frosthexsettings.autojoinsettings.autoJoinTime == 0){
                runCommand("race join");
            }
            chatMessageTime = Config.frosthexsettings.autojoinsettings.autoJoinTime * 20;
        }
    }

    public static void Timer(){
        timerCount++;
        if(timerCount == chatMessageTime && Config.frosthexsettings.autojoinsettings.autoJoin){
            chatMessageTime = 0;
            runCommand("race join");
        }
    }
    public static void runCommand(String command){
        if(client.player != null && client.getNetworkHandler() != null){
            // Send the command exactly like typing it in chat
            client.getNetworkHandler().sendPacket(new CommandExecutionC2SPacket(command));
        }
    }
}
