package com.mihirjha.cloudai.queue.ports.outbound;

public interface StorageProvider {

    String store(
            String fileName,
            String contentType,
            byte[] content
    );
}