package com.hiscene.ui.myparticle;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by junhu on 2019-10-26
 */
public abstract class ParticleFactory {
    public abstract Particle[] generateParticle(Bitmap bitmap, Rect rect);
}
