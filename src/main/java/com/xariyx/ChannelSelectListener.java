package com.xariyx;

import com.xariyx.action.ActionSelect;
import com.xariyx.action.ActionType;
import com.xariyx.executors.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;

public class ChannelSelectListener extends ListenerAdapter {
    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        String[] args = event.getValues().get(0).split(Main.SELECTION_MENU_REGEX);
        InteractionHook hook = event.getHook();

        String actionSelect;
        String actionType;
        String actionTarget;
        try {
            actionSelect = args[0];
            actionType = args[1];
            actionTarget = args[2];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return;
        }


        if (!actionSelect.equals(ActionSelect.CHANNEL_SELECT)) {
            return;
        }

        Guild guild = event.getGuild();

        if (guild == null) {
            return;
        }

        VoiceChannel channel = guild.getVoiceChannelById(actionTarget);
        if(channel== null){
            return;
        }

        switch (actionType) {

            case ActionType.DEAFEN: {
                DeafenExecutor executor = new DeafenExecutor(guild);
                executor.executeChannel(channel);
                event.getHook().deleteOriginal().queue();
                break;
            }

            case ActionType.UNDEAFEN: {
                UndeafenExecutor executor = new UndeafenExecutor(guild);
                executor.executeChannel(channel);
                event.getHook().deleteOriginal().queue();
                break;
            }

            case ActionType.MUTE: {
                MuteExecutor executor = new MuteExecutor(guild);
                executor.executeChannel(channel);
                event.getHook().deleteOriginal().queue();
                break;
            }

            case ActionType.UNMUTE: {
                UnmuteExecutor executor = new UnmuteExecutor(guild);
                executor.executeChannel(channel);
                event.getHook().deleteOriginal().queue();
                break;
            }


            case ActionType.DUMP: {
                DumpExecutor executor = new DumpExecutor(guild);
                executor.executeChannel(channel);
                event.getHook().deleteOriginal().queue();
                break;
            }


        }

        event.deferEdit().queue();

    }

}
