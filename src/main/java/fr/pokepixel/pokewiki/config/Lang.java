package fr.pokepixel.pokewiki.config;

import fr.pokepixel.pokewiki.Pokewiki;
import net.minecraftforge.common.config.Configuration;

public class Lang {

    public static final String CATEGORY_GENERAL_LANG = "General lang config";
    private static String backtoformselection = "&bClick to go back to form selection";
    private static String pokemontype = "&bType:";
    private static String egggroups = "&bEgg group: %egggroups%";
    private static String eggsteps = "&bEggsteps needed: %eggsteps%";
    private static String evo = "&bClick to see the evolutions:";
    private static String noevo = "&cThere is no evolution for this pokemon!";
    private static String spawninfos = "&bSpawn infos:";
    private static String lorespawninfo1 = "&eClick to see the spawn list";
    private static String lorespawninfo2 = "&cThis pokemon dont spawn naturally!";
    private static String catchrate = "&bCatch Rate:";
    private static String baserate = "&eBase rate: %baserate%%";
    private static String genderless = "&aGenderless";
    private static String malepercent = "&aMale chance: &e%malepercent%";
    private static String femalepercent = "&aFemale chance: &e%femalepercent%";
    private static String ability = "&bAbility:";
    private static String normalability = "&e%ability%";
    private static String hiddenability = "&6%hiddenability% (Hidden)";
    private static String breed = "&bBreed:";
    private static String drops = "&bDrops:";
    private static String movesbylevel = "&bMoves by level:";
    private static String typeeffectiveness = "&bType effectiveness:";
    private static String basestats = "&bBase Stats:";
    private static String evyield = "&bEV Yield:";
    private static String tutormoves = "&bTutor Moves:";
    private static String tmhmmoves = "&bTM/HM Moves:";
    private static String trmoves = "&bTR Moves:";
    private static String eggmoves = "&bEgg Moves:";

    public static final String CATEGORY_SPAWN_LANG = "Spawn lang config";
    private static String back = "&cBack:";
    private static String typeoflocation = "&eType of spawn location: &6%spawnlocation%";
    private static String minlevel = "&eMinimum level: &6%minlevel%";
    private static String maxlevel = "&eMaximum level: &6%maxlevel%";
    private static String helditems = "&eHeld items: &6%helditems%";
    private static String biomes = "&eBiomes: %biomes%";
    private static String nearbyblocks = "&eNearby blocks: %nearbyblocks%";
    private static String rarity = "&eRarity: %rarity%";
    private static String weathers = "&eWeather: %weathers%";
    private static String times = "&eTimes: %times%";


    public static final String CATEGORY_EVO_LANG = "Evolution lang config";
    private static String back2 = "&cBack:";
    private static String levellingup = "&aLevelling up ";
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
    private static String movecondition = "&bKnowing move: %move%";
    private static String movetypecondition = "&bWith a move of type: %movetype%";
    private static String withpokemoncondition = "&bWith these Pokémon in party: %pokemonlist%";
    private static String withtypecondition = "&bWith Pokémon of these types in party: %typelist%";
    private static String withformcondition = "&bWith Pokémon of these forms in party: %formlist%";
    private static String statratiocondition = "&bWith a stat ratio of %ratio% between %stat1% and %stat2%";
    private static String timecondition = "&bDuring: %time%";
    private static String weathercondition = "&bWith weather: %weather%";
    private static String scrollcondition = "&bWith Scroll: %scroll% at range %range%";
    private static String battlecriticalcondition = "&bWith critical: %crit%";
    private static String recoildamagecondition = "&bWith Recoil damage: %recoil%";
    private static String naturecondition = "&bWith nature: %natures%";

