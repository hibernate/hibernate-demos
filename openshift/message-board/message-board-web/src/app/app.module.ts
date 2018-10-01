import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';

import { MessageService } from './message.service';
import { AccountService } from './account.service';
import { EventService } from './event.service';
import { WebSocketService } from './websocket.service';

import { AppComponent } from './app.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { BoardDetailComponent } from './board-detail/board-detail.component';
import { PostMessageComponent } from './post-message/post-message.component';
import { MessageComponent } from './message/message.component';
import { EventComponent } from './event/event.component';
import { TagComponent } from './tag/tag.component';
import { TermComponent } from './term/term.component';
import { TimeComponent } from './time/time.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule
  ],
  declarations: [
    AppComponent,
    CreateAccountComponent,
    BoardDetailComponent,
    PostMessageComponent,
    MessageComponent,
    EventComponent,
    TagComponent,
    TermComponent,
    TimeComponent
  ],
  providers: [MessageService, AccountService, EventService, WebSocketService],
  bootstrap: [AppComponent]
})
export class AppModule {}
