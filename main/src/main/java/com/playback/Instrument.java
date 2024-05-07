package com.playback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import com.parts.Measure;
import com.parts.Note;
import com.parts.Part;

public class Instrument {
    
    private float[] harmonics;

    private HashMap<Integer, HashMap<Integer, short[]>> cachedNotes = new HashMap<>();

    private HashMap<NoteShell, short[]> flatCache = new HashMap<>();

    public Instrument(float[] harmonics) {
        this.harmonics = harmonics;
    }

    public void cacheNotes(int octaves, int notesPerOctave, float centralFrequency) {
        for (int o = 0; o < octaves; o++) {
            HashMap<Integer, short[]> currentOctave = new HashMap<>();
            cachedNotes.put(o, currentOctave);
            for (int i = 0; i < notesPerOctave; i++) {
                currentOctave.put(i, calculateBytes((float) (centralFrequency * Math.pow(2, o + ((double)i/notesPerOctave)))));
            }
        }
    }

    private ByteBuffer calculateNote(short[] data) {

        int nFrames = data.length;
        int sampleBytes = Short.SIZE / 8;
        int nBytes = nFrames * sampleBytes;
        ByteBuffer byteData = ByteBuffer.allocate(nBytes);

        // Generate all samples
        for ( int i=0; i<nFrames; ++i )
        {
            int index = (i)*sampleBytes;
            byteData.putShort(index, (short) data[i]);
        }
        return byteData;
    }
    
    // A function that needs cleaning
    private ByteBuffer calculateNote(float frequency) {
        return calculateNote(calculateBytes(frequency));
    }

    private short[] calculateBytes(float frequency) {
        int channels = 1;
        
        double duration = 2;

        int nFrames = (int) Math.ceil(AudioSettings.instance().sampleRate * duration);
        int nSamples = nFrames * channels;
        short[] data = new short[nFrames];

        int numWaves = harmonics.length;
        float normalizer = 0;
        for (int i = 0; i < numWaves; i++) {
            normalizer += harmonics[i];
        }
        normalizer /= numWaves;

        int falloffWindow = 4; // Hz // 16 applies some vibrato

        double falloffSpeed = Math.pow(0.00001, (double)2/falloffWindow);

        for (int i = 0; i < numWaves; i++) {
            for (int o = -1 * falloffWindow; o <= falloffWindow; o+=2) {
                addSine(data, frequency * i + o, numWaves * (falloffWindow + 1), (harmonics[i] / normalizer) * Math.pow(falloffSpeed, Math.abs(o)/2));
            }
        }

        WaveClamp clamp = new WaveClamp(0.5f, 0.2f, 40f, 0.4f);
        clamp.clamp(data, 1);

        // addSine(data, 440, numWaves);
        // addSine(data, 440 * Math.pow(2, (double)2/12), numWaves);
        // addSine(data, 440 * Math.pow(2, (double)7/12), numWaves);
        return data;
    }

    private short[] calculateBytes(NoteShell shell) {
        int channels = 1;

        System.out.println(shell.getCanonLength());
        
        float duration = AudioSettings.instance().timeForNote(shell.getCanonLength());
        float frequency = AudioSettings.instance().freqForNote(shell.pitch);

        int nFrames = (int) Math.ceil(AudioSettings.instance().sampleRate * duration);
        int nSamples = nFrames * channels;
        short[] data = new short[nFrames];

        int numWaves = harmonics.length;
        float normalizer = 0;
        for (int i = 0; i < numWaves; i++) {
            normalizer += harmonics[i];
        }
        normalizer /= numWaves;

        if (shell.noteDecoration.rest) {
            return data;
        }

        int falloffWindow = 4; // Hz // 16 applies some vibrato

        double falloffSpeed = Math.pow(0.00001, (double)2/falloffWindow);

        for (int i = 0; i < numWaves; i++) {
            for (int o = -1 * falloffWindow; o <= falloffWindow; o+=2) {
                addSine(data, frequency * i + o, numWaves * (falloffWindow + 1), (harmonics[i] / normalizer) * Math.pow(falloffSpeed, Math.abs(o)/2));
            }
        }

        WaveClamp clamp = new WaveClamp(0.3f, 0.3f, 10f, 0.1f);
        clamp.clamp(data, duration-0.3f);

        // addSine(data, 440, numWaves);
        // addSine(data, 440 * Math.pow(2, (double)2/12), numWaves);
        // addSine(data, 440 * Math.pow(2, (double)7/12), numWaves);
        return data;
    }

    private void clampSine(short[] data) {
        
    }

    private void addSine(short[] data, double freq, int numWaves, double amplitude) {
        for ( int i=0; i<data.length; ++i )
        {
            double value = (Math.sin((double)i/(double)AudioSettings.instance().sampleRate*freq*2*Math.PI + 2*Math.PI * ((int)freq % 199))*(Short.MAX_VALUE) / numWaves) * amplitude;
            data[i] += value;
        }
    }

    public void startPlayingNote(int octave, int note) {
        ByteBuffer bytes = calculateNote(cachedNotes.get(octave).get(note));

        AudioFormat format =
            new AudioFormat(Encoding.PCM_SIGNED,
                            AudioSettings.instance().sampleRate,
                            Short.SIZE,
                            1,
                            Short.SIZE / 8,
                            AudioSettings.instance().sampleRate,
                            true);
        AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(bytes.array()), format, bytes.array().length);
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

    public void cachePart(Part part) {
        ArrayList<Note> notes = new ArrayList<>();
        for (Measure m : part.getMeasures()) {
            for (Note n : m.notes) {
                notes.add(n);
                if (!n.noteDecoration.tie) {
                    NoteShell shell = new NoteShell(notes);
                    if (!flatCache.containsKey(shell)) {
                        flatCache.put(shell, calculateBytes(shell));
                    }
                    part.noteShells.add(shell);
                    notes = new ArrayList<>();
                }
            }
        }
    }

    public short[] getCachedNote(NoteShell note) {
        return flatCache.get(note);
    }
}
