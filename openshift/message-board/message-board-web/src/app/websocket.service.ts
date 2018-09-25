import { Injectable } from '@angular/core';

@Injectable()
export class WebSocketService {

  private socket;

  constructor() {
  	this.socket = new WebSocket(window.location.origin.replace("http","ws").replace("web","message") + "/message-service/board");
  	console.log("WS created: " + this.socket);
  }

  listenTo(user : String) {
    this.socket.onopen = () => this.socket.send(user);
    if (this.socket.readyState == this.socket.OPEN) {
    	this.socket.send(user);
    }
  }

  ws(): WebSocket {
    return this.socket;
  }

}