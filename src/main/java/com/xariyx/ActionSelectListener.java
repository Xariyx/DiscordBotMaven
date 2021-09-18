package com.xariyx;

import com.xariyx.action.ActionSelect;
import com.xariyx.action.ActionTarget;
import com.xariyx.action.ActionType;
import com.xariyx.executors.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ActionSelectListener extends ListenerAdapter {

    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        String[] args = event.getValues().get(0).split(Main.SELECTION_MENU_REGEX);
        InteractionHook hook = event.getHook().setEphemeral(true);

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


        if (!actionSelect.equals(ActionSelect.ACTION_SELECT)) {
            return;
        }

        Guild guild = event.getGuild();

        if (guild == null) {
            return;
        }

        switch (actionType) {

            case ActionType.DEAFEN: {
                DeafenExecutor executor = new DeafenExecutor(guild);
                if (actionTarget.equals(ActionTarget.ALL)) {
                    executor.executeAll();
                    event.getHook().deleteOriginal().queue();
                } else {
                    sendChannelSelectionRequest(actionType, guild, hook);
                }
                break;
            }

            case ActionType.UNDEAFEN: {
                UndeafenExecutor executor = new UndeafenExecutor(guild);
                if (actionTarget.equals(ActionTarget.ALL)) {
                    executor.executeAll();
                    event.getHook().deleteOriginal().queue();
                } else {
                    sendChannelSelectionRequest(actionType, guild, hook);
                }
                break;
            }

            case ActionType.MUTE: {
                MuteExecutor executor = new MuteExecutor(guild);
                if (actionTarget.equals(ActionTarget.ALL)) {
                    executor.executeAll();
                    event.getHook().deleteOriginal().queue();
                } else {
                    sendChannelSelectionRequest(actionType, guild, hook);
                }
                break;
            }

            case ActionType.UNMUTE: {
                UnmuteExecutor executor = new UnmuteExecutor(guild);
                if (actionTarget.equals(ActionTarget.ALL)) {
                    executor.executeAll();
                    event.getHook().deleteOriginal().queue();
                } else {
                    sendChannelSelectionRequest(actionType, guild, hook);
                }
                break;
            }


            case ActionType.DUMP: {
                DumpExecutor executor = new DumpExecutor(guild);
                if (actionTarget.equals(ActionTarget.ALL)) {
                    executor.executeAll();
                    event.getHook().deleteOriginal().queue();
                } else {
                    sendChannelSelectionRequest(actionType, guild, hook);
                }
                break;
            }

        }

        event.deferEdit().queue();

    }



    private void sendChannelSelectionRequest(String actionType, Guild guild, InteractionHook hook) {

        List<VoiceChannel> channelList = guild.getVoiceChannels();

        ArrayList<SelectOption> options = new ArrayList<>(channelList.size());

        for (VoiceChannel voiceChannel : channelList) {
            options.add(SelectOption.of(
                    voiceChannel.getName(),
                    ActionSelect.CHANNEL_SELECT
                            + Main.SELECTION_MENU_REGEX
                            + actionType
                            + Main.SELECTION_MENU_REGEX
                            + voiceChannel.getId()
            ));
        }


        SelectionMenu menu = SelectionMenu
                .create(actionType)
                .addOptions(options)
                .setPlaceholder("Select channel")
                .build();

        hook.editOriginal("Channel selection menu").setActionRow(menu).queue();

    }


}
