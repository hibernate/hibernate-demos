import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { AccountService } from '../account.service';
import { User } from '../user';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html'
})
export class MessageComponent implements OnInit {
  users: User[] = [];

  userName: string;

  constructor( private route: ActivatedRoute, private service: AccountService ) { }

  ngOnInit() {
    this.service.findAllUser().subscribe(users => this.users = users);
    this.refreshUserName();
  }

  refreshUserName(): void {
    this.userName = this.route.snapshot.paramMap.get('userName');
  }

  onSelect(user: User): void {
    this.userName = user.userName;
  }

}
