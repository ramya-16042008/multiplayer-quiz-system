package com.cipherx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cipherx.model.Player;

public interface ParticipantRepository
        extends JpaRepository<Player, Integer> {

    boolean existsByUsername(String username);

    Optional<Player> findByUsername(String username);

    long count();
}