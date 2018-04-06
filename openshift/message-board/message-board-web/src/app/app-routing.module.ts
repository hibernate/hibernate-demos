import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';

import {MessageComponent} from './message/message.component';
import {CreateAccountComponent} from './create-account/create-account.component';

const routes: Routes = [
  { path: '', redirectTo: '/message', pathMatch: 'full' },
  { path: 'message', component: MessageComponent },
  { path: 'message/:userName', component: MessageComponent },
  { path: 'createAccount', component: CreateAccountComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
