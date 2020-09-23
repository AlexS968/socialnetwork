package main.repository;

import main.model.Friendship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {

  @Query(nativeQuery = true, value =
      "SELECT friendship FROM Friendship friendship "
      + "JOIN Person person ON friendship.srcPersonId = person.id AND friendship.dstPersonId = person.id "
      + "WHERE "
      + "(friendship.srcPersonId = :srcPersonId and friendship.dstPersonId = :dstPersonId) "
      + "OR (friendship.dstPersonId = :srcPersonId and friendship.srcPersonId = :dstPersonId)")
  Friendship findFriendshipByPersonsId(
      @Param("srcPersonId") Integer srcPersonId,
      @Param("dstPersonId") Integer dstPersonId);

}
