import { NgModule } from '@angular/core'
import { MatButtonModule } from '@angular/material/button'
import { MatMenuModule } from '@angular/material/menu'
import { CommonModule } from '@angular/common'
import { MatChipsModule } from '@angular/material/chips'
import { MatIconModule } from '@angular/material/icon'
import { RouterModule } from '@angular/router';

@NgModule ({
  imports: [
    MatButtonModule,
    MatMenuModule,
    MatChipsModule,
    MatIconModule,
    RouterModule,
    CommonModule
  ],
  exports: [
    MatButtonModule,
    MatMenuModule,
    MatChipsModule,
    MatIconModule,
    RouterModule,
    CommonModule
  ]
})
export class TaskDetailsModule {}