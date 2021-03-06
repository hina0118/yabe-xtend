package models

import play.db.jpa.Model
import play.data.validation.Required
import play.data.validation.MaxSize
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.ManyToOne
import java.util.Date

@Entity
class Comment extends Model {
	@Required
	public String author
	@Required
	public Date postedAt
	@Required
	@Lob
	@MaxSize(10000)
	public String content
	@Required
	@ManyToOne
	public Post post
	
	new(Post post, String author, String content) {
        this.post = post
        this.author = author
        this.content = content
        this.postedAt = new Date
	}
	
	override toString() {
		if (content.length > 50) content.substring(0, 50) + "..." else content
	}
}