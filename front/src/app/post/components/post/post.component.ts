import {
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { Post } from 'src/app/models/post.model';
import { PostsService } from '../../services/posts.service';
import { distinctUntilChanged } from 'rxjs';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  @Input() post!: Post;

  filterState: boolean = false;
  followingList: string[] = [];
  filter: boolean = false;

  constructor(private postsService: PostsService) {}

  ngOnInit(): void {
    this.postsService
      .getFilter()
      .pipe(distinctUntilChanged())
      .subscribe(data => {
        this.filterState = data;
        this.filter = this.filterState; //&& !this.post.followingAuthor
      });
  }

  //TODO: UPVOTES AND DOWNVOTES
  //upvoted: boolean = this.post.upVotes.find;
  //downvoted: boolean = this.post.downVotes.
}
