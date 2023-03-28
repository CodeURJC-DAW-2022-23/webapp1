import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NavComponent } from './components/nav/nav.component';
import { SearchComponent } from './components/search/search.component';
import { ErrorComponent } from './pages/error/error.component';

@NgModule({
  declarations: [NavComponent, SearchComponent, ErrorComponent],
  imports: [CommonModule, RouterModule],
  exports: [NavComponent, ErrorComponent],
})
export class SharedModule {}
