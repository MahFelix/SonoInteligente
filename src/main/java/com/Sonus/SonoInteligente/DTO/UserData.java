package com.Sonus.SonoInteligente.DTO;

public class UserData {

    private String bedtime;         // Hora de dormir (ex: "22:30")
    private String wakeupTime;      // Hora de acordar (ex: "06:30")
    private String difficulties;    // Dificuldades relatadas (ex: "Insônia, sonolência diurna")
    private int sleepQuality;       // Qualidade do sono (escala de 1 a 10)
    private int stressLevel;        // Nível de estresse (escala de 1 a 10)
    private boolean usesMedication; // Usa medicamentos para dormir?
    private String sleepNotes;      // Observações adicionais sobre o sono

    // Construtor padrão
    public UserData() {
    }

    // Construtor com todos os campos
    public UserData(String bedtime, String wakeupTime, String difficulties, int sleepQuality, int stressLevel, boolean usesMedication, String sleepNotes) {
        this.bedtime = bedtime;
        this.wakeupTime = wakeupTime;
        this.difficulties = difficulties;
        this.sleepQuality = sleepQuality;
        this.stressLevel = stressLevel;
        this.usesMedication = usesMedication;
        this.sleepNotes = sleepNotes;
    }

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

    public String getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(String difficulties) {
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

    public String getSleepNotes() {
        return sleepNotes;
    }

    public void setSleepNotes(String sleepNotes) {
        this.sleepNotes = sleepNotes;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "bedtime='" + bedtime + '\'' +
                ", wakeupTime='" + wakeupTime + '\'' +
                ", difficulties='" + difficulties + '\'' +
                ", sleepQuality=" + sleepQuality +
                ", stressLevel=" + stressLevel +
                ", usesMedication=" + usesMedication +
                ", sleepNotes='" + sleepNotes + '\'' +
                '}';
    }
}