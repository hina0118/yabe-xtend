package models

import play.db.jpa.Model
import play.data.validation.Required
import play.data.validation.MaxSize
import play.data.binding.As
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.ManyToMany
import java.util.Date
import java.util.List
import java.util.Set
import java.util.ArrayList
import java.util.TreeSet

import static javax.persistence.CascadeType.*

@Entity
class Post extends Model {
	@Required
	@Property
	String title
	@Required
	@As("yyyy-MM-dd")
	@Property
	Date postedAt
	@Required
	@Lob
	@MaxSize(10000)
	@Property
	String content
	@Required
	@ManyToOne
	@Property
	User author
	@OneToMany(mappedBy="_post", cascade=ALL)
	@Property
	List<Comment> comments
	@ManyToMany(cascade=PERSIST)
	@Property
	Set<Tag> tags
	
	new(User author, String title, String content) {
		this.comments = new ArrayList<Comment>
		this.tags = new TreeSet
		this.author = author
		this.title = title
		this.content = content
		this.postedAt = new Date
	}
	
	def addComment(String author, String content) {
		val newComment = new Comment(this, author, content)
		comments.add(newComment)
		this.<Post>save
	}
	
	def previous() {
		Post::find("postedAt < ?1 order by postedAt desc", postedAt).<Post>first
	}
	
	def next() {
		Post::find("postedAt > ?1 order by postedAt asc", postedAt).<Post>first
	}
	
	def tagItWith(String name) {
		tags.add(Tag::findOrCreateByName(name))
		this
	}
	
	def static findTaggedWith(String tag) {
		Post::find("select distinct p from Post p join p.tags as t where t.name = ?", tag).<Post>fetch
	}
	
	def static findTaggedWith(String... tags) {
		Post::find("select distinct p.id from Post p join p.tags as t where t.name in (:tags) group by p.id having count(t.id) = :size")
				.bind("tags", tags).bind("size", tags.size).<Post>fetch
	}

	override toString() {
		title
	}
}