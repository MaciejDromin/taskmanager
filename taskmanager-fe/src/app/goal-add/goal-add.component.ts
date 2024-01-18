import { Component } from '@angular/core';
import { GoalAddModule } from './goal-add.module';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-goal-add',
  standalone: true,
  imports: [GoalAddModule],
  templateUrl: './goal-add.component.html',
  styleUrl: './goal-add.component.css'
})
export class GoalAddComponent {

  goal = {
    name: null,
    finishDate: null
  }

  constructor(public dialogRef: MatDialogRef<GoalAddComponent>) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
