// change-password.component.ts
import { Component } from '@angular/core';
import { ChangePasswordService } from '../../services/change-password.service'; // Import the service

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  oldPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  showOldPassword: boolean = false;
  showNewPassword: boolean = false;

  constructor(private changePasswordService: ChangePasswordService) {}

  changePassword() {
    if (this.newPassword !== this.confirmPassword) {
      alert('New password and confirm password must match.');
      return;
    }

    // Call the service method to change the password
    this.changePasswordService.changePassword(this.oldPassword, this.newPassword).subscribe(
      (response: any) => {
        alert(response.message || 'Password changed successfully!');
      },
      (error) => {
        if (error.error && error.error.message) {
          alert(error.error.message); // Backend-provided error message
        } else {
          alert('Failed to change password. Please try again.');
        }
        console.error('Error:', error);
      }
    );
  }
}
