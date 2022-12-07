package dev.ifrs.calendar;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;


@WebServlet("/go")
public class Ui extends HttpServlet{
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Client client = ClientBuilder.newClient();
    WebTarget myResource = client.target("http://localhost:9080/api/calendar");
    String response = myResource.request(MediaType.APPLICATION_JSON).get(String.class);
    System.out.println(response);
  }
}
