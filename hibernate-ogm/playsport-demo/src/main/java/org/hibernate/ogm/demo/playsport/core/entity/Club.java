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
package org.hibernate.ogm.demo.playsport.core.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Indexed
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "athletes", "employees"})
public class Club {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private String id;

    @Field(analyze= Analyze.NO)
    @NotEmpty
    private String name;

    @Column(unique = true, nullable = false)
    @Field(analyze= Analyze.NO)
    @NotEmpty
    private String code;

    @Field(analyze= Analyze.NO)
    @NotEmpty
    private String taxCode;

    @Field(analyze= Analyze.NO)
    @NotEmpty
    private String vatNumber;

    @OneToMany(mappedBy = "club")
    private List<Athlete> athletes = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    private List<ClubEmployee> employees = new ArrayList<>();

    public Club() {
    }

    public Club(String name,
                String code,
                String taxCode,
                String vatNumber) {
        this.name = name;
        this.code = code;
        this.taxCode = taxCode;
        this.vatNumber = vatNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    public List<ClubEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<ClubEmployee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Club sportClub = (Club) o;

        return code != null ? code.equals(sportClub.code) : sportClub.code == null;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SportClub{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", vatNumber='" + vatNumber + '\'' +
                '}';
    }

}
