package se.newton.sysjg3.chessapi.entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name="tokenString")
    private String tokenString;

    @ManyToOne
    @JoinColumn(name="id")
    private Player player;

    //----- Constructors -----//

    //No Arg-Constructor required by Hibernate
    public Token() {}


    public Token(Player player) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateString = dateFormat.format(currentDate);

        this.tokenString = Integer.toString((player.getName() + currentDateString).hashCode());

        this.player = player;
    }

    //----- Getters and Setters -----//

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
