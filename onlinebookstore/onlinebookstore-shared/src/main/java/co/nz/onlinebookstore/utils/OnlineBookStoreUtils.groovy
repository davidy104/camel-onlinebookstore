package co.nz.onlinebookstore.utils

import java.text.DateFormat
import java.text.SimpleDateFormat

class OnlineBookStoreUtils {
	static final Integer DEFAULT_ERROR_LENGTH = 255
	static final String DEFAULT_FORMAT = "yyyy-MM-dd hh:mm:ss"
	static final String DEFAULT_TIME_ZONE = "UTC"

	static Date strToDate(String dateStr) {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE)
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT)
		dateFormat.setTimeZone(tz)
		Date date = dateFormat.parse(dateStr)
		return date
	}

	static Date strToDate(String dateStr,String dateFormatStr) {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE)
		DateFormat dateFormat = new SimpleDateFormat(dateFormatStr)
		dateFormat.setTimeZone(tz)
		Date date = dateFormat.parse(dateStr)
		return date
	}

	static String dateToStr(Date date) {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE)
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT)
		dateFormat.setTimeZone(tz)
		String result = dateFormat.format(date)
		return result
	}

	static String dateToStr(Date date,String dateFormatStr) {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE)
		DateFormat dateFormat = new SimpleDateFormat(dateFormatStr)
		dateFormat.setTimeZone(tz)
		String result = dateFormat.format(date)
		return result
	}

	def static getExceptionInfo = {Throwable error->
		def stackStr
		def errorStr
		def getErrorStack = {Throwable e->
			StringWriter sw = new StringWriter()
			e.printStackTrace(new PrintWriter(sw))
			stackStr = sw.toString()
		}
		getErrorStack(error)
		def stackLength=DEFAULT_ERROR_LENGTH
		if(stackStr.length()<stackLength){
			stackLength = stackStr.length()
		}
		errorStr = stackStr[0..stackLength]
		errorStr
	}
}
