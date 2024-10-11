package com.llamalad7.ipcdemo

import java.io.InputStream
import java.util.concurrent.Future

interface ProcessScope {
    fun sendLine(message: String)
    fun receiveLine(): String
}

inline fun <T> withProcess(vararg processArgs: String, block: ProcessScope.() -> T): T {
    val process = ProcessBuilder().command(*processArgs).start()
    val result: T
    process.outputWriter().printWriter().use { stdin ->
        process.inputReader().use { stdout ->
            val scope = ProcessScopeImpl(process, stdin::println, stdout::readLine)
            result = scope.block()
        }
    }
    process.waitFor()
    return result
}

@PublishedApi
internal class ProcessScopeImpl(
    private val process: Process,
    private val send: (String) -> Unit,
    private val receive: () -> String?,
) : ProcessScope {
    private val forwardErrors = forwardErrors(process.errorStream, "[Process stderr] ")

    override fun sendLine(message: String) {
        send(message)
    }

    override fun receiveLine(): String {
        return receive() ?: processClosed()
    }

    private fun processClosed(): Nothing {
        process.waitFor()
        // Wait for any pending errors to be forwarded by the background thread
        forwardErrors.get()
        error("Process exited with code ${process.exitValue()} before providing output")
    }
}

private val background = newDaemonThreadPool()

private fun forwardErrors(source: InputStream, prefix: String): Future<*> {
    return background.submit {
        source.bufferedReader().useLines { lines ->
            for (line in lines) {
                System.err.print(prefix)
                System.err.println(line)
            }
        }
    }
}
