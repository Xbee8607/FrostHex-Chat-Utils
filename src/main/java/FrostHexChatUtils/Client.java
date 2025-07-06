package FrostHexChatUtils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;


public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ///  Colours ///
        int lightBlueColour = 0x80c3fc;
        int darkBlueColour = 0x527bd8;
        int grayColour = 0x545454;
        int greenColour = 0x54fb54;
        int redColour = 0xfb5454;
        int yellowColour = 0xfbfb54;

        // Listen for incoming chat messages //
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, sender) -> {
            String rawMessage = message.getString();

            /// Join Messages, Vote Messages and Blank Messages ///
            if(JoinGame.joinServerChat(rawMessage, grayColour, greenColour, redColour, yellowColour)){
                return false;
            }
            else if(RaceGame.raceVotingChat(rawMessage, lightBlueColour, darkBlueColour)){
                return false;
            }
            else if(BlankGame.blankChat(rawMessage)){
                return false;
            }
            return true;
        });
    }
}
