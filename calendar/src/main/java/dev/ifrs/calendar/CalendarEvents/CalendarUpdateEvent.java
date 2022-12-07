package dev.ifrs.calendar.CalendarEvents;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import dev.ifrs.calendar.CalendarQuickstart;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/update")
public class CalendarUpdateEvent extends CalendarQuickstart {

  private static final String APPLICATION_NAME = "Google Calendar API Java - Gustavo Bittencourt dos Santos";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  
  @PUT
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Event updateEvent(@FormParam("summary") String summary, @FormParam("eventId") String eventId) throws IOException, GeneralSecurityException {
    
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        getCredentials(HTTP_TRANSPORT))
        .setApplicationName(APPLICATION_NAME)
        .build();
    
    // Retrieve the event from the API
    Event event = service.events().get("primary", eventId).execute();

    // Make a change
    event.setSummary(summary);

    // Update the event
    Event updatedEvent = service.events().update("primary", event.getId(), event).execute();
    System.out.printf("Evento alteradoooo: %s\n", updatedEvent.getUpdated());

    return updatedEvent;
  }

}
