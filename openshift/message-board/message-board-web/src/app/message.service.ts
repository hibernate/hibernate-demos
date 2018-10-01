import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { NgbDateStruct, NgbTimeStruct } from '@ng-bootstrap/ng-bootstrap';

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

  findMessagesByTag(term: string): Observable<Message[]> {
    if (!term || !term.trim()) {
      // if not search term, return empty message array.
      return of([]);
    }
    return this.http.get<Message[]>(`message-service/messages/tag/${term}`)
      .pipe( catchError( this.handleError<Message[]>('findMessagesByTag', []) ) );
  }

  findMessagesByTerm(term: string): Observable<Message[]> {
	if (!term || !term.trim()) {
      // if not search term, return empty message array.
	  return of([]);
	}
	return this.http.get<Message[]>(`message-service/messages/term/${term}`)
	  .pipe( catchError( this.handleError<Message[]>('findMessagesByTerm', []) ) );
  }

  findMessagesByTime(startDate: NgbDateStruct, endDate: NgbDateStruct, startTime: NgbTimeStruct, endTime: NgbTimeStruct): Observable<Message[]> {
    if (!startDate || !endDate || !startTime || !endTime) {
	  return of([]);
    }
    return this.http.get<Message[]>(`message-service/messages/since/${startDate.year}/${startDate.month}/${startDate.day}/${startTime.hour}/${startTime.minute}/${startTime.second}/to/${endDate.year}/${endDate.month}/${endDate.day}/${endTime.hour}/${endTime.minute}/${endTime.second}`)
	  .pipe( catchError( this.handleError<Message[]>('findMessagesByTime', []) ) );
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
