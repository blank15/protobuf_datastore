package com.blank.protobufsample

import io.grpc.ManagedChannelBuilder
import java.util.concurrent.Executors

class GrpcService {
    fun createManagedChannel() = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
        .executor(Executors.newSingleThreadExecutor())
        .usePlaintext().build()
}