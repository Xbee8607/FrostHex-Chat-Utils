package FrostHexChatUtils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "frosthexchatutils")

public class ModConfigScreen implements ConfigData {

    public boolean enableMod = true;

    @ConfigEntry.Gui.Tooltip()
    public boolean entryMessages = true;
    
    @ConfigEntry.Gui.Tooltip()
    public boolean voteRaceMessages = true;

    @ConfigEntry.Gui.Tooltip()
    public boolean frostHexMessages = true;


    public boolean frostHexHelpMessages = false;

    @ConfigEntry.Gui.CollapsibleObject
    public ChatHelpSettings extraSettings = new ChatHelpSettings();

    public static class ChatHelpSettings {
        @ConfigEntry.Gui.Tooltip()
        public boolean voteRaceHelp = false;

        @ConfigEntry.Gui.Tooltip()
        public boolean raceJoinHelp = false;

        @ConfigEntry.Gui.Tooltip()
        public boolean trackTimeHelp = false;
    }

}



