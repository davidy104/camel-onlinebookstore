package co.nz.onlinebookstore.data

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.onlinebookstore.utils.BigDecimalAdapter

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["bookId","bookName","isbn"])
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class BookDto implements Serializable{
	@XmlElement
	Long bookId

	@XmlElement
	String bookName

	@XmlElement
	String isbn

	@XmlElement
	Integer pages

	@XmlElement
	String publishDate

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal price

	@XmlElement
	String author

	@XmlElement
	Integer orderAmount

	@XmlElement
	Integer inventoryAmount
}
