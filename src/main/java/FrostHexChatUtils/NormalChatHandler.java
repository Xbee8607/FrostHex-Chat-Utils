package FrostHexChatUtils;
import FrostHexChatUtils.normalchat.NormalMain;

public class NormalChatHandler {
    //  Colours //
    static final int lightBlueColour = 0x80c3fc;
    static final int darkBlueColour = 0x527bd8;
    static final int cyanColour = 0x54fbfc;

    static final int grayColour = 0x545454;
    static final int greenColour = 0x54fb54;

    static final int redColour = 0xfb5454;
    static final int yellowColour = 0xfbfb54;
    static final int orangeColour = 0xfba800;

    public static boolean show(String rawMessage) {if(rawMessage.contains("»")){
        return true;
    }
    else if(NormalMain.joinServerMessage(rawMessage, grayColour, greenColour, redColour, yellowColour)){
        return false;
    }
    else if(NormalMain.raceVotingMessage(rawMessage, lightBlueColour, darkBlueColour, cyanColour)){
        return false;
    }
    else if(NormalMain.raceFinishMessage(rawMessage, orangeColour)){
        return false;
    }
    else if(rawMessage.contains("whispers to you")){
        return true;
    }
    else if(NormalMain.boatUtilsMessage(rawMessage, grayColour, cyanColour, orangeColour)){
        return false;
    }
        return true;}

}
