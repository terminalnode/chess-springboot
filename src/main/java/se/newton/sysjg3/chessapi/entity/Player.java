package se.newton.sysjg3.chessapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  private long id;

  @NaturalId
  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "password")
  private String password;

  @JsonIgnore
  @OneToMany(
      mappedBy = "player",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  private List<Token> playerTokens;

  @JsonIgnore
  @OneToMany(mappedBy = "challenger", fetch = FetchType.LAZY)
  private List<Challenge> sentChallenges;

  @JsonIgnore
  @OneToMany(mappedBy = "challenged", fetch = FetchType.LAZY)
  private List<Challenge> pendingChallenges;

  @JsonIgnore
  @OneToMany(mappedBy = "blackPlayer", fetch = FetchType.LAZY)
  private List<Game> gamesAsBlack;

  @JsonIgnore
  @OneToMany(mappedBy = "whitePlayer", fetch = FetchType.LAZY)
  private List<Game> gamesAsWhite;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "friend",
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
    token.setPlayer(this);
  }

  @Override
  public String toString() {
    return String.format(
        "<Player id=%s, name=%s>",
        id,
        name
    );
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

  public void setPlayerTokens(List<Token> playerTokens) {
    this.playerTokens = playerTokens;
  }

  public void setFriends(Set<Player> friends) {
    this.friends = friends;
  }

  public void setSentChallenges(List<Challenge> sentChallenges) {
    this.sentChallenges = sentChallenges;
  }

  public void setPendingChallenges(List<Challenge> pendingChallenges) {
    this.pendingChallenges = pendingChallenges;
  }

  public void setGamesAsBlack(List<Game> gamesAsBlack) {
    this.gamesAsBlack = gamesAsBlack;
  }

  public void setGamesAsWhite(List<Game> gamesAsWhite) {
    this.gamesAsWhite = gamesAsWhite;
  }

  //----- Getters -----//
  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public List<Token> getPlayerTokens() {
    return playerTokens;
  }

  public Set<Player> getFriends() {
    return friends;
  }

  public List<Challenge> getSentChallenges() {
    return sentChallenges;
  }

  public List<Challenge> getPendingChallenges() {
    return pendingChallenges;
  }

  public List<Game> getGamesAsBlack() {
    return gamesAsBlack;
  }

  public List<Game> getGamesAsWhite() {
    return gamesAsWhite;
  }

  @JsonIgnore
  public List<Game> getGames() {
    List<Game> gamesAsBlack = getGamesAsBlack();
    List<Game> gamesAsWhite = getGamesAsWhite();
    if (gamesAsWhite == null) {
      return gamesAsBlack;
    } else if (gamesAsBlack == null) {
      return gamesAsWhite;
    }
    gamesAsBlack.addAll(gamesAsWhite);
    return gamesAsBlack;
  }
}
