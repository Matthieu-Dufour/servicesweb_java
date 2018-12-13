/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lpro.intervenantservice.boundary;

import exception.NotFound;
import java.util.UUID;
import org.lpro.intervenantservice.entity.Projet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dufour76u
 */

@RestController
public class ProjetRepresentation {
    
    private ProjetRessource pr;
    private IntervenantResource ir;

    public ProjetRepresentation(ProjetRessource pr, IntervenantResource ir) {
        this.pr = pr;
        this.ir = ir;
    }
    
    @GetMapping("/intervenants/{id}/projets")
    public ResponseEntity<?> getProjetByIntervenantId(@PathVariable("id") String id) throws NotFound{
        if(!ir.existsById(id)){
            throw new NotFound("Intervenant inexistant");
        }
        return new ResponseEntity<>(pr.findByIntervenantId(id), HttpStatus.OK);
    }
    
    
    @PostMapping("/intervenants/{id}/projets")
    public ResponseEntity<?> ajoutProjet(@PathVariable("id") String id, @RequestBody Projet projet) throws NotFound {
        return ir.findById(id)
                .map(intervenant -> {
                    projet.setId(UUID.randomUUID().toString());
                    projet.setIntervenant(intervenant);
                    pr.save(projet);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }).orElseThrow( () -> new NotFound("Intervenant inexistant"));
    }
    
    
    
    @PutMapping("/intervenants/{intervenantId}/projets/{projetId}")
    public ResponseEntity<?> putMethod(@PathVariable("intervenantId") String intervenantId, @PathVariable("projetId") String projetId, @RequestBody Projet projetUpdated) throws NotFound {
       //Verifier si l'intervenant existe
        
       // modifier l'id du proket pour le projet en parametre
       // sauvegarder le projet
       // traiter le cas où le projet n'existe pas
        
        if(!ir.existsById(intervenantId)){
            throw new NotFound("intervenant inexistant");
        }
        return pr.findById(projetId).map(projet -> {
            projet.setId(projetUpdated.getId());
            pr.save(projet);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseThrow( () -> new NotFound("projet inexistant"));
        
        
    }

                

    
    @DeleteMapping("/intervenants/{intervenantId}/projets/{projetId}")
    public ResponseEntity<?> deleteMethod(@PathVariable("intervenantId") String intervenantId, @PathVariable("projetId") String projetId) throws NotFound {
        
        //vérifier si l'intervenant existe
        
        //supprimer le projet
        
        //traiter le cas où le projet n'existe pas
        
        if(!ir.existsById(intervenantId)) {
            throw new NotFound("Intervenant inexistant");
        }
        
        return pr.findById(projetId)
                .map(projet->{
                    pr.delete(projet);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow(()->new NotFound("Projet inexistant"));
    }
}
