import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.test.WithApplication;
import play.data.Form;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.libs.Yaml;

import com.avaje.ebean.*;


import models.*;

import views.html.*;

import static play.test.Helpers.*;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

public class ApplicationTest {

    private FakeApplication app;

    @Before
    public void startApp() throws IOException {
        app = fakeApplication(inMemoryDatabase());
        start(app);
    }

    @After
    public void stopApp() {
        stop(app);
    }

    @Test
    public void testInitialData() {

        // Should be 4 notes in DB
        assertEquals(4, Note.find.findRowCount());

        // Should have note with text "The first note"
        Note myNote = Note.find.where().eq("text", "The first note").findUnique();
        assertNotNull(myNote);
    }

    @Test
    public void renderFrontPage() {

        Form<Note> noteForm = Form.form(Note.class);
        Content html = views.html.index.render(Note.all(), noteForm);

        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Notes");
    }
}
