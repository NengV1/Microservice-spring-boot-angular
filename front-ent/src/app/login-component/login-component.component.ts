import { NotificationType } from './../enum/notification-type.enum';
import { User } from './../modal/user';
import { NotificationService } from './../service/notification.service';
import { AuthenticattionService } from './../service/authenticattion.service';
import { Router } from '@angular/router';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { HeaderType } from '../enum/header-type.enum';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.css']
})
export class LoginComponentComponent implements OnInit,OnDestroy {

  private showLoading : boolean;
  private subscription :Subscription[]=[];
  private title : String;

  constructor(private router:Router,
    private authenticattionService: AuthenticattionService,
    private notificationService: NotificationService,
    private spinner: NgxSpinnerService
    ) { }

  ngOnInit(): void {
    if(this.authenticattionService.isLoggIn()){
      this.router.navigateByUrl('/home');
    }else{
      this.router.navigateByUrl('/login');
    }
  }


  public onLogin(user:User){
    this.showLoading = true;
    console.log(user);
    this.spinner.show();
      this.subscription.push(
        this.authenticattionService.login(user).subscribe(
          (response : HttpResponse<User>) => {
            const token = response.headers.get(HeaderType.JWT_TOKEN);
            this.authenticattionService.saveToken(token);
            this.authenticattionService.addUserToLocalCache(response.body);
            this.spinner.hide();
            this.router.navigateByUrl('/home');
          },( errorResponse: HttpErrorResponse) =>{
            this.spinner.hide();
            console.log(errorResponse);
            this.sendErrorNotification(NotificationType.ERROR,errorResponse.error.message);
          }
        )
      );
    }
    
  sendErrorNotification(notificationType: NotificationType ,message: string) {
    if(message){
      this.notificationService.notify(notificationType,message);
    } else{
      this.notificationService.notify(notificationType,'Please try agian')
    }
  }
  

  ngOnDestroy(): void {
    this.subscription.forEach(sub => sub.unsubscribe());
  }
}
