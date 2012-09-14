package models;

import com.google.common.base.Objects;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import play.data.validation.Required;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;

@Entity
@SuppressWarnings("all")
public class Tag extends Model implements Comparable<Tag> {
  @Required
  private String _name;
  
  public String getName() {
    return this._name;
  }
  
  public void setName(final String name) {
    this._name = name;
  }
  
  private Tag(final String name) {
    this.setName(name);
  }
  
  public static Tag findOrCreateByName(final String name) {
    Tag _xblockexpression = null;
    {
      JPAQuery _find = Tag.find("byName", name);
      final Tag tag = _find.<Tag>first();
      Tag _xifexpression = null;
      boolean _notEquals = (!Objects.equal(tag, null));
      if (_notEquals) {
        _xifexpression = tag;
      } else {
        Tag _tag = new Tag(name);
        _xifexpression = _tag;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public static List<Map> getCloud() {
    JPAQuery _find = Tag.find("select new map(t.name as tag, count(p.id) as pound) from Post p join p.tags as t group by t.name");
    List<Map> _fetch = _find.<Map>fetch();
    return _fetch;
  }
  
  public int compareTo(final Tag otherTag) {
    String _name = this.getName();
    String _name_1 = otherTag.getName();
    int _compareTo = _name.compareTo(_name_1);
    return _compareTo;
  }
  
  public String toString() {
    String _name = this.getName();
    return _name;
  }
}
