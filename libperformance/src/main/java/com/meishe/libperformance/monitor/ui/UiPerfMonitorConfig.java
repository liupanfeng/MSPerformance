package com.meishe.libperformance.monitor.ui;

import android.os.Environment;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : lpf
 * @FileName: UiPerfMonitorConfig
 * @Date: 2022/5/6 22:15
 * @Description: UI卡顿监听
 */
public interface UiPerfMonitorConfig {

    //警报级别
    public final int TIME_WARNING_LEVEL_1 = 100;
    public final int TIME_WARNING_LEVEL_2 = 300;


    public final int UI_PERF_LEVEL_0 = 0;
    public final int UI_PERF_LEVEL_1 = 1;
    public final int UI_PERF_LEVEL_2 = 2;


    /**
     * 安卓开发应避免使用Enum（枚举类），因为相比于静态常量Enum会花费两倍以上的内存。
     * 使用@IntDef注解来代替枚举是个不错的选择。
     */
    @IntDef({UI_PERF_LEVEL_0, UI_PERF_LEVEL_1, UI_PERF_LEVEL_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PER_LEVEL {
    }

    /**
     * 开始标记
     */
    public final int UI_PERF_MONITER_START = 0x01;
    /**
     * 结束标记
     */
    public final int UI_PERF_MONITER_STOP = 0x01 << 1;

    public final String LOG_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/androidtech";

    public final String FILENAME = "UiMonitorLog";


}
