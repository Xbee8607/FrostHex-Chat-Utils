package FrostHexChatUtils;

import FrostHexChatUtils.racechat.RaceMain;

public class RaceChatHandler {

    ///  Colours ///
    static final int lightBlueColour = 0x80c3fc;
    static final int darkBlueColour = 0x527bd8;
    static final int redColour = 0xfb5454;

    public static boolean show(String rawMessage){
        if(RaceMain.showFriendMessgages(rawMessage)){
            return true;
        }
        else if(RaceMain.showRaceVotingMessages(rawMessage, redColour, lightBlueColour, darkBlueColour)){
            return true;
        }
        else if(RaceMain.showRaceJoinMessages(rawMessage)){
            return true;
        }
        // I do not know if this works //
        else if(RaceMain.showMessagesWithPlayerName(rawMessage)){
            return true;
        }
        return false;
    }
}
