package com.hiscene.ui.myparticle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junhu on 2019-10-26
 */
public class FallingParticleFactory extends ParticleFactory {
    private static final int PARTICLE_SIZE = 10;

    @Override
    public Particle[] generateParticle(Bitmap bitmap, Rect rect) {
        //计算粒子数量
        int width = rect.width();
        int height = rect.height();

        int countW = width / PARTICLE_SIZE;
        int countH = height / PARTICLE_SIZE;

        countW = countW > 0 ? countW : 1;
        countH = countH > 0 ? countH : 1;

        List<Particle> particles = new ArrayList<>();

        for (int i = 0; i < countW; i++) {
            for (int j = 0; j < countH; j++) {
                int x = rect.left + i * PARTICLE_SIZE;
                int y = rect.top + j * PARTICLE_SIZE;

                if (x >= rect.width() || y >= rect.height()) {
                    continue;
                }

                int color = bitmap.getPixel(x, y);
                if (Color.alpha(color) == 0) {
                    continue;
                }

                Particle particle = new FallingParticle(x, y, color, PARTICLE_SIZE);
                particles.add(particle);
            }
        }

        Particle[] particless = new Particle[particles.size()];
        return particles.toArray(particless);
    }
}
