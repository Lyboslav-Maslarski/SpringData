package _06_FootballBettingDatabase.entities;

public enum Prediction {
    HOME_TEAM_WIN("HOME TEAM WIN"),
    DRAW_GAME("DRAW GAME"),
    AWAY_TEAM_WIN("AWAY TEAM WIN");
    private String value;

    Prediction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
