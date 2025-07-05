package FrostHexChatUtils;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements ClientModInitializer {


    private static final Pattern JOIN_PATTERN = Pattern.compile("\\[\\d+/\\d+]");

    @Override
    public void onInitializeClient() {
        // Listen for incoming chat messages
        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            String rawMessage = message.getString();

           /// Join Messages ///
            if(joinServerChat(rawMessage)){
                return false;
            }

            /// Race Voting ///
            if(raceVotingChat(rawMessage)){
                return false;
            }
            return true;



        });
    }
    private boolean joinServerChat(String rawMessage){
        Matcher joinPatternMatch = JOIN_PATTERN.matcher(rawMessage);

        if (joinPatternMatch.find()) {
            String newMessage = rawMessage.replaceAll("\\[\\d+/\\d+]", "");

            if (rawMessage.contains("[Heat Driver]")) {
                newMessage = newMessage.replace("[Heat Driver]", "");
            }

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(newMessage));
            return true;
        }
        return false;
    }

    private boolean raceVotingChat(String rawMessage){
        if (rawMessage.contains(" just voted for a race on")){

            // Remove filler text
            rawMessage = rawMessage.replace(" just voted for a race on", " :");

            // Make ':' appear the same colour as rest of text
            try{
                String[] Message = rawMessage.split(" ", 4);
                Text textMessage = Text.literal(Message[1])                                                 // Player Name
                        .append(Text.literal(Message[2]).setStyle(Style.EMPTY.withColor(0x7FB8FF))) // Colon
                        .append(' ' + Message[3]);                                                          // Rest of Text

                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return true;
            } catch (Exception ArrayIndexOutOfBoundsException){
                return false;
            }
        }
        return false;
    }
}
