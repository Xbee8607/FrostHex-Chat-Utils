package FrostHexChatUtils;
import FrostHexChatUtils.commands.RaceModeCommand;
import FrostHexChatUtils.commands.FriendAddCommand;
import FrostHexChatUtils.commands.FriendShowCommand;
import FrostHexChatUtils.commands.FriendRemoveCommand;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMain implements ClientModInitializer {

    private static final Pattern letterFound = Pattern.compile("[a-zA-Z0-9]");

    @Override
    public void onInitializeClient() {

        /// RaceMode Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            RaceModeCommand.Activate(dispatcher);});

        /// AddFriend Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                FriendAddCommand.addFriend(dispatcher));

        /// RemoveFriend Command ///
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

                /// WIP (kinda) ///
                if(RaceChatHandler.show(rawMessage)){
                    return true;
                }
                return false;
            }

            /// Race Mode Disabled ///
            else {
                if(!NormalChatHandler.show(rawMessage)){
                    return false;
                }
                return true;
            }
        });
    }
}
