package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiClient {

    @Value("${GOOGLE_API_KEY}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String generateDescription(String title) {
        String prompt = "Write a short movie description for the film titled: \"" + title + "\".";
        return sendPrompt(prompt);
    }

    private String sendPrompt(String prompt) {
        String jsonRequest = "{ \"contents\": [ { \"parts\": [ { \"text\": " + quote(prompt) + " } ] } ] }";

        RequestBody body = RequestBody.create(
                jsonRequest,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                System.err.println("Gemini API error: " + response.code() + " " + response.message());
                System.err.println("Error body: " + responseBody);
                return "Description not found - API error.";
            }
            return parseDescription(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
            return "Description not found - Exception Error.";
        }
    }

    private String quote(String text) {
        return "\"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private String parseDescription(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode textNode = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.isNull()) {
                return "Description not found.";
            }
            return textNode.asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Description not found.";
        }
    }
}
