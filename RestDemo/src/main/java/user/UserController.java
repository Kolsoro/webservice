package user;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import post.NoPostException;
import post.Post;

@RestController
public class UserController {
	@Autowired
	private UserDaoService userDaoService;

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> retriveAllUser() throws NoUserException {
		List<User> users = userDaoService.findAll();
		if (users.isEmpty())
			throw new NoUserException("there is no user in database");
		return users;
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable Integer id) throws UserNotFoundException {
		User user = userDaoService.findOne(id);
		if (user == null)
			throw new UserNotFoundException("No such id->" + id);
		EntityModel<User> model=EntityModel.of(user);
		WebMvcLinkBuilder linkToAllUsers=linkTo(methodOn(this.getClass()).retriveAllUser());
//		WebMvcLinkBuilder linkToDeleteUser=linkTo(methodOn(this.getClass()).deleteUser(id));
//		model.add(linkToDeleteUser.withRel("Delete-Users"));
		model.add(linkToAllUsers.withRel("All-Users"));
		return model;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

//	@GetMapping("/users/{id}/posts/{postId}")
//	public Post retrivePostForAUser(@PathVariable("id") Integer id, @PathVariable("postId") Integer pid)
//			throws NoPostException {
//		User user = retriveUser(id);
////		List<Post> posts = user.getPost();
//		if (!posts.isEmpty()) {
//			for (Post p : posts) {
//				if (p.getPostId().equals(pid))
//					return p;
//			}
//		}
//		throw new NoPostException("Users haven't created any post yet");
//	}

	@DeleteMapping("users/{userId}")
       public String deleteUser(@PathVariable("userId") Integer id) throws NoUserException{
		boolean flag=false;
		List<User> users=retriveAllUser();
		Iterator<User> iterator=users.iterator();
		while(iterator.hasNext()) {
		    User user=iterator.next();
		    if(user.getId().equals(id)) {
		    	users.remove(id);
              flag=true;
		    }
		}
		if(flag)
			return "User with id :"+id+" deleted succesfully";
		throw new NoUserException(String.format("There is no such user with id: %s", id));
		
	} 

}
