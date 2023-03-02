package net.daw.alist.repositories;

import net.daw.alist.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    public void enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.locked = TRUE WHERE u.username = ?1")
    public void banUser (String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.locked = FALSE WHERE u.username = ?1")
    public void unbanUser (String username);

    @Query(value = "SELECT username FROM User u")
    List<String> findAllUsernames();





}
