package com.example.demo.MusicController;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Objects;

public class Bgm {

    protected MediaPlayer mediaPlayer;
    private double musicVolume;

    public Bgm(double volume) {
        this.musicVolume = volume;
    }

    /**
     * Plays the background music from the provided path.
     *
     * @param musicPath The path to the music file.
     */
    public void playBGM(String musicPath) {
        if (mediaPlayer != null) return;

        new Thread(() -> {
            try {
                URL resource = getClass().getClassLoader().getResource(musicPath);
                if (resource == null) {
                    throw new IllegalArgumentException("Audio file not found: " + musicPath);
                }
                Media media = new Media(resource.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(musicVolume);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                Platform.runLater(mediaPlayer::play); // Play on JavaFX thread
            } catch (Exception e) {
                System.err.println("Error playing background music: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Stops the background music if it is playing.
     */
    public void stopBGM() {
        if (mediaPlayer == null) return; // If mediaPlayer is null, no need to stop anything

        mediaPlayer.stop();
        mediaPlayer = null;
    }

    /**
     * Pauses the background music if it is playing.
     */
    public void pauseBGM() {
        if (mediaPlayer == null) return; // If mediaPlayer is null, no need to pause anything
        mediaPlayer.pause();
    }

    /**
     * Resumes the background music if it is paused.
     */
    public void resumeBGM() {
        if (mediaPlayer == null) return; // If mediaPlayer is null, no need to resume anything
        mediaPlayer.play();
    }

    /**
     * Sets the volume of the background music.
     *
     * @param volume The volume level (0.0 to 1.0).
     */
    public void setVolume(double volume) {
        this.musicVolume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Checks if the background music is currently playing.
     *
     * @return true if the music is playing, false otherwise.
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
