package com.Sonus.SonoInteligente.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; // Data do registro
    private String bedtime; // Hora de dormir
    private String wakeupTime; // Hora de acordar
    private String difficulties; // Dificuldades relatadas

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBedtime() {
        return bedtime;
    }

    public void setBedtime(String bedtime) {
        this.bedtime = bedtime;
    }

    public String getWakeupTime() {
        return wakeupTime;
    }

    public void setWakeupTime(String wakeupTime) {
        this.wakeupTime = wakeupTime;
    }

    public String getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(String difficulties) {
        this.difficulties = difficulties;
    }
}