import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavComponent } from './components/nav/nav.component';
import { SearchComponent } from './components/search/search.component';
import { ErrorComponent } from './pages/error/error.component';

@NgModule({
  declarations: [NavComponent, SearchComponent, ErrorComponent],
  imports: [CommonModule],
  exports: [NavComponent, ErrorComponent],
})
export class SharedModule {}
