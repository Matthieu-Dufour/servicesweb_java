/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lpro.intervenantservice.boundary;

import java.util.List;
import org.lpro.intervenantservice.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author dufour76u
 */
public interface ProjetRessource extends JpaRepository<Projet, String> {
    
    List<Projet> findByIntervenantId(String intervenantId);
    
}
