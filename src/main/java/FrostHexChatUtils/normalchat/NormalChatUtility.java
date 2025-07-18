package FrostHexChatUtils.normalchat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class NormalChatUtility {
    public static String raceConditions(String rawMessage){
        // formatted as (player) : (track) : (laps) : (pits) //
        try {
            String[] Message = rawMessage.split(" with", 2);
            String[] endMessageArray = Message[1].split(" ", 5);

            //splits Player and Race
            String[] startMessageArray = Message[0].split(" ", 2);

            return startMessageArray[0] + ":" + startMessageArray[1] + ":" + endMessageArray[1] + ":" + endMessageArray[3];
        }catch(Exception ArrayIndexOutOfBoundsException){
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Could not create array: raceConditions"));
            return "[player] : [track] : [laps] : [pits]";
        }
    }
}
