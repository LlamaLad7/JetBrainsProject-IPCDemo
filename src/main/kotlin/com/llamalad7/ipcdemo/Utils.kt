package com.llamalad7.ipcdemo

import java.io.PrintWriter
import java.io.Writer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun Writer.printWriter() = PrintWriter(this, true)

fun newDaemonThreadPool(): ExecutorService = Executors.newCachedThreadPool { task ->
    Executors.defaultThreadFactory().newThread(task).also { it.isDaemon = true }
}