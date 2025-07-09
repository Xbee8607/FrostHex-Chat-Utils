package FrostHexChatUtils.racechat;
import FrostHexChatUtils.commands.Utility;

public class RaceMain {
    public static boolean showFriendMessgages(String rawMessage){
        String[] rawMessageArray = rawMessage.split(" ", 2);
        // Player is rawMessageArray[0] //
        for(String player : Utility.getFriendList())
            if (player.equals(rawMessageArray[0])){
                return true;
            }
        return false;
    }
}
