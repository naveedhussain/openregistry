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

package org.openregistry.core.web;

import org.openregistry.core.service.SearchCriteria;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.inject.Named;
import javax.validation.Validation;

/**
 * Bridges the Spring Validation code with the JSR330 validation code.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Named("searchCriteriaValidator")
public final class SearchCriteriaValidator implements Validator {

    public final void validate(final Object o, final Errors errors) {
        final SearchCriteria searchCriteria = (SearchCriteria) o;

        if (StringUtils.hasText(searchCriteria.getIdentifierValue()) ||
            StringUtils.hasText(searchCriteria.getName()) ||
            StringUtils.hasText(searchCriteria.getFamilyName()) ||
            StringUtils.hasText(searchCriteria.getGivenName()) ||
            searchCriteria.getDateOfBirth() != null) {
            return;
        }

        errors.reject("search.criteria.empty");
    }

    public boolean supports(final Class aClass) {
        return aClass.isAssignableFrom(SearchCriteriaValidator.class);
    }
}
