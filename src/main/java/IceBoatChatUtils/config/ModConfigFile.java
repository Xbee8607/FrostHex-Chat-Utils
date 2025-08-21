package IceBoatChatUtils.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModConfigFile {

    public static final List<String> trackList = new ArrayList<>();
    private static final Path TRACK_FILE = Path.of("config/ibcuconfigtrack.json");

    public static final List<String> friendList = new ArrayList<>();
    private static final Path FRIENDS_FILE = Path.of("config/ibcuconfigfriends.json");
    private static final Gson GSON = new Gson();

    public static void load() {
        if (!Files.exists(FRIENDS_FILE) || !Files.exists(TRACK_FILE)) {
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
        try (Reader reader = new FileReader(TRACK_FILE.toFile())) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> loaded = GSON.fromJson(reader, listType);
            trackList.clear();
            if (loaded != null) trackList.addAll(loaded);

        } catch (IOException e) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Track list could not be loaded"));
        }
    }

    public static void save() {
        try {
            Files.createDirectories(FRIENDS_FILE.getParent());
            try (Writer writer = new FileWriter(FRIENDS_FILE.toFile())) {
                GSON.toJson(friendList, writer);
            }
        } catch (IOException e) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("File could not be saved"));
        }
        try {
            Files.createDirectories(TRACK_FILE.getParent());
            try (Writer writer = new FileWriter(TRACK_FILE.toFile())) {
                GSON.toJson(trackList, writer);
            }
        } catch (IOException e) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("File could not be saved"));
        }
    }
}
