package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends Serializable, DateRange {

    Long getId();

    Type getAffiliationType();

    Set<Address> getAddresses();

    void setSponsor(Person sponsor);
    
    Person getSponsor();

    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);

    Set<Phone> getPhones();

    Set<EmailAddress> getEmailAddresses();

    Set<Url> getUrls();

    Address addAddress();

    Url addUrl();

    EmailAddress addEmailAddress();

    Phone addPhone();

    Set<Leave> getLeaves();

    String getTitle();

    OrganizationalUnit getOrganizationalUnit();

    Campus getCampus();

    String getLocalCode();

    Type getTerminationReason();

    void setTerminationReason(Type reason);

    /**
     * Determine if this role has been terminated for an associated Person
     * @return true if the role is terminated, false otherwise, i.e. the role is active
     */
    boolean isTerminated();
}