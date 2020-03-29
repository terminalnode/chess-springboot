package se.newton.sysjg3.chessapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "password")
  private String password;

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
}
