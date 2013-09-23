import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;
import java.util.*;
import play.mvc.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import models.*;

public class NotesFunctionalTest extends WithApplication {

  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void createNewNote() {
    Result result = callAction(controllers.routes.ref.Application.newNote());

    // Should return bad request if no data is given
    assertThat(status(result)).isEqualTo(BAD_REQUEST);

    Map<String,String> data = new HashMap<String,String>();
    data.put("text", "My note");

    result = callAction(
        controllers.routes.ref.Application.newNote(),
        fakeRequest().withFormUrlEncodedBody(data)
    );

    // Should return redirect status if successful
    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/");

    Note newNote = Note.find.where().eq("text", "My note").findUnique();

    // Should be saved to DB
    assertNotNull(newNote);
    assertEquals("My note", newNote.text);
  }
}