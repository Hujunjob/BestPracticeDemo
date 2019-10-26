package com.hiscene.ui.particle;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by junhu on 2019-10-25
 * 粒子工厂，生成需要的粒子
 */
public abstract class ParticleFactory {
    protected abstract Particle[] generateParticles(Bitmap bitmap, Rect rect);
}
