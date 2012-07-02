/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author salaboy
 */
@Entity
public class CompletionBehavior {

    @Id
    @GeneratedValue
    private long id;
}
