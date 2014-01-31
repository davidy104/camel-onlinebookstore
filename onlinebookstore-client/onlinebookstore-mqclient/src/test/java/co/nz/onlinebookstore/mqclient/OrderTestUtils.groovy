package co.nz.onlinebookstore.mqclient

import groovy.xml.MarkupBuilder

class OrderTestUtils {
	/**
	 * <order>
	 * 	<customer name="">
	 * 		<person-id></person-id>
	 * 		<email></email>
	 * 	</customer>
	 * 	<order-list>
	 * 		<book name="" amount=""/>
	 * 		<book name="" amount=""/>
	 * 	</order-list>
	 * </order>
	 *
	 */
	static String orderXmlReq(){
		println "orderXmlReq start:{}"
		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)
		builder.'order'() {
			customer(name:"david") {
				'person-id' "2983209301"
				email "david.yuan@yellow.co.nz"
			}

			"order-list"(){
				book(name:"Camel in action",amount:"80"){}
				book(name:"Spring in action",amount:"60"){}
			}
		}
		xmlStr = sw.toString()
		println "respXmlMarshal end:{} $xmlStr"
		return xmlStr
	}
}
