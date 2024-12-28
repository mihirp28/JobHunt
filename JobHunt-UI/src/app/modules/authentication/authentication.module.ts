import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RouterModule } from '@angular/router';
import { MatFormFieldModule, MatSelectModule, MatButtonModule, MatSnackBarModule, MatInputModule, MatCardModule} from '@angular/material';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from './services/authentication.service';
import { ChangePasswordComponent } from './components/change-password/change-password.component';

@NgModule({
  declarations: [LoginComponent, RegisterComponent, ChangePasswordComponent],
  imports: [
    CommonModule,
    MatFormFieldModule,
     MatSelectModule,
      FormsModule,
       RouterModule, 
       MatFormFieldModule,
       MatButtonModule,
       MatSnackBarModule,
       MatFormFieldModule,
       MatMenuModule,
      MatIconModule,
       MatInputModule,
       MatCardModule
  ],
  providers:[]
  
})
export class AuthenticationModule { }
