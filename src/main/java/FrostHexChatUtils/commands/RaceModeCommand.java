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
                literal("FHCU")
                        .then(literal("racemode")
                                .executes(context -> {
                                // Turn off race mode //
                                if(raceModeNumber){
                                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("RaceMode disabled!"));
                                    raceModeNumber = false;
                                    if(MinecraftClient.getInstance().player != null){
                                        MinecraftClient.getInstance().player.experienceProgress = 0.0f;
                                    }

                                }
                                // Turn on race mode //
                                else{
                                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("RaceMode enabled!"));
                                    raceModeNumber = true;
                                    if(MinecraftClient.getInstance().player != null){
                                        MinecraftClient.getInstance().player.experienceProgress = 1.0f;
                                    }
                                }
                                    return 1;
                                })));
    }

}
