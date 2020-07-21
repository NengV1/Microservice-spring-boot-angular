import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './../modal/user'
import { JwtHelperService } from "@auth0/angular-jwt";



@Injectable({
  providedIn: 'root'
})
export class AuthenticattionService {
  
  public host = environment.apiUrl;
  private token : string;
  private loggedInUsername : any;
  private jwtHelper = new JwtHelperService;
  constructor(private http: HttpClient) { }

public login(user:User): Observable<HttpResponse<User>>{
  return this.http.post<User>(`${this.host}/login`,user,{ observe:'response' });
}

public register(user:User): Observable<User>{
  return this.http.post<User>(`${this.host}/register`,user);
}

public logOut(): void{
  this.token = null;
  this.loggedInUsername = null;
  localStorage.removeItem('user');
  localStorage.removeItem('token');
  localStorage.removeItem('users');
}

public saveToken(token: string): void{
  this.token = token;
  localStorage.setItem('token',token);
}

public addUserToLocalCache(user:User): void{
  localStorage.setItem('user',JSON.stringify(user));
}

public getUserFromLocalCache(): User {
  return JSON.parse(localStorage.getItem('user'));
}

public loadToken(): void{
  this.token = localStorage.getItem('token');
}

public getToken(): string{
  return this.token;
}

public isLoggIn():boolean {
  this.loadToken();
  if(this.token != null && this.token !==''){
    if(this.jwtHelper.decodeToken(this.token).sub != null || ''){
      if(!this.jwtHelper.isTokenExpired(this.token)){
        this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
        return true;
      }
    }
  }else{
    this.logOut();
    return false;
  }
}

}
