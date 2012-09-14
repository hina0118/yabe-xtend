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
  private String _email;
  
  public String getEmail() {
    return this._email;
  }
  
  public void setEmail(final String email) {
    this._email = email;
  }
  
  @Required
  private String _password;
  
  public String getPassword() {
    return this._password;
  }
  
  public void setPassword(final String password) {
    this._password = password;
  }
  
  private String _fullname;
  
  public String getFullname() {
    return this._fullname;
  }
  
  public void setFullname(final String fullname) {
    this._fullname = fullname;
  }
  
  private boolean _isAdmin;
  
  public boolean isIsAdmin() {
    return this._isAdmin;
  }
  
  public void setIsAdmin(final boolean isAdmin) {
    this._isAdmin = isAdmin;
  }
  
  public User(final String email, final String password, final String fullname) {
    this.setEmail(email);
    this.setPassword(password);
    this.setFullname(fullname);
  }
  
  public static User connect(final String email, final String password) {
    JPAQuery _find = GenericModel.find("byEmailAndPassword", email, password);
    User _first = _find.<User>first();
    return _first;
  }
  
  public String toString() {
    String _email = this.getEmail();
    return _email;
  }
}
