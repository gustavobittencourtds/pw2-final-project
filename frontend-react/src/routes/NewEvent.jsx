import calendarFetch from "../axios/config";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import "./NewEvent.css";

const NewEvent = () => {
  const navigate = useNavigate();

  const [summary, setSummary] = useState();
  const [location, setLocation] = useState();
  const [description, setDescription] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();

  const createEvent = async (e) => {
    e.preventDefault();
    
    const event = { summary, location, description, startDate, endDate };

    await calendarFetch.post("/insert", {
      summary: summary,
      location: location,
      description: description,
      start: startDate,
      end: endDate,
    });

    navigate("/")
  }

  return (
    <div className='new-event'>
      <h2>Cadastar novo evento:</h2>

      <form onSubmit={ (e)=> createEvent(e) }>
        <div className="field">
          <label htmlFor="summary"></label>
          <input
            type="text"
            id="summary"
            name="summary"
            placeholder="Como se chamará o evento..."
            required
            onChange={ (e)=> setSummary(e.target.value) }
          />
        </div>

        <div className="field">
          <label htmlFor="location"></label>
          <input
            type="text"
            id="location"
            name="location"
            placeholder="Onde acontecerá..."
            required
            onChange={(e) => setLocation(e.target.value)}
          />
        </div>

        <div className="field">
          <label htmlFor="description"></label>
          <textarea
            name="description"
            id="description"
            placeholder="Insira uma descrição..."
            required
            onChange={(e) => setDescription(e.target.value)}
          >
          </textarea>
        </div>

        <div className="field">
          <label htmlFor="start">Início do evento</label>
          <input
            type="datetime-local"
            id="start"
            name="start"
            required
            onChange={(e) => setStartDate(`${e.target.value}:00-00:00`)}
          />
        </div>

        <div className="field">
          <label htmlFor="end">Fim do evento</label>
          <input
            type="datetime-local"
            id="end"
            name="end"
            required
            onChange={(e) => setEndDate(`${e.target.value}:00-00:00`)}
          />
        </div>

        <input type="submit" value="Criar evento" className="btn" />
      </form>
    </div>
  )
}

export default NewEvent