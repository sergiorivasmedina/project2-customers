package com.bootcamp.customer.controllers;

import com.bootcamp.customer.model.Personal;
import com.bootcamp.customer.services.PersonalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class PersonalController {
    
    @Autowired
    private PersonalService personalService;

    @GetMapping(value = "/personal")
    public @ResponseBody Flux<Personal> getAllPersonal() {
        // list all data in personal collection
        return personalService.findAll();
    }

    @PostMapping(value = "/personal/new")
    public Mono<Personal> newPersonal(@RequestBody Personal newPersonal) {
        // adding a new personal to the collection
        return personalService.save(newPersonal);
    }

    @PutMapping(value = "/personal/{personalId}")
    public Mono <ResponseEntity<Personal>> updatePersonal(@PathVariable(name = "PersonalId") String PersonalId, @RequestBody Personal personal) {
        return personalService.findById(PersonalId)
            .flatMap(existingPersonal -> {
                return personalService.save(personal);
            })
            .map(updatePersonal -> new ResponseEntity<>(updatePersonal, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/personal/{personalId}")
    public Mono<ResponseEntity<Void>> deletePersonal(@PathVariable(name = "personalId") String personalId) {
        return personalService.findById(personalId)
            .flatMap(existingPersonal ->
                personalService.delete(existingPersonal)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}