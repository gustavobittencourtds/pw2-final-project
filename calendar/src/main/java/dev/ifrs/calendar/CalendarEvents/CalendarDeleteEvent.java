package dev.ifrs.calendar.CalendarEvents;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;

import dev.ifrs.calendar.CalendarQuickstart;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/remove")
public class CalendarDeleteEvent extends CalendarQuickstart {
  
  private static final String APPLICATION_NAME = "Google Calendar API Java - Gustavo Bittencourt dos Santos";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public static void deleteEvent() throws IOException, GeneralSecurityException {

  final NetHttpTransport HTTP_TRANSPORT =
  GoogleNetHttpTransport.newTrustedTransport();
  Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
  getCredentials(HTTP_TRANSPORT))
  .setApplicationName(APPLICATION_NAME)
  .build();

  String calendarId = "primary";
  String eventID = "gbndai5j66rtapfv9guci22fjc_20221220T160000Z";

  service.events().delete(calendarId, eventID).execute();
  System.out.println("File deleted");

  }
}
