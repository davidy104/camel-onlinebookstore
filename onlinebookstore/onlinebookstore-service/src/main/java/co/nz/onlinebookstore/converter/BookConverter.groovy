package co.nz.onlinebookstore.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.data.BookModel
import co.nz.onlinebookstore.service.Converter
import co.nz.onlinebookstore.utils.OnlineBookStoreUtils;
@Slf4j
@Component("bookConverter")
class BookConverter implements Converter<BookDto,BookModel>{

	@Override
	public BookModel toModel(BookDto dto) {
		log.debug "toModel start:{} $dto"
		BookModel model = new BookModel(bookName:dto.bookName,author:dto.author,price:dto.price)
		if(dto.publishDate){
			model.publishDate = OnlineBookStoreUtils.strToDate(dto.publishDate)
		}
		log.debug "toModel end:{} $model"
		return model
	}

	@Override
	public BookDto toDto(BookModel model, Object... loadStrategies) {
		log.debug "toDto start:{} $dto"
		BookDto dto = new BookDto(bookId:model.bookId,bookName:model.bookName,isbn:model.isbn,
		price:model.price,author:model.author,inventoryAmount:model.inventoryAmount)

		if(model.publishDate){
			dto.publishDate = OnlineBookStoreUtils.dateToStr(model.publishDate)
		}
		log.debug "toDto end:{} $dto"
		return dto
	}
}
