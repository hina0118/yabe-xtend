package models

import play.db.jpa.Model
import play.data.validation.Email
import play.data.validation.Required
import javax.persistence.Entity

@Entity
class User extends Model {
	@Email
	@Required
	@Property
	String email
	@Required
	@Property
	String password
	@Property
	String fullname
	@Property
	boolean isAdmin
	
	new(String email, String password, String fullname) {
		this.email = email
    	this.password = password
    	this.fullname = fullname
	}
	
	def static connect(String email, String password) {
		find("byEmailAndPassword", email, password).<User>first
	}
	
	override toString() {
		email
	}
}