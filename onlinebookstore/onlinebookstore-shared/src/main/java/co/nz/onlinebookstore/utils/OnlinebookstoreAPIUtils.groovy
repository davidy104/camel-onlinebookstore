package co.nz.onlinebookstore.utils

import groovy.util.logging.Slf4j

import javax.ws.rs.core.CacheControl
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

import co.nz.onlinebookstore.GenericAPIError
import co.nz.onlinebookstore.NotFoundException


@Slf4j
class OnlinebookstoreAPIUtils {
	static final String UNKNOWN_ERROR_CODE = "Unknown error"

	static final String BAD_REQUEST_CODE = "Request is incorrect"

	def static GenericAPIError errorHandle(Exception e) {
		GenericAPIError result
		Response.Status respStatus = Response.Status.INTERNAL_SERVER_ERROR

		String errorMsg = e.getMessage() ?: UNKNOWN_ERROR_CODE

		String errorCauseStr = OnlineBookStoreUtils.getExceptionInfo(e)
		errorMsg = errorMsg + ':'+errorCauseStr

		if (e instanceof NotFoundException) {
			respStatus = Response.Status.NOT_FOUND;
		}
		result = new GenericAPIError(respStatus:respStatus,errorMessage:errorMsg)
		return result
	}

	static Response buildResponse(Object entity,
			GenericAPIError genericAPIError) {
		Response response
		Response.Status respStatus = Status.OK
		String errorMessage

		if (genericAPIError) {
			respStatus = genericAPIError.respStatus
			errorMessage = genericAPIError.errorMessage
		}

		CacheControl cc = new CacheControl()
		cc.setNoCache(true)

		if (respStatus != Status.OK) {
			errorMessage = errorMessage?: UNKNOWN_ERROR_CODE
			response = Response.status(respStatus).cacheControl(cc)
					.entity(errorMessage).build()
		}
		else {
			response = Response.status(respStatus).cacheControl(cc)
					.entity(entity).build()
		}

		return response
	}
}
