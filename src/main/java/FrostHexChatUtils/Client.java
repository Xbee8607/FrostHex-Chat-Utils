package FrostHexChatUtils;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements ClientModInitializer {


    private static final Pattern JOIN_PATTERN = Pattern.compile("\\[\\d+/\\d+]");

    @Override
    public void onInitializeClient() {
        int lightBlueColour = 0x80c3fc;
        int darkBlueColour = 0x527bd8;
        int grayColour = 0x545454;
        int greenColour = 0x54fb54;
        int redColour = 0xfb5454;
        int yellowColour = 0xfbfb54;
        // Listen for incoming chat messages //
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, sender) -> {
            String rawMessage = message.getString();

            /// Join Messages and Vote Messages ///
            if(joinServerChat(rawMessage, grayColour, greenColour, redColour, yellowColour)){
                return false;
            } else if (raceVotingChat(rawMessage, lightBlueColour, darkBlueColour)) {
                return false;
            }
            return true;
        });
    }

    private boolean joinServerChat(String rawMessage, int gray, int green, int red, int yellow){
        Matcher joinPatternMatch = JOIN_PATTERN.matcher(rawMessage);

        // For player count //
        if (joinPatternMatch.find()) {
            String newMessage = rawMessage.replaceAll("\\[\\d+/\\d+]", "");

            // If player was in race //
            if (rawMessage.contains("[Heat Driver]")) {
                newMessage = newMessage.replace("[Heat Driver]", "");
            }

            // Colour in text //
            /// [+] or [-] "Player" ///
            try {
                if (rawMessage.contains("+")) {
                    String[] joinServerArray = newMessage.split(" ", 2);
                    String joinServerSymbol = joinServerArray[0];
                    String[] joinServerSymbolArray = joinServerSymbol.split("\\+", 2);
                    Text textMessage = Text.literal((joinServerSymbolArray[0])).setStyle(Style.EMPTY.withColor(gray))
                            .append(Text.literal("+").setStyle(Style.EMPTY.withColor(green)))
                            .append(Text.literal(joinServerSymbolArray[1]).setStyle(Style.EMPTY.withColor(gray)))
                            .append(Text.literal(" " + joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                    return true;
                }
            } catch (Exception ArrayIndexOutOfBoundsException) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Array Index out of bounds - [+]"));
                return false;
            }
            try {
                if (rawMessage.contains("-")) {
                    String[] joinServerArray = newMessage.split(" ", 2);
                    String joinServerSymbol = joinServerArray[0];
                    String[] joinServerSymbolArray = joinServerSymbol.split("-", 2);
                    Text textMessage = Text.literal((joinServerSymbolArray[0])).setStyle(Style.EMPTY.withColor(gray))
                            .append(Text.literal("-").setStyle(Style.EMPTY.withColor(red)))
                            .append(Text.literal(joinServerSymbolArray[1]).setStyle(Style.EMPTY.withColor(gray)))
                            .append(Text.literal(" " + joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                    return true;
                }
            } catch (Exception ArrayIndexOutOfBoundsException) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Array Index out of bounds - [+]"));
                return false;
            }

            return false;
        }
        return false;
    }

    private boolean raceVotingChat(String rawMessage, int lightBlue, int darkBlue){
        if (rawMessage.contains(" just voted for a race on")){

            // Remove filler text
            rawMessage = rawMessage.replace(" just voted for a race on", "");

            /// [track] with [int] laps and [int] pits -> Click to Vote <- ///
            /// Command: just voted for a race on appleseed with 3 laps 0 pits -> click to vote <- ///
            // Colour in [track], and both int values //

            try{
                // Split Player and Race with rest of command //
                String[] Message = rawMessage.split(" with", 2);

                String endMessage = Message[1];
                String[] endMessageArray = endMessage.split(" ", 5);

                //splits Player and Race
                String startMessage = Message[0];
                String[] startMessageArray = startMessage.split(" ", 2);

                String voteraceCommand = "/voterace " + startMessageArray[1] + " " + endMessageArray[1] + " " + endMessageArray[3];

                Text textMessage = Text.literal((startMessageArray[0])).setStyle(Style.EMPTY.withColor(lightBlue))
                        .append(Text.literal(": " + startMessageArray[1] + " ").setStyle(Style.EMPTY.withColor(darkBlue)))
                        .append(Text.literal("with ").setStyle(Style.EMPTY.withColor(lightBlue)))
                        .append(Text.literal(endMessageArray[1]).setStyle(Style.EMPTY.withColor(darkBlue)))
                        .append(Text.literal(" laps, ").setStyle(Style.EMPTY.withColor(lightBlue)))
                        .append(Text.literal(endMessageArray[3]).setStyle(Style.EMPTY.withColor(darkBlue)))
                        .append(Text.literal(" pits ").setStyle(Style.EMPTY.withColor(lightBlue)))
                        .append(Text.literal(" -> Vote").setStyle(Style.EMPTY.withColor(0x17acfc).withBold(true)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, voteraceCommand))));

                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return true;

            } catch (Exception ArrayIndexOutOfBoundsException){
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Array index is out of bounds - voterace"));
                return false;
            }
        }
        return false;
    }
}
