package se.newton.sysjg3.chessapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Entity
@Table(name = "token")
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

  @JsonIgnore
  @Column(name = "createdAt")
  private long createdAt;

  //----- Constructors -----//
  public Token() {
    //No Arg-Constructor required by Hibernate
  }

  public Token(Player player) {
    // Token is a combination of the player name and the current time
    // in milliseconds encoded in Base64.
    byte[] toBeToken = String
      .format(
        "%s %s",
        player.getName(),
        System.currentTimeMillis())
      .getBytes();

    this.tokenString = Base64
        .getEncoder()
        .encodeToString(toBeToken);

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
