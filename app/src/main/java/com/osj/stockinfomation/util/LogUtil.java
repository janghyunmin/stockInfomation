package com.osj.stockinfomation.util;

import android.util.Log;
/**
 * jhm
 * @class LogUtil
 * @date 2021-07-07
 * @time 오후 3:50
 * 로그 처리시 공통
 **/
public class LogUtil {
    public static void logE(String msg) {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[3];
        String fileName = trace.getFileName();
        String classPath = trace.getClassName();
        String className = classPath.substring(classPath.lastIndexOf(".") + 1);
        String methodName = trace.getMethodName();
        int lineNumber = trace.getLineNumber();

        Log.e("APP# " + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")", msg);
    }
}
