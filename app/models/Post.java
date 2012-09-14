package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import models.Comment;
import models.Tag;
import models.User;
import org.eclipse.xtext.xbase.lib.Conversions;
import play.data.binding.As;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;

@Entity
@SuppressWarnings("all")
public class Post extends Model {
  @Required
  private String _title;
  
  public String getTitle() {
    return this._title;
  }
  
  public void setTitle(final String title) {
    this._title = title;
  }
  
  @Required
  @As(value = "yyyy-MM-dd")
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
  private User _author;
  
  public User getAuthor() {
    return this._author;
  }
  
  public void setAuthor(final User author) {
    this._author = author;
  }
  
  @OneToMany(mappedBy = "_post", cascade = CascadeType.ALL)
  private List<Comment> _comments;
  
  public List<Comment> getComments() {
    return this._comments;
  }
  
  public void setComments(final List<Comment> comments) {
    this._comments = comments;
  }
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  private Set<Tag> _tags;
  
  public Set<Tag> getTags() {
    return this._tags;
  }
  
  public void setTags(final Set<Tag> tags) {
    this._tags = tags;
  }
  
  public Post(final User author, final String title, final String content) {
    ArrayList<Comment> _arrayList = new ArrayList<Comment>();
    this.setComments(_arrayList);
    TreeSet<Tag> _treeSet = new TreeSet<Tag>();
    this.setTags(_treeSet);
    this.setAuthor(author);
    this.setTitle(title);
    this.setContent(content);
    Date _date = new Date();
    this.setPostedAt(_date);
  }
  
  public Post addComment(final String author, final String content) {
    Post _xblockexpression = null;
    {
      Comment _comment = new Comment(this, author, content);
      final Comment newComment = _comment;
      List<Comment> _comments = this.getComments();
      _comments.add(newComment);
      Post _save = this.<Post>save();
      _xblockexpression = (_save);
    }
    return _xblockexpression;
  }
  
  public Post previous() {
    Date _postedAt = this.getPostedAt();
    JPAQuery _find = Post.find("postedAt < ?1 order by postedAt desc", _postedAt);
    Post _first = _find.<Post>first();
    return _first;
  }
  
  public Post next() {
    Date _postedAt = this.getPostedAt();
    JPAQuery _find = Post.find("postedAt > ?1 order by postedAt asc", _postedAt);
    Post _first = _find.<Post>first();
    return _first;
  }
  
  public Post tagItWith(final String name) {
    Post _xblockexpression = null;
    {
      Set<Tag> _tags = this.getTags();
      Tag _findOrCreateByName = Tag.findOrCreateByName(name);
      _tags.add(_findOrCreateByName);
      _xblockexpression = (this);
    }
    return _xblockexpression;
  }
  
  public static List<Post> findTaggedWith(final String tag) {
    JPAQuery _find = Post.find("select distinct p from Post p join p.tags as t where t.name = ?", tag);
    List<Post> _fetch = _find.<Post>fetch();
    return _fetch;
  }
  
  public static List<Post> findTaggedWith(final String... tags) {
    JPAQuery _find = Post.find("select distinct p.id from Post p join p.tags as t where t.name in (:tags) group by p.id having count(t.id) = :size");
    JPAQuery _bind = _find.bind("tags", tags);
    int _size = ((List<String>)Conversions.doWrapArray(tags)).size();
    JPAQuery _bind_1 = _bind.bind("size", Integer.valueOf(_size));
    List<Post> _fetch = _bind_1.<Post>fetch();
    return _fetch;
  }
  
  public String toString() {
    String _title = this.getTitle();
    return _title;
  }
}
