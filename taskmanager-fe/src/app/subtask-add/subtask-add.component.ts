import { Component, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SubTaskAddModule } from './subtask-add.module';

@Component({
  selector: 'app-task-add',
  standalone: true,
  imports: [SubTaskAddModule],
  templateUrl: './subtask-add.component.html',
  styleUrl: './subtask-add.component.css'
})
export class SubTaskAddComponent {

  priorities = [
    "TRIVIAL",
    "LOW",
    "MEDIUM",
    "HIGH"
  ]

  subTask: any = {
    id: null,
    name: null,
    finishDate: null,
    priority: null,
    description: null,
    status: null,
    task: null
  }

  constructor(public dialogRef: MatDialogRef<SubTaskAddComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    if (data === null) return
    this.subTask = {
      id: data.id,
      name: data.name,
      finishDate: data.finishDate,
      priority: data.priority,
      description: data.description,
      goal: null,
      status: data.status,
      task: data.task
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
