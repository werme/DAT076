package models;

import com.avaje.ebean.*;

import java.util.*;

import org.junit.*;

import play.test.WithApplication;
import play.libs.*;

import models.*;

import static org.junit.Assert.*;

import static play.test.Helpers.*;

public class UsersTest extends WithApplication {

  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
    Ebean.save((List) Yaml.load("test-data.yml"));
  }

  @Test
  public void createAndRetrieveUser() {

    // Valid user
    new User("pingu@notes.com", "Pingu", "mysecretpasword").save();
    User pingu = User.find.where().eq("email", "pingu@notes.com").findUnique();
    assertNotNull(pingu);
    assertEquals("Pingu", pingu.username);

    // Invalid users
    new User("pingu@notes.com", "P", "mysecretpasword").save();
    User pingu = User.find.where().eq("email", "pingu@notes.com").findUnique();
    // Should not create user with too short username
    assertNull(pingu);

    // TODO: More invalid user cases
  }

  @Test
  public void changeUserPassword() {
    // TODO
  }

  @Test
  public void deleteUser() {
    User student = User.find.where().eq("email", "student@notes.com").findUnique();
    User.delete(student.id);

    User myDeletedUser = User.find.where().eq("email", "student@notes.com").findUnique();
    assertNull(myDeletedUser);
  }

  // Basic query methods

  @Test
  public void findUserByEmail() {
    User student = User.findByEmail("student@notes.com");
    assertEquals("student", student.username);
  }
}