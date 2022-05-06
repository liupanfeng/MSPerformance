package com.meishe.libperformance.monitor.ui;

/**
 * @author : lpf
 * @FileName: LogPrinterListener
 * @Date: 2022/5/6 22:46
 * @Description: 日志打印
 */
interface LogPrinterListener {
    void onStartLoop();

    void onEndLoop(long starttime, long endtime, String loginfo, int level);
}
