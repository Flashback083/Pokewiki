package fr.pokepixel.pokewiki.cmds;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import fr.pokepixel.pokewiki.Pokewiki;
import fr.pokepixel.pokewiki.config.Config;
import fr.pokepixel.pokewiki.config.Lang;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Configuration;

import java.util.List;

import static fr.pokepixel.pokewiki.Pokewiki.enPoke;
import static fr.pokepixel.pokewiki.Pokewiki.trPoke;
import static fr.pokepixel.pokewiki.config.GsonUtils.getAllCustom;
import static fr.pokepixel.pokewiki.gui.ChoiceForm.openChoiceFormGUI;
import static fr.pokepixel.pokewiki.gui.DisplayInfo.displayInfoGUI;
import static net.minecraftforge.server.permission.PermissionAPI.hasPermission;
import static fr.pokepixel.pokewiki.Pokewiki.customSpawnPokemonInfoListInfo;

public class UserCmd extends CommandBase {

    @Override
    public String getName() {
        return "pokewiki";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("pwiki");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return  "§a/pwiki <pokemon>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 1){
            if (args[0].equalsIgnoreCase("reload")){
                if (sender instanceof EntityPlayerMP){
                    EntityPlayerMP player = (EntityPlayerMP) sender;
                    if (!hasPermission(player,"pokewiki.reload")){
                        sender.sendMessage(new TextComponentString("You don't have the permission to do this!"));
                        return;
                    }
                }
                Configuration cfglang = Pokewiki.lang;
                Lang.readConfig();
                if (cfglang.hasChanged()) {
                    cfglang.save();
                }
                cfglang.load();
                Configuration cfggen = Pokewiki.config;
                Config.readConfig();
                if (cfggen.hasChanged()) {
                    cfggen.save();
                }
                cfggen.load();
                customSpawnPokemonInfoListInfo = getAllCustom();
                sender.sendMessage(new TextComponentString("§aConfig and Lang reloaded!"));
            }else{
                EntityPlayerMP player = (EntityPlayerMP) sender;
                if (EnumSpecies.getFromNameAnyCase(args[0]) != null){
                    if (EnumSpecies.getFromNameAnyCase(args[0]).getPossibleForms(false).size()>1){
                        openChoiceFormGUI(player,EnumSpecies.getFromNameAnyCase(args[0]));
                    }else{
                        displayInfoGUI(player, Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(args[0])));
                    }
                }else{
                    sender.sendMessage(new TextComponentString("§cThis pokemon do not exist!"));
                }
            }
        }else{
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        }
    }


    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        List<String> tab = Lists.newArrayList();
        tab.addAll(enPoke);
        tab.addAll(trPoke);
        return getListOfStringsMatchingLastWord(args,tab);
    }

}
