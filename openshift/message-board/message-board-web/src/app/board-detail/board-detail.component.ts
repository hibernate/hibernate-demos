import {Component, OnInit, Input} from '@angular/core';

import {Message} from '../message';
import {MessageService} from '../message.service';
import {WebSocketService} from '../websocket.service';
import {EventService} from '../event.service';

@Component({
  selector: 'app-board-detail',
  templateUrl: './board-detail.component.html'
})
export class BoardDetailComponent implements OnInit {

  private _userName: string;
  messages: Message[];

  constructor(private service: MessageService, private socket: WebSocketService, private eventService: EventService) {}

  ngOnInit() {
    console.log('init BoardDetailComponent');
    this.getMessages();
	this.socket.ws().onmessage = (response) => {
	  console.log('inc ', response);
	  this.eventService.add("Board Update:" + response.data);
	  this.messages = JSON.parse(response.data);
	}
  }

  get userName(): string {
    return this._userName;
  }

  @Input()
  set userName(userName: string) {
    console.log('user changed: ' + this._userName + ' -> ' + userName);
    this._userName = userName;
    this.getMessages();
    this.socket.listenTo(userName);
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
