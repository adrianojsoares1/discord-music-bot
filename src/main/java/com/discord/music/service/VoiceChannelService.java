package com.discord.music.service;

import com.discord.music.component.audio.YouTubeAudioProvider;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class VoiceChannelService {
    private final Logger logger;
    private final YouTubeAudioProvider audioProvider;
    private final GatewayDiscordClient discordClient;

    public VoiceChannelService(Logger logger, YouTubeAudioProvider audioProvider, GatewayDiscordClient discordClient) {
        this.logger = logger;
        this.audioProvider = audioProvider;
        this.discordClient = discordClient;
    }

    /**
     * Determines if the bot is currently in the user's channel.
     *
     * @param guild The Guild to scan.
     * @param member The member whose VoiceChannel to scan.
     * @return true if the bot is currently joined to the member's channel, and false otherwise.
     */
    public boolean botInMemberChannel(Guild guild, Member member) {
        VoiceState memberVoiceState = member.getVoiceState().block();
        if (memberVoiceState == null) {
            return false;
        }
        VoiceChannel userChannel = memberVoiceState.getChannel().block();
        if (userChannel == null) {
            return false;
        }
        Snowflake botId = guild.getClient().getSelfId();
        Boolean inChannel = userChannel
                .getVoiceStates().any(state -> state.getUserId().equals(botId))
                .block();
        return inChannel != null && inChannel;
    }

    /**
     * Determines if the bot is currently joined to any channel.
     *
     * @param guild The Guild to scan.
     * @return true if the bot is currently joined to any channel, and false otherwise.
     */
    public boolean botInAnyChannel(Guild guild) {
        Snowflake botId = guild.getClient().getSelfId();
        Boolean inChannel = guild
                .getVoiceStates().any(state -> state.getUserId().equals(botId))
                .block();
        return inChannel != null && inChannel;
    }

    /**
     * Commands the bot to join the member's channel. The member must be in an active voice channel.
     *
     * @param member The member to obtain a channel from.
     */
    public void joinVoiceChannel(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("member cannot be null");
        }
        VoiceState voiceState = member.getVoiceState().block();
        if (voiceState == null) {
            logger.warn("member {} is not connected to voice, cannot be joined.", member.getId());
            return;
        }
        VoiceChannel channel = voiceState.getChannel().block();
        if (channel == null) {
            logger.warn("member {} is not in active voice channel, cannot be joined.", member.getId());
            return;
        }
        VoiceChannelJoinSpec spec = VoiceChannelJoinSpec.builder()
                .provider(this.audioProvider)
                .timeout(Duration.ofSeconds(30))
                .build();
        channel.join(spec).block();
    }
}
