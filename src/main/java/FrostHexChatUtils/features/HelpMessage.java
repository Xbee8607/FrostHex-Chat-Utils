package FrostHexChatUtils.features;


import FrostHexChatUtils.config.ModConfigScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;


public class HelpMessage {
    private static final ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();
    private static final String[] voteRaceMessageHelpList = {"start a race"};
    private static final String[] raceJoinHelpList = {"join a race", "join race"};
    private static final String[] trackTimeHelpList = {"track times"};
    private static final String[] spawnBoatHelpList = {"spawn a boat", "summon a boat"};


    public static void Check (String rawMessage){
        rawMessage = rawMessage.toLowerCase();
        if(rawMessage.contains("how")){
            for(String s : voteRaceMessageHelpList){
                if(Config.extraSettings.voteRaceHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./voterace (track) (laps) (pits)");
                    return;
                }
            }
            for(String s : raceJoinHelpList){
                if(Config.extraSettings.raceJoinHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./race join");
                    return;
                }
            }
            for(String s: trackTimeHelpList){
                if(Config.extraSettings.trackTimeHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./t times (track)");
                    return;
                }
            }
            for(String s: spawnBoatHelpList){
                if(Config.extraSettings.spawnBoatHelp && rawMessage.contains(s)){
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage(
                            "./boat");
                    return;
                }
            }
        }
    }
}
