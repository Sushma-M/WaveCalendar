/*Copyright (c) 2015-2016 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wavemaker.sampleapps.web.wavecalendar.eventdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.wavemaker.sampleapps.web.wavecalendar.eventdb.Events;
import com.wavemaker.sampleapps.web.wavecalendar.eventdb.service.EventsService;


/**
 * Controller object for domain model class Events.
 * @see Events
 */
@RestController("eventDB.EventsController")
@Api(value = "EventsController", description = "Exposes APIs to work with Events resource.")
@RequestMapping("/eventDB/Events")
public class EventsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @Autowired
	@Qualifier("eventDB.EventsService")
	private EventsService eventsService;

	@ApiOperation(value = "Creates a new Events instance.")
	@RequestMapping(method = RequestMethod.POST)
        @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Events createEvents(@RequestBody Events events) {
		LOGGER.debug("Create Events with information: {}" , events);

		events = eventsService.create(events);
		LOGGER.debug("Created Events with information: {}" , events);

	    return events;
	}


    @ApiOperation(value = "Returns the Events instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Events getEvents(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Events with id: {}" , id);

        Events foundEvents = eventsService.getById(id);
        LOGGER.debug("Events details with id: {}" , foundEvents);

        return foundEvents;
    }

    @ApiOperation(value = "Updates the Events instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Events editEvents(@PathVariable("id") Integer id, @RequestBody Events events) throws EntityNotFoundException {
        LOGGER.debug("Editing Events with id: {}" , events.getId());

        events.setId(id);
        events = eventsService.update(events);
        LOGGER.debug("Events details with id: {}" , events);

        return events;
    }

    @ApiOperation(value = "Deletes the Events instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteEvents(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Events with id: {}" , id);

        Events deletedEvents = eventsService.delete(id);

        return deletedEvents != null;
    }

    /**
     * @deprecated Use {@link #findEvents(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Events instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Events> searchEventsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Events list");
        return eventsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Events instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Events> findEvents(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Events list");
        return eventsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Events instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Events> filterEvents(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Events list");
        return eventsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportEvents(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return eventsService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of Events instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
	@WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countEvents( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Events");
		return eventsService.count(query);
	}


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service EventsService instance
	 */
	protected void setEventsService(EventsService service) {
		this.eventsService = service;
	}

}

