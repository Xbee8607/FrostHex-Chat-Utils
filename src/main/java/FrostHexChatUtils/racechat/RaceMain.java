package FrostHexChatUtils.racechat;
import FrostHexChatUtils.commands.CommandUtility;
import FrostHexChatUtils.normalchat.NormalChatUtility;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;


public class RaceMain {
    public static boolean showFriendMessgages(String rawMessage){
        String[] rawMessageArray = rawMessage.split(" ", 2);
        // Player is rawMessageArray[0] //
        for(String player : CommandUtility.getFriendList())
            if (player.equals(rawMessageArray[0])){
                return true;
            }
        return false;
    }
    public static boolean showRaceVotingMessages(String rawMessage, int red, int darkBlue, int lightBlue){
        if (rawMessage.contains(" just voted for a race on")){

            // Remove filler text
            rawMessage = rawMessage.replace(" just voted for a race on", "");

            String[] conditionsArray = NormalChatUtility.raceConditions(rawMessage).split(":", 4);

            String voteraceCommand = "/voterace " + conditionsArray[1] + " " + conditionsArray[2] + " " + conditionsArray[3];

            Text textMessage = Text.literal(conditionsArray[0] + ":").setStyle(Style.EMPTY.withColor(red))
                    .append(Text.literal(" " + conditionsArray[1] + " ").setStyle(Style.EMPTY.withColor(darkBlue)))
                    .append(Text.literal("with ").setStyle(Style.EMPTY.withColor(lightBlue)))
                    .append(Text.literal(conditionsArray[2]).setStyle(Style.EMPTY.withColor(darkBlue)))
                    .append(Text.literal(" laps, ").setStyle(Style.EMPTY.withColor(lightBlue)))
                    .append(Text.literal(conditionsArray[3]).setStyle(Style.EMPTY.withColor(darkBlue)))
                    .append(Text.literal(" pits ").setStyle(Style.EMPTY.withColor(lightBlue)))
                    .append(Text.literal(" -> Vote").setStyle(Style.EMPTY.withColor(0x17acfc).withBold(true)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, voteraceCommand))));

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("")); // Add blank line
            return true;
        }
        return false;
    }
    public static boolean showRaceJoinMessages(String rawMessage){
        if(rawMessage.contains("Click to join a race on")){
            return true;
        }
        return false;
    }
    public static boolean showMessagesWithPlayerName(String rawMessage){
        assert MinecraftClient.getInstance().player != null;
        if(rawMessage.contains(MinecraftClient.getInstance().player.toString())){
            return true;
        }
        return false;
    }
}
