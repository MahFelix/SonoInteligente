package com.Sonus.SonoInteligente.Controller;

import com.Sonus.SonoInteligente.DTO.UserData;
import com.Sonus.SonoInteligente.Model.SleepPlan;
import com.Sonus.SonoInteligente.Repository.SleepPlanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/sleep")
public class SleepController {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private final String apiKey = "AIzaSyDEP0ECT9U7Z2MBzaGIM3VH4n35cH_Iiww"; // Substitua pela sua chave de API

    @Autowired
    private SleepPlanRepository sleepPlanRepository;

    public SleepController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/generate-prompt")
    public ResponseEntity<String> generatePrompt(@Valid @RequestBody UserData userData) {
        try {
            // Monta o prompt com os dados do usuário
            String difficulties = String.join(", ", userData.getDifficulties());
            String medicationDetails = userData.getMedicationDetails() != null ? userData.getMedicationDetails() : "Nenhum detalhe fornecido";
            String sleepNotes = userData.getSleepNotes() != null ? userData.getSleepNotes() : "Nenhuma observação fornecida";

            String prompt = String.format(
                    "Gere um plano de sono personalizado com base nos seguintes dados: " +
                            "Hora de dormir: %s, Hora de acordar: %s, Dificuldades: %s, " +
                            "Qualidade do sono: %d, Nível de estresse: %d, Usa medicamentos: %b, Detalhes dos medicamentos: %s, Observações: %s",
                    userData.getBedtime(),
                    userData.getWakeupTime(),
                    difficulties,
                    userData.getSleepQuality(),
                    userData.getStressLevel(),
                    userData.isUsesMedication(),
                    medicationDetails,
                    sleepNotes
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
            ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                    apiUrl,
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

    @PostMapping("/save")
    public ResponseEntity<String> saveSleepPlan(@RequestBody SleepPlan sleepPlan) {
        try {
            sleepPlanRepository.save(sleepPlan);
            return ResponseEntity.ok("Plano de sono salvo com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o plano de sono: " + e.getMessage());
        }
    }

    @GetMapping("/save/history")
    public ResponseEntity<List<SleepPlan>> getSleepPlans() {
        try {
            // Busca todos os planos de sono no banco de dados
            List<SleepPlan> sleepPlans = sleepPlanRepository.findAll();
            return ResponseEntity.ok(sleepPlans); // Retorna a lista de planos de sono
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSleepPlan(@PathVariable Long id) {
        try {
            sleepPlanRepository.deleteById(id);
            return ResponseEntity.ok("Plano de sono deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o plano de sono: " + e.getMessage());
        }
    }
}