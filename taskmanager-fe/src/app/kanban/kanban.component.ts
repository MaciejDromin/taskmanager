import { Component } from '@angular/core';
import { KanbanModule } from './kanban.module';
import { RxStompService } from '../rx-stomp.service';
import { Message } from '@stomp/stompjs'
import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';

const priorityIcons: any = {
  HIGH: "keyboard_arrow_up",
  LOW: "keyboard_arrow_down",
  MEDIUM: "menu",
  TRIVIAL: "arrow_downward"
}

const priorityColors: any = {
  HIGH: "cl-red",
  MEDIUM: "cl-orange",
  LOW: "cl-yellow",
  TRIVIAL: "cl-blue"
}

@Component({
  selector: 'app-kanban',
  standalone: true,
  imports: [KanbanModule],
  templateUrl: './kanban.component.html',
  styleUrl: './kanban.component.css'
})
export class KanbanComponent {

  statusData$: any = {
    NOT_STARTED: null,
    ON_HOLD: null,
    IN_PROGRESS: null,
    FINISHED: null
  }

  statuses = [
    "NOT_STARTED",
    "ON_HOLD",
    "IN_PROGRESS",
    "FINISHED"
  ]

  constructor(private rxStompService: RxStompService) {}

  ngOnInit(): void {
    this.rxStompService.watch('/taskmngr/daily').subscribe((daily: Message) => {
      let notStarted: any[] = []
      let onHold: any[] = []
      let inProgress: any[] = []
      let finished: any[] = []
      JSON.parse(daily.body).tasks.forEach((task:any) => {
        task.subTasks.forEach((subTask:any) => {
          switch (subTask.status) {
            case (this.statuses[0]):
              notStarted.push({
                type: "SUBTASK", 
                item: subTask
              })
              break
            case (this.statuses[1]):
              onHold.push({
                type: "SUBTASK", 
                item: subTask
              })
              break
            case (this.statuses[2]):
              inProgress.push({
                type: "SUBTASK", 
                item: subTask
              })
              break
            case (this.statuses[3]):
              finished.push({
                type: "SUBTASK", 
                item: subTask
              })
              break
          }
        })
        switch(task.status) {
          case (this.statuses[0]):
            notStarted.push({
              type: "TASK",
              item: task
            })
          break
          case (this.statuses[1]):
            onHold.push({
              type: "TASK", 
              item: task
            })
            break
          case (this.statuses[2]):
            inProgress.push({
              type: "TASK", 
              item: task
            })
            break
          case (this.statuses[3]):
            finished.push({
              type: "TASK", 
              item: task
            })
            break
        }
      })
      this.statusData$ = {
        NOT_STARTED: notStarted,
        ON_HOLD: onHold,
        IN_PROGRESS: inProgress,
        FINISHED: finished
      }
    })
    this.rxStompService.publish({ destination: '/app/daily', body: "init" })
  }

  getColumnData(status:string): any[] {
    return this.statusData$[status]
  }

  drop(event: CdkDragDrop<any>, newStatus: any) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
      this.rxStompService.publish({ destination: '/app/tasks/update', body: JSON.stringify({
        id: event.container.data[event.currentIndex].item.id,
        type: event.container.data[event.currentIndex].type,
        newStatus: newStatus
      })})
    }
  }

  determinePriorityIcon(priority: string): string {
    return priorityIcons[priority]
  }

  determinePriorityColor(priority: string): string {
    return priorityColors[priority]
  } 

}
