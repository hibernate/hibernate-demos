package org.hibernate.ogm.demo.playsport.core.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class ClubEmployee extends Person {

    @ManyToOne
    private Club company;

    public Club getCompany() {
        return company;
    }

    public void setCompany(Club company) {
        this.company = company;
    }

}
