package com.playback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import com.display.VisualNote;
import com.display.VisualSettings;
import com.parts.Measure;
import com.parts.Part;

public class Player {

    private short[] fullSong;
    private ArrayList<Part> staffs;
    
    public Player(ArrayList<Part> staffs) {
        // load all notes
        // generate waveforms
        this.staffs = staffs;
        loadFullSong();
    }

    public void loadFullSong() {
        int maxLen = 0;
        for (Part part : staffs) {
            if (part.getMeasures().size() > maxLen) {
                maxLen = part.getMeasures().size();
            }
        }
        fullSong = new short[maxLen * (int)Math.ceil(VisualSettings.instance().notesPerMeasure * (int) Math.ceil(AudioSettings.instance().sampleRate * (60 / AudioSettings.instance().tempo) ))];
        for (Part part : staffs) {
            int ptr = 0;
            part.instrument.cachePart(part);
            for (NoteShell shell : part.noteShells) {
                System.out.println("added note");
                short[] bytes = part.instrument.getCachedNote(shell);
                addShortArray(fullSong, bytes, (int)Math.floor(AudioSettings.instance().sampleRate * ptr * 60f / (12f * AudioSettings.instance().tempo)));
                ptr += shell.length;
            }
        }
    }

    public void playFullSong(int startCursor) {

        // TODO implement startCursor

        int sampleBytes = Short.SIZE / 8;
        ByteBuffer byteData = ByteBuffer.allocate(fullSong.length * sampleBytes);

        // Generate all samples
        for ( int i=0; i<fullSong.length; ++i )
        {
            int index = (i)*sampleBytes;
            byteData.putShort(index, (short) fullSong[i]);
        }

        AudioFormat format =
            new AudioFormat(Encoding.PCM_SIGNED,
                            AudioSettings.instance().sampleRate,
                            Short.SIZE,
                            1,
                            Short.SIZE / 8,
                            AudioSettings.instance().sampleRate,
                            true);
        AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(byteData.array()), format, byteData.array().length);
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            clip.drain();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFullSong(int startCursor) {
        
        // TODO implement startCursor

        int sampleBytes = Short.SIZE / 8;
        ByteBuffer byteData = ByteBuffer.allocate(fullSong.length * sampleBytes);

        // Generate all samples
        for ( int i=0; i<fullSong.length; ++i )
        {
            int index = (i)*sampleBytes;
            byteData.putShort(index, (short) fullSong[i]);
        }

        AudioFormat format =
            new AudioFormat(Encoding.PCM_SIGNED,
                            AudioSettings.instance().sampleRate,
                            Short.SIZE,
                            1,
                            Short.SIZE / 8,
                            AudioSettings.instance().sampleRate,
                            true);
        AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(byteData.array()), format, byteData.array().length);
        try {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File("C:\\Users\\Brandon\\Classes\\FNRT-485\\scale-synthesis\\output\\result.wav"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
     * Adds the second short array to the first, modifying the first.
     * Clamps result to the range of the root array.
     */
    public void addShortArray(short[] root, short[] add, int offset) {
        for (int i = offset; i < offset + add.length && i < root.length; i++) {
            root[i] += add[i - offset];
        }
    }
}
