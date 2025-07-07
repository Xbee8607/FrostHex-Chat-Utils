package FrostHexChatUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class RaceGame {

    public static boolean raceVotingChat(String rawMessage, int lightBlue, int darkBlue, int cyan){
        if (rawMessage.contains(" just voted for a race on")){

            // Remove filler text
            rawMessage = rawMessage.replace(" just voted for a race on", "");

                String[] conditionsArray = raceConditions(rawMessage).split(":", 4);

                String voteraceCommand = "/voterace " + conditionsArray[1] + " " + conditionsArray[2] + " " + conditionsArray[3];

                Text textMessage = Text.literal(conditionsArray[0] + ":").setStyle(Style.EMPTY.withColor(cyan))
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

    public static boolean raceFinishChat(String rawMessage, int orange){
        if(rawMessage.contains("50% of players have")){
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Race Ending in 30 Seconds")
                    .setStyle(Style.EMPTY.withColor(orange)));
            return true;
        }
        return false;
    }

    private static String raceConditions(String rawMessage){
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
