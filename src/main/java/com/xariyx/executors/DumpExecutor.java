package com.xariyx.executors;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;

public class DumpExecutor extends Executor {


    public DumpExecutor(Guild guild) {
        super(guild);
    }

    @Override
    public void executeAll() {
        List<VoiceChannel> channels = guild.getVoiceChannels();
        for (VoiceChannel channel : channels) {
            for (Member member : channel.getMembers()) {
                guild.kickVoiceMember(member).queue();
            }
        }
    }

    @Override
    public void executeChannel(VoiceChannel channel) {
        for (Member member : channel.getMembers()) {
            channel.getGuild().kickVoiceMember(member).queue();
        }
    }
}
