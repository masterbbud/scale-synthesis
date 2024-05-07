package com.playback;

public class WaveClamp {
    
    public float attackLength;
    public float releaseLength;
    public float decaySpeed;
    public float relativeVolume;

    public WaveClamp(float attackLength, float releaseLength, float decaySpeed, float relativeVolume) {
        this.attackLength = attackLength;
        this.releaseLength = releaseLength;
        this.decaySpeed = decaySpeed;
        this.relativeVolume = relativeVolume;
    }

    public void clamp(short[] note, float noteLength) {
        float sampleRate = AudioSettings.instance().sampleRate;
        for (int i = 0; i < note.length; i++) {
            note[i] = (short)(note[i] * resultOfClamp(i / sampleRate, noteLength));
        }
    }

    public float resultOfClamp(float time, float noteLength) {
        // TODO have release, attack vary WRT noteLength
        // TODO add window at the front
        float tempAttackLength = attackLength * Math.min(1, noteLength);
        if (time < attackLength / 16f) {
            return (float)((-1 * Math.cos(Math.PI * (16 * time / attackLength)) + 1) * (1 / 2f));
        }
        else if (time < attackLength) {
            return (float)((Math.cos(Math.PI * (time - (attackLength / 16f)) / ((15/16f)*attackLength)) + 1) * (1 - relativeVolume) / 2f + relativeVolume);
        }
        else if (time <= noteLength) {
            return (float)(Math.exp(-((time - attackLength)/decaySpeed)) * relativeVolume);
        }
        else if (time <= noteLength + releaseLength) {
            return (float)((Math.cos(Math.PI * (time - noteLength) / releaseLength)+1) * (Math.exp(-((noteLength - attackLength)/decaySpeed)) * relativeVolume) / 2);
        }
        return 0;
    }
}
