/**
 * 
 */
package edu.nyu.library.primo.plugins;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.perf4j.StopWatch;

import com.exlibris.primo.api.common.IPrimoLogger;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Base abstract class for NYU Plugins.  Should be extended by 
 * all NYU Plugin subclasses in order to provide logging and other
 * central features that all plugins should have available.
 * 
 * @author Scot Dalton
 * 
 */
public abstract class NyuPlugin {
	private List<Logger> loggers;
	private List<IPrimoLogger> primoLoggers;
	private SimpleDateFormat dateFormat;
	private Map<String, StopWatch> stopWatches;
	
	/**
	 * Public constructor takes the subsystem name as an argument.
	 * 
	 * @param name - subsystem name
	 */
	public NyuPlugin(String name) {
		loggers = Lists.newArrayList();
		primoLoggers = Lists.newArrayList();
		registerLogger(Logger.getLogger(name));
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
		stopWatches = Maps.newHashMap();
	}
	
	/**
	 * Register logger with the plugin.
	 */
	public void registerLogger(Logger logger) {
		loggers.add(logger);
	}
	
	/**
	 * Register Primo logger with the plugin.
	 */
	public void registerLogger(IPrimoLogger primoLogger) {
		primoLogger.setClass(this.getClass());
		primoLoggers.add(primoLogger);
	}
	
	/**
	 * Logs errors.
	 * 
	 * @param msg - error message
	 */
	protected void logError(String msg) {
		for (Logger logger: loggers)
			logger.severe(formatMsg(msg));
		for (IPrimoLogger primoLogger: primoLoggers)
			primoLogger.error(formatMsg(msg));
	}
	
	/**
	 * Logs errors.
	 * 
	 * @param msg - error message
	 */
	protected void logError(String msg, Exception e) {
		for (Logger logger: loggers)
			logger.severe(formatMsg(msg));
		for (IPrimoLogger primoLogger: primoLoggers)
			primoLogger.error(formatMsg(msg), e);
	}
	
	/**
	 * Logs warnings.
	 * 
	 * @param msg - warning message
	 */
	protected void logWarning(String msg) {
		for (Logger logger: loggers)
			logger.warning(formatMsg(msg));
		for (IPrimoLogger primoLogger: primoLoggers)
			primoLogger.warn(formatMsg(msg));
	}
	
	/**
	 * Logs informative messages
	 * 
	 * @param msg - informative message
	 */
	protected void logInfo(String msg) {
		for (Logger logger: loggers)
			logger.info(formatMsg(msg));
		for (IPrimoLogger primoLogger: primoLoggers)
			primoLogger.info(formatMsg(msg));
	}
	
	/**
	 * Starts a stopwatch with the given tag
	 * 
	 * @param tag
	 */
	protected void startStopWatch(String tag) {
		if(!stopWatches.containsKey(tag))
			stopWatches.put(tag, new StopWatch(tag));
		stopWatches.get(tag).start();
	}
	
	/**
	 * Stops the stopwatch with the given tag and
	 * logs the elapsed time.
	 * 
	 * @param tag
	 */
	protected void stopStopWatch(String tag) {
		if(!stopWatches.containsKey(tag))
			throw new IllegalArgumentException("The requested StopWatch was never started.");
		StopWatch stopWatch = stopWatches.get(tag);
		stopWatch.stop();
		logInfo("Elapsed time for " + tag +": " + Long.toString(getStopWatchElapsedTime(tag)));
	}
	
	/**
	 * Get the StopWatch start time.
	 * 
	 * @param tag
	 * @return
	 */
	protected long getStopWatchStartTime(String tag) {
		if(!stopWatches.containsKey(tag))
			throw new IllegalArgumentException("The requested StopWatch was never started.");
		return stopWatches.get(tag).getStartTime();
	}
	
	/**
	 * Get the StopWatch elapsed time.
	 * 
	 * @param tag
	 * @return
	 */
	protected long getStopWatchElapsedTime(String tag) {
		if(!stopWatches.containsKey(tag))
			throw new IllegalArgumentException("The requested StopWatch was never started.");
		return stopWatches.get(tag).getElapsedTime();
	}
	
	/**
	 * Returns a formatted message.
	 * 
	 * @param msg
	 * @return
	 */
	private String formatMsg(String msg) {
		String formattedMsg = dateFormat.format(Calendar.getInstance().getTime());
		formattedMsg += " ["+ getClass().getName() + "]";
		formattedMsg += " "+ msg;
		return formattedMsg;
	}
}