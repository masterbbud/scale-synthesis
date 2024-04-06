package com.playback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Dictionary;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Instrument {
    
    private float[] harmonics;

    private HashMap<Integer, HashMap<Integer, ByteBuffer>> cachedNotes = new HashMap<>();

    public Instrument(float[] harmonics) {
        this.harmonics = harmonics;
    }

    public void cacheNotes(int octaves, int notesPerOctave, float centralFrequency) {
        for (int o = 0; o < octaves; o++) {
            HashMap<Integer, ByteBuffer> currentOctave = new HashMap<>();
            cachedNotes.put(o, currentOctave);
            for (int i = 0; i < notesPerOctave; i++) {
                currentOctave.put(i, calculateNote((float) (centralFrequency * Math.pow(2, o + ((double)i/notesPerOctave)))));
            }
        }
    }
    
    // A function that needs cleaning
    private ByteBuffer calculateNote(float frequency) {
        int channels = 1;
        int sampleBytes = Short.SIZE / 8;
        
        double duration = 4.0;

        int nFrames = (int) Math.ceil(AudioSettings.instance().sampleRate * duration);
        int nSamples = nFrames * channels;
        int nBytes = nSamples * sampleBytes;
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

        // addSine(data, 440, numWaves);
        // addSine(data, 440 * Math.pow(2, (double)2/12), numWaves);
        // addSine(data, 440 * Math.pow(2, (double)7/12), numWaves);

        ByteBuffer byteData = ByteBuffer.allocate(nBytes);

        // Generate all samples
        for ( int i=0; i<nFrames; ++i )
        {
            int index = (i)*sampleBytes;
            byteData.putShort(index, (short) data[i]);
        }
        return byteData;
    }

    private void addSine(short[] data, double freq, int numWaves, double amplitude) {
        for ( int i=0; i<data.length; ++i )
        {
            double value = (Math.sin((double)i/(double)AudioSettings.instance().sampleRate*freq*2*Math.PI)*(Short.MAX_VALUE) / numWaves) * amplitude;
            data[i] += value;
        }
    }

    public void startPlayingNote(int octave, int note) {
        ByteBuffer bytes = cachedNotes.get(octave).get(note);

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
}
