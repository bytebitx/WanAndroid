package com.bbgo.common_base.util.log;

import static android.util.Log.INFO;

import android.annotation.SuppressLint;
import android.util.Log;

import com.bbgo.common_base.BaseApplication;
import com.bbgo.common_base.BuildConfig;
import com.bbgo.common_base.pool.ThreadPoolUtils;
import com.bbgo.common_base.util.FileUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public final class ReleaseReportingTree extends Timber.Tree {

    /**
     * 是否记录log
     *
     * @param tag      tag
     * @param priority 级别
     * @return true 往下走log，否则不走
     */
    @Override
    protected boolean isLoggable(@Nullable String tag, int priority) {
        return priority >= INFO;
    }

    /**
     * 自己处理对应的日志信息
     *
     * @param priority 级别
     * @param tag      tag
     * @param message  message
     * @param t        错误信息
     */
    @Override
    protected void log(int priority, @Nullable final String tag, @NotNull final String message, @Nullable final Throwable t) {
        /* 如果日志界别是v或d就不做任何处理 */
//        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
//            return;
//        }
        if (BuildConfig.DEBUG) {
            logcat(tag, message, t);
        }
        ThreadPoolUtils.Companion.getInstance().execute(() -> {
            try {
                saveLogcat(priority, tag, message, t);
            } catch (Exception e) {
                Logs.INSTANCE.e(e, "saveLogcat error");
            }
        });

    }

    /**
     * 打印日志
     */
    @SuppressLint("LogNotTimber")
    private void logcat(@Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (tag == null) {
            if (t == null) {
                Log.e("TAG", message);
            } else {
                Log.e("TAG", message, t);
            }
        } else {
            if (t == null) {
                Log.e(tag, message);
            } else {
                Log.e(tag, message, t);
            }
        }
    }

    /**
     * 保存日志
     */
    private void saveLogcat(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) throws Exception {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        sb.append(sdf.format(new Date())).append(" /").append(BaseApplication.getContext().getPackageName()).append(" ");
        String level = "I";
        switch (priority) {
            case Log.DEBUG:
                level = "D";
                break;
            case Log.INFO:
                level = "I";
                break;
            case Log.VERBOSE:
                level = "V";
                break;
            case Log.WARN:
                level = "W";
                break;
            case Log.ERROR:
                level = "E";
                break;
        }
        sb.append(level).append("/").append(tag).append(": ").append(message).append("\n");
        if (t != null) {
            Writer writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            t.printStackTrace(pw);

            Throwable cause = t.getCause();
            // 循环着把所有的异常信息写入writer中
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
            pw.close();// 记得关闭
            String result = writer.toString();
            sb.delete(0, sb.length());
            sb.append("FATAL Exception：").append("\n").append(result);
        }

        // 目录
        File dir = new File(FileUtil.getExternalFilePath() + File.separator + "log");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        FileOutputStream fos = new FileOutputStream(new File(dir, fileFormat.format(new Date())), true);
        fos.write(sb.toString().getBytes());
        fos.flush();
        fos.close();
    }
}
