package com.xariyx;

import com.xariyx.action.ActionSelect;
import com.xariyx.action.ActionTarget;
import com.xariyx.action.ActionType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        InteractionHook hook = event.getHook();

        String actionType = ActionType.ERROR;
        String command = event.getName();

        switch (command){
            case ActionType.DEAFEN: {
                actionType = ActionType.DEAFEN;
                break;
            }

            case ActionType.UNDEAFEN: {
                actionType = ActionType.UNDEAFEN;
                break;
            }

            case ActionType.MUTE: {
                actionType = ActionType.MUTE;
                break;
            }

            case ActionType.UNMUTE: {
                actionType = ActionType.UNMUTE;
                break;
            }


            case ActionType.DUMP: {
                actionType = ActionType.DUMP;
                break;
            }

            case ActionType.MOVE: {
                actionType = ActionType.MOVE;
                break;
            }

            case "uptime": {
                hook.editOriginal("Uptime : " + formatMillis(System.currentTimeMillis() - Main.TIME)).queue();
                event.deferReply().queue();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                hook.deleteOriginal().queue();
                return;
            }
        }

        SelectOption[] selectOptions = new SelectOption[] {
                SelectOption.of(
                        "All",
                        ActionSelect.ACTION_SELECT
                                + Main.SELECTION_MENU_REGEX
                                + actionType
                                + Main.SELECTION_MENU_REGEX
                                + ActionTarget.ALL),

                SelectOption.of("Channel",
                        ActionSelect.ACTION_SELECT
                                + Main.SELECTION_MENU_REGEX
                                + actionType
                                + Main.SELECTION_MENU_REGEX
                                + ActionTarget.CHANNEL)
        };
        SelectionMenu menu = SelectionMenu
                .create("Target selection")
                .addOptions(selectOptions)
                .setPlaceholder("Select Target")
                .build();

        hook.editOriginal("Choose target").setActionRow(menu).queue();
        event.deferReply().queue();

    }

    public static String formatMillis(long millis) {
        long remainder = millis;
        long days = remainder/86400000;
        remainder -= days * 86400000;
        long hours = remainder/3600000;
        remainder -= hours * 3600000;
        long minutes = remainder/60000;
        remainder -= minutes*60000;
        long seconds = remainder/1000;

        StringBuilder result = new StringBuilder();
        boolean b = false;
        if (days != 0) {
            result.append(days).append("d ");
            b = true;
        }
        if (hours != 0 || b) {
            result.append(hours).append("h ");
            b = true;
        }
        if (minutes != 0 || b) {
            result.append(minutes).append("m ");
        }
        result.append(seconds).append("s");

        return result.toString();
    }
}
