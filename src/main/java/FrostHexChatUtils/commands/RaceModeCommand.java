package FrostHexChatUtils.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class RaceModeCommand {
    public static boolean raceModeNumber = false;

    public static void Activate(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(
                literal("racemode")
                        .executes(context -> {
                            // Turn off race mode //
                            if(raceModeNumber){
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("RaceMode disabled!"));
                                raceModeNumber = false;
                                return 1;
                            }
                            // Turn on race mode //
                            else{
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("RaceMode enabled!"));
                                raceModeNumber = true;
                                return 1;
                            }
                        })
        );
    }

}
