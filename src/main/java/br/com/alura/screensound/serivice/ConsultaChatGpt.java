package br.com.alura.screensound.serivice;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGpt {
    public static String obterInformacao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("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"));


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("me fale sobre o artista: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
