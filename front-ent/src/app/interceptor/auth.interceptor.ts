import { AuthenticattionService } from './../service/authenticattion.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticattionService: AuthenticattionService) {}

  intercept(httpRequest: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(httpRequest.url.includes(`${this.authenticattionService.host}/login`))
    {
      return next.handle(httpRequest);
    }
    
    if(httpRequest.url.includes(`${this.authenticattionService.host}/register`))
    {
      return next.handle(httpRequest);
    }

    this.authenticattionService.loadToken();
    const token = this.authenticattionService.getToken();
    const request = httpRequest.clone({ setHeaders: { Authorization: `Bearer ${token}`}});
    return next.handle(httpRequest);
    
  }
}
