package com.Sonus.SonoInteligente.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SleepPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plan;
    private LocalDateTime createdAt;
    private String bedtime;
    private String wakeupTime;
    private String difficulties;
    private int stressLevel;
    private boolean usesMedication;
    private String medicationDetails;
    private String sleepNotes;


    @Column(length = 1000) // Campo problemático
    private String descricao;

    // Construtor padrão (necessário para JPA)
    public SleepPlan() {
    }

    // Construtor com parâmetros
    public SleepPlan(String bedtime, String wakeupTime, String difficulties) {
        this.bedtime = bedtime;
        this.wakeupTime = wakeupTime;
        this.difficulties = difficulties;
        this.createdAt = LocalDateTime.now(); // Define a data e hora atuais
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
    

    public int getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
    }

    public boolean isUsesMedication() {
        return usesMedication;
    }

    public void setUsesMedication(boolean usesMedication) {
        this.usesMedication = usesMedication;
    }

    public String getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    public String getSleepNotes() {
        return sleepNotes;
    }

    public void setSleepNotes(String sleepNotes) {
        this.sleepNotes = sleepNotes;
    }
}