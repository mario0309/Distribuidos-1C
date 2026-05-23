package com.sistemasdistr.basico.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class FlaskApiService {

    private final RestTemplate restTemplate;

    public FlaskApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String callHealth() {
        return safeCall("http://localhost:5000/api/health");
    }

    public String callFileTest(String mode) {
        return safeCall("http://localhost:5000/api/tests/file?mode=" + mode);
    }

    public String callDbTest(String mode) {
        return safeCall("http://localhost:5000/api/tests/db?mode=" + mode);
    }
    
    public String callPokemonTest(String mode) {
        return safeCall("http://localhost:5000/api/tests/pokemon?mode=" + mode);
    }
    
    private String safeCall(String url) {
        try {
            return restTemplate.getForObject(url, String.class);

        } catch (HttpClientErrorException.NotFound e) {
            return "{\"ok\": false, \"message\": \"Archivo o recurso no encontrado\"}";

        } catch (HttpClientErrorException e) {
            return "{\"ok\": false, \"message\": \"Error en la petición al API Flask: " + e.getStatusCode() + "\"}";

        } catch (HttpServerErrorException e) {
            return "{\"ok\": false, \"message\": \"Error interno del API Flask: " + e.getStatusCode() + "\"}";

        } catch (ResourceAccessException e) {
            return "{\"ok\": false, \"message\": \"No se puede conectar con el API Flask. Comprueba que está arrancado en el puerto 5000\"}";

        } catch (RestClientException e) {
            return "{\"ok\": false, \"message\": \"Error llamando al API Flask: " + e.getMessage() + "\"}";
        }
    }
}