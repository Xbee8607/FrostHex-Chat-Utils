package FrostHexChatUtils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements ClientModInitializer {

    private static final Pattern letterFound = Pattern.compile("[a-zA-Z]");

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


        // Listen for incoming chat messages //
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, sender) -> {
            
            String rawMessage = message.getString();
            Matcher checkBlankMessage = letterFound.matcher(rawMessage);

            /// Someone Chats ///
            if(rawMessage.contains("»")){
                return true;
            }

            /// Join Messages, Race Messages and Blank Messages ///
            if(JoinGame.joinServerChat(rawMessage, grayColour, greenColour, redColour, yellowColour)){
                return false;
            }
            else if(!checkBlankMessage.find()){
                return false;
            }
            else if(RaceGame.raceVotingChat(rawMessage, lightBlueColour, darkBlueColour, cyanColour)){
                return false;
            }
            else if(RaceGame.raceFinishChat(rawMessage, orangeColour)){
                return false;
            }

            /// Someone Whispers ///
            if(rawMessage.contains("whispers to you")){
                return true;
            }

            /// FrostHex Utils Messages ///
            else if(rawMessage.contains("[FrostHexUtils]")){
                if(FrostHexUtils.boatUtilsMessage(rawMessage, grayColour, cyanColour, orangeColour)){
                    return false;
                }
                return true;
            }
            return true;
        });
    }
}
