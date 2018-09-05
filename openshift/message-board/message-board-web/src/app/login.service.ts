import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';



import {User} from './user';

@Injectable()
export class LoginService {

  private loginUrl = 'users';

  private user: User;

  constructor(
    private http: HttpClient) {

  }

  login(userName: String): Observable<User> {
    const url = `account-service/user?username=${userName}`;
    console.log(url);
    return this.http.get<User>(url);
  }


  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  /**
 * Handle Http operation that failed.
 * Let the app continue.
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      return Observable.throw('Username or password is incorrect');
    };
  }
}
