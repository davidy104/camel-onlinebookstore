package co.nz.onlinebookstore

import groovy.transform.ToString

import javax.ws.rs.core.Response

@ToString(includeNames = true, includeFields=true)
class GenericAPIError {
	Response.Status respStatus
	String errorMessage
}
