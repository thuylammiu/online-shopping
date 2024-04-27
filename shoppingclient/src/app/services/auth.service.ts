import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {LoginViewModel} from '../models/login-view-model'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import {SignupViewModel} from '../models/signup-view-model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.finalexamshopApiUrl
  user = new BehaviorSubject<LoginViewModel>(new LoginViewModel());
  currentUserName: any = null;
  constructor(private httpClient: HttpClient) {

   }

   public Login(loginViewModel: LoginViewModel): Observable<any> {
    
    return this.httpClient.post<any>(this.apiUrl + "/signin", loginViewModel, { responseType: "json" })
      .pipe(map(user => {
        if (user) {
          console.log("user login ", user);
          let muuser = new LoginViewModel();
          this.currentUserName = user.username;
          muuser.username = user.username;
          muuser.token = user.accessToken;
          localStorage.setItem("isLogin", "true");
          localStorage.setItem("token", user.accessToken)
          this.user.next(muuser);
        }

        return user.user;
      }));
  }

  public SignUp(signupViewModel: LoginViewModel): Observable<any> {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
  'Access-Control-Allow-Credentials': 'true' });
  let options = { headers: headers };
    return this.httpClient.post<any>(this.apiUrl + "/signup", signupViewModel, options)
      .pipe(map(user => {
        if (user) {
          this.currentUserName = user.userName;
        }
        return user;
      }));
  }

  public signOut () {
    this.currentUserName = null;
    this.user.next(new LoginViewModel());
    localStorage.removeItem("token");

  }
}
