package _06_FootballBettingDatabase.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "player_statistics")
public class PlayerStatistic implements Serializable {
    //    TODO: ManyToOne or OneToOne ?
    @Id
    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;
    @Id
    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;
    @Column(name = "scored_goals")
    private int scoredGoals;
    @Column(name = "player_assists")
    private int playerAssists;
    @Column(name = "played_minutes")
    private int playedMinutesDuringGame;

    public PlayerStatistic() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public int getPlayerAssists() {
        return playerAssists;
    }

    public void setPlayerAssists(int playerAssists) {
        this.playerAssists = playerAssists;
    }

    public int getPlayedMinutesDuringGame() {
        return playedMinutesDuringGame;
    }

    public void setPlayedMinutesDuringGame(int playedMinutesDuringGame) {
        this.playedMinutesDuringGame = playedMinutesDuringGame;
    }
}
