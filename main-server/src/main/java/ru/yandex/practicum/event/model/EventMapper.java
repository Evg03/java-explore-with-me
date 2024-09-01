package ru.yandex.practicum.event.model;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import ru.yandex.practicum.event.dto.EventDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;

public class EventMapper extends ModelMapper {
    public EventMapper() {
        TypeMap<NewEventDto, Event> newEventDtoEventTypeMap = this.createTypeMap(NewEventDto.class, Event.class);
        newEventDtoEventTypeMap.addMapping(newEventDto -> newEventDto.getLocation().getLat(), Event::setLatitude);
        newEventDtoEventTypeMap.addMapping(newEventDto -> newEventDto.getLocation().getLon(), Event::setLongitude);

        TypeMap<UpdateEventUserRequest, Event> updateEventUserRequestEventTypeMap = this.createTypeMap(UpdateEventUserRequest.class, Event.class);
        updateEventUserRequestEventTypeMap.addMapping(updateEventUserRequest -> updateEventUserRequest.getLocation().getLat(), Event::setLatitude);
        updateEventUserRequestEventTypeMap.addMapping(updateEventUserRequest -> updateEventUserRequest.getLocation().getLon(), Event::setLongitude);

        TypeMap<Event, EventDto> eventEventDtoTypeMap = this.createTypeMap(Event.class, EventDto.class);
        eventEventDtoTypeMap.addMappings(new PropertyMap<Event, EventDto>() {
            @Override
            protected void configure() {
                using(mappingContext -> {
                    Float latitude = ((Event) mappingContext.getSource()).getLatitude();
                    Float longitude = ((Event) mappingContext.getSource()).getLongitude();
                    return new Location(latitude, longitude);
                }).map(source, destination.getLocation());
            }
        });
        this.getConfiguration().setSkipNullEnabled(true);
    }
}
