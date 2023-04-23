import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { PostComponent } from './components/post/post.component';
import { CommentComponent } from './components/comment/comment.component';
import { TopComponent } from './pages/top/top.component';
import { FeedComponent } from './pages/feed/feed.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';

@NgModule({
  declarations: [
    PostComponent,
    CommentComponent,
    TopComponent,
    FeedComponent,
    CreatePostComponent,
  ],
  imports: [CommonModule, MatProgressSpinnerModule],
  exports: [FeedComponent, TopComponent, PostComponent],
})
export class PostModule {}
