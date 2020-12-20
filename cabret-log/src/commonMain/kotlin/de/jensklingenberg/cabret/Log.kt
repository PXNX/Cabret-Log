package de.jensklingenberg.cabret


@Target(AnnotationTarget.FUNCTION)
annotation class DebugLog(val logReturn: Boolean = false, val logLevel: Cabret.LogLevel = Cabret.LogLevel.DEBUG)


object LogHandler {

    private var listener: Cabret.Listener = DefaultListener()

    fun onLog(tag: String, name: String, logLevel: String) {
        val serv = Cabret.LogLevel.valueOf(logLevel)
        listener.log(tag,  name, serv)
    }

    fun addListener(listener: Cabret.Listener) {
        this.listener = listener
    }

    /**
     * This is used to log the retun values
     */
    fun <T> logReturn(tag: String, returnObject: T, logLevel: String): T {
        onLog(tag,  returnObject.toString(), logLevel)
        return returnObject
    }

    fun removeListener() {
        listener = DefaultListener()
    }
}

expect class DefaultListener() : Cabret.Listener {
    override fun log(tag: String, msg: String, logLevel: Cabret.LogLevel)
}

class CommonListener : Cabret.Listener {
    override fun log(tag: String, msg: String, logLevel: Cabret.LogLevel) {
        println(tag + " " + msg + " " + logLevel)
    }
}

object Cabret {

    enum class LogLevel {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    interface Listener {
        fun log(tag: String, msg: String, logLevel: Cabret.LogLevel)
    }

    fun addListener(listener: Cabret.Listener) {
        LogHandler.addListener(listener)
    }

    fun removeListener() {
        LogHandler.removeListener()
    }
}