/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Diego
 */
public class EntityMain {
    
 private static  final EntityManagerFactory Entidad = Persistence.createEntityManagerFactory("Inventario_ZapateriaPU");

    public EntityMain() {
        
    }

    public static EntityManagerFactory getEntity() {
        return Entidad;
    }
    
    
    
}
