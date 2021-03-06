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

package org.openregistry.core.web.resources;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * A support abstract class containing common pieces of <code>JerseyClient</code> API boilerplate code that all
 * JerseyTest-based functional tests could take advantage of
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public abstract class JerseyTestSupport extends JerseyTest {

    protected static final String POST_HTTP_METHOD = "POST";
    protected static final String PUT_HTTP_METHOD = "PUT";
    protected static final String GET_HTTP_METHOD = "GET";
    protected static final String DELETE_HTTP_METHOD = "DELETE";

    public JerseyTestSupport(WebAppDescriptor webAppDescriptor) {
        super(webAppDescriptor);
        //Disable automatic redirects by this user-agent e.g. in cases of HTTP 303, 301, 307, etc.
        client().setFollowRedirects(false);
    }

    protected final ClientResponse handleClientRequestForUriPathAndHttpMethod(URI uri, String httpMethod) {
        return client().handle(ClientRequest.create().build(uri, httpMethod));
    }

    protected final ClientResponse handleClientRequestForUriPathAndHttpMethodAndEntity(URI uri, String httpMethod, Object entity) {
        final ClientRequest req = ClientRequest.create().entity(entity).build(uri, httpMethod);
        return client().handle(req);
    }

    protected final ClientResponse handleClientRequestForUriPathAndHttpMethodAndMediaTypeAndEntity(URI uri, String httpMethod,
                                                                                                   MediaType mediaType, Object entity) {
        final ClientRequest req = ClientRequest.create().entity(entity, mediaType).build(uri, httpMethod);
        return client().handle(req);
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(int statusCode, String uriPath,
                                                                                             String httpMethod, Object entity) {

        final ClientResponse response = handleClientRequestForUriPathAndHttpMethodAndEntity(pathToURI(uriPath), httpMethod, entity);
        assertEquals(statusCode, response.getStatus());
        return response;
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntityWithQueryParam(int statusCode, String uriPath,
                                                                                                           String httpMethod, Object entity,
                                                                                                           String paramKey, String paramValue) {

        final ClientResponse response = handleClientRequestForUriPathAndHttpMethodAndEntity(pathToURIWithQueryParam(uriPath, paramKey, paramValue),
                httpMethod, entity);
        assertEquals(statusCode, response.getStatus());
        return response;
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(int statusCode,
                                                                                                                        String uriPath,
                                                                                                                        String httpMethod,
                                                                                                                        MediaType mediaType,
                                                                                                                        Object entity,
                                                                                                                        Map<String, String> params) {

        final ClientResponse response =
                handleClientRequestForUriPathAndHttpMethodAndMediaTypeAndEntity(pathToURIWithQueryParams(uriPath, params),
                        httpMethod, mediaType, entity);
        assertEquals(statusCode, response.getStatus());
        return response;
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethod(int statusCode, String uriPath, String httpMethod) {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod(pathToURI(uriPath), httpMethod);
        assertEquals(statusCode, response.getStatus());
        return response;
    }

    protected final URI pathToURI(String uriPath) {
        return resource().path(uriPath).getURI();
    }

    protected final URI pathToURIWithQueryParam(String uriPath, String paramKey, String paramValue) {
        return resource().path(uriPath).queryParam(paramKey, paramValue).getURI();
    }

    protected final URI pathToURIWithQueryParams(String uriPath, Map<String, String> params) {
        Set<Map.Entry<String, String>> paramEntries = params.entrySet();
        MultivaluedMap<String, String> paramsMap = new MultivaluedMapImpl();
        for (Map.Entry<String, String> param : paramEntries) {
            paramsMap.putSingle(param.getKey(), param.getValue());

        }
        return resource().path(uriPath).queryParams(paramsMap).getURI();
    }
}
