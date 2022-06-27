package user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import post.Post;

@RestController
public class UserJpacontroller {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@RequestMapping(method = RequestMethod.GET, path = "/jpa/users")
	public List<User> retriveAllUser() throws NoUserException {
		List<User> users = userRepository.findAll();
		if (users.isEmpty())
			throw new NoUserException("there is no user in database");
		return users;
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable Integer id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException("No such id->" + id);
		
		EntityModel<User> model=EntityModel.of(user.get());
		WebMvcLinkBuilder linkToAllUsers=linkTo(methodOn(this.getClass()).retriveAllUser());
//		WebMvcLinkBuilder linkToDeleteUser=linkTo(methodOn(this.getClass()).deleteUser(id));
//		model.add(linkToDeleteUser.withRel("Delete-Users"));
		model.add(linkToAllUsers.withRel("All-Users"));
		return model;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
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

	@DeleteMapping("/jpa/users/{userId}")
       public void deleteUser(@PathVariable("userId") Integer id) throws NoUserException{
/*	boolean flag=false;
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
		*/
	   userRepository.deleteById(id);
	   System.out.println("id is deleted succesfully");
	
	} 
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retriveAllPosts(@PathVariable("id") Integer id){
		
		 Optional<User> user= userRepository.findById(id);
		 if(!user.isPresent())
			 throw new UserNotFoundException("No user with id :"+id);
		return user.get().getPosts();
	}
	
	
	@GetMapping("/jpa/users/{id}/posts/{post_id}")
	public Post retrivePost(@PathVariable Integer id,@PathVariable Integer post_id) throws Exception {
		
		Optional<User> ouser=userRepository.findById(id);
		User user=ouser.get();
		List<Post> posts=user.getPosts();
		
		for(Post p:posts) {
			if(p.getId().equals(post_id))
				return p;
		}
		throw new Exception("No post with such id"+id);
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer id,@RequestBody Post post) {
		Optional<User> optionalUser=userRepository.findById(id);
		if(!optionalUser.isPresent())
			throw new UserNotFoundException("No user with id :"+id);
		User user=optionalUser.get();
		post.setUser(user);
		Post savedPost=postRepository.save(post);
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{/id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
		
	}

}
