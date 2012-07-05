/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author salaboy
 */
@Entity
public class TaskInstance implements Serializable{
    
    /**
     * Task Id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    private String taskType;
    
    private String name;
    
    private Status status;
    
    @ManyToOne(cascade= CascadeType.ALL)
    private User taskInstantiator;
    
    @ManyToOne(cascade= CascadeType.ALL)
    private User actualOwner;
    
    @Temporal(TemporalType.DATE)
    private Date createdTime;
    
    @ManyToOne(cascade= CascadeType.ALL)
    private User createdBy;
    
    @Temporal(TemporalType.DATE)
    private Date lastModifiedTime;
    
    @ManyToOne(cascade= CascadeType.ALL)
    private User lastModifiedBy;
    
    @Temporal(TemporalType.DATE)
    private Date activationTime;
    
    @Temporal(TemporalType.DATE)
    private Date expirationTime;
    
    private boolean skipable;
    
    private boolean hasPotentialOwners;
    
    private boolean startByTimeExists;
    
    private boolean completeByTimeExists;
    
    /**
     * PresentationTexts should contain the I18N name and Subject
     *  This class is replacing the field defined in the WS-HT Spec
     *      - presentationName
     *      - presentationSubject
     * 
     */
    
    //private PresentationElements presentationTexts;
    
    private boolean renderingMethodExists;
    
    private long outputId;
    
    private boolean hasOutput;
    
    private boolean hasFault;
    
    private boolean hasAttachments;
    
    private boolean hasComments;
    
    private boolean escalated;
    
    /**
     * This optional element is used to search for task instances based on a custom
     *  search criterion.
     */
    private String searchBy;
    
    private String outcome;
    
    /**
     * The TaskParent class represent the owner of this task
     *  This class replace the TaskParentId proposed by the Specification
     *  The taskParent should contain the information of the endpoint that 
     *      needs to be informed about the task lifecycle
     */
    
    private TaskParent taskParent;
    
    private boolean hasSubTasks;
   
    /**
     * Task Version , Technical Field
     */
    @Version
    @Column(name = "OPTLOCK")
    private int  version;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getTaskInstantiator() {
        return taskInstantiator;
    }

    public void setTaskInstantiator(User taskInstantiator) {
        this.taskInstantiator = taskInstantiator;
    }

    
    public User getActualOwner() {
        return actualOwner;
    }

    public void setActualOwner(User actualOwner) {
        this.actualOwner = actualOwner;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isSkipable() {
        return skipable;
    }

    public void setSkipable(boolean skipable) {
        this.skipable = skipable;
    }

    public boolean isHasPotentialOwners() {
        return hasPotentialOwners;
    }

    public void setHasPotentialOwners(boolean hasPotentialOwners) {
        this.hasPotentialOwners = hasPotentialOwners;
    }

    public boolean isStartByTimeExists() {
        return startByTimeExists;
    }

    public void setStartByTimeExists(boolean startByTimeExists) {
        this.startByTimeExists = startByTimeExists;
    }

    public boolean isCompleteByTimeExists() {
        return completeByTimeExists;
    }

    public void setCompleteByTimeExists(boolean completeByTimeExists) {
        this.completeByTimeExists = completeByTimeExists;
    }

//    public PresentationElements getPresentationTexts() {
//        return presentationTexts;
//    }
//
//    public void setPresentationTexts(PresentationElements presentationTexts) {
//        this.presentationTexts = presentationTexts;
//    }

    public boolean isRenderingMethodExists() {
        return renderingMethodExists;
    }

    public void setRenderingMethodExists(boolean renderingMethodExists) {
        this.renderingMethodExists = renderingMethodExists;
    }

    public boolean isHasOutput() {
        return hasOutput;
    }

    public void setHasOutput(boolean hasOutput) {
        this.hasOutput = hasOutput;
    }

    public boolean isHasFault() {
        return hasFault;
    }

    public void setHasFault(boolean hasFault) {
        this.hasFault = hasFault;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public boolean isHasComments() {
        return hasComments;
    }

    public void setHasComments(boolean hasComments) {
        this.hasComments = hasComments;
    }

    public boolean isEscalated() {
        return escalated;
    }

    public void setEscalated(boolean escalated) {
        this.escalated = escalated;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public TaskParent getTaskParent() {
        return taskParent;
    }

    public void setTaskParent(TaskParent taskParent) {
        this.taskParent = taskParent;
    }

    public boolean isHasSubTasks() {
        return hasSubTasks;
    }

    public void setHasSubTasks(boolean hasSubTasks) {
        this.hasSubTasks = hasSubTasks;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getOutputId() {
        return outputId;
    }

    public void setOutputId(long outputId) {
        this.outputId = outputId;
    }
    
    
    
}
