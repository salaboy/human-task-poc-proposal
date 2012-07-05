/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author salaboy
 */
@Entity
public class CompletionBehavior implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    
    
}
