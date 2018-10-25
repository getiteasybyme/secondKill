package com.secondKill.util;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolUtil {
    private volatile static ThreadPoolProxy threadPoolProxy;

    public static ThreadPoolProxy getThreadPoolProxy(){
        if (threadPoolProxy == null){
            synchronized (ThreadPoolProxy.class){
                if (threadPoolProxy == null){
                    threadPoolProxy = new ThreadPoolProxy(5,10,600);
                }
            }
        }
        return threadPoolProxy;
    }

    public static class ThreadPoolProxy{
        private ThreadPoolExecutor poolExecutor;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        private ThreadPoolProxy(int corePoolSize,int maximumPoolSize,long keepAliveTime){
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable runnable){
            if(poolExecutor==null||poolExecutor.isShutdown()){
                poolExecutor=new ThreadPoolExecutor(
                        //核心线程数量
                        corePoolSize,
                        //最大线程数量
                        maximumPoolSize,
                        //当线程空闲时，保持活跃的时间
                        keepAliveTime,
                        //时间单元 ，毫秒级
                        TimeUnit.MILLISECONDS,
                        //无界队列
                        new LinkedBlockingQueue<Runnable>(),
                        //创建线程的工厂
                        Executors.defaultThreadFactory());
            }
            poolExecutor.execute(runnable);
        }
    }
}
