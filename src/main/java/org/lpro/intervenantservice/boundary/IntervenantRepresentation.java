package org.lpro.intervenantservice.boundary;

import exception.NotFound;
import java.util.Optional;
import java.util.UUID;
import org.lpro.intervenantservice.entity.Intervenant;
import org.springframework.hateoas.ExposesResourceFor;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/intervenants", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Intervenant.class)
public class IntervenantRepresentation {
    
    private final IntervenantResource ir;
    
    public IntervenantRepresentation(IntervenantResource ir) {
        this.ir = ir;
    }

    @GetMapping
    public ResponseEntity<?> getAllIntervenants() {
        Iterable<Intervenant> allIntervenants = ir.findAll();
        return new ResponseEntity<>(allIntervenants, HttpStatus.OK);
    }
            
    @PostMapping
    public ResponseEntity<?> postMethod(@RequestBody Intervenant intervenant) {
        intervenant.setId(UUID.randomUUID().toString());
        Intervenant saved = ir.save(intervenant);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(IntervenantRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value="/{intervenantId}")
    public ResponseEntity<Intervenant> getMethodeAvecId(@PathVariable("intervenantId") String id) throws NotFound {
        return Optional.ofNullable(ir.findById(id))
                .filter(Optional::isPresent)
                .map(intervenant -> new ResponseEntity<>(intervenant.get(), HttpStatus.OK))
                .orElseThrow( () -> new NotFound("Intervenant inexistant"));
    }
    
    @PutMapping(value="/{intervenantId}")
    public ResponseEntity<?> putMethod(@PathVariable("intervenantId") String id, @RequestBody Intervenant intervenantUpdated) throws NotFound {
        return ir.findById(id)
                .map(intervenant -> { 
                intervenant.setId(intervenantUpdated.getId());
                ir.save(intervenant);
                       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow( () -> new NotFound("Intervenant inexistant"));
    }
    
    @DeleteMapping(value="/{intervenantId}")
    public ResponseEntity<?> deleteMethod(@PathVariable("intervenantId") String id) throws NotFound {
        return ir.findById(id)
                .map(intervenant ->{
                    ir.delete(intervenant);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow( () -> new NotFound("Intervenant inexistant"));
    }
}
