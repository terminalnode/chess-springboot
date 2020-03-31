package se.newton.sysjg3.chessapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "challenges")
public class Challenge {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @ManyToOne
  @JoinColumn(name = "challenger_id")
  private Player challenger;

  @ManyToOne
  @JoinColumn(name = "challenged_id")
  private Player challenged;

  @Column(name = "created_at")
  private long createdAt;

  //----- Constructors -----//
  public Challenge() {
    // mandatory no-args constructor
    createdAt = System.currentTimeMillis();
  }

  public Challenge(Player challenger, Player challenged) {
    this.challenger = challenger;
    this.challenged = challenged;
    createdAt = System.currentTimeMillis();
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }

  public void setChallenger(Player challenger) {
    this.challenger = challenger;
  }

  public void setChallenged(Player challenged) {
    this.challenged = challenged;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  //----- Getters -----//
  public long getId() {
    return id;
  }

  public Player getChallenger() {
    return challenger;
  }

  public Player getChallenged() {
    return challenged;
  }

  public long getCreatedAt() {
    return createdAt;
  }
}
