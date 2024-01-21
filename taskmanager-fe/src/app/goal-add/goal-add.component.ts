import { Component, Inject } from '@angular/core';
import { GoalAddModule } from './goal-add.module';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-goal-add',
  standalone: true,
  imports: [GoalAddModule],
  templateUrl: './goal-add.component.html',
  styleUrl: './goal-add.component.css'
})
export class GoalAddComponent {

  goal = {
    id: null,
    name: null,
    finishDate: null,
    creationDate: null
  }

  constructor(public dialogRef: MatDialogRef<GoalAddComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    if (data !== null) {
      this.goal = {
        id: data.id,
        name: data.name,
        finishDate: data.finishDate,
        creationDate: data.creationDate
      } 
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
