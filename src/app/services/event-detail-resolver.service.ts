import { Injectable } from '@angular/core';
import {EventService} from './event.service';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {EMPTY, Observable, of} from 'rxjs';
import {mergeMap, take} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EventDetailResolverService implements Resolve<Event> {
  constructor(private es: EventService, private  router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Event> | Observable<any> {

    const id = route.paramMap.get('id');

    return this.es.getEventById(id).pipe(
        take(1),
        mergeMap(event => {
          if (event) {
            return of(event);
          } else {
            this.router.navigate(['']);
            return EMPTY;
          }
        })
    );
  }
}
