package FrostHexChatUtils.racechat;
import FrostHexChatUtils.commands.FriendAddCommand;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class RaceMain {
    public static boolean showMessgagesVerification(String rawMessage){
        String[] rawMessageArray = rawMessage.split(" ", 2);
        // Player is rawMessageArray[0] //
        for(String player : FriendAddCommand.getFriendList())
            if (player.equals(rawMessageArray[0])){

                return true;
            }
        return false;
    }
}
