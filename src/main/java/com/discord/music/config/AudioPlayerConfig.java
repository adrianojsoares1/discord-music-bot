package com.discord.music.config;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AudioPlayerConfig {
    @Bean
    AudioPlayerManager getAudioPlayerManager(@Nullable @Value("${discord.authentication.secret.oauth-2-token}") String oAuthToken) {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager
                .getConfiguration()
                .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        // exclude deprecated YouTubeAudioSourceManager, and include the rewritten library.
        AudioSourceManagers.registerRemoteSources(playerManager,
                com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager.class);
        YoutubeAudioSourceManager ytSourceManager = new YoutubeAudioSourceManager();
        boolean skipInitialization = oAuthToken != null;
        ytSourceManager.useOauth2(oAuthToken, skipInitialization);
        playerManager.registerSourceManager(ytSourceManager);
        return playerManager;
    }

    @Bean
    AudioPlayer getAudioPlayer(AudioPlayerManager audioPlayerManager) {
        return audioPlayerManager.createPlayer();
    }
}
