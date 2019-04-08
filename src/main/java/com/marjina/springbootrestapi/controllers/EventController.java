package com.marjina.springbootrestapi.controllers;

import com.marjina.springbootrestapi.entities.Event;
import com.marjina.springbootrestapi.services.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@Api(value = "Event controller CRUD", description = "Operations to get/add/update/delete events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ApiOperation(value = "View a list of available events", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public List<Event> showAll(){
        return eventService.fetchAllEvents();

    }

    @ApiOperation(value = "Get a event by ID(UUID)", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved event"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Event> findById(@PathVariable UUID id){
        return eventService.getEventById(id)
                .map(event -> ResponseEntity.ok().body(event))
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Add an event", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added event"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public Event addEvent(@RequestBody Event event){
        return eventService.addEvent(event);
    }

    @ApiOperation(value = "Update an event", response = Event.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated event"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody Event event){
        return eventService.editEvent(event)
                .map(edited-> ResponseEntity.ok().body(edited))
                .orElse(ResponseEntity.notFound().build());
    }
    @ApiOperation(value = "Delete an event by ID(UUID)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted event"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deleteEvent(@PathVariable("id")UUID id){
        return eventService.deleteEvent(id)
                .map(deleted->ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
