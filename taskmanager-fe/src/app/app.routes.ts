import { Routes } from '@angular/router';
import { GoalsComponent } from './goals/goals.component';
import { HomeComponent } from './home/home.component';
import { KanbanComponent } from './kanban/kanban.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'Home page'
    },
    {
        path: 'goals',
        component: GoalsComponent,
        title: 'Home details'
    },
    {
        path: 'kanban',
        component: KanbanComponent,
        title: 'Kanban Board'
    }
];
