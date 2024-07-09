package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Clip clip;

    // Method untuk memuat dan memutar musik
    public void playMusic(String musicFilePath) {
        try {
            // Mendapatkan file musik
            File musicFile = new File(musicFilePath);
            if (musicFile.exists()) {
                // Membuat objek AudioInputStream dari file musik
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Menambahkan loop
            } else {
                System.out.println("File musik tidak ditemukan.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghentikan musik
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method untuk loop musik
    public void loopMusic() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Method untuk mengatur volume
    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float range = max - min;
            float gain = (range * volume) + min;
            gainControl.setValue(gain);
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
