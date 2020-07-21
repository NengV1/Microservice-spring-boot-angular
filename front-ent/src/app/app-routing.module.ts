import { HomeComponentComponent } from './home-component/home-component.component';
import { RegisterComponentComponent } from './register-component/register-component.component';
import { LoginComponentComponent } from './login-component/login-component.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthenticationGuard } from './guard/authentication.guard';


const routes: Routes = [
  { path: 'login' , component: LoginComponentComponent },
  { path: 'register' , component: RegisterComponentComponent },
  { path: 'home' , component: HomeComponentComponent,canActivate: [AuthenticationGuard] },
  { path: '' , redirectTo:'/login' ,pathMatch:'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
