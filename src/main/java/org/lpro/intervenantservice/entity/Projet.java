/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lpro.intervenantservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author dufour76u
 */
@Entity
public class Projet {
    
    @Id
    private String id;
    private String nom;
    private int budget;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intervenantId", nullable = false)
    @JsonIgnore
    private Intervenant intervenant;
    
    Projet(){
        
    }

    public Projet(String nom, int budget) {
        this.nom = nom;
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }
    
    
}
