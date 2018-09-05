import { Component, OnInit, Input } from '@angular/core';
import { NgbDateStruct, NgbTimeStruct, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { Message } from '../message';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-time',
  templateUrl: './time.component.html'
})
export class TimeComponent implements OnInit {

  messages: Message[];

  private _startDate: NgbDateStruct;
  private _endDate: NgbDateStruct;
  private _startTime = {hour: 0, minute: 0, second: 0};
  private _endTime = {hour: 23, minute: 59, second: 59};

  get startDate(): NgbDateStruct {
  	return this._startDate;
  }

  get endDate(): NgbDateStruct {
	return this._endDate;
  }

  get startTime(): NgbTimeStruct {
	return this._startTime;
  }

  get endTime(): NgbTimeStruct {
  	return this._endTime;
  }

  @Input()
  set startDate(startDate: NgbDateStruct) {
    this._startDate = startDate;
    this.updateMessages();
  }

  @Input()
  set endDate(endDate: NgbDateStruct) {
    this._endDate = endDate;
    this.updateMessages();
  }

  @Input()
  set startTime(startTime: NgbTimeStruct) {
    this._startTime = startTime;
    this.updateMessages();
  }

  @Input()
  set endTime(endTime: NgbTimeStruct) {
    this._endTime = endTime;
    this.updateMessages();
  }

  constructor(private calendar: NgbCalendar, private service: MessageService ) {}

  ngOnInit() {
    this._startDate = this.calendar.getToday();
    this._endDate = this.calendar.getToday();
    this.updateMessages();
  }

  updateMessages() : void {
  	this.service.findMessagesByTime(this.startDate, this.endDate, this.startTime, this.endTime)
	  .subscribe(messages => {
	   this.messages = messages;
	});
  }

}
