package com.blank.protobufsample

import com.blank.protobufsample.grpc.BookServiceGrpc
import com.blank.protobufsample.grpc.ListBookRequest
import com.blank.protobufsample.grpc.ListBookResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class BookService(grpcService: GrpcService) {
    private val stub = BookServiceGrpc.newBlockingStub(grpcService.createManagedChannel())

    fun getDataBook() : Flow<ListBookResponse> = flow{
        val request = ListBookRequest.newBuilder().apply {
            offset = 20
            limit = 20
        }.build()

        emit(stub.listBook(request))
    }
}