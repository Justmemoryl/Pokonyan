package cn.jml.pokonyan.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.IMarkerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;

/**
 * 日志记录工具
 * 
 * @version 1.0 created by chenzhenwei on 2018年6月19日 下午5:40:05
 */
public class LogUtil {
    public static final String          LOG_ID_MDC_KEY_NAME = "logId";

    private static final IMarkerFactory markerFactory       = new BasicMarkerFactory();

    /**
     * 日志接口标记
     */
    public static enum LogMarker {

                                  /**
                                   * 应用记录日志
                                   */
                                  business(markerFactory.getMarker("business")),
                                  /**
                                   * 客户端接口访问日志
                                   */
                                  api(markerFactory.getMarker("api")),
                                  /**
                                   * 远程调用日志
                                   */
                                  remote(markerFactory.getMarker("remote")),
                                  /**
                                   * 消息监听日志
                                   */
                                  mq(markerFactory.getMarker("mq"));
        private Marker marker;

        LogMarker(Marker markder) {
            this.marker = markder;
        }
    }

    /**
     * 初始化logid,以logId作为key放入MDC
     */
    public static String initLogId() {
        String logId = UUIDGenerator.getUUID();
        initLogId(logId);
        return logId;
    }

    /**
     * 初始化logid,以logId作为key放入MDC
     * 
     * @param logId
     */
    public static void initLogId(String logId) {
        MDC.put(LOG_ID_MDC_KEY_NAME, logId);
    }

    /**
     * 获取logid 可能为空
     *
     **/
    public static String getLogId() {
        return MDC.get(LOG_ID_MDC_KEY_NAME);
    }

    /**
     * 获取logId 不存在时会生成一个
     *
     * @return
     */
    public static String initIfAbsent() {
        if (StringUtils.isEmpty(getLogId())) {
            return initLogId();
        } else {
            return getLogId();
        }
    }

    /**
     * 清空logid,清空MDC
     *
     */
    public static void clearLogId() {
        MDC.remove(LOG_ID_MDC_KEY_NAME);
    }

    /**
     * 使用logger将日志信息按照trace级别输出
     *
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void trace(Logger logger, String messageOrFormat, Object... messageParams) {
        trace(LogMarker.business, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照trace级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void trace(LogMarker logMarker, Logger logger, String messageOrFormat, Object... messageParams) {
        trace(logMarker, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照trace级别输出
     *
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void trace(Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        trace(LogMarker.business, throwable, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照trace级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void trace(LogMarker logMarker, Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        if (logger.isTraceEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            logger.trace(logMarker.marker, message, throwable);
        }
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void debug(Logger logger, String messageOrFormat, Object... messageParams) {
        debug(LogMarker.business, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void debug(LogMarker logMarker, Logger logger, String messageOrFormat, Object... messageParams) {
        debug(logMarker, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void debug(Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        debug(LogMarker.business, throwable, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void debug(LogMarker logMarker, Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        if (logger.isDebugEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            logger.debug(logMarker.marker, message, throwable);
        }
    }

    /**
     * 使用logger将日志信息按照info级别输出
     *
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void info(Logger logger, String messageOrFormat, Object... messageParams) {
        info(LogMarker.business, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照info级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void info(LogMarker logMarker, Logger logger, String messageOrFormat, Object... messageParams) {
        info(logMarker, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照info级别输出
     *
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void info(Throwable throwable, Logger logger, String messageOrFormat,
                            Object... messageParams) {
        info(LogMarker.business, throwable, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照info级别输出
     * 
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void info(LogMarker logMarker, Throwable throwable, Logger logger, String messageOrFormat,
                            Object... messageParams) {
        if (logger.isInfoEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            logger.info(logMarker.marker, message, throwable);
        }
    }

    /**
     * 使用logger将日志信息按照warn级别输出
     *
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void warn(Logger logger, String messageOrFormat, Object... messageParams) {
        warn(LogMarker.business, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照warn级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void warn(LogMarker logMarker, Logger logger, String messageOrFormat, Object... messageParams) {
        warn(logMarker, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照warn级别输出
     *
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void warn(Throwable throwable, Logger logger, String messageOrFormat,
                            Object... messageParams) {
        warn(LogMarker.business, throwable, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照warn级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void warn(LogMarker logMarker, Throwable throwable, Logger logger, String messageOrFormat,
                            Object... messageParams) {
        if (logger.isWarnEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            logger.warn(logMarker.marker, message, throwable);
        }
    }

    /**
     * 使用logger将日志信息按照error级别输出
     *
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void error(Logger logger, String messageOrFormat, Object... messageParams) {
        error(LogMarker.business, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照error级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void error(LogMarker logMarker, Logger logger, String messageOrFormat, Object... messageParams) {
        error(logMarker, null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void error(Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        error(LogMarker.business, throwable, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照error级别输出
     *
     * @param logMarker
     *            标记,取值api(客户端访问日志),business(业务日志,默认值),remote(远程调用日志)
     * @param throwable
     *            异常堆栈
     * @param logger
     *            输出日志的logger
     * @param messageOrFormat
     *            日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams
     *            日志格式的参数
     */
    public static void error(LogMarker logMarker, Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        if (logger.isWarnEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            logger.error(logMarker.marker, message, throwable);
        }
    }
}
