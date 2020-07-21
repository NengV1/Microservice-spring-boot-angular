import { NotificationService } from './../service/notification.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticattionService } from '../service/authenticattion.service';
import { NotificationType } from '../enum/notification-type.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
 

constructor(private authenticattionService :AuthenticattionService,private  router: Router,private notificationService: NotificationService){}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    return this.isUserLoggIn();
  }

  private isUserLoggIn(): boolean{
    if(this.authenticattionService.isLoggIn()){
      return true;
    }
    this.router.navigate(['/login'])
    this.notificationService.notify(NotificationType.ERROR,'Please Login'.toUpperCase());
    return false;

  }

  
}
