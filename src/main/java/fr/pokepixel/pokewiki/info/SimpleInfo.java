package fr.pokepixel.pokewiki.info;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;
import com.pixelmonmod.pixelmon.entities.pixelmon.abilities.AbilityBase;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.Evolution;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.conditions.*;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.types.InteractEvolution;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.types.LevelingEvolution;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.types.TradeEvolution;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.enums.forms.IEnumForm;
import fr.pokepixel.pokewiki.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.ConfigCategory;

import java.util.*;

import static fr.pokepixel.pokewiki.config.ChatColor.translateAlternateColorCodes;
import static fr.pokepixel.pokewiki.info.ColorUtils.getTypeColor;

public class SimpleInfo {

    private static final List<String> NONE = Collections.singletonList(TextFormatting.DARK_GRAY + "None");

    public static String getType(Pokemon pokemon){
        if (pokemon.getBaseStats().getTypeList().size()==2){
            return getTypeColor(pokemon.getBaseStats().getType1()) + pokemon.getBaseStats().getType1().getLocalizedName() + " §f/ " + getTypeColor(pokemon.getBaseStats().getType2()) + pokemon.getBaseStats().getType2().getLocalizedName();
        }else{
            return getTypeColor(pokemon.getBaseStats().getType1()) + pokemon.getBaseStats().getType1().getLocalizedName();
        }
    }

    public static String getEggGroup(BaseStats baseStats){
        return TextFormatting.AQUA + "Egg group" + (baseStats.eggGroups.length != 1 ? "s" : "") + ": " + TextFormatting.DARK_AQUA + Arrays.toString(baseStats.eggGroups);
    }

    public static List<String> getDrops(EnumSpecies species) {
        List<String> description = new ArrayList<>();
        for (ReflectionHelper.DropType type : ReflectionHelper.DropType.ORDERED_VALUES) {
            Set<String> drops = type.getDrops(species);
            if (!drops.isEmpty()) {
                description.add("");
                description.add(type.displayName);
                description.addAll(drops);
            }
        }

        return description.isEmpty() ? NONE : description;
    }

    public static List<String> getBreeding(BaseStats stats) {
        EnumMap<EnumType, HashMap<Block, Integer>> typeBlockList = ReflectionHelper.getBreedingTypeList().orElse(null);
        if (typeBlockList == null) {
            return NONE;
        }

        List<String> description = new ArrayList<>();
        for (EnumType type : stats.getTypeList()) {
            HashMap<Block, Integer> blocks = typeBlockList.get(type);
            if (blocks == null || blocks.isEmpty()) {
                continue;
            }

            description.add("");

            String color = getTypeColor(type).toString();
            description.add(TextFormatting.BOLD.toString() + TextFormatting.UNDERLINE + color + type.getLocalizedName());

            for (Map.Entry<Block, Integer> entry : blocks.entrySet()) {
                description.add(color + entry.getKey().getLocalizedName() + " " + entry.getValue());
            }
        }
        return description;
    }
    
