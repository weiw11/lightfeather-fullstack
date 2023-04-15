import { Component, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../restapi/api.service';
import { Subject, debounceTime } from 'rxjs';

@Component({
  selector: 'app-notification-form',
  templateUrl: './notification-form.component.html',
  styleUrls: ['./notification-form.component.css'],
})
export class NotificationFormComponent {
  constructor(private _api: ApiService) {
    this._api.getSupervisors().subscribe((res: string[]) => {
      console.log('Response: ', res);
      this.supervisors = res;
    });
  }

  supervisors: string[] = [];
  alertMsg: string = '';
  formAlertClosed: boolean = true;

  form: FormGroup = new FormGroup({
    firstname: new FormControl(undefined, [
      Validators.required,
      Validators.pattern('[-_a-zA-Z]*'),
    ]),
    lastname: new FormControl(undefined, [
      Validators.required,
      Validators.pattern('[-_a-zA-Z]*'),
    ]),
    email: new FormControl({ value: undefined, disabled: true }, [
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'),
    ]),
    phone: new FormControl({ value: undefined, disabled: true }, [
      Validators.required,
      Validators.pattern('^(\\+\\d{1,3}[- ]?)?\\d{10}$'),
      Validators.maxLength(10)
    ]),
    supervisor: new FormControl(undefined, Validators.required),
  });

  get firstname() {
    return this.form.get('firstname')!;
  }
  get lastname() {
    return this.form.get('lastname')!;
  }
  get email() {
    return this.form.get('email')!;
  }
  get phone() {
    return this.form.get('phone')!;
  }
  get supervisor() {
    return this.form.get('supervisor')!;
  }

  onSubmit() {
    if (this.form.valid) {
      this._api.postForm(this.form).subscribe({
        next: () => {
          this.alertMsg = 'Submitted successfully!';
          this.formAlertClosed = false;
          this.form.reset();
          setTimeout(() => (this.formAlertClosed = true), 3000);
        },
        error: (e) => {
          console.log('Error: ', e);
          this.alertMsg =
            'Error while submitting, please check the fields and try again.';
          this.formAlertClosed = false;
          setTimeout(() => (this.formAlertClosed = true), 3000);
        },
      });
    }
  }

  toggleCheck(prop: string): void {
    const propControl = this.form.get(prop);
    if (!propControl) return;

    if (propControl.disabled) {
      propControl.enable();
      return;
    }
    propControl.disable();
  }
}
