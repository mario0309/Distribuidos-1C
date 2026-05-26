package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.model.Team;
import com.sistemasdistr.basico.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
public class MapController {

    private final TeamService teamService;

    public MapController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/map")
    public String map(@RequestParam(required = false) Integer teamId, Model model) {
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("selectedTeamId", teamId);

        String mapQuery = "Spain";
        String infoMessage = null;
        String errorMessage = null;

        if (teamId != null) {
            Optional<Team> teamOpt = teamService.findById(teamId);

            if (teamOpt.isPresent()) {
                Team team = teamOpt.get();
                model.addAttribute("selectedTeam", team);

                if (team.getCiudad() == null || team.getCiudad().isBlank()) {
                    errorMessage = "El equipo seleccionado no tiene ciudad informada.";
                } else {
                    mapQuery = team.getCiudad();
                    infoMessage = "Mostrando ubicación aproximada de: " + team.getNombre() + " (" + team.getCiudad() + ")";
                }
            } else {
                errorMessage = "No se ha encontrado el equipo seleccionado.";
            }
        }

        String encodedQuery = URLEncoder.encode(mapQuery, StandardCharsets.UTF_8);
        String mapUrl = "https://www.google.com/maps?q=" + encodedQuery + "&z=10&output=embed";

        model.addAttribute("mapUrl", mapUrl);
        model.addAttribute("infoMessage", infoMessage);
        model.addAttribute("errorMessage", errorMessage);

        return "map";
    }
}