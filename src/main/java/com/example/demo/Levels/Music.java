package com.example.demo.Levels;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Music class for playing background music and sound effects.
 */
public class Music {

    private Clip clip;
    private MediaPlayer mediaPlayer;
    private String currentSoundPath;

    /**
     * Plays a sound effect or background music.
     * Uses JavaFX MediaPlayer for MP3 or video files.
     *
     * @param soundPath the path to the sound file
     */
    public void playSound(String soundPath) {
        // Check if the sound is a media file (MP3, MP4, etc.)
        if (soundPath.endsWith(".mp3") || soundPath.endsWith(".mp4")) {
            playMediaSound(soundPath);
        } else {
            playClipSound(soundPath);
        }
    }

    /**
     * Plays a sound using the MediaPlayer for media files (e.g., MP3, MP4).
     *
     * @param soundPath the path to the sound file
     */
    private void playMediaSound(String soundPath) {
        try {
            // Create a Media object and a MediaPlayer to play the sound
            Media media = new Media(new File(soundPath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            // Set volume and start playing the sound
            mediaPlayer.setVolume(0.9); // Volume range from 0.0 to 1.0
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing media sound: " + e.getMessage());
        }
    }

    /**
     * Plays a sound using the Clip for short sound effects (e.g., collisions).
     *
     * @param soundPath the path to the sound file
     */
    private void playClipSound(String soundPath) {
        try {
            // Load the sound into a Clip
            File soundFile = new File(soundPath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodeFormat, audioStream);
            clip = AudioSystem.getClip();
            clip.open(decodedAudioStream);

            // Play the clip
            clip.start();

            // Optionally, loop the clip for background music
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing clip sound: " + e.getMessage());
        }
    }

    /**
     * Stops the currently playing sound if any.
     */
    public void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Example main method to test the music functionality.
     */
    public static void main(String[] args) {
        Music music = new Music();
        // Play a background music file
        music.playSound("src/main/resources/music/background.mp3");

        // Play a sound effect (e.g., collision)
        music.playSound("src/main/resources/sounds/collision.wav");
    }
}
