package fr.pokepixel.pokewiki.config;

import fr.pokepixel.pokewiki.Pokewiki;
import net.minecraftforge.common.config.Configuration;

public class Lang {

    public static final String CATEGORY_GENERAL_LANG = "General lang config";
    private static String backtoformselection = "&bClick to go back to form selection";
    private static String pokemontype = "&bType:";
    private static String egggroups = "&bEgg groups: %egggroups%";
    private static String evo = "&bEvo:";
    private static String spawninfos = "&bSpawn infos:";
    private static String lorespawninfo1 = "&eClick to see the spawn list";
    private static String lorespawninfo2 = "&cThis pokemon dont spawn naturally!";
    private static String catchrate = "&bCatch Rate:";
    private static String baserate = "&eBase rate: %baserate%";
    private static String malepercent = "&aMale chance: &e%malepercent%%";
    private static String femalepercent = "&aFemale chance: &e%femalepercent%%";
    private static String ability = "&bAbility:";
    private static String normalability = "&e%ability%";
    private static String hiddenability = "&6%hiddenability% (Hidden)";
    private static String breed = "&bBreed:";
    private static String drops = "&bDrops:";

    public static final String CATEGORY_SPAWN_LANG = "Spawn lang config";
    private static String back = "&cBack:";
    private static String typeoflocation = "&eType of spawn location: &6%spawnlocation%";
    private static String minlevel = "&eMinimum level: &6%minlevel%";
    private static String maxlevel = "&eMaximum level: &6%maxlevel%";
    private static String helditems = "&eHeld items: &6%helditems%";
    private static String biomes = "&eBiomes: %biomes%";
    private static String rarity = "&eRarity: %rarity%";

    public static final String CATEGORY_EVO_LANG = "Evolution lang config";
    private static String levellingup = "&aLevelling up";
    private static String levelnumber = "&ato level %level%";
    private static String exposedtoitem = "&bWhen exposed to %item%";

    private static String tradedwith = "&dTrading with %pokemon%";
    private static String traded = "&dTrading";
    private static String chancecondition = "&3%chance% percent chance";
    private static String evorockcondition = "&bWithin %range% blocks of a %rockname%";
    private static String friendshipcondition = "&bFriendship: %friendship%";
    private static String gendercondition = "&bGender: %gender%";
    private static String helditemcondition = "&bHeld item: %helditem%";
    private static String abovealtitudecondition = "&bAbove altitude: %altitude%";
    private static String levelcondition = "&bStarting at level: %level%";

    public static void readConfig() {
        Configuration cfg = Pokewiki.lang;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initSpawnConfig(cfg);
            initEvoConfig(cfg);
        } catch (Exception e1) {
            System.out.println("Problem loading config file!");
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
       
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL_LANG, "Language configuration");
        backtoformselection = cfg.getString("backtoformselection", CATEGORY_GENERAL_LANG, backtoformselection, "&bClick to go back to form selection");
        pokemontype = cfg.getString("pokemontype", CATEGORY_GENERAL_LANG, pokemontype, "&bType:");
        egggroups = cfg.getString("egggroups", CATEGORY_GENERAL_LANG, egggroups, "&bEgg groups: %egggroups%");
        evo = cfg.getString("evo", CATEGORY_GENERAL_LANG, evo, "&bEvo:");
        spawninfos = cfg.getString("spawninfos", CATEGORY_GENERAL_LANG, spawninfos,  "&bSpawn infos:");
        lorespawninfo1 = cfg.getString("lorespawninfo1", CATEGORY_GENERAL_LANG, lorespawninfo1, "&eClick to see the spawn list");
        lorespawninfo2 = cfg.getString("lorespawninfo2", CATEGORY_GENERAL_LANG, lorespawninfo2, "&cThis pokemon dont spawn naturally!");
        catchrate = cfg.getString("catchrate", CATEGORY_GENERAL_LANG, catchrate, "&bCatch Rate:");
        baserate = cfg.getString("baserate", CATEGORY_GENERAL_LANG, baserate, "&eBase rate: %baserate%");
        malepercent = cfg.getString("malepercent", CATEGORY_GENERAL_LANG, malepercent, "&aMale chance: &e%malepercent%%");
        femalepercent = cfg.getString("femalepercent", CATEGORY_GENERAL_LANG, femalepercent, "&aFemale chance: &e%femalepercent%%");
        ability = cfg.getString("ability", CATEGORY_GENERAL_LANG, ability, "&bAbility:");
        normalability = cfg.getString("normalability", CATEGORY_GENERAL_LANG, normalability, "&e%ability%");
        hiddenability = cfg.getString("hiddenability", CATEGORY_GENERAL_LANG, hiddenability, "&6%hiddenability% (Hidden)");
        breed = cfg.getString("breed", CATEGORY_GENERAL_LANG, breed, "&bBreed:");
        drops = cfg.getString("drops", CATEGORY_GENERAL_LANG, drops, "&bDrops:");
    }

    private static void initSpawnConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_SPAWN_LANG, "Spawn Language configuration");
        back = cfg.getString("back", CATEGORY_SPAWN_LANG, back, "&cBack:");
        typeoflocation = cfg.getString("typeoflocation", CATEGORY_SPAWN_LANG, typeoflocation, "&eType of spawn location: &6%spawnlocation");
        minlevel = cfg.getString("minlevel", CATEGORY_SPAWN_LANG, minlevel, "&eMinimum level: &6%minlevel%");
        maxlevel = cfg.getString("maxlevel", CATEGORY_SPAWN_LANG, maxlevel, "&eMaximum level: &6%maxlevel%");
        helditems = cfg.getString("helditems", CATEGORY_SPAWN_LANG, helditems, "&eHeld items: &6%helditems%");
        biomes = cfg.getString("biomes", CATEGORY_SPAWN_LANG, biomes, "&eBiomes: %biomes%");
        rarity = cfg.getString("rarity", CATEGORY_SPAWN_LANG, rarity, "&eRarity: %rarity%");
    }

    private static void initEvoConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_EVO_LANG, "Evo Language configuration");
        levellingup = cfg.getString("levellingup", CATEGORY_EVO_LANG, levellingup, "&e%pokemoname%: &aLevelling up");
        levelnumber = cfg.getString("levelnumber", CATEGORY_EVO_LANG, levelnumber, "&ato level %level%");
        exposedtoitem = cfg.getString("exposedtoitem", CATEGORY_EVO_LANG, exposedtoitem, "&bWhen exposed to %item%");
        tradedwith = cfg.getString("tradedwith", CATEGORY_EVO_LANG, tradedwith, "&dTrading with %pokemon%");
        traded = cfg.getString("traded", CATEGORY_EVO_LANG, traded, "&dTrading");
        chancecondition = cfg.getString("chancecondition", CATEGORY_EVO_LANG, chancecondition, "&3%chance% percent chance");
        evorockcondition = cfg.getString("evorockcondition", CATEGORY_EVO_LANG, evorockcondition, "&bWithin %range% blocks of a %rockname%");

    }


}
