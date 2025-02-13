package com.Sonus.SonoInteligente.DTO;

import java.util.List;

public class UserData {
    private String bedtime;
    private String wakeupTime;
    private List<String> difficulties; // Alterado para List<String>
    private int sleepQuality;
    private int stressLevel;
    private boolean usesMedication;
    private String medicationDetails;
    private String sleepNotes;

    // Getters e Setters
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

    public List<String> getDifficulties() { // Alterado para List<String>
        return difficulties;
    }

    public void setDifficulties(List<String> difficulties) { // Alterado para List<String>
        this.difficulties = difficulties;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(int sleepQuality) {
        this.sleepQuality = sleepQuality;
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
