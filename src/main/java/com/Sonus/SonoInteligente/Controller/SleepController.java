package com.Sonus.SonoInteligente.Controller;

import com.Sonus.SonoInteligente.DTO.UserData;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/sleep")
public class SleepController {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private final String apiKey = "AIzaSyDEP0ECT9U7Z2MBzaGIM3VH4n35cH_Iiww"; // Substitua pela sua chave de API

    public SleepController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/generate-prompt")
    public ResponseEntity<String> generatePrompt(@RequestBody UserData userData) {
        try {
            // Monta o prompt com os dados do usuário
            String prompt = String.format(
                    "Gere um plano de sono personalizado com base nos seguintes dados: " +
                            "Hora de dormir: %s, Hora de acordar: %s, Dificuldades: %s, " +
                            "Qualidade do sono: %d, Nível de estresse: %d, Usa medicamentos: %b, Observações: %s",
                    userData.getBedtime(),
                    userData.getWakeupTime(),
                    userData.getDifficulties(),
                    userData.getSleepQuality(),
                    userData.getStressLevel(),
                    userData.isUsesMedication(),
                    userData.getSleepNotes()
            );

            // Monta o corpo da requisição para o Gemini AI
            String requestBody = String.format(
                    "{ \"contents\": [{ \"parts\": [{ \"text\": \"%s\" }] }] }",
                    prompt
            );

            // Configura os headers da requisição
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey);

            // Envia a requisição para o Gemini AI
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<com.Sonus.SonoInteligente.Controller.GeminiResponse> response = restTemplate.exchange(
                    apiUrl + "?key=" + apiKey,
                    HttpMethod.POST,
                    requestEntity,
                    GeminiResponse.class
            );

            // Extrai o texto gerado da resposta
            if (response.getBody() != null &&
                    response.getBody().getCandidates() != null &&
                    !response.getBody().getCandidates().isEmpty() &&
                    response.getBody().getCandidates().get(0).getContent() != null &&
                    !response.getBody().getCandidates().get(0).getContent().getParts().isEmpty()) {

                String generatedText = response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText();
                return ResponseEntity.ok(generatedText);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Resposta inválida da API.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o prompt: " + e.getMessage());
        }
    }
}