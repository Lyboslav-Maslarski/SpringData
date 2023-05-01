package _06_FootballBettingDatabase.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity(name = "bet_games")
public class BetGame implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;
    @Id
    @OneToOne
    @JoinColumn(name = "bet_id", referencedColumnName = "id")
    private Bet bet;
    @OneToOne
    @JoinColumn(name = "result_prediction", referencedColumnName = "id")
    private ResultPrediction resultPrediction;

    public BetGame() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public ResultPrediction getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
    }

}
