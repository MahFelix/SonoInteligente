package com.Sonus.SonoInteligente.Repository;

import com.Sonus.SonoInteligente.Model.SleepRecord;
import com.Sonus.SonoInteligente.Repository.SleepRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sleep")
public class SleepHistoryController {

    @Autowired
    private SleepRecordService sleepRecordService;

    // Rota para salvar um novo registro de sono
    @PostMapping("/history")
    public ResponseEntity<SleepRecord> saveSleepRecord(@RequestBody SleepRecord sleepRecord) {
        SleepRecord savedRecord = sleepRecordService.saveSleepRecord(sleepRecord);
        return ResponseEntity.ok(savedRecord);
    }

    // Rota para obter todos os registros de sono
    @GetMapping("/history")
    public ResponseEntity<List<SleepRecord>> getAllSleepRecords() {
        List<SleepRecord> records = sleepRecordService.getAllSleepRecords();
        return ResponseEntity.ok(records);
    }
}