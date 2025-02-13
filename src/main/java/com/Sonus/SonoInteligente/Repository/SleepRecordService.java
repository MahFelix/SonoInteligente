package com.Sonus.SonoInteligente.Repository;

import com.Sonus.SonoInteligente.Model.SleepRecord;
import com.Sonus.SonoInteligente.Repository.SleepRecordRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SleepRecordService {

    private final SleepRecordRepository sleepRecordRepository;

    public SleepRecordService(SleepRecordRepository sleepRecordRepository) {
        this.sleepRecordRepository = sleepRecordRepository;
    }

    // Salva um novo registro de sono
    public SleepRecord saveSleepRecord(SleepRecord sleepRecord) {
        // Validação dos dados
        if (sleepRecord.getBedtime() == null || sleepRecord.getWakeupTime() == null) {
            throw new IllegalArgumentException("Hora de dormir e hora de acordar são obrigatórias.");
        }

        // Define a data atual se não for fornecida
        if (sleepRecord.getDate() == null) {
            sleepRecord.setDate(LocalDate.now());
        }

        try {
            return sleepRecordRepository.save(sleepRecord);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar o registro de sono no banco de dados.", e);
        }
    }

    // Retorna todos os registros de sono ordenados por data (do mais recente para o mais antigo)
    public List<SleepRecord> getAllSleepRecords() {
        try {
            return sleepRecordRepository.findAllByOrderByDateDesc();
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar o histórico de sono.", e);
        }
    }
}