package IceBoatChatUtils.features.frosthex;

import IceBoatChatUtils.config.ModConfigFile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntryMessages {

    public static boolean Check(String rawMessage){
        Matcher joinPatternMatch = Pattern.compile("\\[\\d+/\\d+]").matcher(rawMessage);
        if(joinPatternMatch.find()){
            try {
                String[] joinServerArray = rawMessage.split(" ", 3);
                if(rawMessage.contains("[+]")){
                    JoinMessage("+", joinServerArray, 0x54fb54);
                    return true;
                }
                if (rawMessage.contains("[-]")) {
                    JoinMessage("-", joinServerArray, 0xfb5454);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }
        return false;
    }
    private static void JoinMessage(String sign, String @NotNull [] playerName, int colour){

        if(ModConfigFile.friendList.contains(playerName[1].toLowerCase())){
            SoundEvents.BLOCK_NOTE_BLOCK_PLING.value();
        }
        Text textMessage = Text.literal("[").formatted(Formatting.DARK_GRAY)
                .append(Text.literal(sign).setStyle(Style.EMPTY.withColor(colour)))
                .append(Text.literal("] ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal(playerName[1]).formatted(Formatting.YELLOW));
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
        return;
    }
}
