package FrostHexChatUtils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ClientPlayNetworkHandler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class FriendAddCommand {


    public static final List<String> friendList = new ArrayList<>();
    private static final Path FRIENDS_FILE = Path.of("config/friendlist.json");
    private static final Gson GSON = new Gson();

    // Autocomplete player names
    public static final SuggestionProvider<FabricClientCommandSource> PLAYER_SUGGESTIONS = (context, builder) -> {
        ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
        if (handler != null) {
            for (PlayerListEntry entry : handler.getPlayerList()) {
                builder.suggest(entry.getProfile().getName());
            }
        }
        return builder.buildFuture();
    };

    public static void addFriend(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        loadFriends(); // Load friends when registering the command

        dispatcher.register(literal("FHCU")
                .then(literal("friend")
                        .then(literal("add")
                            .then(argument("player", StringArgumentType.word())
                                .suggests(PLAYER_SUGGESTIONS)
                                .executes(context -> {
                                    String playerName = StringArgumentType.getString(context, "player");

                                    if (!friendList.contains(playerName)) {
                                        friendList.add(playerName);
                                        saveFriends(); // Save to file
                                        MinecraftClient.getInstance().inGameHud.getChatHud()
                                                .addMessage(Text.literal("Friend added: " + playerName));
                                    } else {
                                        MinecraftClient.getInstance().inGameHud.getChatHud()
                                                .addMessage(Text.literal(playerName + " is already your friend."));
                                    }

                                    return 1;
                                })))));
    }

    public static List<String> getFriendList() {
        return friendList;
    }

    // Save the friend list to a file
    public static void saveFriends() {
        try {
            Files.createDirectories(FRIENDS_FILE.getParent());
            try (Writer writer = new FileWriter(FRIENDS_FILE.toFile())) {
                GSON.toJson(friendList, writer);
            }
        } catch (IOException e) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("File could not be created"));
        }
    }

    // Load the friend list from a file
    public static void loadFriends() {
        if (!Files.exists(FRIENDS_FILE)) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("File does not exist"));
            return;
        }
        try (Reader reader = new FileReader(FRIENDS_FILE.toFile())) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> loaded = GSON.fromJson(reader, listType);
            friendList.clear();
            if (loaded != null) friendList.addAll(loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
