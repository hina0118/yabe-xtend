package models

import play.db.jpa.Model
import play.data.validation.Required
import javax.persistence.Entity
import java.util.Map

@Entity
class Tag extends Model implements Comparable<Tag> {
	@Required
    @Property
	String name
	
	private new(String name) {
		this.name = name
	}
	
	def static findOrCreateByName(String name) {
		val tag = Tag::find("byName", name).<Tag>first
		if (tag != null) tag else new Tag(name)
	}
	
	def static getCloud() {
		Tag::find("select new map(t.name as tag, count(p.id) as pound) from Post p join p.tags as t group by t.name").<Map>fetch
	}
	
	override compareTo(Tag otherTag) {
		name.compareTo(otherTag.name)
	}
	
	override toString() {
		name
	}
}