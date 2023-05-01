import { SearchService } from './search.service';
import { Component } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent {
  userResults: User[] = []

  constructor(private searchService: SearchService) { }

  getUser(username: String) {
    this.searchService.getUser(username).subscribe(
      Response => this.userResults = Response as User[]
    )
  }

}
