// import { Component, OnInit } from '@angular/core';

// @Component({
//   selector: 'app-pagination',
//   templateUrl: './pagination.component.html',
//   styleUrls: ['./pagination.component.css']
// })
// export class PaginationComponent implements OnInit {

//   constructor() { }

//   ngOnInit(): void {
//   }

// }
// export interface MyPagination {
//   itemsCount: number;
//   pageSize: number;
// }
// @Component({
//   selector: 'app-pagination',
//   template: `
//     <div class="pagination" >
//       <span
//         *ngIf="currentPage !== 1"
//         (click)="setPage(currentPage - 1)"
//       >
//         &lt;
//       </span>
//       <ng-container *ngIf="pagesArray.length <= 10" >
//         <span
//           *ngFor="let page of pagesArray; let index = index"
//           [ngClass]="{ 'active': currentPage === index + 1 }"
//           (click)="setPage(index + 1)"
//         >
//           {{ index + 1 }}
//         </span>
//       </ng-container>
//       <ng-container *ngIf="pagesArray.length > 10" >
//         <select
//           [ngModel]="currentPage"
//           (ngModelChange)="setPage($event.target.value)"
//         >
//           <option
//             *ngFor="let p of pagesArray; let index = index"
//             [value]="(index + 1)" >
//             {{ index + 1 }}
//           </option>
//         </select>
//       </ng-container>
//       <span 
//         *ngIf="currentPage !== pagesArray.length"
//         (click)="setPage(currentPage + 1)"
//       >
//         &gt;
//       </span>`,
//   styleUrls: ['./pagination.component.scss']
// })
// export class PaginationComponent {
//   public pagesArray: Array<number> = [];
//   public currentPage: number = 1;
//   @Input() set setPagination(pagination: MyPagination) {
//     if (pagination) {
//       const pagesAmount = Math.ceil(
//         pagination.itemsCount / pagination.pageSize
//       );
//       this.pagesArray = new Array(pagesAmount).fill(1);
//     }
//   }
//   public setPage(pageNumber: number): void {
//     if (pageNumber === this.currentPage)
//       return;
//     this.currentPage = pageNumber;
//     this.goToPage.emit(pageNumber);
//   }
// }