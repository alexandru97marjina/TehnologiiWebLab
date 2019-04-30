import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {EventPageComponent} from './event-page/event-page.component';
import {EventsListComponent} from './events-list/events-list.component';
import {EventDetailResolverService} from './services/event-detail-resolver.service';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  { path: 'events-list',
    component: EventsListComponent
  },
  { path: 'event-page/:id',
    component: EventPageComponent,
    resolve: {
      event: EventDetailResolverService,
    }
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
