package com.codecool.restdemo2;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet(name = "dummy endpoint", value = "/hello")
public class HelloResource extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;");
        String json = new Gson().toJson(new Person("Max", "max.mustermann@codecool.com"));
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(json);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // send post instead
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/hello"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // return received result
        response.setContentType("application/json;");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(httpResponse.body());
    }


}
