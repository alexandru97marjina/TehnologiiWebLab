package com.marjina.tw3.controllers;

import com.marjina.tw3.entities.Event;
import com.marjina.tw3.entities.User;
import com.marjina.tw3.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String showAll(Model model ){
        model.addAttribute("events",eventService.fetchAllEvents());
        return "index";
    }

    @GetMapping("/addForm")
    public String showAddForm(Model model){
        model.addAttribute("event",new Event());
        return "add-event";
    }

    @PostMapping("/addEvent")
    public String addEvent(@Valid Event event, BindingResult result, Model model){

        if (result.hasErrors()) {
            return "add-event";
        }

        eventService.addEvent(event);
        model.addAttribute("events",eventService.fetchAllEvents());
        return "index";
    }

    @GetMapping("/editEvent/{id}")
    public String showUpdateFormEvent(@PathVariable("id") String id, Model model){
        Event event = eventService.getEventById(UUID.fromString(id)).orElseThrow(()->new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("event",event);
        return "update-event";

    }

    @PostMapping("/updateEvent/{id}")
    public String updateEvent(@PathVariable("id") String id, @Valid Event event, BindingResult result,Model model){
        if (result.hasErrors()) {
            event.setId(UUID.fromString(id));
            return "update-event";
        }

        eventService.editEvent(event);
        model.addAttribute("events",eventService.fetchAllEvents());
        return "index";
    }

    @GetMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable("id") UUID id, Model model){
        Event event = eventService.getEventById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        eventService.deleteEvent(id);
        model.addAttribute("events",eventService.fetchAllEvents());
        return "index";
    }




}
