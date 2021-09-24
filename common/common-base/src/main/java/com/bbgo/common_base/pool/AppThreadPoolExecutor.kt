package com.bbgo.common_base.pool

import android.util.Log
import com.alibaba.android.arouter.utils.TextUtils
import java.util.concurrent.*

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/9/24 4:10 下午
 */
class AppThreadPoolExecutor private constructor(
    corePoolSize: Int,
    maximumPoolSize: Int,
    keepAliveTime: Long,
    unit: TimeUnit,
    workQueue: BlockingQueue<Runnable>,
    threadFactory: ThreadFactory
) :
    ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
        RejectedExecutionHandler { r, executor ->
            Log.e(TAG, "Task rejected, too many task!")
        }) {
    /*
     *  线程执行结束，顺便看一下有么有什么乱七八糟的异常
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     */
    override fun afterExecute(r: Runnable?, t: Throwable?) {
        var throwable = t
        super.afterExecute(r, t)
        if (t == null && r is Future<*>) {
            try {
                (r as Future<*>).get()
            } catch (ce: CancellationException) {
                throwable = ce
            } catch (ee: ExecutionException) {
                throwable = ee.cause
            } catch (ie: InterruptedException) {
                Thread.currentThread().interrupt() // ignore/reset
            }
        }
        if (throwable != null) {
            Log.w(TAG, """
                    Running task appeared exception! Thread [${Thread.currentThread().name}], because [${throwable.message}]
                    ${TextUtils.formatStackTrace(throwable.stackTrace)}
                    """.trimIndent())
        }
    }

    companion object {

        private const val TAG = "AppPoolExecutor"
        //    Thread args
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val INIT_THREAD_COUNT = CPU_COUNT + 1
        private val MAX_THREAD_COUNT = INIT_THREAD_COUNT
        private const val SURPLUS_THREAD_LIFE = 30L

        @Volatile
        var instance: AppThreadPoolExecutor? = null
            get() {
                if (null == field) {
                    synchronized(AppThreadPoolExecutor::class.java) {
                        if (null == field) {
                            field = AppThreadPoolExecutor(
                                INIT_THREAD_COUNT,
                                MAX_THREAD_COUNT,
                                SURPLUS_THREAD_LIFE,
                                TimeUnit.SECONDS,
                                ArrayBlockingQueue(64),
                                AppThreadFactory()
                            )
                        }
                    }
                }
                return field
            }
            private set
    }
}