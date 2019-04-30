import { Component, OnInit } from '@angular/core';
import {EventService} from '../services/event.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {
  event: Event;
  constructor(
      private es: EventService,
      private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.data.subscribe((data: {
      event: Event,
    }) => {
      this.event = data.event;
      console.log(JSON.stringify(event));
    });
  }

}
