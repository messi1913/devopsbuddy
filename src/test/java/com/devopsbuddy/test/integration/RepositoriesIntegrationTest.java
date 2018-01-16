package com.devopsbuddy.test.integration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.RolesEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class RepositoriesIntegrationTest {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private static final int BASIC_PLAN_ID = 1;
    private static final int BASIC_ROLE_ID = 1;


    @Before
    public void init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testCreateNewRole() throws Exception {
        Role userRole = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Role retrieveRole = roleRepository.findOne(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrieveRole);
    }

    @Test
    public void createNewUser() throws Exception {
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        User basicUser = createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(basicUser);
        userRole.setRole(basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);

        for(UserRole ur : userRoles) {
            roleRepository.save(ur.getRole()) ;
        }

        basicUser  = userRepository.save(basicUser);
        User newCreatedUser = userRepository.findOne(basicUser.getId());

        Assert.assertNotNull(newCreatedUser);
        Assert.assertNotNull(newCreatedUser.getId() != 0);
        Assert.assertNotNull(newCreatedUser.getPlan());
        Assert.assertNotNull(newCreatedUser.getPlan().getId());

        Set<UserRole> newlyCreatedUserRoles = newCreatedUser.getUserRoles();
        for(UserRole ur : newlyCreatedUserRoles){
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }


    }


    private Role createRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    private User createBasicUser() throws Exception {
        User user = new User();
        user.setUsername("basicUser");
        user.setPassword("secret");
        user.setEmail("me@example.com");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setPhoneNumber("01099891913");
        user.setCountry("KR");
        user.setEnabled(true);
        user.setDescription("A Basic USer");
        user.setProfileImageUrl("https://");

        return user;



    }

    @Test
    public void testCreateNewPlant() throws Exception {

        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(BASIC_PLAN_ID);
        Assert.assertNotNull(retrievedPlan);
    }

    private Plan createBasicPlan() {
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("plan");
        return plan;
    }
}
