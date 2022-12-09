package dev.ifrs.calendar.usecase;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import dev.ifrs.calendar.control.CalendarController;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/update/{eventId}")
public class CalendarUpdateEvent extends CalendarController {

  @PUT
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Event updateEvent(@FormParam("summary") String summary, @PathParam("eventId") String eventId) throws IOException, GeneralSecurityException {
    
    Calendar service = CalendarController.getCalendarService();
    
    // Realiza a busca pelo evento
    Event event = service.events().get("primary", eventId).execute();

    // Realiza a alteração
    event.setSummary(summary);

    // Recebe o evento alterado e o exibe
    Event updatedEvent = service.events().update("primary", event.getId(), event).execute();
    System.out.printf("Evento alterado: %s\n", updatedEvent.getUpdated());

    return updatedEvent;
  }

}
