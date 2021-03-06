package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public class PlayerDAOHibernate implements PlayerDAO {
  private EntityManager entityManager;

  //----- Constructors -----//
  @Autowired
  public PlayerDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  //----- Interface Methods -----//
  @Override
  @Transactional
  public Player create(Player player) {
    Session session = entityManager.unwrap(Session.class);
    session.save(player);
    return player;
  }

  @Override
  public List<Player> getAll() {
    Session session = entityManager.unwrap(Session.class);
    Query<Player> query = session.createQuery("from Player", Player.class);
    return query.getResultList();
  }

  @Override
  public Player getByName(String name) {
    if (name == null) {
      return null;
    }

    Session session = entityManager.unwrap(Session.class);
    return session.byNaturalId(Player.class)
        .using("name", name)
        .load();
  }

  @Override
  public Player getById(long id) {
    if (id == 0) {
      return null;
    }

    Session session = entityManager.unwrap(Session.class);
    return session.get(Player.class, id);
  }

  @Override
  public Player get(Player player) {
    Player actualPlayer = getById(player.getId());

    if (actualPlayer == null) {
      return getByName(player.getName());
    } else {
      return actualPlayer;
    }
  }

  @Override
  public Set<Player> getFriends(Player player) {
    player = ManagedEntityHelper.getManaged(player, entityManager);

    if (player == null) {
      return null;
    }
    return player.getFriends();
  }

  @Override
  @Transactional
  public Player addFriend(Player player, Player newFriend) {
    Player actualPlayer = get(player);
    Player actualFriend = get(newFriend);

    if (actualPlayer != null && actualFriend != null) {
      Session session = entityManager.unwrap(Session.class);
      actualPlayer.addFriend(actualFriend);
      session.save(actualPlayer);

      return actualFriend;
    }
    return null;
  }

  @Override
  public List<Player> searchPlayersByString(String searchString) {

    String searchPatternString = "%" + searchString + "%";

    Session session = entityManager.unwrap(Session.class);
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Player> playerCriteriaQuery = criteriaBuilder.createQuery(Player.class);

    Root<Player> root = playerCriteriaQuery.from(Player.class);
    playerCriteriaQuery.select(root).where(criteriaBuilder.like(root.get("name"), searchPatternString));

    Query<Player> query = session.createQuery(playerCriteriaQuery);
    return query.getResultList();
  }
}
