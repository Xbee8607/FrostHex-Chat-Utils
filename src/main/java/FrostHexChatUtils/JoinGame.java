package FrostHexChatUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinGame {
    private static final Pattern JOIN_PATTERN = Pattern.compile("\\[\\d+/\\d+]");

    public static boolean joinServerChat(String rawMessage, int gray, int green, int red, int yellow){
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
            if(newMessage.contains("+")){
                Text textMessage = Text.literal("[").setStyle(Style.EMPTY.withColor(gray))
                        .append(Text.literal("+").setStyle(Style.EMPTY.withColor(green)))
                        .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(gray)))
                        .append(Text.literal(joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return true;
            }
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
}