    private static String absenceofhealcondition = "&bWith Health absence: (WIP)";
    private static String statuspersistcondition = "&bWith Status: (WIP)";
    private static String withinstructurecondition = "&bWithin Structure: (WIP)";



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
        eggsteps = cfg.getString("eggsteps", CATEGORY_GENERAL_LANG, eggsteps, "&bEggsteps needed: %eggsteps%");
        evo = cfg.getString("evo", CATEGORY_GENERAL_LANG, evo, "&bClick to see the evolutions:");
        noevo = cfg.getString("noevo", CATEGORY_GENERAL_LANG, noevo, "&cThere is no evolution for this pokemon!");
        spawninfos = cfg.getString("spawninfos", CATEGORY_GENERAL_LANG, spawninfos,  "&bSpawn infos:");
        lorespawninfo1 = cfg.getString("lorespawninfo1", CATEGORY_GENERAL_LANG, lorespawninfo1, "&eClick to see the spawn list");
        lorespawninfo2 = cfg.getString("lorespawninfo2", CATEGORY_GENERAL_LANG, lorespawninfo2, "&cThis pokemon dont spawn naturally!");
        catchrate = cfg.getString("catchrate", CATEGORY_GENERAL_LANG, catchrate, "&bCatch Rate:");
        baserate = cfg.getString("baserate", CATEGORY_GENERAL_LANG, baserate, "&eBase rate: %baserate%");
        genderless = cfg.getString("genderless", CATEGORY_GENERAL_LANG, genderless, "&aGenderless");
        malepercent = cfg.getString("malepercent", CATEGORY_GENERAL_LANG, malepercent, "&aMale chance: &e%malepercent%");
        femalepercent = cfg.getString("femalepercent", CATEGORY_GENERAL_LANG, femalepercent, "&aFemale chance: &e%femalepercent%");
        ability = cfg.getString("ability", CATEGORY_GENERAL_LANG, ability, "&bAbility:");
        normalability = cfg.getString("normalability", CATEGORY_GENERAL_LANG, normalability, "&e%ability%");
        hiddenability = cfg.getString("hiddenability", CATEGORY_GENERAL_LANG, hiddenability, "&6%hiddenability% (Hidden)");
        breed = cfg.getString("breed", CATEGORY_GENERAL_LANG, breed, "&bBreed:");
        drops = cfg.getString("drops", CATEGORY_GENERAL_LANG, drops, "&bDrops:");
        movesbylevel = cfg.getString("movesbylevel", CATEGORY_GENERAL_LANG, movesbylevel, "&bMoves by level:");
        typeeffectiveness = cfg.getString("typeeffectiveness", CATEGORY_GENERAL_LANG, typeeffectiveness, "&bType effectiveness:");
        basestats = cfg.getString("basestats", CATEGORY_GENERAL_LANG, basestats, "&bType effectiveness:");
        evyield = cfg.getString("evyield", CATEGORY_GENERAL_LANG, evyield, "&bEV Yield:");
        tutormoves = cfg.getString("tutormoves", CATEGORY_GENERAL_LANG, tutormoves, "&bTutor Moves:");
        tmhmmoves = cfg.getString("tmhmmoves", CATEGORY_GENERAL_LANG, tmhmmoves, "&bTM/HM Moves:");
        trmoves = cfg.getString("trmoves", CATEGORY_GENERAL_LANG, trmoves, "&bTR Moves:");
        eggmoves = cfg.getString("eggmoves", CATEGORY_GENERAL_LANG, eggmoves, "&bEgg Moves:");

    }

    private static void initSpawnConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_SPAWN_LANG, "Spawn Language configuration");
        back = cfg.getString("back", CATEGORY_SPAWN_LANG, back, "&cBack:");
        typeoflocation = cfg.getString("typeoflocation", CATEGORY_SPAWN_LANG, typeoflocation, "&eType of spawn location: &6%spawnlocation%");
        minlevel = cfg.getString("minlevel", CATEGORY_SPAWN_LANG, minlevel, "&eMinimum level: &6%minlevel%");
        maxlevel = cfg.getString("maxlevel", CATEGORY_SPAWN_LANG, maxlevel, "&eMaximum level: &6%maxlevel%");
        helditems = cfg.getString("helditems", CATEGORY_SPAWN_LANG, helditems, "&eHeld items: &6%helditems%");
        biomes = cfg.getString("biomes", CATEGORY_SPAWN_LANG, biomes, "&eBiomes: %biomes%");
        nearbyblocks = cfg.getString("nearbyblocks", CATEGORY_SPAWN_LANG, nearbyblocks, "&eNearby blocks: %nearbyblocks%");
        rarity = cfg.getString("rarity", CATEGORY_SPAWN_LANG, rarity, "&eRarity: %rarity%");
        weathers = cfg.getString("weathers", CATEGORY_SPAWN_LANG, weathers, "&eWeathers: %wheathers%");
        times = cfg.getString("times", CATEGORY_SPAWN_LANG, times, "&eTime: %times%");
    }

    private static void initEvoConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_EVO_LANG, "Evo Language configuration");
        back2 = cfg.getString("back2", CATEGORY_EVO_LANG, back2, "&cBack:");
        levellingup = cfg.getString("levellingup", CATEGORY_EVO_LANG, levellingup, "&e%pokemoname%: &aLevelling up");
        levelnumber = cfg.getString("levelnumber", CATEGORY_EVO_LANG, levelnumber, "&ato level %level%");
        exposedtoitem = cfg.getString("exposedtoitem", CATEGORY_EVO_LANG, exposedtoitem, "&bWhen exposed to %item%");
        tradedwith = cfg.getString("tradedwith", CATEGORY_EVO_LANG, tradedwith, "&dTrading with %pokemon%");
        traded = cfg.getString("traded", CATEGORY_EVO_LANG, traded, "&dTrading");
        chancecondition = cfg.getString("chancecondition", CATEGORY_EVO_LANG, chancecondition, "&3%chance% percent chance");
        evorockcondition = cfg.getString("evorockcondition", CATEGORY_EVO_LANG, evorockcondition, "&bWithin %range% blocks of a %rockname%");
        friendshipcondition = cfg.getString("friendshipcondition", CATEGORY_EVO_LANG, friendshipcondition, "&bFriendship: %friendship%");
        gendercondition = cfg.getString("gendercondition", CATEGORY_EVO_LANG, gendercondition, "&bGender: %gender%");
        helditemcondition = cfg.getString("helditemcondition", CATEGORY_EVO_LANG, helditemcondition, "&bHeld item: %helditem%");
        abovealtitudecondition = cfg.getString("abovealtitudecondition", CATEGORY_EVO_LANG, abovealtitudecondition,"&bAbove altitude: %altitude%");
        levelcondition = cfg.getString("levelcondition", CATEGORY_EVO_LANG, levelcondition, "&bStarting at level: %level%");
        movecondition = cfg.getString("movecondition", CATEGORY_EVO_LANG, movecondition, "&bKnowing move: %move%");
        movetypecondition = cfg.getString("movetypecondition", CATEGORY_EVO_LANG, movetypecondition, "&bWith a move of type: %movetype%");
        withpokemoncondition = cfg.getString("withpokemoncondition", CATEGORY_EVO_LANG, withpokemoncondition, "&bWith these Pokémon in party: %pokemonlist%");
        withtypecondition = cfg.getString("withtypecondition", CATEGORY_EVO_LANG, withtypecondition,"&bWith Pokémon of these types in party: %typelist%" );
        withformcondition = cfg.getString("withformcondition", CATEGORY_EVO_LANG, withformcondition,"&bWith Pokémon of these forms in party: %formlist%" );
        statratiocondition = cfg.getString("statratiocondition", CATEGORY_EVO_LANG, statratiocondition, "&bWith a stat ratio of %ratio% between %stat1% and %stat2%");
        timecondition = cfg.getString("timecondition", CATEGORY_EVO_LANG, timecondition, "&bDuring: %time%");
        weathercondition = cfg.getString("weathercondition", CATEGORY_EVO_LANG, weathercondition, "&bWith weather: %weather%");
        scrollcondition = cfg.getString("scrollcondition", CATEGORY_EVO_LANG, scrollcondition, "&bWith Scroll: %scroll% at range %range%");
        battlecriticalcondition = cfg.getString("battlecriticalcondition", CATEGORY_EVO_LANG, battlecriticalcondition, "&bWith critical: %crit%");
        recoildamagecondition = cfg.getString("recoildamagecondition", CATEGORY_EVO_LANG, recoildamagecondition, "&bWith Recoil damage: %recoil%");
        naturecondition = cfg.getString("naturecondition", CATEGORY_EVO_LANG, naturecondition, "&bWith nature: %natures%");

        absenceofhealcondition = cfg.getString("absenceofhealcondition", CATEGORY_EVO_LANG, absenceofhealcondition, "&bWith Health absence: (WIP)");
        statuspersistcondition = cfg.getString("statuspersistcondition", CATEGORY_EVO_LANG, statuspersistcondition,"&bWith Status: (WIP)");
        withinstructurecondition = cfg.getString("withinstructurecondition", CATEGORY_EVO_LANG, withinstructurecondition, "&bWithin Structure: (WIP)");

    }


}
