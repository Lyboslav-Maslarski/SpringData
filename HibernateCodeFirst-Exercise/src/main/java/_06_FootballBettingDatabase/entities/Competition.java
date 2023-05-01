package _06_FootballBettingDatabase.entities;

import javax.persistence.*;

@Entity(name = "competitions")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    private CompetitionType type;
}
