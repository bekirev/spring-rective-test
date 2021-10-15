package com.example.reactive

import org.springframework.core.io.buffer.DataBuffer

interface RequestHandler {
    suspend fun handle(dataBuffer: DataBuffer): DataBuffer
}
