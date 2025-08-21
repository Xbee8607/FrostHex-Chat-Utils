package IceBoatChatUtils.features.frosthex;

import IceBoatChatUtils.config.ModConfigFile;
import IceBoatChatUtils.config.ModConfigScreen;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import net.minecraft.util.Formatting;

import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Commands {
    static ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();
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

    public static void Load(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        assert client.player != null;
        List<String> ChatUtils = List.of("cu", "IBCU", "IceBoatChatUtils");

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
                                                if(Config.frosthexsettings.enableFrostHex){
                                                    String playerName = StringArgumentType.getString(context, "player").toLowerCase();
                                                    addToList(playerName, ModConfigFile.friendList);
                                                    return 1;
                                                }
                                                modNotEnabled();
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
                                                if(Config.frosthexsettings.enableFrostHex){
                                                    String playerName = StringArgumentType.getString(context, "player");
                                                    removeFromList(playerName, ModConfigFile.friendList);
                                                    return 1;
                                                }
                                                modNotEnabled();
                                                return 1;
                                            })))

                            .then(literal("list")
                                    .executes(context -> {
                                        if(Config.frosthexsettings.enableFrostHex){
                                            showList(ModConfigFile.friendList);
                                            return 1;
                                        }
                                        modNotEnabled();
                                        return 1;
                                    })))

                    .then(literal("instantJoinTrack")
                            .executes(context -> {
                                Config.frosthexsettings.autojoinsettings.autoJoin = !Config.frosthexsettings.autojoinsettings.autoJoin;
                                Text message = FrostHexChatUtilsName.copy()
                                        .append(Text.literal("AutoJoin ").formatted(Formatting.GRAY))
                                        .append(Text.literal(Config.frosthexsettings.autojoinsettings.autoJoin ? "Enabled" : "Disabled")
                                                .formatted(Config.frosthexsettings.autojoinsettings.autoJoin ? Formatting.GREEN : Formatting.RED));
                                client.player.sendMessage(message);
                                return 1;
                            })
                            .then(literal("Remove")
                                    .executes(context -> {
                                        client.player.sendMessage(Text.literal("Missing Args").formatted(Formatting.RED));
                                        return 1;
                                    })
                                    .then(argument("track", StringArgumentType.word())
                                    .executes(context -> {
                                        if(Config.frosthexsettings.enableFrostHex){
                                            String trackName = StringArgumentType.getString(context, "track");
                                            removeFromList(trackName, ModConfigFile.trackList);
                                            return 1;
                                        }
                                        modNotEnabled();
                                        return 1;
                                    })))

                            .then(literal("Add")
                                    .executes(context -> {
                                        client.player.sendMessage(Text.literal("Missing Args").formatted(Formatting.RED));
                                        return 1;
                                    })
                                    .then(argument("track", StringArgumentType.word())
                                    .executes(context -> {
                                        if(Config.frosthexsettings.enableFrostHex){
                                            String trackName = StringArgumentType.getString(context, "track");
                                            addToList(trackName, ModConfigFile.trackList);
                                            return 1;
                                        }
                                        modNotEnabled();
                                        return 1;
                                    })))

                            .then(literal("List")
                                    .executes(context -> {
                                        if(Config.frosthexsettings.enableFrostHex){
                                            showList(ModConfigFile.trackList);
                                            return 1;
                                        }
                                        modNotEnabled();
                                        return 1;
                                    }))
                    )
                    .then(literal("helpMessages")
                            .executes(context -> {
                                if(Config.frosthexsettings.enableFrostHex){
                                    Config.frosthexsettings.chathelpsettings.frostHexHelpMessages = !Config.frosthexsettings.chathelpsettings.frostHexHelpMessages;
                                    Text message = FrostHexChatUtilsName.copy()
                                            .append(Text.literal("HelpMessages ").formatted(Formatting.GRAY))
                                            .append(Text.literal(Config.frosthexsettings.chathelpsettings.frostHexHelpMessages ? "Enabled" : "Disabled")
                                                    .formatted(Config.frosthexsettings.chathelpsettings.frostHexHelpMessages ? Formatting.GREEN : Formatting.RED));
                                    client.player.sendMessage(message);
                                }
                                return 0;
                            }))
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
    private static void addToList(String message, List<String> list){
        message = message.toLowerCase();
        if(!list.contains(message)){
            list.add(message);
            ModConfigFile.save();
            Text Message = FrostHexChatUtilsName.copy()
                    .append(Text.literal(message + " Added!").formatted(Formatting.GRAY));
            client.inGameHud.getChatHud().addMessage(Message);
        }
        else{
            Text Message = FrostHexChatUtilsName.copy()
                    .append(Text.literal(message + "Is already in the list!").formatted(Formatting.GRAY));
            client.inGameHud.getChatHud().addMessage(Message);
        }
    }
    private static void modNotEnabled(){
        Text textMessage = FrostHexChatUtilsName.copy()
                .append(Text.literal("Mod not Enabled!"));
        client.inGameHud.getChatHud().addMessage(textMessage);
    }

    private static void removeFromList(String message, List<String> list){
        message = message.toLowerCase();
        if(list.contains(message)){
            list.remove(message);
            ModConfigFile.save();
            Text Message = FrostHexChatUtilsName.copy()
                    .append(Text.literal(message + " Removed!").formatted(Formatting.GRAY));
            client.inGameHud.getChatHud().addMessage(Message);
        }
        else{
            Text Message = FrostHexChatUtilsName.copy()
                    .append(Text.literal(message + "Is not on the list!").formatted(Formatting.GRAY));
            client.inGameHud.getChatHud().addMessage(Message);
        }
    }

    private static void showList(List<String> list){
        assert client.player != null;
        if(list.isEmpty()){
            client.player.sendMessage(FrostHexChatUtilsName.copy()
                    .append(Text.literal("Track list is empty :(").formatted(Formatting.GRAY)));
        }else{
            Text message = FrostHexChatUtilsName.copy()
                    .append(Text.literal("Track List:\n").formatted(Formatting.AQUA));
            for (String name : list) {
                message = message.copy().append(Text.literal("- " + name + "\n").formatted(Formatting.GRAY));
            }
            client.player.sendMessage(message);
        }
    }
}
