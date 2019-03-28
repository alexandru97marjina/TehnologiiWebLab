package com.marjina.tw3.repositories;

import com.marjina.tw3.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface EventRepository extends JpaRepository<Event,UUID> {
}
