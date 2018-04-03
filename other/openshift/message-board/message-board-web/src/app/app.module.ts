import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { MessageService } from './message.service';
import { AccountService } from './account.service';
import { EventService } from './event.service';

import { AppComponent } from './app.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { BoardDetailComponent } from './board-detail/board-detail.component';
import { PostMessageComponent } from './post-message/post-message.component';
import { MessageComponent } from './message/message.component';
import { EventComponent } from './event/event.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    CreateAccountComponent,
    BoardDetailComponent,
    PostMessageComponent,
    MessageComponent,
    EventComponent
  ],
  providers: [MessageService, AccountService, EventService],
  bootstrap: [AppComponent]
})
export class AppModule {}
