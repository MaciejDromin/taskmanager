import { NgModule } from '@angular/core'
import { MatButtonModule } from '@angular/material/button'
import { MatGridListModule } from '@angular/material/grid-list'
import { MatCardModule } from '@angular/material/card'
import { CommonModule } from '@angular/common'
import { MatIconModule } from '@angular/material/icon'
import {
    CdkDrag,
    CdkDropList,
    CdkDropListGroup
} from '@angular/cdk/drag-drop';

@NgModule ({
  imports: [
    MatButtonModule,
    MatGridListModule,
    MatCardModule,
    CdkDrag,
    CdkDropList,
    CdkDropListGroup,
    MatIconModule,
    CommonModule
  ],
  exports: [
    MatButtonModule,
    MatGridListModule,
    MatCardModule,
    CdkDrag,
    CdkDropList,
    CdkDropListGroup,
    MatIconModule,
    CommonModule
  ]
})
export class KanbanModule {}