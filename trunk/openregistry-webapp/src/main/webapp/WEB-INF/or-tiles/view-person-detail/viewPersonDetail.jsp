<%--

    Copyright (C) 2009 Jasig, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%--
  Created by IntelliJ IDEA.
  User: Nancy Mond
  Date: Feb 23, 2009
  Time: 4:56:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
jQuery(document).ready(function() {
    $('#activationKeyLink').click(function() {
        $.post('<c:url value="/api/v1/people/${preferredPersonIdentifierType}/${person.primaryIdentifiersByType[preferredPersonIdentifierType].value}/activation" />', {}, function(data, textStatus, XMLHttpRequest) {
            var location = XMLHttpRequest.getResponseHeader('Location');
            var activationKey = location.substring(location.lastIndexOf("/")+1);

            $('#activationKeyDialog').html(activationKey);
            $('#activationKeyDialog').dialog({
                show: 'slide',
			    modal: true,
			    buttons: {
				    Ok: function() {
					    $(this).dialog('close');
				    }
			    }
		    });
        })
    })
});
</script>

<div id="calculated-person">

<%--<div class="padded"><strong><spring:message code="officialName.heading" /></strong> ${person.officialName.longFormattedName}</div>--%>

    <div style="text-align:center;">
        <a href="#" id="activationKeyLink" class="button"><button>Generate New Activation Key</button></a>
    </div>
    <div id="activationKeyDialog" title="Activation Key"></div>

    <table>
        <thead>
        <tr>
            <th colspan="2"><spring:message code="viewCalculatedPerson.heading" /></th>
        </tr>
        </thead>
        <tbody class="zebra">
        <c:forEach var="name" items="${person.names}" varStatus="sorPersonLoopStatus">
            <tr>
                <td>${name.type.description} <spring:message code="nameColumn.label" /></td>
                <td>${name.longFormattedName}</td>
            </tr>
        </c:forEach>
        <tr>
            <td><spring:message code="dateOfBirth.label" /></td>
            <td><fmt:formatDate value="${person.dateOfBirth}" dateStyle="long" /></td>
        </tr>
        <tr>
            <td><spring:message code="gender.label" /></td>
            <td><spring:message code="${person.gender}.genderDisplayValue" /></td>
        </tr>
        <c:forEach var="identifier" items="${person.identifiers}" varStatus="sorPersonLoopStatus">
            <tr>
                <td>${identifier.type.name}</td>
                <td>${identifier.value}
                    <c:if test="${identifier.primary}"> (<spring:message code="primary.label" />)</c:if>
                    <c:if test="${identifier.deleted}"> (<spring:message code="deleted.label" />)</c:if>
                </td>
            </tr>
        </c:forEach>
        <c:forEach var="role" items="${person.roles}">
            <tr>
                <td><spring:message code="role.label" /> (<b>${role.personStatus.description}</b>)</td>
                <td>
                    <a href="${flowExecutionUrl}&_eventId=submitViewRole&roleCode=${role.code}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a>
                     <fmt:formatDate value="${role.start}" /> <spring:message code="vcpTo.label" /> <fmt:formatDate value="${role.end}" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:forEach var="sorPerson" items="${sorPersons}" varStatus="sorPersonLoopStatus">
    <div class="sor-person">

    <table>
        <thead>
        <tr>
            <th colspan="2">
                <spring:message code="source.heading" />
                <c:out value="${sorPerson.sourceSor}" />
                <a href="${flowExecutionUrl}&_eventId=updateSorPerson&sorPersonIndex=${sorPersonLoopStatus.index}"><button>Edit</button></a>
                <a href="${flowExecutionUrl}&_eventId=moveSorPerson&sorPersonIndex=${sorPersonLoopStatus.index}"><button>Move</button></a>
            </th>
        </tr>
        </thead>
        <tbody class="zebra">
        <tr>
            <td><spring:message code="sorId.label" /></td>
            <td>${sorPerson.sorId}</td>
        </tr>
        <c:forEach var="sorName" items="${sorPerson.names}" varStatus="nameLoopStatus">
            <tr><td colspan="2"><strong>${sorName.type.description} <spring:message code="nameColumn.label" /></strong></td></tr>
                <tr><td><spring:message code="vcpFirst.label" /> </td><td>${sorName.given}</td></tr>
                <tr><td><spring:message code="vcpLast.label" /> </td><td>${sorName.family}</td></tr>
               <tr> <td><spring:message code="vcpMiddle.label" /> </td><td>${sorName.middle}</td></tr>
               <tr> <td><spring:message code="vcpPrefix.label" /> </td><td>${sorName.prefix}</td></tr>
               <tr> <td><spring:message code="vcpSuffix.label" /> </td><td>${sorName.suffix}</td></tr>
        </c:forEach>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr>
            <td><spring:message code="dateOfBirth.label" /></td>
            <td><fmt:formatDate value="${sorPerson.dateOfBirth}" /></td>
        </tr>
        <tr>
            <td><spring:message code="gender.label" /></td>
            <td><spring:message code="${sorPerson.gender}.genderDisplayValue" /></td>

        </tr>
        <tr>
            <td><spring:message code="ssn.label" /></td>
            <td>${sorPerson.ssn}</td>
        </tr>
        <c:forEach var="role" items="${sorPerson.roles}">
            <tr>
                <td><spring:message code="role.label" /> <b>(${role.personStatus.description})</b></td>
                <td colspan="3">
                    <a href="${flowExecutionUrl}&_eventId=submitViewSoRRole&sorSource=${sorPerson.sourceSor}&roleCode=${role.code}&formattedName=${sorPerson.formattedName}">${role.title}/${role.organizationalUnit.name}/${role.campus.name}</a>
                <fmt:formatDate value="${role.start}" /> <spring:message code="vcpTo.label" /> <fmt:formatDate value="${role.end}" />
                </td>
            </tr>
        </c:forEach>
         </tbody>


    </table>
    </div>
    <%--pass the index of the iteration so that we can grab the correct sorPerson when passing to the update sorPerson flow--%>
    <%--<div><a href="${flowExecutionUrl}&_eventId=updateSorPerson&sorPersonIndex=${sorPersonLoopStatus.index}"><button>Edit</button></a></div>--%>
</c:forEach>

<div class="center"><a href="${flowExecutionUrl}&_eventId=submitNewSearch"><button>New Search</button></a></div>
<%--<div class="row fm-v" style="clear:both;">--%>
    <%--<input style="float:left;" type="submit" id="fm-newSearch-submit1" name="_eventId_submitNewSearch" class="btn-submit" value="New Search" tabindex="11" />--%>
<%--</div>--%>
