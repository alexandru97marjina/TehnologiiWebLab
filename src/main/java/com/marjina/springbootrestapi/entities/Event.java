package com.marjina.springbootrestapi.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "Events")
@ApiModel(description = "All details about the Employee. ")
public class Event {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue
    @ApiModelProperty(notes ="The database generated event ID")
    private UUID id;

    @ApiModelProperty(notes ="Title of event")
    private String title;

    @ApiModelProperty(notes ="Host city of event")
    private String city;

    @ApiModelProperty(notes ="Event's place")
    private String place;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(notes ="Event's date")
    private LocalDate date;
}
