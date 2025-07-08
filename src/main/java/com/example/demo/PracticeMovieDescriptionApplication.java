package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import org.apache.http.HttpException;
import java.io.IOException;
import java.util.Scanner;


@SpringBootApplication
public class PracticeMovieDescriptionApplication {

	public static void main(String[] args) {

		SpringApplication.run(PracticeMovieDescriptionApplication.class, args);

		Client client = Client.builder()
				.apiKey("")
				.build();

//			Scanner scanner = new Scanner(System.in);
//			System.out.println("Describe the movie 'Top Gun'");
//			String query = scanner.nextLine();
		String query = "Describe the movie Top Gun";
		{
			try {
				GenerateContentResponse response = client.models.generateContent("gemini-2.0-flash-001", query, null);
				System.out.println("Gemini's Movie Description: " + response.text());
			} catch (IOException e) {
				System.err.println("Error calling Gemini API: ");
			} catch (HttpException e) {
				System.err.println("Error Gemini API: ");
			}
				}
	}
}
