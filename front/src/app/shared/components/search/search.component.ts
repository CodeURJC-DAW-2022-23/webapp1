import { Post } from 'src/app/models/post.model';
import { SearchService } from './search.service';
import { Component } from '@angular/core';
import { PostItem } from 'src/app/models/postItem.model';
import { User } from 'src/app/models/user.model';
import { PostsService } from 'src/app/post/services/posts.service';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent {
  userResults: User[] = []
  postResults: Post[] = []

  constructor(private searchService: SearchService, private postsService: PostsService) { }

  getUser(username: String) {
    this.searchService.getUser(username).subscribe(
      Response => this.userResults = Response as User[],
      error => this.userResults = []
    )
  }

  getPost(name: String) {
    this.searchService.getPost(name).subscribe(
      Response => this.postResults = Response as Post[],
      error => this.postResults = []
    )
  }

  searcher(name: String) {
    this.getUser(name)
    this.getPost(name)

  }




  hideContainer() {
    //[0]: desktop div [1]: mobile div
    //hide suggestion box
    (document.getElementsByClassName("autoCompleteBox")[0] as HTMLElement).style.visibility = "hidden";
    (document.getElementsByClassName("autoCompleteBox")[1] as HTMLElement).style.visibility = "hidden";
    //add border bottom radius
    (document.getElementsByClassName("searchBar")[0] as HTMLElement).style.borderBottomLeftRadius = "10px";
    (document.getElementsByClassName("searchBar")[1] as HTMLElement).style.borderBottomLeftRadius = "10px";
    (document.getElementsByClassName("searchWrapper")[0] as HTMLElement).style.borderBottomRightRadius = "10px";
    (document.getElementsByClassName("searchWrapper")[1] as HTMLElement).style.borderBottomRightRadius = "10px";
  }

  showContainer() {
    //[0]: desktop div [1]: mobile div
    //reveal suggestion box
    (document.getElementsByClassName("autoCompleteBox")[0] as HTMLElement).style.visibility = "visible";
    (document.getElementsByClassName("autoCompleteBox")[1] as HTMLElement).style.visibility = "visible";

    //remove border bottom radius
    (document.getElementsByClassName("searchBar")[0] as HTMLElement).style.borderBottomLeftRadius = "0px";
    (document.getElementsByClassName("searchBar")[1] as HTMLElement).style.borderBottomLeftRadius = "0px";
    (document.getElementsByClassName("searchWrapper")[0] as HTMLElement).style.borderBottomRightRadius = "0px";
    (document.getElementsByClassName("searchWrapper")[1] as HTMLElement).style.borderBottomRightRadius = "0px";
  }

  fetchImage(src: number | undefined) {
    return this.postsService.downloadImage(src as number);
  }
}
