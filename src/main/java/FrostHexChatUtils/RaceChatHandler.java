package FrostHexChatUtils;

import FrostHexChatUtils.racechat.RaceMain;

public class RaceChatHandler {

    public static boolean show(String rawMessage){
        if(RaceMain.showFriendMessgages(rawMessage)){
            return true;
        }
        return false;
    }
}
