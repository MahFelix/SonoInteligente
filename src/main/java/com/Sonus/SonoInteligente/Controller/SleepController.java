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
                    "Você é o SONUS, um assistente digital especializado em ajudar pessoas a melhorar a qualidade do sono. " +
                            "Com base nos dados fornecidos pelo usuário, crie um plano de sono personalizado e único, com um tom amigável e profissional. " +
                            "O plano deve incluir dicas práticas, sugestões para melhorar a qualidade do sono, comentários sobre medicamentos (se aplicável) e estratégias para reduzir o estresse. " +
                            "Abaixo estão os dados do usuário:\n\n" +

                            "**Dados do usuário:**\n" +
                            "- Hora de dormir: %s\n" +
                            "- Hora de acordar: %s\n" +
                            "- Dificuldades relatadas: %s\n" +
                            "- Qualidade do sono: %d/10\n" +
                            "- Nível de estresse: %d/10\n" +
                            "- Usa medicamentos: %b\n" +
                            "- Detalhes dos medicamentos: %s\n" +
                            "- Observações adicionais: %s\n\n" +

                            "**Instruções para o SONUS:**\n" +
                            "1. Analise os horários de dormir e acordar do usuário. Calcule a duração do sono e comente se é suficiente ou insuficiente para uma noite de descanso ideal.\n" +
                            "2. Com base nas dificuldades relatadas, forneça 3 dicas personalizadas para ajudar o usuário a superar esses desafios.\n" +
                            "3. Avalie a qualidade do sono e sugira 2 ações práticas para melhorá-la.\n" +
                            "4. Comente sobre o nível de estresse e ofereça 2 estratégias para reduzi-lo, já que o estresse pode afetar diretamente a qualidade do sono.\n" +
                            "5. Se o usuário usa medicamentos, analise os detalhes fornecidos e dê orientações sobre como eles podem impactar o sono (efeitos colaterais, interações, etc.).\n" +
                            "6. Finalize o plano com uma mensagem motivacional, incentivando o usuário a seguir as recomendações.\n\n" +

                            "**Formato esperado:**\n" +
                            "O plano deve ser escrito em primeira pessoa, como se o SONUS estivesse conversando diretamente com o usuário. Use um tom acolhedor e encorajador.\n\n" +

                            "**Exemplo de início:**\n" +
                            "'Olá! Eu sou o SONUS, seu assistente digital para uma noite de sono melhor. Vou criar um plano de sono personalizado exclusivamente para você, com base nas informações que você compartilhou. Vamos lá!'",
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