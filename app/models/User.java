package models;

import javax.persistence.Entity;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;

@Entity
@SuppressWarnings("all")
public class User extends Model {
  @Email
  @Required
  public String email;
  
  @Required
  public String password;
  
  public String fullname;
  
  public boolean isAdmin;
  
  public User(final String email, final String password, final String fullname) {
    this.email = email;
    this.password = password;
    this.fullname = fullname;
  }
  
  public static User connect(final String email, final String password) {
    JPAQuery _find = GenericModel.find("byEmailAndPassword", email, password);
    User _first = _find.<User>first();
    return _first;
  }
  
  public String toString() {
    return this.email;
  }
}
