package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import models.Post;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@SuppressWarnings("all")
public class Comment extends Model {
  @Required
  public String author;
  
  @Required
  public Date postedAt;
  
  @Required
  @Lob
  @MaxSize(value = 10000)
  public String content;
  
  @Required
  @ManyToOne
  public Post post;
  
  public Comment(final Post post, final String author, final String content) {
    this.post = post;
    this.author = author;
    this.content = content;
    Date _date = new Date();
    this.postedAt = _date;
  }
  
  public String toString() {
    String _xifexpression = null;
    int _length = this.content.length();
    boolean _greaterThan = (_length > 50);
    if (_greaterThan) {
      String _substring = this.content.substring(0, 50);
      String _plus = (_substring + "...");
      _xifexpression = _plus;
    } else {
      _xifexpression = this.content;
    }
    return _xifexpression;
  }
}
