package FrostHexChatUtils.racechat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class RaceMain {
    public static boolean showMessgages(String rawMessage){
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Boolean Works"));
        return true;
    }
}
