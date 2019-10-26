package com.hiscene.ui.particle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junhu on 2019-10-25
 */
public class FallingFactory extends ParticleFactory {
    //粒子大小
    public static final int PARTICLE_SIZE = 10;

    @Override
    protected Particle[] generateParticles(Bitmap bitmap, Rect rect) {

        int width = rect.width();
        int height = rect.height();

        //一个图片生成多少粒子
        int countW = width / PARTICLE_SIZE;
        int countH = height / PARTICLE_SIZE;

        countW = countW > 0 ? countW : 1;
        countH = countH > 0 ? countH : 1;

        //循环生成粒子
        List<Particle> particles = new ArrayList<>();
        int startX = rect.left;
        int startY = rect.top;
        for (int i = 0; i < countW; i++) {
            for (int j = 0; j < countH; j++) {
                int x = PARTICLE_SIZE * i;
                int y = PARTICLE_SIZE * j;

                int color = bitmap.getPixel(x, y);
                if (Color.alpha(color)==0){
                    continue;
                }
                Particle particle = new FallingParticle(startX + x, startY + y, color,rect);
                particles.add(particle);
            }
        }
        Particle[] particle = new Particle[particles.size()];
        return particles.toArray(particle);
    }
}
