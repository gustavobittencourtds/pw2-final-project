package dev.ifrs.calendar.usecase;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import dev.ifrs.calendar.control.CalendarController;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/list")
public class CalendarListAllEvents extends CalendarController{

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Event> getEvents() throws IOException, GeneralSecurityException {
  
    Calendar service = CalendarController.getCalendarService();
    DateTime now = new DateTime(System.currentTimeMillis());

    // Busca eventos
    Events events = service.events().list("primary")
        .setMaxResults(10)
        .setTimeMin(now)
        .setOrderBy("startTime")
        .setSingleEvents(true)
        .execute();

    // Array com todos os eventos
    List<Event> items = events.getItems();
    
    // Exibe infos dos eventos - Terminal
    if (items.isEmpty()) {
      System.out.println("Não há eventos cadastros.");
    } else {
      System.out.println("Próximos eventos: ");
      
      for (Event event : items) {
        DateTime start = event.getStart().getDateTime();
        if (start == null) {
          start = event.getStart().getDate();
        }
        System.out.println(event.getSummary());
      }
    }

    return items;
  }
}
