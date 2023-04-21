import { Component, OnInit } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Comment } from 'src/app/models/comment.model';
import { Post } from 'src/app/models/post.model';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css'],
})
export class TopComponent implements OnInit {
  isLoggedIn: boolean = false;
  comments: Comment[] = [];
  post!: Post;

  constructor(
    private postsService: PostsService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.postsService
      .getPostById(parseInt(this.route.snapshot.paramMap.get('postId') || '-1'))
      .subscribe(response => {
        this.post = response as Post;
      });
    console.log(this.postsService)



  }
}
