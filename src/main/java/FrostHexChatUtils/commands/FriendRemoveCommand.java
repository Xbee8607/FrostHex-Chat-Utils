package FrostHexChatUtils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Iterator;
import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class FriendRemoveCommand {

    public static void friendRemove(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(literal("FHCU")
                .then(literal("friend")
                        .then(literal("remove")
                                .then(argument("player", StringArgumentType.word())
                                        .suggests(Utility.PLAYER_SUGGESTIONS_ONLINE)
                                        .executes(context -> {
                                            String playerNameRemove = StringArgumentType.getString(context, "player");
                                            Iterator<String> iterator = Utility.getFriendList().iterator();
                                            if(Utility.getFriendList().isEmpty()){
                                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("No friend to remove"));
                                            } else{
                                                while (iterator.hasNext()) {
                                                    String player = iterator.next();
                                                    if (Objects.equals(player, playerNameRemove)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(playerNameRemove + " has been removed!"));
                                                return 1;
                                            }


                                            return 1;
                                        })))));
    }
}
