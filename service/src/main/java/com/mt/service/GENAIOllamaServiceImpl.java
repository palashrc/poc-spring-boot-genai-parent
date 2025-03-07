package com.mt.service;

import com.mt.util.GENAIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("ollamaService")
public class GENAIOllamaServiceImpl implements GENAIService {

    private static final Logger LOG = LoggerFactory.getLogger(GENAIOllamaServiceImpl.class);

    private static final String GREETING_MSG = "Hello! I'm PalRobo.";

    private static final String PROMPT_TEMPLATE_MSG = """
                <INST> You are an AI Assistant who can answer of the question.
                Your answer should start with the greeting provided.
                If you don't know the answer, don't make suggestions just say "Sorry, I don't know".
                Show the details in proper readable format. </INST>
                greeting: {greetingKey}
                input: {promptKey}
                """;

    private static final String PROMPT_TEMPLATE_RAG_MSG = """
                <INST> You are an AI Assistant who can answer of the question.
                Your answer should start with the greeting provided.
                If you don't know the answer, don't make suggestions just say "Sorry, I don't know".
                Show the details in proper readable format. </INST>
                greeting: {greetingKey}
                content: {contentKey}
                input: {promptKey}
                """;

    private static final String CONTENT_RAG = """
            ICC Men's Champions Trophy, formerly called the Mini World Cup.  
            ICC Men's Champions Trophy was Inaugurated in 1998.           
            ICC Men's Champions Trophy's 2025 edition is underway and is being played in ODI format.          
            """;

    @Value("classpath:content-rag.pdf")
    private Resource resource;

    @Autowired
    @Qualifier("ollamaChatClient")
    private ChatClient chatClient;

    @Autowired
    @Qualifier("vectorStoreOllama")
    VectorStore vectorStore;

    @Override
    public String generateTextFromText(String promptInput) {
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_TEMPLATE_MSG);
        Prompt prompt = promptTemplate.create(Map.of("greetingKey", GREETING_MSG, "promptKey", promptInput));
        return chatClient.prompt(prompt).call().content();
    }

    @Override
    public Flux<String> generateTextFromTextStream(String promptInput) {
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_TEMPLATE_MSG);
        Prompt prompt = promptTemplate.create(Map.of("greetingKey", GREETING_MSG, "promptKey", promptInput));
        return chatClient.prompt(prompt).stream().content().doOnNext(s -> System.out.print(s));
    }

    @Override
    public Flux<String> generateTextFromTextStreamRAG(String promptInput) {
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_TEMPLATE_RAG_MSG);
        List<Document> documents = vectorStore.similaritySearch(promptInput);
        Prompt prompt = promptTemplate.create(Map.of("greetingKey", GREETING_MSG, "contentKey", !documents.isEmpty()? GENAIUtils.getContents(documents) : CONTENT_RAG, "promptKey", promptInput));
        return chatClient.prompt(prompt).stream().content().doOnNext(s -> System.out.print(s));
    }

    @Override
    public String generateTextFromImage(String promptTextInput, MultipartFile promptImageInput) {
        return null;
    }

    @Override
    public void generateNewTextFileResource() {
        List<Document> documents = new ArrayList<>();
        TextReader textReader = new TextReader(resource);
        textReader.setCharset(Charset.defaultCharset());
        documents.addAll(textReader.get());
        vectorStore.add(documents);
        LOG.info("Added Documents to VectorStore!");
    }

    @Override
    public void generateNewPdfFileResource() {
        List<Document> documents = new ArrayList<>();
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        documents.addAll(tikaDocumentReader.get());
        vectorStore.add(documents);
        LOG.info("Added Documents to VectorStore!");
    }
}
