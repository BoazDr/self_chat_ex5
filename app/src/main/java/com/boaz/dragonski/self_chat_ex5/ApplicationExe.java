package com.boaz.dragonski.self_chat_ex5;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;


public class ApplicationExe {
    private static final ApplicationExe myApp = new ApplicationExe(Executors.newSingleThreadExecutor());
    private Executor workerThread;
    private ApplicationExe(Executor workerThread) {
        this.workerThread = workerThread;
    }
    public Executor getWorkerThread() {
        return workerThread;
    }
    public static ApplicationExe getApp() {
        return myApp;
    }
}
