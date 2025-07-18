package FrostHexChatUtils;

import FrostHexChatUtils.racechat.RaceMain;

public class RaceChatHandler {

    ///  Colours ///
    static final int lightBlueColour = 0x80c3fc;
    static final int darkBlueColour = 0x527bd8;
    static final int redColour = 0xfb5454;

    public static boolean show(String rawMessage, String playerName){

        if(RaceMain.showFriendMessgages(rawMessage)){
            return true;
        }
        else if(RaceMain.showRaceVotingMessages(rawMessage, redColour, lightBlueColour, darkBlueColour)){
            return false;
        }
        else if(RaceMain.showRaceJoinMessages(rawMessage)){
            return true;
        }
        else if(RaceMain.showMessagesWithPlayerName(rawMessage, playerName)){
            return true;
        }
        return false;
    }
}
