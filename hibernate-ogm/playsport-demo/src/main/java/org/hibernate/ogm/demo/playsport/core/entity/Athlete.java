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
 * Created by fabio on 18/08/2017.
 */
package org.hibernate.ogm.demo.playsport.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Athlete extends Person {

    public static final String CODE_REGEX = "\\d{9}";

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "\\d{9}")
    @Field(analyze= Analyze.NO)
    private String uispCode;

    @ManyToOne
    private Club club;

    public String getUispCode() {
        return uispCode;
    }

    public void setUispCode(String uispCode) {
        this.uispCode = uispCode;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Athlete athlete = (Athlete) o;

        return uispCode != null ? uispCode.equals(athlete.uispCode) : athlete.uispCode == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uispCode != null ? uispCode.hashCode() : 0);
        return result;
    }

}
