package FrostHexChatUtils;
import FrostHexChatUtils.commands.RaceModeCommand;
import FrostHexChatUtils.commands.FriendAddCommand;
import FrostHexChatUtils.commands.FriendShowCommand;
import FrostHexChatUtils.commands.FriendRemoveCommand;

import FrostHexChatUtils.normalchat.NormalMain;
import FrostHexChatUtils.racechat.RaceMain;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements ClientModInitializer {

    private static final Pattern letterFound = Pattern.compile("[a-zA-Z0-9]");

    @Override
    public void onInitializeClient() {

        ///  Colours ///
        int lightBlueColour = 0x80c3fc;
        int darkBlueColour = 0x527bd8;
        int cyanColour = 0x54fbfc;

        int grayColour = 0x545454;
        int greenColour = 0x54fb54;

        int redColour = 0xfb5454;
        int yellowColour = 0xfbfb54;
        int orangeColour = 0xfba800;

        /// RaceMode Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            RaceModeCommand.Activate(dispatcher);});

        /// AddFriend Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                FriendAddCommand.addFriend(dispatcher));

        /// ShowFriend Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                FriendRemoveCommand.friendRemove(dispatcher));

        /// ShowFriend Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                FriendShowCommand.friendShow(dispatcher));


        // Listen for incoming chat messages //
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, sender) -> {
            String rawMessage = message.getString();
            Matcher checkBlankMessage = letterFound.matcher(rawMessage);

            // Blank Line //
            if(!checkBlankMessage.find()){
                return false;
            }

            /// Race Mode Enabled//
            if(RaceModeCommand.raceModeNumber){

                /// WIP ///
                // Friend Messages //
                if(RaceMain.showMessgagesVerification(rawMessage)){
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Friend Message Shown"));
                    return true;
                }
                return false;
            }

            /// Race Mode Disabled ///

            else {
                if(rawMessage.contains("»")){
                    return true;
                }
                else if(NormalMain.joinServerChat(rawMessage, grayColour, greenColour, redColour, yellowColour)){
                    return false;
                }
                else if(NormalMain.raceVotingChat(rawMessage, lightBlueColour, darkBlueColour, cyanColour)){
                    return false;
                }
                else if(NormalMain.raceFinishChat(rawMessage, orangeColour)){
                    return false;
                }
                else if(rawMessage.contains("whispers to you")){
                    return true;
                }
                else if(NormalMain.boatUtilsMessage(rawMessage, grayColour, cyanColour, orangeColour)){
                    return false;
                }
                return true;
            }
        });
    }
}
