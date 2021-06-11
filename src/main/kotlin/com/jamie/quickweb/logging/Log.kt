package com.jamie.quickweb.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private enum class LoggerType {
    DEV, AUDIT, TRACE, API, ERROR
}

/**
 * Log writes logs in 5 different streams: DEV, AUDIT, TRACE, API
 *      The DEV stream is for app logs and for developers to debug
 *      The AUDIT stream is for sending audit logs that indicates the transaction (if any) states.
 *      The TRACE stream is for deep debugging logs
 *      The API stream is for logs when entering and leaving an API call.
 *      The ERROR stream is for sending error logs.
 */
object Log {
    /**
     * THE MDC is the Mapped Diagnostic Context.
     */
    object MDC {
        fun put(k: String, v: String?) {
            org.slf4j.MDC.put(k, v)
        }

        fun get(k: String): String? {
            return org.slf4j.MDC.get(k)
        }

        fun remove(k: String) {
            org.slf4j.MDC.remove(k)
        }

        fun clear() {
            org.slf4j.MDC.clear()
        }
    }

    private val cacheMDC = ThreadLocal<MutableMap<String, String?>>();

    private val devLogger: org.slf4j.Logger = LoggerFactory.getLogger(LoggerType.DEV.name)
    private val auditLogger: org.slf4j.Logger = LoggerFactory.getLogger(LoggerType.AUDIT.name)
    private val traceLogger: org.slf4j.Logger = LoggerFactory.getLogger(LoggerType.TRACE.name)
    private val apiLogger: org.slf4j.Logger = LoggerFactory.getLogger(LoggerType.API.name)
    private val errorLogger: org.slf4j.Logger = LoggerFactory.getLogger(LoggerType.ERROR.name)

    private fun saveToCache(context: Map<String, String>) {
        var cache = cacheMDC.get()
        if (cache == null) {
            cache = mutableMapOf()
            cacheMDC.set(cache)
        } else {
            cache.clear()
        }

        context.forEach() {
            cache[it.key] = MDC.get(it.key)
            MDC.put(it.key, it.value)
        }
    }

    private fun loadFromCache(context: Map<String, String>) {
        var cache = cacheMDC.get()
        if (cache != null) {
            context.forEach() {
                if (cache[it.key] == null) {
                    MDC.remove(it.key)
                } else {
                    MDC.put(it.key, cache[it.key])
                }
            }
        }
    }

    fun getDevLogger(): Logger {
        return devLogger
    }

    fun getTraceLogger(): Logger {
        return traceLogger
    }

    fun getErrorLogger(): Logger {
        return errorLogger
    }

    fun getAuditLogger(): Logger {
        return auditLogger
    }

    fun getApiLogger(): Logger {
        return apiLogger
    }

    /**
     * Sending message to DEV log stream.
     *
     * @param msg The message to write to log stream.
     * @param context The MDC context that will be appended to the log MDC and removed when the call returns.
     */
    fun dev(msg: String, context: MutableMap<String, String> = mutableMapOf()) {
        context["logger"] = LoggerType.DEV.name
        saveToCache(context = context)
        getDevLogger().debug(msg)
        loadFromCache(context = context)
    }

    /**
     * Sending message to AUDIT log stream.
     *
     * @param msg The message to write to log stream.
     * @param context The MDC context that will be appended to the log MDC and removed when the call returns.
     */
    fun audit(msg: String, context: MutableMap<String, String> = mutableMapOf()) {
        context["logger"] = LoggerType.AUDIT.name
        saveToCache(context = context)
        getAuditLogger().info(msg)
        loadFromCache(context = context)
    }

    /**
     * Sending message to TRACE log stream.
     *
     * @param msg The message to write to log stream.
     * @param context The MDC context that will be appended to the log MDC and removed when the call returns.
     */
    fun trace(msg: String, context: MutableMap<String, String> = mutableMapOf()) {
        context["logger"] = LoggerType.TRACE.name
        saveToCache(context = context)
        getTraceLogger().trace(msg)
        loadFromCache(context = context)
    }

    /**
     * Sending message to API log stream.
     *
     * @param msg The message to write to log stream.
     * @param context The MDC context that will be appended to the log MDC and removed when the call returns.
     */
    fun api(msg: String, context: MutableMap<String, String> = mutableMapOf()) {
        context["logger"] = LoggerType.API.name
        saveToCache(context = context)
        getApiLogger().info(msg)
        loadFromCache(context = context)
    }

    /**
     * Sending message to error log stream.
     *
     * @param msg The message to write to log stream.
     * @param printStackTrace The thread's stack trace will be written.
     * @param context The MDC context that will be appended to the log MDC and removed when the call returns.
     */
    fun error(msg: String, printStackTrace: Boolean = false, context: MutableMap<String, String> = mutableMapOf()) {
        context["logger"] = LoggerType.ERROR.name
        saveToCache(context = context)

        if (printStackTrace) {
            val traceElements = mutableListOf(*Thread.currentThread().stackTrace)
            traceElements.removeFirst()
            var traceElementsToPrint = traceElements.joinToString(separator = System.lineSeparator() + "    ")
            getErrorLogger().error(msg + System.lineSeparator() + "    $traceElementsToPrint");
        } else {
            getErrorLogger().error(msg);
        }

        loadFromCache(context = context)
    }
}
