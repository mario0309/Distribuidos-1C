package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}