package FrostHexChatUtils.normalchat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class NormalChatUtility {
    public static String raceConditions(String rawMessage){
        // formatted as (player) : (track) : (laps) : (pits) //
        try {
            String[] Message = rawMessage.split(" with", 2);
            String endMessage = Message[1];
            String[] endMessageArray = endMessage.split(" ", 5);

            //splits Player and Race
            String startMessage = Message[0];
            String[] startMessageArray = startMessage.split(" ", 2);

            return startMessageArray[0] + ":" + startMessageArray[1] + ":" + endMessageArray[1] + ":" + endMessageArray[3];
        }catch(Exception ArrayIndexOutOfBoundsException){
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Could not create array: raceConditions"));
            return "[player] : [track] : [laps] : [pits]";
        }
    }
}
