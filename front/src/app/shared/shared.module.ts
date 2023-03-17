import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavComponent } from './components/nav/nav.component';
import { SearchComponent } from './components/search/search.component';

@NgModule({
  declarations: [NavComponent, SearchComponent],
  imports: [CommonModule],
  exports: [NavComponent],
})
export class SharedModule {}
