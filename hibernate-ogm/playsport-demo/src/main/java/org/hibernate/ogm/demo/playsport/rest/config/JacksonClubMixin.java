package org.hibernate.ogm.demo.playsport.rest.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "athletes", "employees"})
public class JacksonClubMixin {

}
