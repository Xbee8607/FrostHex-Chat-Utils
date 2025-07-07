package FrostHexChatUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class FrostHexUtils {

    public static boolean boatUtilsMessage(String rawMessage, int gray, int cyan, int orange){
        if(rawMessage.contains("BoatUtils(BU)")){
            Text textMessage = Text.literal("[").setStyle(Style.EMPTY.withColor(gray))
                    .append(Text.literal("FrostHex").setStyle(Style.EMPTY.withColor(cyan)))
                    .append(Text.literal("Utils")).setStyle(Style.EMPTY.withColor(0xFFFFFF))
                    .append(Text.literal("]").setStyle(Style.EMPTY.withColor(gray)))
                    .append(Text.literal(" BoatUtils Message Sent")).setStyle(Style.EMPTY.withColor(orange));

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
            return true;
        }
        return  false;
    }
}
