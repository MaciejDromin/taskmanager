import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { MatGridListModule } from '@angular/material/grid-list'
import { MatCardModule } from '@angular/material/card'
import { MatChipsModule } from '@angular/material/chips'
import { CommonModule } from '@angular/common';

@NgModule ({
  imports: [
    MatButtonModule,
    MatIconModule,
    MatGridListModule,
    MatCardModule,
    MatChipsModule,
    CommonModule
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatGridListModule,
    MatCardModule,
    MatChipsModule,
    CommonModule
  ]
})
export class TasksModule {}