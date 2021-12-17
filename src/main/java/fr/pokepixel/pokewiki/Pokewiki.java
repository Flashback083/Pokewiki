package fr.pokepixel.pokewiki;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import fr.pokepixel.pokewiki.config.Lang;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.util.List;

@Mod(
        modid = Pokewiki.MOD_ID,
        name = Pokewiki.MOD_NAME,
        version = Pokewiki.VERSION,
        serverSideOnly = true,
        dependencies = "required-after:gooeylibs2",
        acceptableRemoteVersions = "*"
)
public class Pokewiki {

    public static final String MOD_ID = "pokewiki";
    public static final String MOD_NAME = "Pokewiki";
    public static final String VERSION = "1.3.0";

    public static File directory;
    public static Configuration lang;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Pokewiki INSTANCE;

    public static List<String> enPoke = Lists.newArrayList();
    public static List<String> trPoke = Lists.newArrayList();

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        directory = new File(event.getModConfigurationDirectory(), MOD_NAME);
        directory.mkdir();
        lang = new Configuration(new File(directory.getPath(), "lang.cfg"));
        Lang.readConfig();
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        for (EnumSpecies value : EnumSpecies.values()) {
            if (!value.getPokemonName().equalsIgnoreCase(value.getLocalizedName())){
                trPoke.add(value.getLocalizedName());
            }
            enPoke.add(value.getPokemonName());
        }
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new UserCmd());
    }


}
