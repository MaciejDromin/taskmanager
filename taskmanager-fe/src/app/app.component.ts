import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterModule, Router, NavigationEnd } from '@angular/router';
import { AppModule } from './app.module';
import { RxStompService } from './rx-stomp.service';
import { RxState } from '@rx-angular/state';
import { filter } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { TaskAddComponent } from './task-add/task-add.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, AppModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  state$ = this.state.select()

  constructor(private router: Router,
    private state: RxState<{href: string,
      name: string, isActive: boolean}[]>,
      public dialog: MatDialog, private rxStompService: RxStompService) {
    this.state.set([...this.links])

    this.router.events.pipe(filter((event) => event instanceof NavigationEnd))
    .subscribe((event) => {
      let toUpdate: {href: string, name: string, isActive: boolean}[] = [...this.links]
      toUpdate.forEach((link) => {
        if (link.href === (event as NavigationEnd).url) {
          link.isActive = true
          return
        }
        link.isActive = false
      })

      this.state.set(toUpdate)
    })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(TaskAddComponent, {
      data: null
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return
      }
      // console.log(typeof result.goal === 'object' ? result.goal.id : null)
      this.rxStompService.publish({ destination: '/app/tasks/add', body: JSON.stringify({
        name: result.name,
        finishDate: result.finishDate.set({ hour: 12 }).toUTC().toISO(),
        goalId: typeof result.goal === 'object' ? result.goal.id : null,
        priority: result.priority,
        description: result.description
      }) })
    });
  }

  title = 'taskmanager-fe';
  links: {href: string, name: string, isActive: boolean}[] = [
    {
      href: "/goals",
      name: "Goals",
      isActive: false
    },
    {
      href: "/kanban",
      name: "Kanban",
      isActive: false
    },
    {
      href: "/tasks",
      name: "Tasks",
      isActive: false
    }
  ]
}
