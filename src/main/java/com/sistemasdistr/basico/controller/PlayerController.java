package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.model.Player;
import com.sistemasdistr.basico.service.PlayerService;
import com.sistemasdistr.basico.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping("/players")
    public String listPlayers(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "players/list";
    }

    @GetMapping("/players/new")
    public String showCreateForm(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("teams", teamService.findAll());
        return "players/form";
    }

    @PostMapping("/players/save")
    public String savePlayer(@ModelAttribute Player player) {
        playerService.save(player);
        return "redirect:/players";
    }

    @GetMapping("/players/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Player player = playerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + id));
        model.addAttribute("player", player);
        model.addAttribute("teams", teamService.findAll());
        return "players/form";
    }

    @GetMapping("/players/delete/{id}")
    public String deletePlayer(@PathVariable Integer id) {
        playerService.deleteById(id);
        return "redirect:/players";
    }
}