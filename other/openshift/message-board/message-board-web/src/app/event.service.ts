import { Injectable } from '@angular/core';

@Injectable()
export class EventService {

  events: string[] = [];

  add(event: string) {
    this.events.push(event);
  }

  clear(index: number) {
    this.events.splice(index, 1);
  }

}
