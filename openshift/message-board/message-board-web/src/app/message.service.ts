import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Message } from './message';
import { EventService } from './event.service';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class MessageService {

  private username: string;

  constructor( private http: HttpClient, private eventService: EventService ) { }

  findMessagesByUsername(term: string): Observable<Message[]> {
    if (!term || !term.trim()) {
      // if not search term, return empty message array.
      return of([]);
    }
    this.username = term;
    return this.http.get<Message[]>(`message-service/messages?username=${term}`)
      .pipe( catchError( this.handleError<Message[]>('findMessagesByUsername', []) ) );
  }

  postMessage(body: string): Observable<any> {
    const message = new Message();
    message.username = this.username;
    message.body = body;
    return this.http.post<Message>('message-service/messages', message, httpOptions)
      .pipe( catchError( this.handleError<any>('postMessage') ) );
  }

  delete(id: number): Observable<any> {
    return this.http.delete<Message>(`message-service/messages/${id}`, httpOptions)
      .pipe( catchError( this.handleError<any>('postMessage') ) );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      const event = `${operation} failed: ${error.error.message}`;
      console.info(event);
      this.eventService.add(event);
      return of(result as T);
    };
  }

}
