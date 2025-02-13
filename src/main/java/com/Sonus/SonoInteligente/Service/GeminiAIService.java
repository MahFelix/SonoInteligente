package com.Sonus.SonoInteligente.Service;

import com.Sonus.SonoInteligente.DTO.UserData;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiAIService {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"; // Endpoint do Gemini AI
    private final String apiKey = "AIzaSyDEP0ECT9U7Z2MBzaGIM3VH4n35cH_Iiww"; // Substitua pela sua chave de API

    public GeminiAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateSleepPrompt(UserData userData) {
        try {
            // Configura os headers da requisição
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey); // Adiciona a chave de API

            // Monta o corpo da requisição
            String requestBody = String.format(
                    "{ \"contents\": [{ \"parts\": [{ \"text\": \"Gere um plano de sono personalizado com base nos seguintes dados: Hora de dormir: %s, Hora de acordar: %s, Dificuldades: %s, Qualidade do sono: %d, Nível de estresse: %d, Usa medicamentos: %b, Observações: %s\" }] }] }",
                    userData.getBedtime(),
                    userData.getWakeupTime(),
                    userData.getDifficulties(),
                    userData.getSleepQuality(),
                    userData.getStressLevel(),
                    userData.isUsesMedication(),
                    userData.getSleepNotes()
            );

            // Cria a requisição HTTP
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Envia a requisição POST
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl + "?key=" + apiKey, // Adiciona a chave de API como parâmetro de consulta
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Retorna a resposta da API
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o prompt: " + e.getMessage(), e);
        }
    }
}