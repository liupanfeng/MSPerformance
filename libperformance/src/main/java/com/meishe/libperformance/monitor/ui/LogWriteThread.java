package com.meishe.libperformance.monitor.ui;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.meishe.libperformance.utils.GLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

/**
 * @author : lpf
 * @FileName: LogWriteThread
 * @Date: 2022/5/6 22:47
 * @Description:
 */
class LogWriteThread implements UiPerfMonitorConfig {

    private Handler mWriteHandler = null;
    private final Object FILE_LOCK = new Object();
    private final SimpleDateFormat FILE_NAME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat TIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String TAG = "LogWriteThread";

    public void saveLog(final String loginfo) {
        getControlHandler().post(new Runnable() {
            @Override
            public void run() {
                synchronized (FILE_LOCK) {
                    saveLog2Local(loginfo);
                }
            }
        });
    }

    /**
     * 日志保存到本地
     *
     * @param info
     */
    private void saveLog2Local(String info) {
        long time = System.currentTimeMillis();
        File logFile = new File(LOG_PATH + "/" + FILENAME + "-" + FILE_NAME_FORMATTER.format(time) + ".txt");
        StringBuffer mSb = new StringBuffer("/***************************************/\r\n");
        mSb.append(TIME_FORMATTER.format(time));
        mSb.append("\r\n/***************************************/\r\n");
        mSb.append(info + "\r\n");
        GLog.d(TAG, "saveLogToSDCard: " + mSb.toString());
        if (logFile.exists()) {
            writeLog4SameFile(logFile.getPath(), mSb.toString());
        } else {
            BufferedWriter writer = null;
            try {
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(logFile.getPath(), true), "UTF-8");
                writer = new BufferedWriter(out);
                writer.write(mSb.toString());
                writer.flush();
                writer.close();
                writer = null;
            } catch (Throwable t) {
                Log.e(TAG, "saveLogToSDCard: ", t);
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                        writer = null;
                    }
                } catch (Exception e) {
                    GLog.e(TAG, "saveLogToSDCard: ", e);
                }
            }
        }
    }

    /**
     * @param fileName
     * @param content
     */
    public static void writeLog4SameFile(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send2Server() {
        getControlHandler().post(new Runnable() {
            @Override
            public void run() {
                //TODO 发送到服务器
            }
        });
    }

    private Handler getControlHandler() {
        if (null == mWriteHandler) {
            HandlerThread mHT = new HandlerThread("SamplerThread");
            mHT.start();
            mWriteHandler = new Handler(mHT.getLooper());
        }
        return mWriteHandler;
    }
}

