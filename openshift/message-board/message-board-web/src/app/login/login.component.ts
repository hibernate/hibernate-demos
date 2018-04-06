import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

import {LoginService} from '../login.service';
import {User} from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = {'id': 0, 'userName': ''};
  loading = false;
  returnUrl: string;

  constructor(
    private loginService: LoginService,
    private route: ActivatedRoute,
    private router: Router ) {
    this.loginService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || 'message';
  }

  login() {
    this.loading = true;
    this.loginService.login(this.user.userName)
      .subscribe(
      data => {
        console.log('login succes');
        this.router.navigate([this.returnUrl]);
      },
      error => {
        console.log('login failed');
        this.loading = false;
      });
  }



  ngOnInit() {
  }

}
