package fr.pokepixel.pokewiki;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import fr.pokepixel.pokewiki.cmds.UserCmd;
import fr.pokepixel.pokewiki.config.Config;
import fr.pokepixel.pokewiki.config.Lang;
import fr.pokepixel.pokewiki.info.CustomInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import static fr.pokepixel.pokewiki.config.GsonUtils.getAllCustom;

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
    public static final String VERSION = "2.0.0";

    public static File directory;
    public static Configuration lang;
    public static Configuration config;

    public static Logger logger;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Pokewiki INSTANCE;

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static File customspawninfo;

    public static List<String> enPoke = Lists.newArrayList();
    public static List<String> trPoke = Lists.newArrayList();

    public static HashMap<String, List<CustomInfo.CustomSpawnPokemonInfo>> customSpawnPokemonInfoListInfo;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        directory = new File(event.getModConfigurationDirectory(), MOD_NAME);
        directory.mkdir();
        customspawninfo = new File(directory, "customspawninfo.json");
        boolean check = customspawninfo.exists();
        if (!check) {
            PrintWriter start;
            try {
                start = new PrintWriter(customspawninfo,"UTF-8");
                List<CustomInfo.CustomSpawnPokemonInfo> customSpawnPokemonInfos = Lists.newArrayList();
                customSpawnPokemonInfos.add(new CustomInfo.CustomSpawnPokemonInfo("MissingNo",Lists.newArrayList("Who knows ?")));
                HashMap<String, List<CustomInfo.CustomSpawnPokemonInfo>> defaultHashMap = new HashMap<String, List<CustomInfo.CustomSpawnPokemonInfo>>();
                defaultHashMap.put("MissingNo",customSpawnPokemonInfos);
                start.write(gson.toJson(new CustomInfo(defaultHashMap)));
                start.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        lang = new Configuration(new File(directory.getPath(), "lang.cfg"));
        Lang.readConfig();
        config = new Configuration(new File(directory.getPath(), "config.cfg"));
        Config.readConfig();
        MinecraftForge.EVENT_BUS.register(new ForgeEvents());
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
        customSpawnPokemonInfoListInfo = getAllCustom();
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new UserCmd());
    }


}
