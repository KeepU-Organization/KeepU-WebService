package com.keepu.webAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HuggingFaceService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    private static final String MODEL_URL = "https://api-inference.huggingface.co/models/HuggingFaceH4/zephyr-7b-beta";


    public String infer(String prompt) {
        // Palabras clave ampliadas para temas financieros personales, bancarios y de ahorro
        String[] palabrasClave = {
                "finanzas personales", "ahorro", "presupuesto", "gasto", "inversión", "deuda", "cuenta bancaria",
                "tarjeta de crédito", "tarjeta de débito", "préstamo", "hipoteca", "ingreso", "egreso", "ahorrar", "gastar",
                "dinero", "banco", "transferencia", "saldo", "retiro", "depósito", "interés", "comisión", "cajero automático",
                "pago", "factura", "crédito", "seguro", "impuesto", "renta", "financiamiento", "capital", "dividendo",
                "fondos", "chequera", "movimiento bancario", "extracto", "plazo fijo", "cuenta de ahorro", "cuenta corriente",
                "morosidad", "liquidez", "ahorros", "plan financiero", "educación financiera", "gastos fijos", "gastos variables",
                "billetera", "microcrédito", "microfinanzas", "banca digital", "banca móvil", "banca en línea", "fintech",
                "criptomoneda", "bitcoin", "ethereum", "blockchain", "acciones", "bonos", "fondos mutuos", "cartera de inversión",
                "broker", "corredor de bolsa", "mercado de valores", "bolsa de valores", "rendimiento", "tasa de interés",
                "inflación", "deflación", "recesión", "ahorro programado", "plan de pensiones", "jubilación", "seguro de vida",
                "seguro de auto", "seguro de salud", "prima", "deducible", "beneficiario", "póliza", "riesgo financiero",
                "análisis financiero", "planificación financiera", "meta financiera", "flujo de caja", "balance", "estado de resultados",
                "patrimonio", "activos", "pasivos", "ingreso pasivo", "ingreso activo", "tarifa", "comisión bancaria", "transferencia internacional"
        };
        boolean esFinanzas = false;
        String promptLower = prompt.toLowerCase();
        for (String palabra : palabrasClave) {
            if (promptLower.contains(palabra)) {
                esFinanzas = true;
                break;
            }
        }
        if (!esFinanzas) {
            return "No puedo responder.";
        }

        // Llamada al modelo
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "inputs", "Responde únicamente en español, que sean solo un maximo de 200 palabras y solo si la pregunta es sobre finanzas personales, banca, ahorro o temas similares. Si no, responde: 'No puedo responder.' Pregunta: " + prompt,
                "parameters", Map.of("max_new_tokens", 200)
        );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(MODEL_URL, request, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            if (root.isArray() && root.size() > 0 && root.get(0).has("generated_text")) {
                String respuesta = root.get(0).get("generated_text").asText();
                // Validar que la respuesta también sea relevante
                boolean respuestaValida = false;
                String respuestaLower = respuesta.toLowerCase();
                for (String palabra : palabrasClave) {
                    if (respuestaLower.contains(palabra)) {
                        respuestaValida = true;
                        break;
                    }
                }
                return respuestaValida ? respuesta : "No puedo responder.";
            }
        } catch (Exception e) {
            return "Error procesando la respuesta del modelo.";
        }
        return "No se obtuvo respuesta del modelo.";
    }

}