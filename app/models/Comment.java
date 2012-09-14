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
  private String _author;
  
  public String getAuthor() {
    return this._author;
  }
  
  public void setAuthor(final String author) {
    this._author = author;
  }
  
  @Required
  private Date _postedAt;
  
  public Date getPostedAt() {
    return this._postedAt;
  }
  
  public void setPostedAt(final Date postedAt) {
    this._postedAt = postedAt;
  }
  
  @Required
  @Lob
  @MaxSize(value = 10000)
  private String _content;
  
  public String getContent() {
    return this._content;
  }
  
  public void setContent(final String content) {
    this._content = content;
  }
  
  @Required
  @ManyToOne
  private Post _post;
  
  public Post getPost() {
    return this._post;
  }
  
  public void setPost(final Post post) {
    this._post = post;
  }
  
  public Comment(final Post post, final String author, final String content) {
    this.setPost(post);
    this.setAuthor(author);
    this.setContent(content);
    Date _date = new Date();
    this.setPostedAt(_date);
  }
  
  public String toString() {
    String _xifexpression = null;
    String _content = this.getContent();
    int _length = _content.length();
    boolean _greaterThan = (_length > 50);
    if (_greaterThan) {
      String _content_1 = this.getContent();
      String _substring = _content_1.substring(0, 50);
      String _plus = (_substring + "...");
      _xifexpression = _plus;
    } else {
      String _content_2 = this.getContent();
      _xifexpression = _content_2;
    }
    return _xifexpression;
  }
}
