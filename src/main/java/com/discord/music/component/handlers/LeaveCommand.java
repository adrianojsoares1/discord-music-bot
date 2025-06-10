package com.discord.music.component.handlers;

import com.discord.music.model.CommandHandler;
import com.discord.music.service.VoiceChannelService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LeaveCommand implements CommandHandler<ChatInputInteractionEvent> {
    private final VoiceChannelService voiceChannelService;

    public LeaveCommand(VoiceChannelService voiceChannelService) {
        this.voiceChannelService = voiceChannelService;
    }

    @Override
    public Mono<Void> executeOnCommand(ChatInputInteractionEvent event) {
        if (this.voiceChannelService.leaveVoiceChannel()) {
            return event.reply("Bot will leave voice channel.").withEphemeral(true);
        }
        return event.reply("Bot is not in voice channel.").withEphemeral(true);
    }
}
