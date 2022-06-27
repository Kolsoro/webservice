package user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import post.Post;
//@Component
@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	@Size(min = 3,message = "name should be atleast 4 chracters ")
	private String name;
	@Past(message = "date can not be future")
	private Date birthDate;
//	private  List<Post> post;
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, @Size(min = 3, message = "name should be atleast 4 chracters ") String name,
			@Past(message = "date can not be future") Date birthDate, List<Post> posts) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.posts = posts;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", posts=" + posts + "]";
	}
	
	
}
