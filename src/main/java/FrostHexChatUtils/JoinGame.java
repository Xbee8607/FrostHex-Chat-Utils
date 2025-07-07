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


            /// /// MAKE THIS FASTER /// ///
            try{
                String[] joinServerArray = newMessage.split(" ", 2);
                String joinServerSymbol = joinServerArray[0];

                // Someone joins Server //
                if(rawMessage.contains("+")) {

                    String[] joinServerSymbolArray = joinServerSymbol.split("\\+", 2);
                    Text textMessage = Text.literal((joinServerSymbolArray[0])).setStyle(Style.EMPTY.withColor(gray))
                            .append(Text.literal("+").setStyle(Style.EMPTY.withColor(green)))
                            .append(Text.literal(joinServerSymbolArray[1]).setStyle(Style.EMPTY.withColor(gray)))
                            .append(Text.literal(" " + joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                    return true;
                }

                // Someone leaves server //
                else if(rawMessage.contains("-")){
                    String[] joinServerSymbolArray = joinServerSymbol.split("-", 2);
                    Text textMessage = Text.literal((joinServerSymbolArray[0])).setStyle(Style.EMPTY.withColor(gray))
                            .append(Text.literal("-").setStyle(Style.EMPTY.withColor(red)))
                            .append(Text.literal(joinServerSymbolArray[1]).setStyle(Style.EMPTY.withColor(gray)))
                            .append(Text.literal(" " + joinServerArray[1]).setStyle(Style.EMPTY.withColor(yellow)));
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                    return true;
                    }

                // Someone said [int/int] //
                else{
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Cannot find '+' or '-'"));
                    return false;
                }
            } catch (Exception ArrayIndexOutOfBoundsException){
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Array Index out of bounds"));
                return false;
            }
        }
        return false;
    }
}
