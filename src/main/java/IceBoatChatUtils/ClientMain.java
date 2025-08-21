package IceBoatChatUtils;

import IceBoatChatUtils.config.ModConfigFile;
import IceBoatChatUtils.config.ModConfigScreen;
import IceBoatChatUtils.features.frosthex.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMain implements ClientModInitializer {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    @Override
    public void onInitializeClient() {
        // Sets up Mod config //
        AutoConfig.register(ModConfigScreen.class, GsonConfigSerializer::new);
        ModConfigScreen Config = AutoConfig.getConfigHolder(ModConfigScreen.class).getConfig();

        // Loading Config File //
        AtomicReference<String> ip = new AtomicReference<>("");
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ModConfigFile.load();
            if(client == null || client.getCurrentServerEntry() == null) {return;}
            ip.set(client.getCurrentServerEntry().address);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ModConfigFile.save();
            ip.set(null);
        });

        // Friend Command //
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            Commands.Load(dispatcher);
        });


        // Run Per Tick //
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            AutoJoin.Timer();
        });

        // Get Chat Messages //
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, sender) -> {
            String rawMessage = message.getString();

            if(client == null || client.getCurrentServerEntry() == null) {return false;}

            // Checks if mod is enabled and client is on FrostHex
            if(Config.frosthexsettings.enableFrostHex && ip.get().contains("frosthex")){
                Matcher checkBlankMessage = Pattern.compile("[a-zA-Z0-9]").matcher(rawMessage);

                // Player messages and questions
                if(Config.frosthexsettings.chathelpsettings.frostHexHelpMessages) {HelpMessage.Check(rawMessage);}
                if(rawMessage.contains("»")){return true;}

                // Server Messages
                if(!checkBlankMessage.find()){return false;}
                if(Config.frosthexsettings.entryMessages && EntryMessages.Check(rawMessage)) {return false;}
                if(Config.frosthexsettings.voteRaceMessages && VoteRaceMessages.Check(rawMessage)) {return false;}
                if(Config.frosthexsettings.frostHexMessages && FrostHexMessages.Check(rawMessage)) {return false;}
                AutoJoin.Check(rawMessage);

            }
            return true;
        });
    }
}
