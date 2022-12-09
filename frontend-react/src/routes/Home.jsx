import calendarFetch from '../axios/config';
import { useState, useEffect } from 'react';

import "./Home.css";

const Home = () => {

  const [events, setEvents] = useState([]);

  const getEvents = async () => {
      try {
        const response = await calendarFetch.get("/list");
        const data = response.data;

        console.log('response =>', data);

        setEvents(data);

      } catch (error) {
        console.error(error);
      }

  }

  useEffect(() => {
    getEvents();
  }, [])

  return (
    <div className='home'>
      { events.length > 0 &&
        <div className="events">
        
        {events.length === 0 ? (<p>Carregando...</p>) : (
          events.map((event) => (
            <div className="event" key={event.id}>
              <h2>{event.summary}</h2>
              <p>{event.description}</p>
              <button type='button' className='btn'>Ver evento</button>
              <a href={event.htmlLink} target="_blank" className="anchor"></a>
            </div>
          ))
        )}
        </div>
      }
      
    </div>
  )
}

export default Home