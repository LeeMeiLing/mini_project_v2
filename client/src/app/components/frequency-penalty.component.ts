import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FrequencyPenaltyDialogData } from '../models';


@Component({
  selector: 'app-frequency-penalty',
  templateUrl: './frequency-penalty.component.html',
  styleUrls: ['./frequency-penalty.component.css']
})
export class FrequencyPenaltyComponent {

  constructor(
    public dialogRef: MatDialogRef<FrequencyPenaltyComponent>,
    @Inject(MAT_DIALOG_DATA) public data: FrequencyPenaltyDialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
