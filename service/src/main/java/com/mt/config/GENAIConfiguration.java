package com.mt.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GENAIConfiguration {

    @Bean
    public ChatClient geminiChatClient(VertexAiGeminiChatModel model) {
        return ChatClient.create(model);
    }

    @Bean
    public VectorStore vectorStoreGemini(VertexAiTextEmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}
