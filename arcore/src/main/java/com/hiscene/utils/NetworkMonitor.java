package com.hiscene.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * Created by hujun on 2019/1/24.
 */

public class NetworkMonitor {
    private static final String TAG = "NetworkMonitorTAG";
    public static final int LOSS_GAP = 100;
    public static final int MIN_GAP = 10;
    private Observable<NetworkQuality> observable;
    private ObservableEmitter<NetworkQuality> networkEmitter;
    private Disposable timer;
    private boolean monitor = false;
    private int lastIcmp;
    private int checkIcmp;
    private Queue<Integer> icmpQueue;
    private Queue<Integer> icmpSmallQueue;

    public Observable<NetworkQuality> start() {
        if (monitor) {
            return Observable.error(new Throwable("已经在运行"));
        }
        monitor = true;
        icmpQueue = new ArrayBlockingQueue<>(LOSS_GAP);
        icmpSmallQueue = new ArrayBlockingQueue<>(MIN_GAP);
        observable = Observable.create(emitter -> {
            try {
                networkEmitter = emitter;
                //java层，调用Android Linux内核终端工具
                Process process = Runtime.getRuntime().exec("ping www.baidu.com");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String str = null;
                NetworkQuality quality = new NetworkQuality();
                while ((str = bufferedReader.readLine()) != null && monitor) {
                    Log4aUtil.d(TAG, "checkNetworkQuality %s", str);

                    //计算实时丢包率，每隔LOSS_GAP计算一次
                    //有大小精度，大测量范围LOSS_GAP可以减弱某次小丢包导致丢包率很大
                    //但是LOSS_GAP会导致测量变化太小，只要一次丢包，很长时间丢包率都不会减少
                    //MIN_GAP为小范围，如果小范围内丢包维持为0，则直接将丢包率置为0，可以增加丢包率灵敏度
                    if (str.contains("icmp_seq")) {
                        int start = str.lastIndexOf("icmp_seq=");
                        int end = str.indexOf("ttl=");
                        String icmpSeq = str.substring(start + 9, end - 1);
                        int icmp = Integer.parseInt(icmpSeq);
                        Log4aUtil.d(TAG, "start icmp:%s", icmp);
                        int size = icmpQueue.size();
                        double loss = 0;
                        if (size < LOSS_GAP) {
                            icmpQueue.add(icmp);
                            int icmp0 = icmpQueue.element();
                            size++;
                            if (size > 1) {
                                loss = (double) (icmp - icmp0 - size + 1) / (double) size;
                            }
                        } else {
                            int icmp0 = icmpQueue.remove();
                            icmpQueue.add(icmp);
                            loss = (double) (icmp - icmp0 - LOSS_GAP) / (double) LOSS_GAP;
                        }
                        //如果连续10个没有丢包，则丢包设为0
                        if (icmpSmallQueue.size() == MIN_GAP) {
                            int first = icmpSmallQueue.remove();
                            icmpSmallQueue.add(icmp);
                            if (icmp - first == MIN_GAP) {
                                loss = 0;
                            }
                        } else {
                            icmpSmallQueue.add(icmp);
                        }

                        quality.loss = loss * 100;
                        lastIcmp = icmp;
                    }
                    if (str.contains("time=")) {
                        int start = str.indexOf("time=");
                        int end = str.indexOf("ms");
                        String delay = str.substring(start + 5, end);
                        quality.delay = Double.parseDouble(delay);
                        emitter.onNext(quality);
                    }
                    if (str.contains("packet loss")) {
                        int i = str.indexOf("received");
                        int j = str.indexOf("%");
                        System.out.println("丢包率:" + str.substring(i + 10, j + 1));
                        String lost = str.substring(i + 10, j);
                        quality.loss = Double.parseDouble(lost);
                        emitter.onNext(quality);
                    }
                    if (str.contains("avg")) {
                        String strs[] = str.split("/");
                        quality.avgDelay = Double.parseDouble(strs[4]);
                        emitter.onNext(quality);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        });

        startTimer();

        return observable;
    }

    public void stop() {
        monitor = false;
        timer.dispose();
        icmpQueue.clear();
    }

    //启动一个定时器,用于对很弱的网进行提示
    private void startTimer() {
        timer = Observable.interval(2000, TimeUnit.MILLISECONDS).subscribe(l -> {
            //过了1500ms，还是没有变化，说明网络延时大于1500ms，则需要提示用户
            if (lastIcmp == checkIcmp) {
                if (networkEmitter != null) {
                    NetworkQuality networkQuality = new NetworkQuality();
                    networkQuality.delay = -1;
                    networkEmitter.onNext(networkQuality);
                }
            }
            checkIcmp = lastIcmp;
        });
    }


}
