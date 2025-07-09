package FrostHexChatUtils.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CommandUtility {

    public static final List<String> friendList = new ArrayList<>();
    private static final Path FRIENDS_FILE = Path.of("config/friendlist.json");
    private static final Gson GSON = new Gson();

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
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Friend list could not be loaded"));
        }
    }

    public static void saveFriends() {
        try {
            Files.createDirectories(FRIENDS_FILE.getParent());
            try (Writer writer = new FileWriter(FRIENDS_FILE.toFile())) {
                GSON.toJson(friendList, writer);
            }
        } catch (IOException e) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("File could not be saves"));
        }
    }

    public static List<String> getFriendList() {
        return friendList;
    }
    public static final SuggestionProvider<FabricClientCommandSource> PLAYER_SUGGESTIONS_ONLINE = (context, builder) -> {
        ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
        if (handler != null) {
            for (PlayerListEntry entry : handler.getPlayerList()) {
                builder.suggest(entry.getProfile().getName());
            }
        }
        return builder.buildFuture();
    };
}
