package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.service.FlaskApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Maincontroller {

    private final FlaskApiService flaskApiService;

    public Maincontroller(FlaskApiService flaskApiService) {
        this.flaskApiService = flaskApiService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/pruebas")
    public String pruebas(@RequestParam(required = false) String tipo,
                          @RequestParam(required = false) String mode,
                          Model model) {

        if (tipo != null && mode != null) {
            String resultado = "";

            if ("file".equals(tipo)) {
                resultado = flaskApiService.callFileTest(mode);
            } else if ("db".equals(tipo)) {
                resultado = flaskApiService.callDbTest(mode);
            } else if ("pokemon".equals(tipo)) {
                resultado = flaskApiService.callPokemonTest(mode);
            } else if ("health".equals(tipo)) {
                resultado = flaskApiService.callHealth();
            }

            model.addAttribute("resultado", resultado);
        }

        return "pruebas";
    }
}