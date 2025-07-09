package FrostHexChatUtils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class FriendAddCommand {

    public static void addFriend(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        CommandUtility.loadFriends(); // Load friends when registering the command
        dispatcher.register(literal("FHCU")
                .then(literal("friend")
                        .then(literal("add")
                            .then(argument("player", StringArgumentType.word())
                                .suggests(CommandUtility.PLAYER_SUGGESTIONS_ONLINE)
                                .executes(context -> {
                                    String playerName = StringArgumentType.getString(context, "player");

                                    if (!CommandUtility.friendList.contains(playerName)) {
                                        CommandUtility.friendList.add(playerName);
                                        CommandUtility.saveFriends(); // Save to file
                                        MinecraftClient.getInstance().inGameHud.getChatHud()
                                                .addMessage(Text.literal("Friend added: " + playerName));
                                    } else {
                                        MinecraftClient.getInstance().inGameHud.getChatHud()
                                                .addMessage(Text.literal(playerName + " is already your friend."));
                                    }

                                    return 1;
                                })))));
    }
}
