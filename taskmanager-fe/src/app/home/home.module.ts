import { NgModule } from '@angular/core'
import { MatButtonModule } from '@angular/material/button'
import { CommonModule } from '@angular/common'
import { MatExpansionModule } from '@angular/material/expansion'
import { MatIconModule } from '@angular/material/icon'
import { MatChipsModule } from '@angular/material/chips'

@NgModule ({
  imports: [
    MatButtonModule,
    CommonModule,
    MatExpansionModule,
    MatIconModule,
    MatChipsModule
  ],
  exports: [
    MatButtonModule,
    CommonModule,
    MatExpansionModule,
    MatIconModule,
    MatChipsModule
  ]
})
export class HomeModule {}