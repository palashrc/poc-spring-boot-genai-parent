package com.mt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import java.util.Map;

@Service("openAiService")
public class GENAIOpenAIServiceImpl implements GENAIService {

    private static final Logger LOG = LoggerFactory.getLogger(GENAIOpenAIServiceImpl.class);

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

    @Autowired
    @Qualifier("openAiChatClient")
    private ChatClient chatClient;

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
        Prompt prompt = promptTemplate.create(Map.of("greetingKey", GREETING_MSG, "contentKey", CONTENT_RAG, "promptKey", promptInput));
        return chatClient.prompt(prompt).stream().content().doOnNext(s -> System.out.print(s));
    }

    @Override
    public String generateTextFromImage(String promptTextInput, MultipartFile promptImageInput) {
        return null;
    }

    @Override
    public void generateNewTextFileResource() {}

    @Override
    public void generateNewPdfFileResource() {}
}
