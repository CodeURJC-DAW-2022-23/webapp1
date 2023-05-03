import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostComponent } from './components/post/post.component';
import { CommentComponent } from './components/comment/comment.component';
import { TopComponent } from './pages/top/top.component';
import { FeedComponent } from './pages/feed/feed.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ReactiveFormsModule } from '@angular/forms';
import { CommentFormComponent } from './components/comment/comment-form/comment-form.component';

@NgModule({
  declarations: [
    PostComponent,
    CommentComponent,
    TopComponent,
    FeedComponent,
    CreatePostComponent,
    CommentFormComponent
  ],
  imports: [CommonModule, MatProgressSpinnerModule, MatFormFieldModule, MatSelectModule, BrowserModule, HttpClientModule, FormsModule, CommonModule, SharedModule, ReactiveFormsModule],
  exports: [FeedComponent, TopComponent],
})
export class PostModule {}
