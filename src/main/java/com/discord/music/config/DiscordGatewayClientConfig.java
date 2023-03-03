package com.discord.music.config;

import com.discord.music.config.properties.SecretBotProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.gateway.GatewayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordGatewayClientConfig {
    @Bean
    GatewayDiscordClient createGatewayClient(ObjectMapper objectMapper, SecretBotProperties sbp) {
        return DiscordClientBuilder
                .create(sbp.getDiscordToken()).build()
                .login().block();
    }
}
