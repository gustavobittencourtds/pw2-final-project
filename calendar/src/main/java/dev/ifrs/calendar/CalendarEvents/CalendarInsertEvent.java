package dev.ifrs.calendar.CalendarEvents;

import dev.ifrs.calendar.CalendarQuickstart;

import java.io.IOException;
import java.security.GeneralSecurityException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/insert")
public class CalendarInsertEvent extends CalendarQuickstart {

  private static final String APPLICATION_NAME = "Google Calendar API Java - Gustavo Bittencourt dos Santos";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Event
  setEvent(@FormParam("summary") String summary, @FormParam("location") String location,  @FormParam("description") String description) throws IOException, GeneralSecurityException {

  final NetHttpTransport HTTP_TRANSPORT =
  GoogleNetHttpTransport.newTrustedTransport();
  Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
  getCredentials(HTTP_TRANSPORT))
  .setApplicationName(APPLICATION_NAME)
  .build();

  Event event = new Event()
  .setSummary(summary)
  .setLocation(location)
  .setDescription(description);

  // DateTime startDateTime = new DateTime("2022-12-20T09:00:00-07:00");
  // EventDateTime start = new EventDateTime()
  // .setDateTime(startDateTime)
  // .setTimeZone("America/Sao_Paulo");
  // event.setStart(start);

  DateTime endDateTime = new DateTime("2022-12-24T17:00:00-07:00");
  EventDateTime end = new EventDateTime()
  .setDateTime(endDateTime)
  .setTimeZone("America/Sao_Paulo");
  event.setEnd(end);

  // String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=2" };
  // event.setRecurrence(Arrays.asList(recurrence));

  // EventAttendee[] attendees = new EventAttendee[] {
  // new EventAttendee().setEmail("gustavo.bittencourt@example.com"),
  // new EventAttendee().setEmail("gustavo.santos@example.com"),
  // };
  // event.setAttendees(Arrays.asList(attendees));

  // EventReminder[] reminderOverrides = new EventReminder[] {
  // new EventReminder().setMethod("email").setMinutes(24 * 60),
  // new EventReminder().setMethod("popup").setMinutes(10),
  // };
  // Event.Reminders reminders = new Event.Reminders()
  // .setUseDefault(false)
  // .setOverrides(Arrays.asList(reminderOverrides));
  // event.setReminders(reminders);

  String calendarId = "primary";
  event = service.events().insert(calendarId, event).execute();
  System.out.printf("Evento criadoooo: %s\n", event.getHtmlLink());

  return event;
  }
}
