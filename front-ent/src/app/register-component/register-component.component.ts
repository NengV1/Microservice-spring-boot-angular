import { AuthenticattionService } from './../service/authenticattion.service';
import { Component, OnInit } from '@angular/core';
import { NotificationService } from './../service/notification.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../modal/user';

@Component({
  selector: 'app-register-component',
  templateUrl: './register-component.component.html',
  styleUrls: ['./register-component.component.css']
})
export class RegisterComponentComponent implements OnInit {

  public showLoading: boolean;
  private subscriptions: Subscription[] = [];

  constructor(private router: Router, private authenticattionService: AuthenticattionService,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    if(this.authenticattionService.isLoggIn()){
      this.router.navigateByUrl('/home');
    }
  }

  public onRegister(user: User): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.authenticattionService.register(user).subscribe(
        (response: User) => {
          this.showLoading = false;
          this.sendNotification(NotificationType.SUCCESS, `A new account was created for ${response.firstName}.
          Please check your email for password to log in.`);
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
          this.showLoading = false;
        }
      )
    );
  }

  private sendNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
