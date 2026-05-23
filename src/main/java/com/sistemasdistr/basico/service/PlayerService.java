package com.sistemasdistr.basico.service;

import com.sistemasdistr.basico.model.Player;
import com.sistemasdistr.basico.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Integer id) {
        return playerRepository.findById(id);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public void deleteById(Integer id) {
        playerRepository.deleteById(id);
    }
}