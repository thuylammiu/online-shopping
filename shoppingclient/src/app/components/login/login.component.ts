import { Component, OnInit } from '@angular/core';

import {AuthService} from '../../services/auth.service'

import myAppConfig from '../../config/my-app-config';
import { Router } from '@angular/router';
import { LoginViewModel } from 'src/app/models/login-view-model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginViewModel: LoginViewModel = new LoginViewModel();
  loginError: string = "";
  isSignUp:boolean=false;

  constructor(private router: Router, private loginService: AuthService) { 
   

  }

  ngOnInit(): void {
    
  }

  signUp(e)
  {
    e.preventDefault();
    this.isSignUp=true;
    return false;
  }

  onSubmit()
  {
    if (this.isSignUp)
      {
        this.onSignUp();
      }
      else
      {
        this.onLoginClick();
      }
  }


  onSignUp()
  {
    localStorage.removeItem("token");
    this.loginService.SignUp(this.loginViewModel).subscribe(
      (response) => {
        console.log(response);
        this.isSignUp=false;
        this.loginViewModel.password="";
        this.loginViewModel.username="";
        this.loginError="";
        //this.router.navigate(['/login']);
      },
      (error) => {
        console.log(error);
        this.loginError = error.error.error + ' ' + error.error.message;
      },
    );
  }

  onLoginClick() {
    
    localStorage.removeItem("token");
    this.loginService.Login(this.loginViewModel).subscribe(
      (response) => {
        console.log(response);
        this.router.navigate(['/']);
      },
      (error) => {
        console.log(error);
        this.loginError = error.error.error + ' ' + error.error.message;
      },
    );
  }

}
