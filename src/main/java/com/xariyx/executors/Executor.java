package com.xariyx.executors;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

import javax.annotation.Nonnull;

public abstract class Executor {

    Guild guild;

    protected Executor(@Nonnull Guild guild) {
        this.guild = guild;
    }


    abstract public void executeAll();

    abstract public void executeChannel(VoiceChannel channel);


}
