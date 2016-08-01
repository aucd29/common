/*
 * BkSystem.java
 * Copyright 2014 Burke Choi All rights reserved.
 *             http://www.sarangnamu.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sarangnamu.common;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

/**
 * <pre>
 * {@code
 * BkSystem.isForegroundApp(getApplicationContext());
 *
 * BkSystem.isExistSdCard();
 *
 * BkSystem.getStorageSize();
 * BkSystem.getExternalStorageSize();
 *
 *
 * }
 * </pre>
 * @author <a href="mailto:aucd29@gmail.com">Burke Choi</a>
 */
public class BkSystem {
    private static final org.slf4j.Logger mLog = org.slf4j.LoggerFactory.getLogger(BkSystem.class);

    private static final int MAX_TASK = 20;

//    public static void restartService(Context appContext) {
//        // @see http://stackoverflow.com/questions/20920536/android-kitkat-4-4-kills-my-service-stared-by-alarm-manager
//        // in kitkat,use the code snippet below to restart te service automatically:
//        //
//        // call in onTaskRemoved
//
//        Intent restartService = new Intent(appContext, appContext.getClass());
//        restartService.setPackage(appContext.getPackageName());
//
//        PendingIntent restartServicePI = PendingIntent.getService(appContext, 1, restartService,PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
//
//        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePI);
//    }
//
//    public static void restartService(final Context context, Class<?> clazz) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // A restart intent - this never changes...
//            final int restartAlarmInterval = 20 * 60 * 1000;
//            final int resetAlarmTimer = 2 * 60 * 1000;
//            final Intent restartIntent = new Intent(context, clazz);
//            restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
//            final AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//            Handler restartServiceHandler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    PendingIntent pintent = PendingIntent.getService(context, 0, restartIntent, 0);
//                    alarmMgr.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + restartAlarmInterval, pintent);
//                    sendEmptyMessageDelayed(0, resetAlarmTimer);
//                }
//            };
//
//            restartServiceHandler.sendEmptyMessageDelayed(0, 0);
//        }
//    }

    public static boolean isForegroundApp(final Context context) {
        return isForegroundApp(context, context.getPackageName());
    }

    public static boolean isForegroundApp(final Context context, final String packageName) {
        try {
            if (context == null) {
                throw new Exception("context is null");
            }

            String[] activePackages;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                activePackages = getActivePackagesCompat(context);
            } else {
                activePackages = getActivePackages(context);
            }

            if (activePackages != null) {
                for (String activePackage : activePackages) {
                    if (activePackage.equals(context.getPackageName())) {
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e) {
            mLog.error(e.getMessage());
            return false;
        }
    }

    private static String[] getActivePackagesCompat(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();

        return activePackages;
    }

    private static String[] getActivePackages(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final Set<String> activePackages = new HashSet<>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    public static boolean isExistSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getStorageSize() {
        long size = getBlockSize(Environment.getDataDirectory());
        return BkMath.toFileSizeString(size);
    }

    public static String getExternalStorageSize() {
        long size = getBlockSize(Environment.getExternalStorageDirectory());
        return BkMath.toFileSizeString(size);
    }

    public static boolean isAvaliableStorage(long size) {
        long blockSize = getBlockSize(Environment.getDataDirectory());
        if (blockSize - size > 0) {
            return true;
        }

        return false;
    }

    public static boolean isAvaliableExternalStorage(long size) {
        long blockSize = getBlockSize(Environment.getExternalStorageDirectory());
        if (blockSize - size > 0) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    private static long getBlockSize(File path) {
        long blockSize;
        long availableBlocks;
        StatFs stat = new StatFs(path.getPath());

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }

        return availableBlocks * blockSize;
    }

    public static void sendBroadcast(Context context, Class<?> target,  String action, String data) {
        if (context == null) {
            mLog.error("sendBroadcast <context == null>");
            return ;
        }

        Intent intent = new Intent(context, target);
        intent.setAction(action);

        if (data != null) {
            intent.putExtra(action, data);
        }

        context.sendBroadcast(intent);
    }

    public static void killApp(Activity act) {
        act.moveTaskToBack(true);
        act.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static String getExternalSdStorage() {
        File fp = Environment.getExternalStorageDirectory();
        String exts = null;

        if (fp != null) {
            exts = fp.getPath();
        }

        try {
            File mountFp = new File("/proc/mounts");
            if (!mountFp.exists()) {
                return null;
            }

            String sdCard = null;
            Scanner scanner = new Scanner(mountFp);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("secure") || line.contains("asec")) {
                    continue;
                }

                if (line.contains("fat")) {
                    String[] pars = line.split("\\s");
                    if (pars.length < 2) {
                        continue;
                    }

                    if (pars[1].equals(exts) || "/mnt/sdcard".equals(pars[1])) {
                        continue;
                    }

                    sdCard = pars[1];
                    break;
                }
            }

            if (!TextUtils.isEmpty(sdCard) && sdCard.contains("media_rw")) {
                int pos = sdCard.lastIndexOf('/');
                String name = sdCard.substring(pos, sdCard.length());
                sdCard = "/storage" + name;
            }

            return sdCard;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
