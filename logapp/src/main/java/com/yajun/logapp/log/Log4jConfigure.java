package com.yajun.logapp.log;

import android.os.Environment;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;

import java.io.File;
import java.io.IOException;

import de.mindpipe.android.logging.log4j.LogCatAppender;

/**
 * Created by yajun on 2016/10/17.
 *
 *
 */
public class Log4jConfigure {

    /**
     * Appender类名作 用
     * org.apache.log4j.ConsoleAppender 将日志输出到控制台
     * org.apache.log4j.FileAppender 将日志输出到文件
     * org.apache.log4j.DailyRollingFileAppender  每天产生一个日志文件
     * org.apache.log4j.RollingFileAppender 文件大小到达指定尺寸时产生一个新的文件
     * org.apache.log4j.WriterAppender 将日志信息以流格式发送到任意指定的地方
     *
     * Loggers组件输出日志信息时分为5个级别：DEBUG、INFO、WARN、ERROR、FATAL。
     * 这五个级别的顺序是：DEBUG<INFO<WARN<ERROR<FATAL。
     */

    public static String LOG_FILE_PATH = "log";
    public static String LOG_FILE_NAME = "today.log";
    /**
     * 文件log输出等级
     */
    public static Level fileLevel = Level.INFO;
    /**
     * 控制台log输出等级
     */
    public static Level logcatLevel = Level.DEBUG;
    /**
     * RootLog输出等级
     */
    private static Level rootLevel = Level.DEBUG;
    /**
     * 文件log格式
     */
    private static String fileLogLayoutPatten = "%d{yyyy-MM-dd HH:mm:ss,SSS} [%-5p][%c@%t] - %m%n";


    public static void init() {
        File logDir = getRootDir();
        File appLogFile = new File(logDir + File.separator, LOG_FILE_NAME);
        if(appLogFile.exists()){
            addFileLogger(appLogFile.getPath());
        }
        addLogCatLogger();
    }

    /**
     * @return
     */
    private static Logger getRootLogger() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(rootLevel);
        return rootLogger;
    }

    /**
     * 添加文件日志管理
     *
     * @param logfile
     */
    private static void addFileLogger(String logfile) {
        try {
            DailyRollingFileAppender appender = new DailyRollingFileAppender(new PatternLayout(fileLogLayoutPatten), logfile, "yyyy-MM-dd'.log'");
            appender.setEncoding("UTF-8");
            LevelRangeFilter f = new LevelRangeFilter();
            f.setLevelMin(fileLevel);
            appender.addFilter(f);
            Logger rootLogger = getRootLogger();
            rootLogger.addAppender(appender);
            Log.i("Log4jConfigurator", "Log file " + logfile + " created");
        } catch (IOException e) {
            Log.e("Log4jConfigurator", "Failed to create log file " + logfile, e);
        }
    }

    /**
     * 添加LogCat的日志(控制台)
     */
    private static void addLogCatLogger() {
        LogCatAppender logCatAppender = new LogCatAppender(new PatternLayout());
        LevelRangeFilter f = new LevelRangeFilter();
        f.setLevelMin(logcatLevel);
        logCatAppender.addFilter(f);
        Logger rootLogger = getRootLogger();
        rootLogger.addAppender(logCatAppender);
    }

    /**
     * 移除LOG
     */
    public static void destoryLogger() {
        getRootLogger().removeAllAppenders();
    }

    private static boolean isSdcardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static File getRootDir(){
        File rootWorkDirFile = new File(Environment.getExternalStorageState() + File.separator + LOG_FILE_PATH);
        if (!rootWorkDirFile.exists()) {
            rootWorkDirFile.mkdirs();
        }
        return rootWorkDirFile;
    }

}
