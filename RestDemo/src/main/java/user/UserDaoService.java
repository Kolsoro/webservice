package user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import post.Post;
@Service
public class UserDaoService {
	
	

	private static List<User> users=new ArrayList<User>();
	private static List<Post> user1Posts=new ArrayList<>();
	
	
	static {
//		user1Posts.add(new Post(10, "Hello Guys ! it is my evening post", new Date()));
//		user1Posts.add(new Post(11, "Hello Guys ! it is my Morning post", new Date()));
//		user1Posts.add(new Post(12, "aaj friday hai and i m watching movie!!", new Date()));


//		users.add(new User(1,"kaal",new Date()));
//		users.add(new User(2,"Bali",new Date()));
//		users.add(new User(3, "gori", new Date()));
//		users.add(new User(4, "fal", new Date()));

	}
	private static Integer userCount=3;
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId()==0) {
			user.setId(users.size()+1);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(Integer id) {
		for(User u:users) {
			if(u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}
	
	
}
