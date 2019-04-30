import { Component, OnInit } from '@angular/core';
import {Event} from '../models/event.model';
import {EventService} from '../services/event.service';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {error} from '@angular/compiler/src/util';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {
  events: Event [];
  arrEditEventbtn = [] as Array<boolean>;
  submitted = false;
  loading = false;
  editEventForm: FormGroup;
  addEventBtn = false;
  constructor(private router: Router,
              private route: ActivatedRoute,
              private eventService: EventService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.showEventList();
    this.editEventForm = this.formBuilder.group({
        title: ['', Validators.required],
        city: ['', Validators.required],
        place: ['', Validators.required],
        date: ['', Validators.required]
    });
  }

  showEventList() {
    this.eventService.getEvents().pipe()
        .subscribe(events => {
            this.events = events;
            console.log(JSON.stringify(this.events));
        }
    );
  }
  openEventPage(i: number) {
    this.router.navigate([`/event-page/${this.events[i].id}`]);
  }
  addEvent() {
      this.addEventBtn = this.addEventBtn === false;
  }
  submitAddEvent() {
      this.submitted = true;
      if (this.editEventForm.invalid) {
          return;
      }
      this.loading = true;
      this.eventService.createEvent({
          title: this.editEventForm.get('title').value,
          place: this.editEventForm.get('place').value,
          city: this.editEventForm.get('city').value,
          date: this.editEventForm.get('date').value,
      } as Event).pipe(first()).subscribe(
          data => {
              console.log('data: ' + JSON.stringify(data));
              this.showEventList();
              this.loading = false;
              this.addEventBtn = false;
              this.submitted = false;
          },
          error1 => {
              console.log(error1);
          }
      );
  }
  onCancelAdd() {
      this.addEventBtn = false;
      this.editEventForm.reset();
  }
  editEvent(i: number) {
    console.log(this.events[i].id);
    this.arrEditEventbtn[i] = this.arrEditEventbtn[i] !== true;
    this.editEventForm = this.formBuilder.group({
          title: this.events[i].title,
          city: this.events[i].place,
          place: this.events[i].city,
          date: this.events[i].date
      });
  }
  onSubmitEditEvent(i: number) {
      this.submitted = true;
      if (this.editEventForm.invalid) {
          return;
      }
      this.loading = true;

      const editingEvent = {
          id: this.events[i].id,
          title: this.editEventForm.get('title').value,
          place: this.editEventForm.get('place').value,
          city: this.editEventForm.get('city').value,
          date: this.editEventForm.get('date').value
      } as Event;
      this.eventService.updateEvent(editingEvent).pipe(first()).subscribe(
          () => {
              console.log('Success');
              this.submitted = false;
              this.editEventForm.reset();
              this.arrEditEventbtn[i] = false;
              this.events[i] = editingEvent;
              this.loading = false;
          }, error1 => {
              console.log(error1);
              this.loading = false;
          }
      );
  }
  onCancelEdit(i: number) {
      this.arrEditEventbtn[i] = false;
      this.editEventForm.reset();
  }
  deleteEvent(i: number) {
    if (confirm('Are you sure that you want to delete ' + this.events[i].title + ' presentationId ?')) {
      this.eventService.deleteEvent(this.events[i].id)
          .pipe(first())
          .subscribe(
              data => {
                  console.log(data);
                  this.showEventList();
              },
              error1 => {
                console.log('error: ' + error1);
              }
          );
    }
  }
}
