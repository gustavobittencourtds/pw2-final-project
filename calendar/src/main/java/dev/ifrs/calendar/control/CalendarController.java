package dev.ifrs.calendar.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
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
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Path;

@Path("/calendar")
@Singleton
public class CalendarController {
  
  private static final String APPLICATION_NAME = "Google Calendar API Java - Gustavo Bittencourt dos Santos";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  // CREDENCIAIS
  public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

    // Carrega as chaves do cliente
    InputStream in = CalendarController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }

    //Carrega as chaves para o Google Client baseado nas credenciais do usuário
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Cria o controle/persistência das credenciais do usuário e realiza requisição de autorização
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();

    //Configuração da porta para realização da autenticação do usuário.
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

    //Cria a credencial autorizada e a retorna
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    return credential;
  }

  // SERVICE
  public static Calendar getCalendarService() throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    // Cria o serviço que possibilita a interação com a agenda do usuário (CRUD etc )
    Calendar service = new Calendar
    .Builder(HTTP_TRANSPORT, CalendarController.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
    .setApplicationName(CalendarController.APPLICATION_NAME)
    .build();

    return service;
  }

  public static String getApplicationName() {
    return APPLICATION_NAME;
  }

  public static JsonFactory getJsonFactory() {
    return JSON_FACTORY;
  }
  
}