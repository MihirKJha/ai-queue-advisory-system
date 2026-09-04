package com.mihirjha.cloudai.queue.ports.outbound;

public interface LLMProvider {

    String generateAdvice(String prompt);
}