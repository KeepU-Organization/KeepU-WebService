package com.keepu.webAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException; // Importación correcta
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class PayPalService {
    private final String clientId="Aal_O-XPKrKqiK5u8yLr1Sy00Vx2zKR4OyOrPGgIRQpcLCFF4tw5uO80c169Uj73VU4dwcRh9zaWwBOD";
    private final String Secret="ENIalJjny0-2Znf__HlLHWykn81BY-WRXZsAUxW6zxcEcO-pPx_sKXp2Fe__fLBpFzVUC9tGsOg_4c0C";

    public String getAccessToken() throws IOException, InterruptedException, JsonProcessingException {
        String auth = clientId + ":" + Secret;

        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-m.sandbox.paypal.com/v1/oauth2/token"))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Navegación segura
        JsonNode tokenNode = jsonNode.path("access_token");
        if (tokenNode.isMissingNode()) {
            throw new RuntimeException("No se pudo obtener el token de acceso. Respuesta: " + response.body());
        }

        return tokenNode.asText();
    }

    public BigDecimal captureOrder(String accessToken, String orderId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/capture")) // Corregida la URL
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Verificamos si hay error
        if (jsonNode.has("error")) {
            String errorMessage = jsonNode.path("error").path("message").asText("Error desconocido");
            throw new RuntimeException("Error en la captura: " + errorMessage);
        }

        // Verificamos el estado
        JsonNode statusNode = jsonNode.path("status");
        if (!statusNode.isMissingNode()) {
            String status = statusNode.asText();
            if (!"COMPLETED".equals(status)) {
                throw new RuntimeException("La captura de la orden falló con estado: " + status);
            }
        }

        // Navegación segura por el árbol JSON
        JsonNode purchaseUnits = jsonNode.path("purchase_units");
        if (purchaseUnits.isArray() && !purchaseUnits.isEmpty()) {
            JsonNode firstUnit = purchaseUnits.get(0);
            JsonNode payments = firstUnit.path("payments");
            JsonNode captures = payments.path("captures");

            if (captures.isArray() && !captures.isEmpty()) {
                JsonNode firstCapture = captures.get(0);
                JsonNode amount = firstCapture.path("amount");
                JsonNode value = amount.path("value");

                if (!value.isMissingNode()) {
                    return new BigDecimal(value.asText());
                }
            }
        }

        throw new RuntimeException("No se pudo extraer el monto de la respuesta de PayPal");
    }

    public String createOrder(String accessToken, String amount) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        String body = mapper.writeValueAsString(Map.of(
                "intent", "CAPTURE",
                "purchase_units", List.of(Map.of("amount", Map.of("currency_code", "USD", "value", amount)))
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-m.sandbox.paypal.com/v2/checkout/orders"))
                .headers("Authorization", "Bearer " + accessToken,
                        "Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar estado HTTP
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Error al crear la orden. Código: " + response.statusCode() +
                    ", Respuesta: " + response.body());
        }

        JsonNode jsonNode = mapper.readTree(response.body());

        // Verificar si hay error
        if (jsonNode.has("error")) {
            String errorMessage = jsonNode.path("error").path("message").asText("Error desconocido");
            throw new RuntimeException("Error al crear la orden: " + errorMessage);
        }

        // Navegación segura
        JsonNode idNode = jsonNode.path("id");
        if (idNode.isMissingNode()) {
            throw new RuntimeException("No se pudo obtener el ID de la orden. Respuesta: " + response.body());
        }

        return idNode.asText();
    }
}