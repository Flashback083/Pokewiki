package fr.pokepixel.pokewiki.config;

import fr.pokepixel.pokewiki.Pokewiki;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    private static final String CATEGORY_GENERAL = "General";
    private static boolean allowRightClickOpenWiki = false;

    //-----------------------------------------------------//
  
    
    public static void readConfig() {
        Configuration cfg = Pokewiki.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            Pokewiki.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
       
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        allowRightClickOpenWiki = cfg.getBoolean("allowRightClickOpenWiki", CATEGORY_GENERAL, allowRightClickOpenWiki, "Set to true to allow Sneak + Right clic on a pokemon to open his wiki");
    }
    

    

}
