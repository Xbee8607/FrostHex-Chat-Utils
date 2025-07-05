package FrostHexChatUtils;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.SpriteTexturedVertexConsumer;
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
            String newMessage = "";

            /// JOIN SERVER ///

            Matcher Join_Pattern_Match = JOIN_PATTERN.matcher(rawMessage);

            // Finds a player joined message //
            if(Join_Pattern_Match.find()){
                // Remove player count
                newMessage = rawMessage.replaceAll("\\[\\d+/\\d+]","");

                // Remove heat driver
                if(rawMessage.contains("[Heat Driver]")){
                    newMessage = rawMessage.replace("[Heat Driver]","");
                }
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(newMessage));
                return false;
            }

            /// RACE VOTING ///

            // Finds a race voting message //
            if(rawMessage.contains(" just voted for a race on")){

                // Remove filler text
                rawMessage = rawMessage.replace(" just voted for a race on", " :");

                // Make ':' appear the same colour as rest of text
                String[] Message = rawMessage.split(" ", 4);
                Text textMessage = Text.literal(Message[1])                                                 // Player Name
                        .append(Text.literal(Message[2]).setStyle(Style.EMPTY.withColor(0x7FB8FF))) // Colon
                        .append(' ' + Message[3]);                                                          // Rest of Text

                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return false;
            }
            return true;

        });
    }
}
