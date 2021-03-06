package com.meishe.libperformance.monitor.ui.sampling;

/**
 * @author : lpf
 * @FileName: CpuInfo
 * @Date: 2022/5/6 22:35
 * @Description: 手机cpu信息收集器  不全CPU相关的字段
 */
public class CpuInfo {

    public long mId = 0;
    /**
     * 中央处理器速率
     */
    public long mCpuRate = 0;

    public long mAppRate = 0;
    public long mUserRate = 0;
    public long mSystemRate = 0;
    public long mIoWait = 0;

    public CpuInfo(long id) {
        mId = id;
    }

    public String toString() {
        StringBuffer mci = new StringBuffer();
        mci.append("cpu:").append(mCpuRate).append("% ");
        mci.append("app:").append(mAppRate).append("% ");
        mci.append("[").append("user:").append(mUserRate).append("% ");
        mci.append("system:").append(mSystemRate).append("% ");
        mci.append("ioWait:").append(mIoWait).append("% ]");
        return mci.toString();
    }

}
