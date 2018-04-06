import {Component, OnInit, Input} from '@angular/core';

import {MessageService} from '../message.service';
import {BoardDetailComponent} from '../board-detail/board-detail.component';

@Component({
  selector: 'app-post-message',
  templateUrl: './post-message.component.html'
})
export class PostMessageComponent implements OnInit {

  @Input()
  text: string;

  constructor(private service: MessageService, private messages: BoardDetailComponent) {}

  ngOnInit() {
  }

  postMessage(): void {
    console.log('Posting message:' + this.text);
    this.service.postMessage(this.text)
      .subscribe(result => {
        this.messages.getMessages();
      });
    this.text = '';
  }

}
