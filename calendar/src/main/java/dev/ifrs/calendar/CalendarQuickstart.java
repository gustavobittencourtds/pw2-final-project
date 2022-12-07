package dev.ifrs.calendar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/* class to demonstarte use of Calendar events list API */
@Path("/calendar")
@Singleton
public class CalendarQuickstart {
  private static final String APPLICATION_NAME = "Google Calendar API Java - Gustavo Bittencourt dos Santos";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /**
   * Global instance of the scopes required by this quickstart.
   * If modifying these scopes, delete your previously saved tokens/ folder.
   */
  private static final List<String> SCOPES =
      Collections.singletonList(CalendarScopes.CALENDAR);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    // Load client secrets.
    InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    
    //returns an authorized Credential object.
    return credential;
  }

  // ------------
  // LIST EVENTS
  // ------------
  // @GET
  // @Consumes(MediaType.APPLICATION_JSON)
  // @Produces(MediaType.APPLICATION_JSON)
  // public List<Event> getEvents() throws IOException, GeneralSecurityException {
  //   // String str = "";
  //   // Build a new authorized API client service.
  //   final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  //   Calendar service =
  //       new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
  //           .setApplicationName(APPLICATION_NAME)
  //           .build();

  //   // List the next 10 events from the primary calendar.
  //   DateTime now = new DateTime(System.currentTimeMillis());
  //   Events events = service.events().list("primary")
  //       .setMaxResults(10)
  //       .setTimeMin(now)
  //       .setOrderBy("startTime")
  //       .setSingleEvents(true)
  //       .execute();
  //   List<Event> items = events.getItems();
  //   if (items.isEmpty()) {
  //     // str += "No upcoming events found.";
  //   } else {
  //     // str += "Upcoming events\n";
  //     for (Event event : items) {
  //       DateTime start = event.getStart().getDateTime();
  //       if (start == null) {
  //         start = event.getStart().getDate();
  //       }
  //       // str += event.getSummary() + " : " + start + "\n"; 
  //     }
  //   }

  //   return items;
  // }

  // ------------
  // INSERT EVENT
  // ------------
  // @GET
  // @Consumes(MediaType.APPLICATION_JSON)
  // @Produces(MediaType.APPLICATION_JSON)
  // public Event setEvent() throws IOException, GeneralSecurityException {

  //   final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  //   Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
  //       .setApplicationName(APPLICATION_NAME)
  //       .build();

  //   Event event = new Event()
  //       .setSummary("Bittencourt PW2 - 2022")
  //       .setLocation("Porto Alegre - Rio Grande do Sul - Bairro Santa Tereza")
  //       .setDescription("Testando inserção de novo evento no google calendário");

  //   DateTime startDateTime = new DateTime("2022-12-20T09:00:00-07:00");
  //   EventDateTime start = new EventDateTime()
  //       .setDateTime(startDateTime)
  //       .setTimeZone("America/Sao_Paulo");
  //   event.setStart(start);

  //   DateTime endDateTime = new DateTime("2022-12-24T17:00:00-07:00");
  //   EventDateTime end = new EventDateTime()
  //       .setDateTime(endDateTime)
  //       .setTimeZone("America/Sao_Paulo");
  //   event.setEnd(end);

  //   String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
  //   event.setRecurrence(Arrays.asList(recurrence));

  //   EventAttendee[] attendees = new EventAttendee[] {
  //       new EventAttendee().setEmail("gustavo.bittencourt@example.com"),
  //       new EventAttendee().setEmail("gustavo.santos@example.com"),
  //   };
  //   event.setAttendees(Arrays.asList(attendees));

  //   EventReminder[] reminderOverrides = new EventReminder[] {
  //       new EventReminder().setMethod("email").setMinutes(24 * 60),
  //       new EventReminder().setMethod("popup").setMinutes(10),
  //   };
  //   Event.Reminders reminders = new Event.Reminders()
  //       .setUseDefault(false)
  //       .setOverrides(Arrays.asList(reminderOverrides));
  //   event.setReminders(reminders);

  //   String calendarId = "primary";
  //   event = service.events().insert(calendarId, event).execute();
  //   System.out.printf("Evento criadoooo: %s\n", event.getHtmlLink());

  //   return event;
  // }

  // ------------
  // DELETE EVENT
  // ------------
  // @DELETE
  // @Consumes(MediaType.APPLICATION_JSON)
  // @Produces(MediaType.APPLICATION_JSON)
  // public static void deleteEvent() throws IOException, GeneralSecurityException {

  //     final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  //     Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
  //       .setApplicationName(APPLICATION_NAME)
  //       .build();

  //       String calendarId = "primary";
  //       String eventID = "gbndai5j66rtapfv9guci22fjc_20221221T160000Z";

  //       service.events().delete(calendarId, eventID).execute();
  //       System.out.println("File deleted");
        
  // }
}