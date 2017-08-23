/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

/**
 * Created by fabio on 20/08/2017.
 */
package org.hibernate.ogm.demo.playsport.rest.exception;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class UispNotFoundExceptionMapper implements ExceptionMapper<UispNotFoundException> {

    @Inject
    private Logger log;

    @Override
    public Response toResponse(UispNotFoundException exception) {

        return Response.status(404)
                .entity(new ExceptionDto(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();

    }
}
