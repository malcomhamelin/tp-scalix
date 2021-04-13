package model

case class PaginatedResponse[T](page: Int,
                                results: List[T],
                                totalPages: Int,
                                totalResults: Int
                               )
