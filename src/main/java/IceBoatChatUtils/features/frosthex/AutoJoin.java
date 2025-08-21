package IceBoatChatUtils.features.frosthex;

import IceBoatChatUtils.config.ModConfigFile;
import IceBoatChatUtils.config.ModConfigScreen;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AutoJoin {
    static ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static int chatMessageTime;
    private static int timerCount;

    public static void Check(String rawMessage){
        if(rawMessage.contains("--> Click to join a race")){
            timerCount = 0;
            if(Config.frosthexsettings.autojoinsettings.autoJoinTime == 0 || instantRaceJoin(rawMessage)){
                runCommand();
            }
            chatMessageTime = Config.frosthexsettings.autojoinsettings.autoJoinTime * 20;
        }
    }

    public static void Timer(){
        timerCount++;
        if(timerCount == chatMessageTime && Config.frosthexsettings.autojoinsettings.autoJoin){
            chatMessageTime = 0;
            runCommand();
        }
    }
    private static void runCommand(){
        if(client.player != null && client.getNetworkHandler() != null){
            client.getNetworkHandler().sendPacket(new CommandExecutionC2SPacket("race join"));
        }
    }
    private static Boolean instantRaceJoin(String rawMessage){
        String[] Array = rawMessage.split("Click to join a race on", 2);
        String[] Array1 = Array[1].split("\\(", 2);
        String trackName = Array1[0].replaceAll("\\s+", "").toLowerCase();
        client.inGameHud.getChatHud().addMessage(Text.literal(trackName));
        return ModConfigFile.trackList.contains(trackName);
    }
}
