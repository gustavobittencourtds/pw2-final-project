package dev.ifrs.calendar.usecase;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.services.calendar.Calendar;

import dev.ifrs.calendar.control.CalendarController;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/remove/{eventId}")
public class CalendarDeleteEvent extends CalendarController {
  
  @DELETE
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public static void deleteEvent(@PathParam("eventId") String eventId) throws IOException, GeneralSecurityException {

    Calendar service = CalendarController.getCalendarService();
    String calendarId = "primary";
    String eventID = eventId;

    try {
      service.events().delete(calendarId, eventID).execute();
      System.out.println("Evento deletado com sucesso!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
