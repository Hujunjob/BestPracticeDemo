package com.hiscene.utils;

/**
 * Created by hujun on 2019/1/25.
 */

public class NetworkQuality{
    public double delay;
    public double loss;
    public double avgDelay;

    @Override
    public String toString() {
        return String.format("delay=%sms,loss=%s,avgloss=%sms",delay,loss,avgDelay);
    }
}