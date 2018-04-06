import {Component, OnInit, Input} from '@angular/core';

import {Message} from '../message';
import {MessageService} from '../message.service';

@Component({
  selector: 'app-board-detail',
  templateUrl: './board-detail.component.html'
})
export class BoardDetailComponent implements OnInit {

  private _userName: string;
  messages: Message[];

  constructor(private service: MessageService) {}

  ngOnInit() {
    console.log('init BoardDetailComponent');
    this.getMessages();
  }

  get userName(): string {
    return this._userName;
  }

  @Input()
  set userName(userName: string) {
    console.log('user changed: ' + this._userName + ' -> ' + userName);
    this._userName = userName;
    this.getMessages();
  }

  getMessages(): void {
    this.service.findMessagesByUsername(this.userName)
      .subscribe(messages => this.messages = messages);
  }

  delete(id: number): void {
    this.service.delete(id)
      .subscribe(() => this.getMessages());
  }

}
