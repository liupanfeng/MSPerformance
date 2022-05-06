package com.meishe.libperformance.monitor.ui.sampling;

import android.os.Handler;
import android.os.HandlerThread;

import com.meishe.libperformance.utils.GLog;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : lpf
 * @FileName: BaseSampler
 * @Date: 2022/5/6 22:22
 * @Description: 采样器 基类
 */
public abstract class BaseSampler {
    private final String TAG = BaseSampler.class.getSimpleName();
    private Handler mControlHandler = null;
    private int intervalTime = 50; //ms 采样时间间隔
    /**
     * 原子类，线程并发
     */
    private AtomicBoolean mIsSampling = new AtomicBoolean(false);

    public BaseSampler() {
        GLog.d(TAG, "Init BaseSampler");
    }

    public void start() {
        if (!mIsSampling.get()) {
            GLog.d(TAG, "start Sampler");
            getControlHandler().removeCallbacks(mRunnable);
            getControlHandler().post(mRunnable);
            mIsSampling.set(true);
        }
    }

    public void stop() {
        if (mIsSampling.get()) {
            GLog.d(TAG, "stop Sampler");
            getControlHandler().removeCallbacks(mRunnable);
            mIsSampling.set(false);
        }
    }

    /**
     * 通过HandlerThread 得到一个Handler对象
     *
     * @return
     */
    private Handler getControlHandler() {
        if (null == mControlHandler) {
            HandlerThread mHT = new HandlerThread("SamplerThread");
            mHT.start();
            mControlHandler = new Handler(mHT.getLooper());
        }
        return mControlHandler;
    }

    /**
     * 进行采样操作，暴露一个抽象方法
     */
    abstract void doSample();

    /**
     * 其实就是启动一个定时器，心跳  等等使用RxJava改造下
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doSample();
            if (mIsSampling.get()) {
                getControlHandler().postDelayed(mRunnable, intervalTime);
            }
        }
    };

}
