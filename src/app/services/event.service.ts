import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment.prod';
import {Event} from '../models/event.model';
import {Observable} from 'rxjs';

const SERVER_URL = 'http://localhost:9000';
const EVENT_API = '/events/';
@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient) { }

  createEvent(event: Event) {
    return this.http.post(SERVER_URL + EVENT_API, event);
  }
  getEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(SERVER_URL + EVENT_API);
  }
  getEventById(id: string) {
    return this.http.get<Event>(SERVER_URL + EVENT_API + `/${id}`);
  }
  updateEvent(event: Event) {
    return this.http.put(SERVER_URL + EVENT_API, event);
  }
  deleteEvent(id: string) {
    return this.http.delete(SERVER_URL + EVENT_API + `/${id}`, {
      responseType: `text`
    });
  }
}
