package com.xariyx.executors;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UndeafenExecutor extends Executor {


    public UndeafenExecutor(@NotNull Guild guild) {
        super(guild);
    }

    @Override
    public void executeAll() {

        List<VoiceChannel> channels = guild.getVoiceChannels();
        for (VoiceChannel channel : channels) {
            for (Member member : channel.getMembers()) {
                guild.deafen(member, false).queue();
            }
        }

    }

    @Override
    public void executeChannel(VoiceChannel channel) {
        for (Member member : channel.getMembers()) {
            channel.getGuild().deafen(member,false).queue();
        }
    }
}
