package se.newton.sysjg3.chessapi.entity;

import org.hibernate.annotations.NaturalId;

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

  @NaturalId
  @Column(name="tokenString")
  private String tokenString;

  @ManyToOne(cascade={ CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
  @JoinColumn(name="player_id")
  private Player player;

  @Column(name = "createdAt")
  private long createdAt;

  //----- Constructors -----//
  public Token() {
    //No Arg-Constructor required by Hibernate
    updateCreatedAt();
  }

  public Token(Player player) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date currentDate = new Date();
    String currentDateString = dateFormat.format(currentDate);

    this.tokenString = Integer.toString((player.getName() + currentDateString).hashCode());
    updateCreatedAt();
  }

  //----- Methods -----//
  public void updateCreatedAt() {
    createdAt = System.currentTimeMillis();
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }
}
