package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import play.data.format.Formats.*;
import javax.persistence.*;
import java.util.regex.PatternSyntaxException;
import play.Logger;

import securesocial.core.java.SecureSocial;
import securesocial.core.Identity;

@Entity
public class Comment extends Model {

  @Id
  @Version
  public Long id;

  //@Required
  public User author;

  @Column(name = "created_at")
  public Date createdAt;
 
  @Column(name = "updated_at")
  public Date updatedAt;

  @Required
  @NonEmpty
  @MaxLength(140)
  public String content;

  //@Required
  @ManyToOne
  public Note note;

  public Comment(String content) { // User key?
    this.content = content;
  }

  public Comment(Long noteId, String content, User author) { // User key?
    this.note = Note.find.ref(noteId);
    this.content = content;
    this.author = author;
  }

  public static Finder<Long, Comment> find = new Finder(Long.class, Comment.class);

  public static Comment create(Long noteId, Comment comment, User author) {
    comment.note = Note.find.ref(noteId);
    comment.author = author;
    comment.save();
    return comment;
  }

  public static Comment create(Comment comment) {
    comment.save();
    return comment;
  }

  public String toString() {
    return content.length() > 50 ? content.substring(0, 50) + "..." : content;
  }

  @Override
  public void save() {
    createdAt();
    super.save();
  }
 
  @Override
  public void update() {
    updatedAt();
    super.update();
  }
 
  @PrePersist
  void createdAt() {
    this.createdAt = this.updatedAt = new Date();
  }
 
  @PreUpdate
  void updatedAt() {
    this.updatedAt = new Date();
  }

  public static void delete(Long id) {
    Comment comment = find.ref(id);
    comment.delete();
  }
}
