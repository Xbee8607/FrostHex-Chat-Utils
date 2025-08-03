package IceBoatChatUtils.features.frosthex;


import IceBoatChatUtils.config.ModConfigScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;


public class HelpMessage {
    private static final ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();

    // Put all responses in lowercase //
    private static final String[] voteRaceMessageHelpList = {"start a race"};
    private static final String[] raceJoinHelpList = {"join a race", "join race"};
    private static final String[] trackTimeHelpList = {"track times"};
    private static final String[] spawnBoatHelpList = {"spawn a boat", "summon a boat"};
    private static final String[] raceLeaveHelpList = {"leave race", "leave the race"};

    /// Reformat so that for loop does not repeat if config is disabled ///
    public static void Check (String rawMessage){
        rawMessage = rawMessage.toLowerCase();
        if(rawMessage.contains("how")){
            for(String s : voteRaceMessageHelpList){
                if(Config.frosthexsettings.chathelpsettings.voteRaceHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./voterace (track) (laps) (pits)");
                    return;
                }
            }
            for(String s : raceJoinHelpList){
                if(Config.frosthexsettings.chathelpsettings.raceJoinHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./race join");
                    return;
                }
            }
            for(String s: trackTimeHelpList){
                if(Config.frosthexsettings.chathelpsettings.trackTimeHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./t times (track)");
                    return;
                }
            }
            for(String s: spawnBoatHelpList){
                if(Config.frosthexsettings.chathelpsettings.spawnBoatHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./boat");
                    return;
                }
            }
            for(String s: raceLeaveHelpList){
                if(Config.frosthexsettings.chathelpsettings.raceLeaveHelp  && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./race leave");
                    return;
                }
            }
        }
    }
}
