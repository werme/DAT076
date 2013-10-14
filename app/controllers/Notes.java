package controllers;

import play.mvc.*;
import play.data.*;
import play.Logger;
import models.*;
import views.html.*;
import securesocial.core.java.SecureSocial;

public class Notes extends Controller {

	static Form<Note> noteForm = Form.form(Note.class);

	public static Result index() {
		return ok(views.html.index.render(Note.all(), noteForm));
	}

	public static Result list() {
		return ok(views.html.index.render(Note.all(), noteForm));
	}

	public static Result show(Long id) {
		return ok(views.html.notes.show.render(Note.find.ref(id)));
	}

	@SecureSocial.SecuredAction
	public static Result newNote() {

		Form<Note> filledForm = noteForm.bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(Note.all(), filledForm));
		} else {
			Note.create(filledForm.get(), Form.form().bindFromRequest().get("tagList"));
			return redirect(routes.Notes.list());
		}
	}

	@SecureSocial.SecuredAction(authorization = WithPrivilegeLevel.class, params = {PrivilegeLevel.USER, PrivilegeLevel.ADMIN})
	public static Result delete(Long id) {
		Note.delete(id);
		return redirect(routes.Notes.list());
	}
}
