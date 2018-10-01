import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Message } from '../message';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-term',
  templateUrl: './term.component.html'
})
export class TermComponent implements OnInit {

  private _term: string;

  messages: Message[];

  constructor( private route: ActivatedRoute, private service: MessageService ) { }

  ngOnInit() {
  	this.term = this.route.snapshot.paramMap.get('term');
  }

  get term(): string {
	return this._term;
  }

  @Input()
  set term(term: string) {
	this._term = term;
    this.getMessages();
  }

  getMessages(): void {
   	this.service.findMessagesByTerm(this.term)
	  .subscribe(messages => {
	   this.messages = messages;
	   console.log(messages);
	});
  }

  onSelect(term: string): void {
    this.term = term;
  }
}
