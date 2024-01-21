import { Component } from '@angular/core';
import { TasksModule } from './tasks.module';
import { RxStompService } from '../rx-stomp.service';
import { RxState } from '@rx-angular/state';
import { Message } from '@stomp/stompjs'
import { MatDialog } from '@angular/material/dialog';
import { TaskAddComponent } from '../task-add/task-add.component';

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

const statusColors: any = {
  NOT_STARTED: "cl-grey",
  ON_HOLD: "cl-orange",
  IN_PROGRESS: "cl-yellow",
  FINISHED: "cl-green"
}

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [TasksModule],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css'
})
export class TasksComponent {

  tasks$ = this.state.select('tasks')

  constructor(private rxStompService: RxStompService, private state: RxState<{tasks: any}>, public dialog: MatDialog) {
    this.state.set({
      tasks: null
    })
  }

  ngOnInit() {
    this.rxStompService.watch('/taskmngr/tasks').subscribe((tasks: Message) => {
      this.state.set({
        tasks: JSON.parse(tasks.body)
      })
    })
    this.rxStompService.watch('/taskmngr/tasks/new').subscribe((task: Message) => {
      this.state.set({
        tasks: [...this.state.get('tasks'), JSON.parse(task.body)]
      })
    })
    this.rxStompService.publish({ destination: '/app/tasks', body: "init" })
  }

  deleteTask(taskId: string): void {
    this.rxStompService.publish({ destination: '/app/tasks/delete', body: taskId})

    const deleteGoalFromState = (oldState: {tasks: any}) => {
      return {
        tasks: oldState.tasks.filter((task: any) => {
          return task.id !== taskId
        })
      }
    }

    this.state.set(deleteGoalFromState)
  }

  editTask(task: any): void {
    const dialogRef = this.dialog.open(TaskAddComponent, {
      data: task
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return
      }
      this.rxStompService.publish({ destination: '/app/tasks/update', body: JSON.stringify({
        id: result.id,
        name: result.name,
        finishDate: typeof result.finishDate === 'object' ? result.finishDate.set({ hour: 12 }).toUTC().toISO() : result.finishDate,
        priority: result.priority,
        description: result.description,
        goalId: typeof result.goal === 'object' ? result.goal.id : null
      }) })

      const updatetaskInState = (oldState: {tasks: any}) => {
        return {
          tasks: oldState.tasks.map((task: any) => {
            if (task.id !== result.id) return task
            return {
              id: result.id,
              name: result.name,
              finishDate: typeof result.finishDate === 'object' ? result.finishDate.set({ hour: 12 }).toUTC().toISO() : result.finishDate,
              priority: result.priority,
              description: result.description,
              goalId: typeof result.goal === 'object' ? result.goal.id : null,
              status: result.status
            }
          })
        }
      }
  
      this.state.set(updatetaskInState)

    });
  }

  determinePriorityIcon(priority: string): string {
    return priorityIcons[priority]
  }

  determinePriorityColor(priority: string): string {
    return priorityColors[priority]
  } 

  determineStatusColor(status: string): string {
    return statusColors[status]
  } 

}
