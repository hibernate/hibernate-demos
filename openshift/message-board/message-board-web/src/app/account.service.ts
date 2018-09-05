import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';
import { EventService } from './event.service';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

const url = "account-service/user";

@Injectable()
export class AccountService {

  constructor(private http: HttpClient, private eventService: EventService) { }

  createAccount(user: User): Observable<User> {
  	// remove all spaces from username
  	user.userName = user.userName.replace(/\s/g,'');

    return this.http.post<User>(url, user, httpOptions)
      .pipe(
      	tap( user => this.eventService.add(`Account created: ${user.userName} with id ${user.id}`) ),
      	catchError( this.handleError<any>('Account not created') )
      );
  }

  findAllUser(): Observable<User[]> {
    return this.http.get<User[]>(url + "/all")
      .pipe( catchError( this.handleError<User[]>('findAllUser', []) ) );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      const event = `${operation} - reason: ${error.error.message}`;
      console.info(event);
      this.eventService.add(event);
      return of(result as T);
    };
  }

}
