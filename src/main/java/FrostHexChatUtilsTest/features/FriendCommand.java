package FrostHexChatUtilsTest.features;

import FrostHexChatUtilsTest.config.ModConfigFile;
import FrostHexChatUtilsTest.config.ModConfigScreen;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import net.minecraft.util.Formatting;

import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class FriendCommand {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Text FrostHexChatUtilsName;
    static {
        FrostHexChatUtilsName = Text.literal("")
                .copy()
                .append(Text.literal("[").formatted(Formatting.DARK_GRAY))
                .append(Text.literal("FrostHex").formatted(Formatting.AQUA))
                .append(Text.literal("ChatUtils").formatted(Formatting.WHITE))
                .append(Text.literal("] ").formatted(Formatting.DARK_GRAY));
    }

    public static void FriendCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        assert client.player != null;
        List<String> ChatUtils = List.of("cu", "FHCU", "FrostHexChatUtils");

        for (String cc : ChatUtils) {
            dispatcher.register(literal(cc)
                    .executes(context -> {
                        client.player.sendMessage(Text.literal("Missing Args").formatted(Formatting.RED));
                        return 1;
                    })
                    .then(literal("friend")
                            .executes(context -> {
                                client.player.sendMessage(Text.literal("Missing Args").formatted(Formatting.RED));
                                return 1;
                            })
                            .then(literal("add")
                                    .executes(context -> {
                                        client.player.sendMessage(Text.literal("Missing Args").formatted(Formatting.RED));
                                        return 1;
                                    })
                                    .then(argument("player", StringArgumentType.word())
                                            .suggests(PLAYER_SUGGESTIONS_ONLINE)
                                            .executes(context -> {
                                                if(new ModConfigScreen().enableMod)
                                                {String playerName = StringArgumentType.getString(context, "player");
                                                    if(!ModConfigFile.friendList.contains(playerName)){
                                                        ModConfigFile.friendList.add(playerName);
                                                        ModConfigFile.save();
                                                        Text Message = FrostHexChatUtilsName.copy()
                                                                .append(Text.literal(playerName + " Added!").formatted(Formatting.GRAY));
                                                        client.inGameHud.getChatHud().addMessage(Message);
                                                    }
                                                    else{
                                                        Text Message = FrostHexChatUtilsName.copy()
                                                                .append(Text.literal(playerName + "Is already in the list!").formatted(Formatting.GRAY));
                                                        client.inGameHud.getChatHud().addMessage(Message);
                                                    }
                                                }
                                                return 1;
                                            })))
                            .then(literal("remove")
                                    .executes(context -> {
                                        client.player.sendMessage(Text.literal("Missing Args").formatted(Formatting.RED));
                                        return 1;
                                    })
                                    .then(argument("player", StringArgumentType.word())
                                            .suggests(PLAYER_SUGGESTIONS_ONLINE)
                                            .executes(context -> {
                                                if(new ModConfigScreen().enableMod){
                                                    String playerName = StringArgumentType.getString(context, "player");
                                                    if(ModConfigFile.friendList.contains(playerName)){
                                                        ModConfigFile.friendList.remove(playerName);
                                                        ModConfigFile.save();
                                                        Text Message = FrostHexChatUtilsName.copy()
                                                                .append(Text.literal(playerName + " Removed!").formatted(Formatting.GRAY));
                                                        client.inGameHud.getChatHud().addMessage(Message);
                                                    }
                                                    else{
                                                        Text Message = FrostHexChatUtilsName.copy()
                                                                .append(Text.literal(playerName + "Is not on the list!").formatted(Formatting.GRAY));
                                                        client.inGameHud.getChatHud().addMessage(Message);
                                                    }
                                                }
                                                return 1;
                                            })))
                            .then(literal("list")
                                    .executes(context -> {
                                        if(new ModConfigScreen().enableMod){
                                            if(ModConfigFile.friendList.isEmpty()){
                                                client.player.sendMessage(FrostHexChatUtilsName.copy()
                                                        .append(Text.literal("Friend list is empty :(").formatted(Formatting.GRAY)));
                                            }else{
                                                Text message = FrostHexChatUtilsName.copy()
                                                        .append(Text.literal("Friend List:\n").formatted(Formatting.AQUA));
                                                for (String name : ModConfigFile.friendList) {
                                                    message = message.copy().append(Text.literal("- " + name + "\n").formatted(Formatting.GRAY));
                                                }
                                                client.player.sendMessage(message);
                                            }
                                        }
                                        return 1;
                                    })))
            );
        }
    }
    private static final SuggestionProvider<FabricClientCommandSource> PLAYER_SUGGESTIONS_ONLINE = (context, builder) -> {
        ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
        if (handler != null) {
            for (PlayerListEntry entry : handler.getPlayerList()) {
                builder.suggest(entry.getProfile().getName());
            }
        }
        return builder.buildFuture();
    };
}
