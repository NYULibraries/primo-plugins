/**
 * 
 */
package edu.nyu.libraries.primo.plugins;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * @author Scot Dalton
 * 
 * Base abstract class for NYU Plugins.  Should be extended by 
 * all NYU Plugin subclasses in order to provide logging and other
 * central features that all plugins should have available.
 * 
 */
public abstract class NyuPlugin {
	private Logger logger;
	private SimpleDateFormat dateFormat;
	
	/**
	 * Public constructor takes the subsystem name as an argument.
	 * 
	 * @param name - subsystem name
	 */
	public NyuPlugin(String name) {
		logger = Logger.getLogger(name);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
	}
	
	/**
	 * Logs errors.
	 * 
	 * @param msg - error message
	 */
	protected void logError(String msg) {
		logger.severe(formatMsg(msg));
	}
	
	/**
	 * Logs warnings.
	 * 
	 * @param msg - warning message
	 */
	protected void logWarning(String msg) {
		logger.warning(formatMsg(msg));
	}
	
	/**
	 * Logs informative messages
	 * 
	 * @param msg - informative message
	 */
	protected void logInfo(String msg) {
		logger.info(formatMsg(msg));
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