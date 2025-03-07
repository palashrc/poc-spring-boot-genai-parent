package com.mt.controller;

import com.mt.service.GENAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/genai/ollama/v1")
public class GENAIOllamaController {

	private static final Logger LOG = LoggerFactory.getLogger(GENAIOllamaController.class);

	@Autowired
	@Qualifier("ollamaService")
	private GENAIService genAiService;

	@RequestMapping(value = { "/", "/ping" })
	public String ping() {
		LOG.info("API: /genai/ollama/v1/ping");
		return "Application is up and Running!";
	}

	@RequestMapping(value = "/text-to-text", method = RequestMethod.GET)
	public String generateTextFromText(@RequestParam("prompt") String promptInput) {
		LOG.info("API: /genai/ollama/v1/text-to-text");
		return genAiService.generateTextFromText(promptInput);
	}

	@RequestMapping(value = "/text-to-text/stream", method = RequestMethod.GET)
	public Flux<String> generateTextFromTextStream(@RequestParam("prompt") String promptInput) {
		LOG.info("API: /genai/ollama/v1/text-to-text/stream");
		return genAiService.generateTextFromTextStream(promptInput);
	}

	@RequestMapping(value = "/text-to-text/stream/rag", method = RequestMethod.GET)
	public Flux<String> generateTextFromTextStreamRAG(@RequestParam("prompt") String promptInput) {
		LOG.info("API: /genai/ollama/v1/text-to-text/stream/rag");
		return genAiService.generateTextFromTextStreamRAG(promptInput);
	}

	@RequestMapping(value = "/resource/textfile/new", method = RequestMethod.GET)
	public void generateNewTextFileResource() {
		LOG.info("API: /genai/ollama/v1/resource/textfile/new");
		genAiService.generateNewTextFileResource();
	}

	@RequestMapping(value = "/resource/pdffile/new", method = RequestMethod.GET)
	public void generateNewPdfFileResource() {
		LOG.info("API: /genai/ollama/v1/resource/pdffile/new");
		genAiService.generateNewPdfFileResource();
	}
}
