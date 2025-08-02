package FrostHexChatUtilsTest.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "frosthexchatutilstest")

public class ModConfigScreen implements ConfigData {

    public boolean enableMod = true;

    @ConfigEntry.Gui.Tooltip()
    public boolean entryMessages = true;
    
    @ConfigEntry.Gui.Tooltip()
    public boolean voteRaceMessages = true;

    @ConfigEntry.Gui.Tooltip()
    public boolean frostHexMessages = true;

}
