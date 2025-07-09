package FrostHexChatUtils.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class FriendShowCommand {
    public static void friendShow(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(
                literal("FHCU")
                        .then(literal("friend")
                                .then(literal("list")
                                        .executes(context -> {
                                            MinecraftClient.getInstance().inGameHud.getChatHud()
                                                    .addMessage(Text.literal(CommandUtility.getFriendList().toString())
                                            );
                                            return 1;
                                        }))));
    }

}
