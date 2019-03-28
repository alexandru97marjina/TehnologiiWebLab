package com.marjina.tw3.services;


import com.marjina.tw3.entities.Event;
import com.marjina.tw3.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> fetchAllEvents(){
        return eventRepository.findAll();
    }

    public Optional <Event> getEventById(UUID id){
        return eventRepository.findById(id);
    }

    public Event addEvent(Event event){
        return eventRepository.save(event);
    }

    public Optional<Boolean> editEvent(Event event){
        return eventRepository.findById(event.getId())
                .map(event1 -> {
                    eventRepository.save(event);
                    return true;
                });
    }

    public Optional<Boolean>deleteEvent(UUID id){
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(optionalEvent.isPresent()){
            eventRepository.deleteById(id);

            return Optional.of(!eventRepository.existsById(id));
        }
        else {
            return Optional.empty();
        }
    }
}
