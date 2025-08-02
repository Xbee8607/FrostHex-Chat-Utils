package FrostHexChatUtilsTest.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class FrostHexMessages {
    public static boolean Check(String rawMessage){
        if(rawMessage.contains("BoatUtils(BU)")){
            Text textMessage = Text.literal("[").formatted(Formatting.DARK_GRAY)
                    .append(Text.literal("FrostHex").formatted(Formatting.AQUA))
                    .append(Text.literal("Utils").formatted(Formatting.WHITE))
                    .append(Text.literal("]").formatted(Formatting.DARK_GRAY))
                    .append(Text.literal(" BoatUtils Message Sent").formatted(Formatting.GOLD));
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
            return true;
        }
        if(rawMessage.contains("The first driver has")){
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Race Ending in 120 Seconds")
                    .formatted(Formatting.GOLD));
            return true;
        }
        if(rawMessage.contains("50% of players have")) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Race Ending in 30 Seconds")
                    .formatted(Formatting.GOLD));
            return true;
        }
        return false;
    }
}
