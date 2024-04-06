package com;

import java.nio.ByteBuffer;

public class Waveform {

    public static float[] pianoHarmonics = new float[]{0.064f, 0.0213f, 0.0285f, 0.014f, 0.013f, 0.0025f, 0.003f, 0.0023f, 0.002f, 0.0018f, 0.0015f, 0.001f, 0.0005f};
    
    public float frameRate = 44100f; // 44100 samples/s

    public ByteBuffer getBytes(float freq) {

        int channels = 1;
        int sampleBytes = Short.SIZE / 8;
        
        double duration = 4.0;

        int nFrames = (int) Math.ceil(frameRate * duration);
        int nSamples = nFrames * channels;
        int nBytes = nSamples * sampleBytes;
        short[] data = new short[nFrames];

        int numWaves = pianoHarmonics.length;
        float normalizer = 0;
        for (int i = 0; i < numWaves; i++) {
            normalizer += pianoHarmonics[i];
        }
        normalizer /= numWaves;

        int falloffWindow = 4; // Hz // 16 applies some vibrato

        double falloffSpeed = Math.pow(0.00001, (double)2/falloffWindow);

        for (int i = 0; i < numWaves; i++) {
            for (int o = -1 * falloffWindow; o <= falloffWindow; o+=2) {
                addSine(data, freq * i + o, numWaves * (falloffWindow + 1), (pianoHarmonics[i] / normalizer) * Math.pow(falloffSpeed, Math.abs(o)/2));
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
            double value = (Math.sin((double)i/(double)frameRate*freq*2*Math.PI)*(Short.MAX_VALUE) / numWaves) * amplitude;
            data[i] += value;
        }
    }
}
