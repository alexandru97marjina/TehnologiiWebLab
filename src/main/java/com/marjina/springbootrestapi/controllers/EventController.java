package com.marjina.springbootrestapi.controllers;

import com.marjina.springbootrestapi.entities.Event;
import com.marjina.springbootrestapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> showAll(){
        return eventService.fetchAllEvents();

    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Event> findById(@PathVariable UUID id){
        return eventService.getEventById(id)
                .map(event -> ResponseEntity.ok().body(event))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event addEvent(@RequestBody Event event){
        return eventService.addEvent(event);
    }
    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody Event event){
        return eventService.editEvent(event)
                .map(edited-> ResponseEntity.ok().body(edited))
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deleteEvent(@PathVariable("id")UUID id){
        return eventService.deleteEvent(id)
                .map(deleted->ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
