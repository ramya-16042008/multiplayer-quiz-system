package com.cipherx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cipherx.model.Player;
import com.cipherx.repository.ParticipantRepository;

@Service
public class PlayerService {

    private final ParticipantRepository participantRepository;

    public PlayerService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Player> getAllPlayers() {
        return participantRepository.findAll();
    }

    public Optional<Player> getPlayerById(int playerId) {
        return participantRepository.findById(playerId);
    }

    public Optional<Player> getPlayerByUsername(String username) {
        return participantRepository.findByUsername(username);
    }

    public Player createPlayer(Player player) {
        return participantRepository.save(player);
    }

    public boolean playerExists(String username) {
        return participantRepository.existsByUsername(username);
    }
}