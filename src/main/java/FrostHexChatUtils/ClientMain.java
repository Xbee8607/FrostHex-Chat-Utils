package FrostHexChatUtils;
import FrostHexChatUtils.commands.RaceModeCommand;
import FrostHexChatUtils.commands.FriendAddCommand;
import FrostHexChatUtils.commands.FriendShowCommand;
import FrostHexChatUtils.commands.FriendRemoveCommand;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMain implements ClientModInitializer {

    private static final Pattern letterFound = Pattern.compile("[a-zA-Z0-9]");

    @Override
    public void onInitializeClient() {

        /// RaceMode Command ///
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            RaceModeCommand.Activate(dispatcher);
        });

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

            if (!checkBlankMessage.find()) {
                return false;
            }

            // Only try to get player AFTER the game is running and player exists
            /// Get Player Username ///
            if (MinecraftClient.getInstance().player == null) return true; // fallback
            String playerName = MinecraftClient.getInstance().player.getName().getString();
            String[] playerArray = playerName.split("\\{", 2);

            // Race mode check
            if (RaceModeCommand.raceModeNumber) {
                return RaceChatHandler.show(rawMessage, playerArray.length > 1 ? playerArray[1] : playerName);
            } else {
                return NormalChatHandler.show(rawMessage);
            }
        });
    }
}
