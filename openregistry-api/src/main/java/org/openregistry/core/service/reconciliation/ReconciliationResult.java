/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.service.reconciliation;

import java.util.List;
import java.io.Serializable;

/**
 * The result of an attempt to determine if a person already exists in the system.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ReconciliationResult extends Serializable {

    /**
     * Enumeration that determines how confident we are about the matching of this new person to an existing person in the
     * system.
     *
     * EXACT - means we are reasonably confident this is the one person.
     * NONE - means we could not find any matches
     * MAYBE - means one or more possible possible matches
     * MISMATCH - means we were given an Identifier which only we can generate but we didn't find it in the Registry
     *
     */
    enum ReconciliationType {EXACT, NONE, MAYBE, MISMATCH}

    /**
     * Returns the type of reconciliation that resulted.
     * 
     * @return the type of reconciliation.  CANNOT be NULL.
     */
    ReconciliationType getReconciliationType();

    /**
     * The list of possible matches.  CANNOT be NULL.  CAN be EMPTY (if ReconciliationType = NONE)
     *
     * @return the list of matches.
     */
    List<PersonMatch> getMatches();

    boolean noPeopleFound();

    boolean personAlreadyExists();

    boolean multiplePeopleFound();

    String getReconciliationFeedback();

    void setReconciliationFeedback(String feedback);

}
