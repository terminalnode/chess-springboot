package se.newton.sysjg3.chessapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  @NaturalId
  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "password")
  private String password;

  @JsonIgnore
  @OneToMany(mappedBy = "player")
  private List<Token> playerTokens = new ArrayList<>();



  //---- Constructors -----//
  public Player() {
    // Hibernate requires a no-arg constructor.
  }

  public Player(String name, String password) {
    this.name = name;
    this.password = password;
}

  //----- Getters and setters -----//
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Token> getPlayerTokens() {
    return playerTokens;
  }

  public void setPlayerTokens(List<Token> playerTokens) {
    this.playerTokens = playerTokens;
  }
}
