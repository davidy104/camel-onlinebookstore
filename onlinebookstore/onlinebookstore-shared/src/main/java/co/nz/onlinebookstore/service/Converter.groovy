package co.nz.onlinebookstore.service

interface Converter<M,V> {

	V toModel(M dto)

	M toDto(V model,Object... loadStrategies)
}
