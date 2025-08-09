package IceBoatChatUtils.features.frosthex;


import IceBoatChatUtils.config.ModConfigScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;


public class HelpMessage {
    private static final ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();
    private static final MinecraftClient client = MinecraftClient.getInstance();

    // Put all responses in lowercase //
    private static final String[] voteRaceMessageHelpList = {"start a race", "make a race", "start race"};
    private static final String[] raceJoinHelpList = {"join a race", "join race", "join the race"};
    private static final String[] trackTimeHelpList = {"track times", "see times"};
    private static final String[] spawnBoatHelpList = {"spawn a boat", "summon a boat", "spawn boat", "boat back"};
    private static final String[] raceLeaveHelpList = {"leave race", "leave the race"};

    public static void Check (String rawMessage){
        rawMessage = rawMessage.toLowerCase();
        if(rawMessage.contains("how")){
            if(Config.frosthexsettings.chathelpsettings.voteRaceHelp){
                for(String s : voteRaceMessageHelpList){
                    if(rawMessage.contains(s)){
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatMessage(
                                "/voterace (track) (laps) (pits)");
                        return;
                    }
                }
            }
            if(Config.frosthexsettings.chathelpsettings.raceJoinHelp){
                for(String s : raceJoinHelpList){
                    if(rawMessage.contains(s)){
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatMessage(
                                "/race join");
                        return;
                    }
                }
            }
            if(Config.frosthexsettings.chathelpsettings.trackTimeHelp){
                for(String s: trackTimeHelpList){
                    if(rawMessage.contains(s)){
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatMessage(
                                "/t times (track)");
                        return;
                    }
                }
            }
            if(Config.frosthexsettings.chathelpsettings.spawnBoatHelp){
                for(String s: spawnBoatHelpList){
                    if(rawMessage.contains(s)){
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatMessage(
                                "/boat");
                        return;
                    }
                }
            }
            if(Config.frosthexsettings.chathelpsettings.raceLeaveHelp){
                for(String s: raceLeaveHelpList){
                    if(rawMessage.contains(s)){
                        Objects.requireNonNull(client.getNetworkHandler()).sendChatMessage(
                                "/race leave");
                        return;
                    }
                }
            }

        }
    }
}
