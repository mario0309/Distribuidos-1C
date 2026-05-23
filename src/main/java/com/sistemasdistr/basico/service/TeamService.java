package com.sistemasdistr.basico.service;

import com.sistemasdistr.basico.model.Team;
import com.sistemasdistr.basico.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Optional<Team> findById(Integer id) {
        return teamRepository.findById(id);
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public void deleteById(Integer id) {
        teamRepository.deleteById(id);
    }
}