import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Message } from '../message';
import { Tag } from '../tag';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html'
})
export class TagComponent implements OnInit {

  private _tag: string;

  messages: Message[];

  constructor( private route: ActivatedRoute, private service: MessageService ) { }

  ngOnInit() {
  	this.tag = this.route.snapshot.paramMap.get('tag');
  }

  get tag(): string {
	return this._tag;
  }

  @Input()
  set tag(tag: string) {
    if (tag) {
	  this._tag = tag.replace("#","");
	}
    this.getMessages();
  }

  getMessages(): void {
   	this.service.findMessagesByTag(this.tag)
	  .subscribe(messages => {
	   this.messages = messages;
	   console.log(messages);
	});
  }

  onSelect(tag: Tag): void {
    this.tag = tag.name;
  }
}
