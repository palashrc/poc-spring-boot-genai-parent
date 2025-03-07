package com.mt.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

public interface GENAIService {

    String generateTextFromText(String promptInput);

    Flux<String> generateTextFromTextStream(String promptInput);

    String generateTextFromImage(String promptTextInput, MultipartFile promptImageInput);

    Flux<String> generateTextFromTextStreamRAG(String promptInput);

    void generateNewTextFileResource();

    void generateNewPdfFileResource();
}
