package com.xariyx;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;


public class Main {

    public final static String TOKEN = "ODY4ODcyNDk0MDU5MjM3NDE2.YP1-kQ.ucWoKHUdevxfSfiY8GhNOcMZF30";
    public final static long TIME = System.currentTimeMillis();
    public final static String SELECTION_MENU_REGEX = "-";

    public static void main(String[] args) throws LoginException {

        JDA jda = JDABuilder
                .createLight(TOKEN, EnumSet.noneOf(GatewayIntent.class))
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(
                        new CommandListener(),
                        new ActionSelectListener(),
                        new ChannelSelectListener()
                ).build();


//        CommandListUpdateAction commands = jda.updateCommands();
//
//        commands.addCommands(
//                new CommandData("dump", "Disconnect users"),
//                new CommandData("deafen", "Deafen users"),
//                new CommandData("undeafen", "Undeafens users"),
//                new CommandData("mute", "Mutes users"),
//                new CommandData("unmute", "Unmutes users"),
//                new CommandData("uptime", "Shows bot uptime")
//        ).queue();
//
//        commands.queue();

    }

    public static Member checkPermission(SlashCommandEvent event, Permission permission) {
        Member member = event.getMember();
        if (member == null) {
            return null;
        }

        if (!member.hasPermission(permission)) {
            event.getHook().sendMessage("You don't have permission to do that!").setEphemeral(true).queue();
            return null;
        }
        return member;
    }


}
