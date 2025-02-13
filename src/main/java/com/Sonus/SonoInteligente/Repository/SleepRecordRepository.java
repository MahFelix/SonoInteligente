package com.Sonus.SonoInteligente.Repository;

import com.Sonus.SonoInteligente.Model.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SleepRecordRepository extends JpaRepository<SleepRecord, Long> {
    List<SleepRecord> findAllByOrderByDateDesc(); // Retorna todos os registros ordenados por data
}