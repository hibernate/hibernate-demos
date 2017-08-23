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
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
package org.hibernate.ogm.demo.playsport.it;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.hibernate.ogm.demo.playsport.core.entity.Address;
import org.hibernate.ogm.demo.playsport.core.entity.Athlete;
import org.hibernate.ogm.demo.playsport.core.entity.ClubEmployee;
import org.hibernate.ogm.demo.playsport.core.repo.AthleteRepo;
import org.hibernate.ogm.demo.playsport.core.repo.ClubRepo;
import org.hibernate.ogm.demo.playsport.core.entity.Club;
import org.hibernate.ogm.demo.playsport.core.repo.ClubEmployeeRepo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PlaySportServicesIT {

    public static final String CLUB_1_CODE = "RM731";
    public static final String CLUB_2_CODE = "MI731";
    public static final String CLUB_3_CODE = "TO733";
    public static final String ATH_1_CODE = "123456789";
    public static final String ATH_2_CODE = "987654321";
    public static final String ATH_3_CODE = "333111333";
    public static final String ATH_4_CODE = "111222333";

    @Deployment
    public static WebArchive create() {
        return ShrinkWrap
            .create(WebArchive.class, "playsport-demo.war")
            .addPackages(true, "org.hibernate.ogm.demo.playsport.core")
            .addAsResource("META-INF/persistence.xml")
            .addAsResource("hotrodclient.properties")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-deployment-structure.xml"));
    }

    @Inject
    private Logger log;

    @Inject
    private ClubRepo clubRepo;

    @Inject
    private AthleteRepo athleteRepo;

    @Inject
    private ClubEmployeeRepo employeeRepo;

    @Test
    @InSequence(1)
    public void insertClubs() throws Exception {

        Club club = new Club("Roma", CLUB_1_CODE, "RM21M2222", "CSSDSD333S");
        clubRepo.add(club);

        String id = club.getId();
        assertNotNull(id);
        log.info("*** Created sport club with id {}", id);

        // verify load by id
        club = clubRepo.findById(id);
        assertNotNull(club);

        // verify find by name
        List<Club> clubs = clubRepo.findByName("Roma");
        assertFalse(clubs.isEmpty());

        // verify find by code
        clubs = clubRepo.findByCode(CLUB_1_CODE);
        assertFalse(clubs.isEmpty());

        clubRepo.add(new Club("Milan", CLUB_2_CODE, "MI21M2222", "CSSDSD333D"));
        clubRepo.add(new Club("Juve", CLUB_3_CODE, "TO21M2222", "CSSDSD323D"));

        // verify get all (bulk)
        clubs = clubRepo.findAll();
        assertEquals(3, clubs.size());

    }

    @Test
    @InSequence(2)
    public void insertAthletes() throws Exception {

        Athlete athlete = new Athlete();
        athlete.setUispCode(ATH_1_CODE);
        athlete.setName("Fabio Massimo");
        athlete.setSurname("Rossi");
        athlete.setTaxCode("J39d39JD3");
        athlete.setEmail("f.ciao@gmail.com");
        athlete.setHometown("Rome");

        Calendar cal = Calendar.getInstance();
        cal.set(1979, Calendar.NOVEMBER, 21);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via mario iv");
        address.setZip("01921");
        athlete.setAddress(address);

        athleteRepo.add(athlete);
        String id = athlete.getId();

        // verify load by id
        athlete = athleteRepo.findById(id);
        assertNotNull(athlete);

        // verify find by uisp code
        List<Athlete> athletes = athleteRepo.findByUispCode(ATH_1_CODE);
        assertFalse(athletes.isEmpty());

        athlete = new Athlete();
        athlete.setUispCode(ATH_2_CODE);
        athlete.setName("Stefano");
        athlete.setSurname("Bianchi");
        athlete.setTaxCode("J39dW9JD3");
        athlete.setEmail("s.ciao@gmail.com");
        athlete.setHometown("Rome");

        cal = Calendar.getInstance();
        cal.set(1977, Calendar.MARCH, 10);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via dario vii");
        address.setZip("01922");
        athlete.setAddress(address);

        athleteRepo.add(athlete);

        athlete = new Athlete();
        athlete.setUispCode(ATH_3_CODE);
        athlete.setName("Giovanni");
        athlete.setSurname("Viola");
        athlete.setTaxCode("J19dW9JD3");
        athlete.setEmail("g.ciao@gmail.com");
        athlete.setHometown("Rome");

        cal = Calendar.getInstance();
        cal.set(1981, Calendar.MARCH, 24);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via viola ix");
        address.setZip("01911");
        athlete.setAddress(address);

        athleteRepo.add(athlete);

        // verify get all (bulk)
        athletes = athleteRepo.findAll();
        assertEquals(3, athletes.size());

    }

    @Test
    @InSequence(3)
    public void associateClubToAthletes() throws Exception {

        List<Athlete> fabioAthleteList = athleteRepo.findByUispCode(ATH_1_CODE);
        List<Athlete> stefanoAthleteList = athleteRepo.findByUispCode(ATH_2_CODE);
        List<Athlete> giovanniAthleteList = athleteRepo.findByUispCode(ATH_3_CODE);

        List<Club> romaClubList = clubRepo.findByCode(CLUB_1_CODE);
        List<Club> milanClubList = clubRepo.findByCode(CLUB_2_CODE);
        List<Club> juveClubList = clubRepo.findByCode(CLUB_3_CODE);

        // verify cardinality
        assertEquals(1, fabioAthleteList.size());
        assertEquals(1, stefanoAthleteList.size());
        assertEquals(1, giovanniAthleteList.size());
        assertEquals(1, romaClubList.size());
        assertEquals(1, milanClubList.size());
        assertEquals(1, juveClubList.size());

        Athlete fabio = fabioAthleteList.get(0);
        Athlete stefano = stefanoAthleteList.get(0);
        Athlete giovanni = giovanniAthleteList.get(0);
        Club roma = romaClubList.get(0);
        Club milan = milanClubList.get(0);
        Club juve = juveClubList.get(0);

        athleteRepo.addAthleteToClub(fabio, roma);
        athleteRepo.addAthleteToClub(stefano, milan);
        athleteRepo.addAthleteToClub(giovanni, juve);

    }

    @Test
    @InSequence(4)
    public void createClubEmployee() throws Exception {

        ClubEmployee clubEmployee = new ClubEmployee();
        clubEmployee.setName("Michele");
        clubEmployee.setSurname("Diodati");
        clubEmployee.setTaxCode("J31d39JD3");
        clubEmployee.setEmail("gg.ciao@gmail.com");
        clubEmployee.setHometown("Milan");

        Calendar cal = Calendar.getInstance();
        cal.set(1979, Calendar.NOVEMBER, 21);
        clubEmployee.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Milan");
        address.setStreet("via tony 2");
        address.setZip("01911");
        clubEmployee.setAddress(address);

        List<Club> clubList = clubRepo.findByCode(CLUB_3_CODE);
        assertEquals(1, clubList.size());

        employeeRepo.createEmployee(clubEmployee, clubList.get(0));

    }

    @Test
    @InSequence(5)
    public void createAthleteWithClub() throws Exception {

        Athlete athlete = new Athlete();
        athlete.setUispCode(ATH_4_CODE);
        athlete.setName("Lorenzo");
        athlete.setSurname("Rossi");
        athlete.setTaxCode("J31d39JD3");
        athlete.setEmail("lr.ciao@gmail.com");
        athlete.setHometown("Milan");

        Calendar cal = Calendar.getInstance();
        cal.set(1979, Calendar.NOVEMBER, 21);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Milan");
        address.setStreet("via tony 2");
        address.setZip("01911");
        athlete.setAddress(address);

        List<Club> clubList = clubRepo.findByCode(CLUB_1_CODE);
        assertEquals(1, clubList.size());

        athlete = athleteRepo.createAthlete(athlete, clubList.get(0));

        String id = (athlete.getId());
        assertNotNull(id);

    }

    @Test
    @InSequence(6)
    public void verifyMatchClubAthletes() {

        Athlete fabio = athleteRepo.findByUispCode(ATH_1_CODE).get(0);
        Athlete stefano = athleteRepo.findByUispCode(ATH_2_CODE).get(0);
        Athlete giovanni = athleteRepo.findByUispCode(ATH_3_CODE).get(0);
        Athlete lorenzo = athleteRepo.findByUispCode(ATH_4_CODE).get(0);

        assertEquals(CLUB_1_CODE, fabio.getClub().getCode());
        assertEquals(CLUB_2_CODE, stefano.getClub().getCode());
        assertEquals(CLUB_3_CODE, giovanni.getClub().getCode());
        assertEquals(CLUB_1_CODE, lorenzo.getClub().getCode());

    }

    @Test
    @InSequence(7)
    public void testRemoveAthlete() {

        List<Athlete> allAthletes = athleteRepo.findAll();
        assertEquals(4, allAthletes.size());

        Set<String> athleteSubset = allAthletes.stream()
                .filter(athlete -> ATH_3_CODE.equals(athlete.getUispCode()) || ATH_4_CODE.equals(athlete.getUispCode()))
                .map(athlete -> athlete.getId())
                .collect(Collectors.toSet());
        assertEquals(2, athleteSubset.size());

        for (String id : athleteSubset) {
            athleteRepo.delete(id);
        }

        List<Club> allClubs = clubRepo.findAll();
        assertEquals(3, allClubs.size());

        Set<String> clubSubset = allClubs.stream()
                .filter(club -> CLUB_3_CODE.equals(club.getCode()))
                .map(athlete -> athlete.getId())
                .collect(Collectors.toSet());
        assertEquals(1, clubSubset.size());

        for (String id : clubSubset) {
            clubRepo.delete(id);
        }

        allAthletes = athleteRepo.findAll();
        allClubs = clubRepo.findAll();

        assertEquals(2, allAthletes.size());
        assertEquals(2, allClubs.size());

    }

}
