import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-search-input',
  template: `
    <input
      type="text"
      [placeholder]="placeholder"
      (keyup)="updateSearch($event.target.value)"
    />`
})
export class SearchInputComponent implements OnDestroy {
  // Optionally, I have added a placeholder input for customization 
  @Input() readonly placeholder: string = '';
  @Output() setValue: EventEmitter<string> = new EventEmitter();
  private _searchSubject: Subject<string> = new Subject();
  constructor() {
    this._setSearchSubscription();
  }
  public updateSearch(searchTextValue: string) {
    this._searchSubject.next( searchTextValue );
  }
  private _setSearchSubscription() {
    this._searchSubject.pipe(
      debounceTime(500)
    ).subscribe((searchValue: string) => {
      this.setValue.emit( searchValue );
    });
  }
  ngOnDestroy() {
    this._searchSubject.unsubscribe();
  }
}
function debounceTime(arg0: number): import("rxjs").OperatorFunction<string, unknown> {
  throw new Error('Function not implemented.');
}

