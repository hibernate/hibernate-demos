import {Component, OnInit, Input} from '@angular/core';

import {AccountService} from '../account.service';
import {User} from '../user';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html'
})
export class CreateAccountComponent implements OnInit {

  @Input()
  user: User = new User();
  creating = false;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
  }

  createAccount(): void {
    this.creating = true;
    this.accountService.createAccount(this.user)
      .subscribe(result => {
          this.user = new User();
          this.creating = false;
      });
  }

  isDisabled(): boolean {
    return !this.user.userName || this.creating;
  }

}
