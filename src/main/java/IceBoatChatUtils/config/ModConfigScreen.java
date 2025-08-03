package IceBoatChatUtils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "iceboatchatutils")

public class ModConfigScreen implements ConfigData {

    @ConfigEntry.Category("FrostHex")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public FrostHex frosthexsettings = new FrostHex();

    public static class FrostHex {
        public boolean enableFrostHex = true;

        @ConfigEntry.Gui.Tooltip
        public boolean entryMessages = true;

        @ConfigEntry.Gui.Tooltip
        public boolean voteRaceMessages = true;

        @ConfigEntry.Gui.Tooltip
        public boolean frostHexMessages = true;

        public boolean frostHexHelpMessages = false;

        @ConfigEntry.Gui.CollapsibleObject
        public ChatHelpCollapsible chathelpsettings = new ChatHelpCollapsible();
    }

    public static class ChatHelpCollapsible {
        @ConfigEntry.Gui.Tooltip
        public boolean voteRaceHelp = false;

        @ConfigEntry.Gui.Tooltip
        public boolean raceJoinHelp = false;

        @ConfigEntry.Gui.Tooltip
        public boolean raceLeaveHelp = false;

        @ConfigEntry.Gui.Tooltip
        public boolean trackTimeHelp = false;

        @ConfigEntry.Gui.Tooltip
        public boolean spawnBoatHelp = false;
    }

    @ConfigEntry.Category("BoatLabs")
    public boolean test = false;
}



