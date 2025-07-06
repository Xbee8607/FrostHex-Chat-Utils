package FrostHexChatUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlankGame {
    private static final Pattern letterFound = Pattern.compile("[a-zA-Z]");
    private static int count = 0;
    public static boolean blankChat(String rawMessage) {
        Matcher checkLetter = letterFound.matcher(rawMessage);

        if (!checkLetter.find()) {
            count++;
            if(count % 2 == 0){
                return true;
            }
            return false;
        }
        return false;
    }
}