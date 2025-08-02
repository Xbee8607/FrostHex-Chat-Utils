package FrostHexChatUtilsTest.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;


public class VoteRaceMessages{

    public static boolean Check(String rawMessage){
        if (rawMessage.contains(" just voted for a race on")){
            try {
                String[] Message = rawMessage.split(" ", 13);
                SendMessage(Message);
                return true;

            }catch(Exception ArrayIndexOutOfBoundsException){
               return false;
            }
        }
        return false;
    }
    private static void SendMessage(String[] Message){

        String voteraceCommand = "/voterace " + Message[7] + " " + Message[9] + " " + Message[11];
        Text textMessage = Text.literal(Message[0] + ":").setStyle(Style.EMPTY.withColor(0x54fbfc))
                .append(Text.literal(" " + Message[7] + " ").setStyle(Style.EMPTY.withColor(0x527bd8)))
                .append(Text.literal("with ").setStyle(Style.EMPTY.withColor(0x80c3fc)))
                .append(Text.literal(Message[9]).setStyle(Style.EMPTY.withColor(0x527bd8)))
                .append(Text.literal(" laps, ").setStyle(Style.EMPTY.withColor(0x80c3fc)))
                .append(Text.literal(Message[11]).setStyle(Style.EMPTY.withColor(0x527bd8)))
                .append(Text.literal(" pits ").setStyle(Style.EMPTY.withColor(0x80c3fc)))
                .append(Text.literal(" -> Vote").setStyle(Style.EMPTY.withColor(0x17acfc).withBold(true)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, voteraceCommand))));

        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(textMessage);
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("")); // Add blank line
    }
}
