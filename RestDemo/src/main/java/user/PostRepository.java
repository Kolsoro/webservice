package user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import post.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
