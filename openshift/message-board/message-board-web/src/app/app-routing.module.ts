import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';

import {MessageComponent} from './message/message.component';
import {TagComponent} from './tag/tag.component';
import {TermComponent} from './term/term.component';
import {TimeComponent} from './time/time.component';
import {CreateAccountComponent} from './create-account/create-account.component';

const routes: Routes = [
  { path: '', redirectTo: '/message', pathMatch: 'full' },
  { path: 'message', component: MessageComponent },
  { path: 'message/:userName', component: MessageComponent },
  { path: 'createAccount', component: CreateAccountComponent },
  { path: 'tag', component: TagComponent },
  { path: 'tag/:tag', component: TagComponent },
  { path: 'term', component: TermComponent },
  { path: 'term/:term', component: TermComponent },
  { path: 'time', component: TimeComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
