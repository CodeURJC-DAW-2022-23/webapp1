import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

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
  imports: [CommonModule, RouterModule, MatProgressSpinnerModule],
  exports: [FeedComponent, TopComponent, PostComponent],

import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  declarations: [PostComponent, CommentComponent, TopComponent, FeedComponent, CreatePostComponent],
  imports: [CommonModule, MatProgressSpinnerModule, MatFormFieldModule, MatSelectModule, BrowserModule, HttpClientModule,FormsModule, CommonModule, SharedModule, ReactiveFormsModule],
  exports: [FeedComponent, TopComponent],
})
export class PostModule { }
