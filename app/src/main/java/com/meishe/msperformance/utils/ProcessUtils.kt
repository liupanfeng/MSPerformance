package com.meishe.msperformance.utils

import android.app.ActivityManager
import android.content.Context


/**
 * * All rights reserved,Designed by www.meishesdk.com
 * @Author : lpf
 * @CreateDate : 2022/4/15 下午2:11
 * @Description : 进程工具
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
class ProcessUtils {

    /**
     * 通过ActivityManager获取到进程名字
     */
    companion object{
       fun getCurrentProcessName(context:Context):String?{
           val myPid = android.os.Process.myPid()
           val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
           val runningAppProcesses = activityManager.runningAppProcesses
           if (!runningAppProcesses.isNullOrEmpty()){
               runningAppProcesses.forEach {
                   if (it.pid==myPid){
                       return it.processName
                   }
               }
           }
           return null
       }
    }
}