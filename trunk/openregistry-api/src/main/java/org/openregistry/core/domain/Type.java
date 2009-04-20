package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Type extends Serializable {

    enum Types {TERMINATION, STATUS, ADDRESS, PHONE, EMAIL, CAMPUS}

    Long getId();

    String getDataType();

    String getDescription();

    
}