    public static List<String> getInfoEvo(Pokemon pokemon, ConfigCategory lang){
        List<String> evoinfo = Lists.newArrayList();
        //String formname1 = pokemon.getFormEnum().getFormSuffix().isEmpty() ? "-normal" : pokemon.getFormEnum().getFormSuffix();
        if (pokemon.getBaseStats().getEvolutions().size()>0){
            for (Evolution evolution : pokemon.getBaseStats().getEvolutions()) {
                if (evolution == null) {
                    continue; // Possible, though very unlikely.
                }
                String formname = "";
                Pokemon pokemonevo = Pixelmon.pokemonFactory.create(evolution.to);
                IEnumForm form = pokemonevo.getFormEnum();
                if (!form.getFormSuffix().equalsIgnoreCase("-normal") && !form.getFormSuffix().isEmpty()){
                    formname = " " + form.getLocalizedName();
                }
                StringBuilder baseMsg = new StringBuilder("§e"+pokemonevo.getLocalizedName()+formname+": "+translateAlternateColorCodes('&',lang.get("levellingup").getString()));
                //StringBuilder baseMsg = new StringBuilder(TextFormatting.YELLOW + "  " + pokemonevo.getLocalizedName() + formname+ ": " + TextFormatting.GREEN + "Levelling up ");
                if (evolution instanceof LevelingEvolution) {
                    LevelingEvolution levelingEvolution = (LevelingEvolution) evolution;
                    if (levelingEvolution.level != null && levelingEvolution.level > 1) {
                        baseMsg.append(translateAlternateColorCodes('&',lang.get("levelnumber").getString().replaceFirst("%level%",String.valueOf(levelingEvolution.level))));
                        //baseMsg.append("to level ").append(levelingEvolution.level);
                    }
                } else if (evolution instanceof InteractEvolution) {
                    if (((InteractEvolution) evolution).item != null){
                        //"§e"+pokemonevo.getLocalizedName()+formname+": "+
                        baseMsg = new StringBuilder("§e"+pokemonevo.getLocalizedName()+formname+": " +translateAlternateColorCodes('&',lang.get("exposedtoitem").getString().replaceFirst("%item%",((InteractEvolution) evolution).item.getItemStack().getDisplayName())));
                        //baseMsg = new StringBuilder(TextFormatting.YELLOW + "  " + evolution.to.name + formname+ ": " + TextFormatting.AQUA + "When exposed to " + ((InteractEvolution) evolution).item.getItemStack().getDisplayName());
                    }
                } else if (evolution instanceof TradeEvolution) {
                    TradeEvolution tradeEvo = (TradeEvolution) evolution;
                    if (tradeEvo.with != null) {
                        baseMsg = new StringBuilder("§e"+pokemonevo.getLocalizedName()+formname+": " +translateAlternateColorCodes('&',lang.get("tradedwith").getString().replaceFirst("%pokemon%",tradeEvo.with.name)));
                        //baseMsg = new StringBuilder(TextFormatting.YELLOW + "  " + evolution.to.name + formname + ": " + TextFormatting.LIGHT_PURPLE + "Trading with " + tradeEvo.with.name);
                    } else {
                        baseMsg = new StringBuilder("§e"+pokemonevo.getLocalizedName()+formname+": " +translateAlternateColorCodes('&',lang.get("traded").getString()));
                        //baseMsg = new StringBuilder(TextFormatting.YELLOW + "  " + evolution.to.name + formname+ ": " + TextFormatting.LIGHT_PURPLE + "Trading");
                    }
                }

                evoinfo.add(baseMsg.toString());

                if (evolution.conditions != null && !evolution.conditions.isEmpty()) {
                    TextFormatting headingColour = TextFormatting.GOLD;
                    TextFormatting valueColour = TextFormatting.DARK_AQUA;
                    evoinfo.add(TextFormatting.GOLD + "    " + TextFormatting.UNDERLINE + "Conditions:");
                    for (EvoCondition condition : evolution.conditions) {
                        if (condition instanceof BiomeCondition) {
                            BiomeCondition biomeCondition = (BiomeCondition) condition;
                            StringBuilder biomes = new StringBuilder(headingColour + "Biomes: " + valueColour);
                            for (int i = 0; i < biomeCondition.biomes.size(); i++) {
                                Biome b = Biome.REGISTRY.getObject(new ResourceLocation(biomeCondition.biomes.get(i)));
                                String biomeName = ReflectionHelper.getBiomeName(b).orElse("???");
                                //String biomeName = b == null ? biomeCondition.biomes.get(i) : b.biomeName;
                                if (i == 0) {
                                    biomes.append(biomeName);
                                } else {
                                    biomes.append(headingColour).append(", ").append(valueColour).append(biomeName);
                                }
                            }
                            evoinfo.add("      " + biomes);
                        } else if (condition instanceof ChanceCondition) {
                            ChanceCondition chanceCondition = (ChanceCondition) condition;
                            String conditiontxt = translateAlternateColorCodes('&', lang.get("chancecondition").getString().replaceFirst("%chance%", String.valueOf(chanceCondition.chance*100)));
                            evoinfo.add("      " + valueColour + conditiontxt);
                        } else if (condition instanceof EvoRockCondition) {
                            EvoRockCondition evoRockCond = (EvoRockCondition) condition;
                            String evorockcondition = translateAlternateColorCodes('&', lang.get("evorockcondition").getString().replaceFirst("%range%", String.valueOf(Math.sqrt(evoRockCond.maxRangeSquared))).replaceFirst("%rockname%",evoRockCond.evolutionRock.name()));
                            evoinfo.add("      " + evorockcondition);
                        } else if (condition instanceof FriendshipCondition) {
                            evoinfo.add("      " + translateAlternateColorCodes('&', lang.get("friendshipcondition").getString().replaceFirst("%friendship%", String.valueOf(((FriendshipCondition) condition).friendship))));
                        } else if (condition instanceof GenderCondition) {
                            GenderCondition genderCondition = (GenderCondition) condition;
                            String conditiontxt = translateAlternateColorCodes('&', lang.get("gendercondition").getString().replaceFirst("%gender%", genderCondition.genders.get(0).name()));
                            StringBuilder genders = new StringBuilder(conditiontxt);
                            for (int i = 1; i < genderCondition.genders.size(); i++) {
                                genders.append(headingColour).append(", ").append(valueColour).append(genderCondition.genders.get(i).name());
                            }
                            evoinfo.add("      " + genders);
                        } else if (condition instanceof HeldItemCondition) {
                            HeldItemCondition heldItemCondition = (HeldItemCondition) condition;
                            ItemStack stack = heldItemCondition.item.getItemStack();
                            String conditiontxt = translateAlternateColorCodes('&', lang.get("helditemcondition").getString().replaceFirst("%helditem%", (stack == null ? heldItemCondition.item.itemID : stack.getDisplayName())));
                            evoinfo.add("      " + conditiontxt);
                        } else if (condition instanceof HighAltitudeCondition) {
                            HighAltitudeCondition altitudeCondition = (HighAltitudeCondition) condition;
                            String conditiontxt = translateAlternateColorCodes('&', lang.get("abovealtitudecondition").getString().replaceFirst("%altitude%", String.valueOf((int) altitudeCondition.minAltitude)));
                            evoinfo.add("      " + conditiontxt);
                        } else if (condition instanceof LevelCondition) {
                            String conditiontxt = translateAlternateColorCodes('&', lang.get("levelcondition").getString().replaceFirst("%level%", String.valueOf((((LevelCondition) condition).level))));
                            evoinfo.add("    " + conditiontxt);
                        } else if (condition instanceof MoveCondition) {
                            String conditiontxt = translateAlternateColorCodes('&', lang.get("levelcondition").getString().replaceFirst("%level%", String.valueOf((((LevelCondition) condition).level))));

                            evoinfo.add("      " + headingColour + "Knowing move: " + valueColour + AttackBase.getAttackBase(((MoveCondition) condition).attackIndex).get().getTranslatedName().getFormattedText());
                        } else if (condition instanceof MoveTypeCondition) {
                            evoinfo.add("      " + headingColour + "With a move of type: " + valueColour + ((MoveTypeCondition) condition).type.getLocalizedName());
                        } else if (condition instanceof PartyCondition) {
                            ArrayList<EnumSpecies> withPokemon = new ArrayList<>();
                            ArrayList<EnumType> withTypes = new ArrayList<>();
                            ArrayList<String> withForms = new ArrayList<>();
                            PartyCondition partyCond = (PartyCondition) condition;
                            if (partyCond.withPokemon != null) {
                                withPokemon = partyCond.withPokemon;
                            }
                            if (partyCond.withTypes != null) {
                                withTypes = partyCond.withTypes;
                            }
                            if (partyCond.withForms != null) {
                                withForms = partyCond.withForms;
                            }
                            if (!withPokemon.isEmpty()) {
                                StringBuilder pokemonWith = new StringBuilder(headingColour + "      With these Pokémon in party: " + valueColour + withPokemon.get(0).name);
                                for (int i = 1; i < withPokemon.size(); i++) {
                                    pokemonWith.append(headingColour).append(", ").append(valueColour).append(withPokemon.get(i).name);
                                }
                                evoinfo.add( pokemonWith.toString());
                            }
                            if (!withTypes.isEmpty()) {
                                StringBuilder typesWith = new StringBuilder(headingColour + "      With Pokémon of these types in party: " + valueColour + withTypes.get(0).getLocalizedName());
                                for (int i = 1; i < withTypes.size(); i++) {
                                    typesWith.append(headingColour).append(", ").append(valueColour).append(withTypes.get(i).getLocalizedName());
                                }
                                evoinfo.add(typesWith.toString());
                            }
                            if (!withForms.isEmpty()) {
                                StringBuilder formsWith = new StringBuilder(headingColour + "      With Pokémon of these forms in party: " + valueColour + withForms.get(0));
                                for (int i = 1; i < withForms.size(); i++) {
                                    formsWith.append(headingColour).append(", ").append(valueColour).append(withForms.get(i));
                                }
                                evoinfo.add(formsWith.toString());
                            }
                        } else if (condition instanceof StatRatioCondition) {
                            StatRatioCondition statCond = (StatRatioCondition) condition;
                            evoinfo.add(headingColour + "      With a stat ratio of " + valueColour + statCond.ratio + headingColour + " between " + valueColour + statCond.stat1.getLocalizedName() + headingColour + " and " + valueColour + statCond.stat2.getLocalizedName());
                        } else if (condition instanceof TimeCondition) {
                            evoinfo.add(headingColour + "      During: " + valueColour + (((TimeCondition) condition).time.name()));
                        } else if (condition instanceof WeatherCondition) {
                            evoinfo.add(headingColour + "      With weather: " + valueColour + "Rain");
                        }else if (condition instanceof EvoScrollCondition){
                            EvoScrollCondition evoScrollCondition = (EvoScrollCondition) condition;
                            evoinfo.add(headingColour + "      With Scroll: " + evoScrollCondition.evolutionScroll.getName() + " at range " + evoScrollCondition.maxRangeSquared);
                        }else if (condition instanceof BattleCriticalCondition){
                            BattleCriticalCondition battleCriticalCondition = (BattleCriticalCondition) condition;
                            evoinfo.add(headingColour + "      With critical: " + battleCriticalCondition.critical);
                        }else if (condition instanceof AbsenceOfHealthCondition){
                            AbsenceOfHealthCondition absenceOfHealthCondition = (AbsenceOfHealthCondition) condition;
                            evoinfo.add(headingColour + "      With Health absence: (WIP)");
                        }else if (condition instanceof StatusPersistCondition){
                            StatusPersistCondition statusPersistCondition = (StatusPersistCondition) condition;
                            evoinfo.add(headingColour + "      With Status: (WIP)");
                        }else if (condition instanceof WithinStructureCondition){
                            WithinStructureCondition withinStructureCondition = (WithinStructureCondition) condition;
                            evoinfo.add(headingColour + "      Within Structure: (WIP)");
                        }
                    }
                }
            }
        }
        return evoinfo;
    }

    public static List<String> getAbilityForPoke(Pokemon pokemon, ConfigCategory lang){
        List<String> abilityList = Lists.newArrayList();
        for (AbilityBase allAbility : pokemon.getBaseStats().getAllAbilities()) {
            pokemon.setAbility(allAbility);
            if (pokemon.getAbilitySlot() == 2){
                abilityList.add(translateAlternateColorCodes('&',lang.get("hiddenability").getString().replaceFirst("%hiddenability%",allAbility.getLocalizedName())));
            }else{
                abilityList.add(translateAlternateColorCodes('&',lang.get("normalability").getString().replaceFirst("%ability%",allAbility.getLocalizedName())));
            }
        }
        return abilityList;
    }


}
