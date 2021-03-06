package fr.pokepixel.pokewiki.info;

import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumType;
import net.minecraft.util.text.TextFormatting;

public class ColorUtils {

    public static TextFormatting getTypeColor(EnumType type) {
        switch (type) {
            case Fire:
                return TextFormatting.RED;
            case Water:
            case Flying:
                return TextFormatting.BLUE;
            case Electric:
                return TextFormatting.YELLOW;
            case Grass:
            case Bug:
                return TextFormatting.GREEN;
            case Ice:
            case Mystery:
                return TextFormatting.AQUA;
            case Fighting:
                return TextFormatting.DARK_RED;
            case Poison:
                return TextFormatting.DARK_PURPLE;
            case Ground:
                return TextFormatting.GOLD;
            case Psychic:
            case Fairy:
                return TextFormatting.LIGHT_PURPLE;
            case Rock:
                return TextFormatting.DARK_GRAY;
            case Ghost:
            case Steel:
                return TextFormatting.GRAY;
            case Dragon:
                return TextFormatting.DARK_BLUE;
            case Dark:
                return TextFormatting.BLACK;
            default:
                return TextFormatting.WHITE;
        }
    }

    public static TextFormatting getStatColor(StatsType stat) {
        switch (stat) {
            case HP:
                return TextFormatting.GREEN;
            case Attack:
                return TextFormatting.RED;
            case Defence:
                return TextFormatting.GOLD;
            case SpecialAttack:
                return TextFormatting.LIGHT_PURPLE;
            case SpecialDefence:
                return TextFormatting.YELLOW;
            case Speed:
                return TextFormatting.BLUE;
            default:
                return TextFormatting.WHITE;
        }
    }

}
