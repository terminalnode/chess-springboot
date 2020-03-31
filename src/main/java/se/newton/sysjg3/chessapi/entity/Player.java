package se.newton.sysjg3.chessapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
  private List<Token> playerTokens = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "friends",
      joinColumns = @JoinColumn(name="player_id"),
      inverseJoinColumns = @JoinColumn(name="friend_id")
  )
  private Set<Player> friends;


  //---- Constructors -----//
  public Player() {
    // Hibernate requires a no-arg constructor.
  }

  public Player(String name, String password) {
    this.name = name;
    this.password = password;
}

  //----- Methods -----///
  public void addFriend(Player player) {
    if (friends == null) {
      friends = new HashSet<>();
    }
    friends.add(player);
  }

  public void addToken(Token token) {
    if (playerTokens == null) {
      playerTokens = new ArrayList<>();
    }
    playerTokens.add(token);
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

  public void setFriends(Set<Player> friends) {
    this.friends = friends;
  }

  public Set<Player> getFriends() {
    return friends;
  }
}
