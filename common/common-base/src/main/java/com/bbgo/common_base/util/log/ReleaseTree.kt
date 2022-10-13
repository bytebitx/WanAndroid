package com.bbgo.common_base.util.log

import android.text.TextUtils
import android.util.Log
import com.bbgo.common_base.BaseApplication.Companion.getContext
import com.bbgo.common_base.pool.ThreadPoolUtils
import com.bbgo.common_base.util.FileUtils
import com.bbgo.common_base.util.TimeUtils
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @Author：Wangyuebin
 * @Date：2022-09-22
 * @Description：
 */
class ReleaseTree : Timber.Tree() {

    companion object {
        private const val LOG_DIR = "log"
        // 日志文件名日期格式
        private const val LOG_FILE_NAME_DATE_FORMAT = "yyyy-MM-dd"
        // 保存日志的天数
        private const val SAVE_LOG_DAYS = 7
        // 日志文件名
        private const val LOG_FILE_NAME_FORMAT = "%s.log"
        // 日志文件名称
        private var mLogFile: File? = null
        // 当前日期
        private var mCurrentDate: String? = null
        // 日志队列
        private val mLogQueue = LinkedBlockingQueue<String>()

        /**
         * 初始化日志文件名
         */
        private fun init() {
            mCurrentDate = TimeUtils.date2String(Date(), LOG_FILE_NAME_DATE_FORMAT)
            val logFileName = String.format(LOG_FILE_NAME_FORMAT, mCurrentDate)
            val logDir = FileUtils.getExternalFilePath() + File.separator + LOG_DIR
            val fileDir = File(logDir)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            if (mLogFile == null) {
                mLogFile = File(logDir, logFileName)
            }
        }
    }
    // 日志写入器
    private val mLogWriter: LogWriter = LogWriter()

    init {
        init()
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        putLogQueue(priority, tag, message, t)
    }

    /**
     * 保存日志
     */
    private fun putLogQueue(priority: Int, tag: String?, message: String, t: Throwable?) {
        val logContent = StringBuilder()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        logContent.append(sdf.format(Date())).append(" /").append(getContext().packageName).append(" ")
        var level = "I"
        when (priority) {
            Log.DEBUG -> level = "D"
            Log.INFO -> level = "I"
            Log.VERBOSE -> level = "V"
            Log.WARN -> level = "W"
            Log.ERROR -> level = "E"
        }
        logContent.append(level).append("/").append(tag).append(": ").append(message).append("\n")
        if (t != null) {
            val writer: Writer = StringWriter()
            val pw = PrintWriter(writer)
            t.printStackTrace(pw)
            var cause = t.cause
            // 循环着把所有的异常信息写入writer中
            while (cause != null) {
                cause.printStackTrace(pw)
                cause = cause.cause
            }
            pw.close() // 记得关闭
            val result = writer.toString()
            logContent.delete(0, logContent.length)
            logContent.append("FATAL Exception：").append("\n").append(result).append("\n")
        }
        mLogQueue.put(logContent.toString())
        // 确保运行着
        mLogWriter.ensureRunning()
    }

    /**
     * 日志写入器
     */
    class LogWriter : Runnable {
        // 消费者线程是否在运行
        private val isLogWriterRunning = AtomicBoolean(false)

        override fun run() {
            while (isLogWriterRunning.get()) {
                try {
                    // 检查日期发生变化
                    val currentDate: String = TimeUtils.getNowTimeString()
                    if (TextUtils.isEmpty(mCurrentDate) || mCurrentDate != currentDate) {
                        checkDeletePastDueLog()
                        init()
                    }
                    if (mLogFile != null) {
                        var bw: BufferedWriter? = null
                        try {
                            if (mLogFile?.exists() == false) {
                                mLogFile?.createNewFile()
                            }
                            bw = BufferedWriter(FileWriter(mLogFile, true))
                            bw.write(mLogQueue.take())
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } finally {
                            bw?.close()
                        }
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            isLogWriterRunning.set(false)
        }

        /**
         * 确保运行着
         */
        fun ensureRunning() {
            // 写入线程在运行
            if (isLogWriterRunning.get()) {
                return
            }
            // 启动线程
            isLogWriterRunning.set(true)
            Thread(this).start()
        }

        /**
         * 检测删除过期日志，只保存近7天的日志
         */
        private fun checkDeletePastDueLog() {
            val logDirPath: String = FileUtils.getExternalFilePath() + File.separator + LOG_DIR
            val logDir = File(logDirPath)
            if (!logDir.exists() || logDir.listFiles().isNullOrEmpty()) {
                return
            }
            logDir.listFiles()?.let {
                for (logFile in it) {
                    if (logFile.exists() && isPastDueLogFile(logFile)) {
                        logFile.delete()
                    }
                }
            }
        }

        /**
         * 是否是过期的日志文件
         */
        private fun isPastDueLogFile(logFile: File): Boolean {
            val fileName: String = FileUtils.getFileNameNotSuffix(logFile)
            // 日志文件创建时间
            val logFileCreateTime = TimeUtils.string2Millis(
                fileName,
                LOG_FILE_NAME_DATE_FORMAT
            )
            // 日志文件名称格式解析错误，删除
            if (logFileCreateTime <= 0) {
                return true
            }
            // 检测时间是否已经过期
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -SAVE_LOG_DAYS)
            return calendar.timeInMillis > logFileCreateTime
        }
    }

}