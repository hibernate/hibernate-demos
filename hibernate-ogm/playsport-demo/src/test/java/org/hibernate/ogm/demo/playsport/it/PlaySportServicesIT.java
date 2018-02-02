package org.hibernate.ogm.demo.playsport.it;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.hibernate.ogm.demo.playsport.core.entity.Address;
import org.hibernate.ogm.demo.playsport.core.entity.Athlete;
import org.hibernate.ogm.demo.playsport.core.entity.Club;
import org.hibernate.ogm.demo.playsport.core.entity.ClubEmployee;
import org.hibernate.ogm.demo.playsport.core.repo.AthleteRepo;
import org.hibernate.ogm.demo.playsport.core.repo.ClubEmployeeRepo;
import org.hibernate.ogm.demo.playsport.core.repo.ClubRepo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import static org.junit.Assert.*;

/**
 * This integration test verifies the behavior of project repositories.
 * Test subjects are:
 * <ul>
 *     <li>club repository {@link ClubRepo}</li>
 *     <li>athlete repository {@link AthleteRepo}</li>
 *     <li>club employee repository {@link ClubEmployeeRepo}</li>
 * </ul>
 * <p>
 * At the beginning of the test relative caches
 * <ul>
 *     <li>club cache {@link Club}</li>
 *     <li>athlete cache {@link Athlete}</li>
 *     <li>club employee cache {@link ClubEmployee}</li>
 * </ul>
 * are empty.
 * <p>
 * Test methods will be executed in sequence, using {@link InSequence} Arquillian annotation.
 *
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@RunWith(Arquillian.class)
public class PlaySportServicesIT {

    public static final String CLUB_1_CODE = "RM731";
    public static final String CLUB_2_CODE = "MI731";
    public static final String CLUB_3_CODE = "TO733";
    public static final String ATH_1_CODE = "123456789";
    public static final String ATH_2_CODE = "987654321";
    public static final String ATH_3_CODE = "333111333";
    public static final String ATH_4_CODE = "111222333";

    /**
     * Define the deployment used for test.
     * Only core packages are required for enabling repositories injection:
     * <code>org.hibernate.ogm.demo.playsport.core.+</code>
     *
     * @return shrinkwrap WebArchive used for test.
     */
    @Deployment
    public static WebArchive create() {
        return ShrinkWrap
            .create(WebArchive.class, "playsport-demo.war")
            .addPackages(true, "org.hibernate.ogm.demo.playsport.core")
            .addAsResource("META-INF/persistence.xml")
            .addAsResource("hotrodclient.properties")
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

    /**
     * This step inserts three sport clubs: Roma, Milan and Juve.
     * <p>
     * It tests also:
     * <ul>
     *     <li>Generation of ID</li>
     *     <li>Find by ID</li>
     *     <li>Find by Name. Using Hibernate Search fullText EntityManager</li>
     *     <li>Find by Code. Using Hibernate Search fullText EntityManager</li>
     *     <li>Find All</li>
     * </ul>
     *
     * @throws Exception
     */
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

    /**
     * This step inserts three athletes: Fabio Massimo, Stefano and Giovanni.
     * <p>
     * It tests also:
     * <ul>
     *     <li>Generation of ID</li>
     *     <li>Find by ID</li>
     *     <li>Find by findByUispCode. Using Hibernate Search fullText EntityManager</li>
     *     <li>Find All</li>
     * </ul>
     * @throws Exception
     */
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

        log.debug("BirthDate long value is {}", cal.getTimeInMillis());

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

        log.debug("BirthDate long value is {}", cal.getTimeInMillis());

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

    /**
     * This step associate athletes to clubs and verify the associations,
     * using {@link ClubRepo}::getClubByIdWithAthletes method.
     *
     * @throws Exception
     */
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

        // when I associate athletes to clubs
        athleteRepo.addAthleteToClub(fabio, roma);
        athleteRepo.addAthleteToClub(stefano, milan);
        athleteRepo.addAthleteToClub(giovanni, juve);

        // then I expect that clubs now contain the relative athletes
        roma = clubRepo.getClubByIdWithAthletes(roma.getId());

        assertEquals(1, roma.getAthletes().size());
        assertEquals(fabio.getId(), roma.getAthletes().get(0).getId());

        milan = clubRepo.getClubByIdWithAthletes(milan.getId());

        assertEquals(1, milan.getAthletes().size());
        assertEquals(stefano.getId(), milan.getAthletes().get(0).getId());

        juve = clubRepo.getClubByIdWithAthletes(juve.getId());

        assertEquals(1, juve.getAthletes().size());
        assertEquals(giovanni.getId(), juve.getAthletes().get(0).getId());

        // and then I expect that athletes has clubs
        fabio = athleteRepo.findByUispCode(ATH_1_CODE).get(0);
        stefano = athleteRepo.findByUispCode(ATH_2_CODE).get(0);
        giovanni = athleteRepo.findByUispCode(ATH_3_CODE).get(0);

        assertEquals(CLUB_1_CODE, fabio.getClub().getCode());
        assertEquals(CLUB_2_CODE, stefano.getClub().getCode());
        assertEquals(CLUB_3_CODE, giovanni.getClub().getCode());

    }

    /**
     * This step creates a club employee and associates the employee to a club.
     * The method {@link ClubEmployeeRepo}::createEmployee allows to create a new employee
     * and associate it directly (in the same transaction) with an existing club.
     *
     * @throws Exception
     */
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

        log.debug("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Milan");
        address.setStreet("via tony 2");
        address.setZip("01911");
        clubEmployee.setAddress(address);

        List<Club> clubList = clubRepo.findByCode(CLUB_3_CODE);
        assertEquals(1, clubList.size());

        employeeRepo.createEmployee(clubEmployee, clubList.get(0));

        List<ClubEmployee> employees = employeeRepo.findAll();
        assertEquals(1, employees.size());
        assertNotNull(employees.get(0).getId());

    }

    /**
     * This step creates a fourth athlete.
     * The method {@link AthleteRepo}::createEmployee allows to create a new athlete
     * and associate it directly (in the same transaction) with an existing club.
     *
     * @throws Exception
     */
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

        athleteRepo.createAthlete(athlete, clubList.get(0));

        String id = (athlete.getId());
        assertNotNull(id);

        athlete = athleteRepo.findByUispCode(ATH_4_CODE).get(0);
        assertEquals(CLUB_1_CODE, athlete.getClub().getCode());

    }

    /**
     * This step verifies the behaviour of {@link AthleteRepo}::remove method.
     * <p>
     * Given 4 athletes and 3 clubs.
     * When remove 2 athletes and 1 club.
     * Then we have 2 athletes and 1 club.
     *
     */
    @Test
    @InSequence(6)
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

    @Test
    @InSequence(7)
    public void testCreateClubWithNewEmployees() {

        ClubEmployee clubEmployee = new ClubEmployee();
        clubEmployee.setName("Diego");
        clubEmployee.setSurname("Diodati");
        clubEmployee.setTaxCode("J31d39JD1");
        clubEmployee.setEmail("xx.ciao@gmail.com");
        clubEmployee.setHometown("Rome");

        Calendar cal = Calendar.getInstance();
        cal.set(1980, Calendar.SEPTEMBER, 18);
        clubEmployee.setBirthDate(cal);

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via leone 4");
        address.setZip("01911");
        clubEmployee.setAddress(address);

        ArrayList<ClubEmployee> clubEmployees = new ArrayList<>();
        clubEmployees.add( clubEmployee );

        Club club = new Club( "New Team", "CODE123", "TAX123", "VAT123" );

        clubEmployee.setCompany( club );
        club.setEmployees(clubEmployees);

        clubRepo.add( club );

    }

}
