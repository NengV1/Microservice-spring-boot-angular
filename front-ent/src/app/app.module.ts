import { TopBarComponent } from './top-bar/top-bar.component';
import { NotificationService } from './service/notification.service';
import { NotificationModule } from './notification.module';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { AuthenticattionService } from './service/authenticattion.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthenticationGuard } from './guard/authentication.guard';
import { LoginComponentComponent } from './login-component/login-component.component';
import { RegisterComponentComponent } from './register-component/register-component.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxSpinnerModule } from "ngx-spinner";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponentComponent,
    RegisterComponentComponent,
    TopBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NotificationModule,
    FormsModule,
    BrowserAnimationsModule,
    NgxSpinnerModule
  ],
  providers: [
    NotificationService,
    AuthenticationGuard,
    AuthenticattionService,
    { provide: HTTP_INTERCEPTORS, useClass:AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
