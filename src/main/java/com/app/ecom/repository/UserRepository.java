package com.app.ecom.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.ecom.enums.USER_STATUS;
import com.app.ecom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByStatus(USER_STATUS status);

    @Query("SELECT COUNT(u) FROM User u")
    long getTotalUsersCount();

    @Query("SELECT COUNT(u) FROM User u WHERE FUNCTION('YEAR', u.createdAt) = :year AND FUNCTION('MONTH', u.createdAt) = :month")
    Long countUsersByMonth(@Param("year") int year, @Param("month") int month);

    // @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    // Page<User> searchUsers(@Param("search") String search, Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
        "(:search IS NULL OR :search = '' OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) " +
        "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);

}
