import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthRouterModule } from './modules/authentication/authentication-routing.module';
import { JobRouterModule } from './modules/jobportal/job-routing.module';
import { ChangePasswordComponent } from './modules/authentication/components/change-password/change-password.component';
const appRoutes: Routes = [
  {path:'',
  redirectTo:'login',
  pathMatch: 'full'  
  },
  {
    path: 'change-password', // Add the path for the Change Password page
    component: ChangePasswordComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes), AuthRouterModule,JobRouterModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
