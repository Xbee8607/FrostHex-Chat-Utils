package FrostHexChatUtils;

import FrostHexChatUtils.config.ModConfigFile;
import FrostHexChatUtils.config.ModConfigScreen;
import FrostHexChatUtils.features.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Sets up Mod config //
        AutoConfig.register(ModConfigScreen.class, GsonConfigSerializer::new);
        ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();

        // Loading Config File //
        AtomicReference<String> ip = new AtomicReference<>("");
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ModConfigFile.load();
            if(MinecraftClient.getInstance() == null || MinecraftClient.getInstance().getCurrentServerEntry() == null) {return;}
            ip.set(MinecraftClient.getInstance().getCurrentServerEntry().address);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ModConfigFile.save();
        });

        // Friend Command //
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            FriendCommand.FriendCommands(dispatcher);
        });

        ClientReceiveMessageEvents.ALLOW_GAME.register((message, sender) -> {
            String rawMessage = message.getString();

            if(MinecraftClient.getInstance() == null || MinecraftClient.getInstance().getCurrentServerEntry() == null) {return false;}

            // Checks if mod is enabled and client is on FrostHex
            if(AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig().enableMod && ip.get().contains("frosthex")){
                Matcher checkBlankMessage = Pattern.compile("[a-zA-Z0-9]").matcher(rawMessage);

                // Player messages and questions
                if(Config.frostHexHelpMessages) {HelpMessage.Check(rawMessage);}
                if(rawMessage.contains("»")){return true;}

                // Server Messages
                if(!checkBlankMessage.find()){return false;}
                if(Config.entryMessages && EntryMessages.Check(rawMessage)) {return false;}
                if(Config.voteRaceMessages && VoteRaceMessages.Check(rawMessage)) {return false;}
                if(Config.frostHexMessages && FrostHexMessages.Check(rawMessage)) {return false;}

                return true;

            }
            return true;
        });
    }
}
