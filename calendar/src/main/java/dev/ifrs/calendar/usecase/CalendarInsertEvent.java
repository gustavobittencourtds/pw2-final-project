package dev.ifrs.calendar.usecase;

import java.io.IOException;
import java.security.GeneralSecurityException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import dev.ifrs.calendar.control.CalendarController;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/insert")
public class CalendarInsertEvent extends CalendarController {

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Event setEvent(
    @FormParam("summary") String summary,
    @FormParam("location") String location,
    @FormParam("description") String description,
    @FormParam ("start") DateTime startDateTime,
    @FormParam ("end") DateTime endDateTime
  ) throws IOException, GeneralSecurityException {

    Calendar service = CalendarController.getCalendarService();
    // Insere Titulo, Local e Descrição do evento
    Event event = new Event()
    .setSummary(summary)
    .setLocation(location)
    .setDescription(description);

    //Insere data de início
    EventDateTime start = new EventDateTime()
    .setDateTime(startDateTime)
    .setTimeZone("America/Sao_Paulo");
    event.setStart(start);

    //Insere data de finalização do evento
    EventDateTime end = new EventDateTime()
    .setDateTime(endDateTime)
    .setTimeZone("America/Sao_Paulo");
    event.setEnd(end);

    // Insere o evento no calendário
    String calendarId = "primary";
    event = service.events().insert(calendarId, event).execute();
    System.out.printf("Evento criado: %s\n", event.getHtmlLink());
    return event;
  }
}
