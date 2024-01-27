import { Routes } from '@angular/router';
import { GoalsComponent } from './goals/goals.component';
import { HomeComponent } from './home/home.component';
import { KanbanComponent } from './kanban/kanban.component';
import { TasksComponent } from './tasks/tasks.component';
import { TaskDetailsComponent } from './task-details/task-details.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'Home page'
    },
    {
        path: 'goals',
        component: GoalsComponent,
        title: 'Goals'
    },
    {
        path: 'kanban',
        component: KanbanComponent,
        title: 'Kanban Board'
    },
    {
        path: 'tasks',
        component: TasksComponent,
        title: 'Tasks'
    },
    {
        path: 'tasks/:taskId',
        component: TaskDetailsComponent,
        title: 'Task Details'
    },
    {
        path: 'subtasks/:subtaskId',
        component: TaskDetailsComponent,
        title: 'Task Details'
    }
];
