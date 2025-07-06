package FrostHexChatUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class RaceGame {

    public static boolean raceVotingChat(String rawMessage, int lightBlue, int darkBlue){
        if (rawMessage.contains(" just voted for a race on")){

            // Remove filler text
            rawMessage = rawMessage.replace(" just voted for a race on", "");

            try{
                // Split Player and Race with rest of command //
                String[] Message = rawMessage.split(" with", 2);

                String endMessage = Message[1];
                String[] endMessageArray = endMessage.split(" ", 5);

                //splits Player and Race
                String startMessage = Message[0];
                String[] startMessageArray = startMessage.split(" ", 2);

                String voteraceCommand = "/voterace " + startMessageArray[1] + " " + endMessageArray[1] + " " + endMessageArray[3];

                Text textMessage = Text.literal((startMessageArray[0])).setStyle(Style.EMPTY.withColor(lightBlue))
                        .append(Text.literal(": " + startMessageArray[1] + " ").setStyle(Style.EMPTY.withColor(darkBlue)))
                        .append(Text.literal("with ").setStyle(Style.EMPTY.withColor(lightBlue)))
                        .append(Text.literal(endMessageArray[1]).setStyle(Style.EMPTY.withColor(darkBlue)))
                        .append(Text.literal(" laps, ").setStyle(Style.EMPTY.withColor(lightBlue)))
                        .append(Text.literal(endMessageArray[3]).setStyle(Style.EMPTY.withColor(darkBlue)))
                        .append(Text.literal(" pits ").setStyle(Style.EMPTY.withColor(lightBlue)))
                        .append(Text.literal(" -> Vote").setStyle(Style.EMPTY.withColor(0x17acfc).withBold(true)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, voteraceCommand))));

                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
                return true;

            } catch (Exception ArrayIndexOutOfBoundsException){
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Array index is out of bounds - voterace"));
                return false;
            }
        }
        return false;
    }
}
