import axios from "axios";

const calendarFetch = axios.create({
  baseURL: "http://localhost:9080/api",
  headers: { 'content-type': 'application/x-www-form-urlencoded' }
});

export default calendarFetch;