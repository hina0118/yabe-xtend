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
  public String title;
  
  @Required
  @As(value = "yyyy-MM-dd")
  public Date postedAt;
  
  @Required
  @Lob
  @MaxSize(value = 10000)
  public String content;
  
  @Required
  @ManyToOne
  public User author;
  
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  public List<Comment> comments;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  public Set<Tag> tags;
  
  public Post(final User author, final String title, final String content) {
    ArrayList<Comment> _arrayList = new ArrayList<Comment>();
    this.comments = _arrayList;
    TreeSet<Tag> _treeSet = new TreeSet<Tag>();
    this.tags = _treeSet;
    this.author = author;
    this.title = title;
    this.content = content;
    Date _date = new Date();
    this.postedAt = _date;
  }
  
  public Post addComment(final String author, final String content) {
    Post _xblockexpression = null;
    {
      Comment _comment = new Comment(this, author, content);
      final Comment newComment = _comment;
      this.comments.add(newComment);
      Post _save = this.<Post>save();
      _xblockexpression = (_save);
    }
    return _xblockexpression;
  }
  
  public Post previous() {
    JPAQuery _find = Post.find("postedAt < ?1 order by postedAt desc", this.postedAt);
    Post _first = _find.<Post>first();
    return _first;
  }
  
  public Post next() {
    JPAQuery _find = Post.find("postedAt > ?1 order by postedAt asc", this.postedAt);
    Post _first = _find.<Post>first();
    return _first;
  }
  
  public Post tagItWith(final String name) {
    Post _xblockexpression = null;
    {
      Tag _findOrCreateByName = Tag.findOrCreateByName(name);
      this.tags.add(_findOrCreateByName);
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
    return this.title;
  }
}
