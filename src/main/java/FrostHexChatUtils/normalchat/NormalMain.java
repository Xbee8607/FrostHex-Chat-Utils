package FrostHexChatUtils.normalchat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalMain {
    public static boolean boatUtilsMessage(String rawMessage, int gray, int cyan, int orange){
        if(rawMessage.contains("BoatUtils(BU)")){
            Text textMessage = Text.literal("[").setStyle(Style.EMPTY.withColor(gray))
                    .append(Text.literal("FrostHex").setStyle(Style.EMPTY.withColor(cyan)))
                    .append(Text.literal("Utils").setStyle(Style.EMPTY.withColor(0xFFFFFF)))
                    .append(Text.literal("]").setStyle(Style.EMPTY.withColor(gray)))
                    .append(Text.literal(" BoatUtils Message Sent")).setStyle(Style.EMPTY.withColor(orange));

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
            return true;
        }
        return  false;
    }
    private static final Pattern JOIN_PATTERN = Pattern.compile("\\[\\d+/\\d+]");

    public static boolean joinServerMessage(String rawMessage, int gray, int green, int red, int yellow){
        Matcher joinPatternMatch = JOIN_PATTERN.matcher(rawMessage);

        // For player count //
        if (joinPatternMatch.find()) {
            String newMessage = rawMessage.replaceAll("\\[\\d+/\\d+]", "");

            // If player was in race //
            if (rawMessage.contains("[Heat Driver]")) {
                newMessage = newMessage.replace("[Heat Driver]", "");
            }

            /// Colour in text ///

            String[] joinServerArray = newMessage.split(" ", 2);

            // [+] Player //
            if(newMessage.contains("+")){
                Text textMessage = Text.literal("[").setStyle(Style.EMPTY.withColor(gray))
                        .append(Text.literal("+").setStyle(Style.EMPTY.withColor(green)))
                        .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(gray)))
                        .append(Text.literal(joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return true;
            }
            // [-] Player //
            else{
                Text textMessage = Text.literal("[").setStyle(Style.EMPTY.withColor(gray))
                        .append(Text.literal("-").setStyle(Style.EMPTY.withColor(red)))
                        .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(gray)))
                        .append(Text.literal(joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return true;
            }
        }
        return false;
    }
    public static boolean raceVotingMessage(String rawMessage, int lightBlue, int darkBlue, int cyan){
        if (rawMessage.contains(" just voted for a race on")){

            // Remove filler text
            rawMessage = rawMessage.replace(" just voted for a race on", "");

            String[] conditionsArray = NormalChatUtility.raceConditions(rawMessage).split(":", 4);

            String voteraceCommand = "/voterace " + conditionsArray[1] + " " + conditionsArray[2] + " " + conditionsArray[3];

            Text textMessage = Text.literal(conditionsArray[0] + ":").setStyle(Style.EMPTY.withColor(cyan))
                    .append(Text.literal(" " + conditionsArray[1] + " ").setStyle(Style.EMPTY.withColor(darkBlue)))
                    .append(Text.literal("with ").setStyle(Style.EMPTY.withColor(lightBlue)))
                    .append(Text.literal(conditionsArray[2]).setStyle(Style.EMPTY.withColor(darkBlue)))
                    .append(Text.literal(" laps, ").setStyle(Style.EMPTY.withColor(lightBlue)))
                    .append(Text.literal(conditionsArray[3]).setStyle(Style.EMPTY.withColor(darkBlue)))
                    .append(Text.literal(" pits ").setStyle(Style.EMPTY.withColor(lightBlue)))
                    .append(Text.literal(" -> Vote").setStyle(Style.EMPTY.withColor(0x17acfc).withBold(true)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, voteraceCommand))));

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("")); // Add blank line
            return true;
        }
        return false;
    }

    public static boolean raceFinishMessage(String rawMessage, int orange){
        if(rawMessage.contains("The first driver has")){
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Race Ending in 120 Seconds")
                    .setStyle(Style.EMPTY.withColor(orange)));
            return true;
        }

        if(rawMessage.contains("50% of players have")){
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Race Ending in 30 Seconds")
                    .setStyle(Style.EMPTY.withColor(orange)));
            return true;
        }
        return false;
    }

}
