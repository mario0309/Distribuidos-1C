package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.model.Team;
import com.sistemasdistr.basico.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public String listTeams(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "teams/list";
    }

    @GetMapping("/teams/new")
    public String showCreateForm(Model model) {
        model.addAttribute("team", new Team());
        return "teams/form";
    }

    @PostMapping("/teams/save")
    public String saveTeam(@ModelAttribute Team team) {
        teamService.save(team);
        return "redirect:/teams";
    }
}