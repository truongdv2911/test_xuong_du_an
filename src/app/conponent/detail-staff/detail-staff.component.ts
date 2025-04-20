import { Component, OnInit } from '@angular/core';
import { StaffService } from '../../service/staff.service';
import { SMFService } from '../../service/smf.service';
import { ActivatedRoute } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { StaffApi } from '../../type/staffApiType';
import { error } from 'console';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { smfApi } from '../../type/smfApiType';
import { majorApi } from '../../type/majorApi';
import { departmentApi } from '../../type/departmentApi';
import { facilityApi } from '../../type/FacilityApi';
import { smfResponse } from '../../type/smfResponse';

@Component({
  selector: 'app-detail-staff',
  imports: [NgIf, NgFor, FormsModule, ReactiveFormsModule],
  templateUrl: './detail-staff.component.html',
  styleUrl: './detail-staff.component.css'
})
export class DetailStaffComponent implements OnInit{
  id: string = '';
  staff!: StaffApi;
  smf: smfApi[] = [];
  majors: majorApi[]=[];
  departments: departmentApi[]=[];
  facilitys: facilityApi[] = [];
  assignForm!: FormGroup;
  constructor(private staffService: StaffService,
            private smfService: SMFService,
            private router: ActivatedRoute,
            private fb: FormBuilder
  ){
    this.assignForm = fb.group({
      idFacility: ['', Validators.required],
      idMajor: ['', Validators.required],
      idDepart: ['', Validators.required]
    });
  }

  getDetailStaff(id: string){
    this.staffService.getOneStaff(id).subscribe({
      next: (res: any) => {
          this.staff = res
      },
      error:(error) => {
        alert(error)
      }
    })
  }
  getDetailAssignment(id: string){
    this.smfService.getSmf(id).subscribe({
      next: (res: any) => {
          this.smf = res
      },
      error:(error) => {
        alert(error)
      }
    })
  }

  getListMajor(){
    this.smfService.getMajor().subscribe({
      next: (res: any) => {
          this.majors = res
      },
      error: (error) => {
        alert(error)
      }
    })
  }

  getListDepartment(){
    this.smfService.getDepartment().subscribe({
      next: (res: any) => {
          this.departments = res
      },
      error: (error) => {
        alert(error)
      }
    })
  }

  getListFacility(){
    const idStaff = this.router.snapshot.paramMap.get('id') || '';
    this.smfService.getFacility(idStaff).subscribe({
      next: (res: any) => {
          this.facilitys = res
      },
      error: (error) => {
        alert(error)
      }
    })
  }

  ngOnInit(): void {
    const idStaff = this.router.snapshot.paramMap.get('id')
    if(idStaff){
      this.getDetailStaff(idStaff);
      this.getDetailAssignment(idStaff);
      this.getListDepartment();
      this.getListFacility();
      this.getListMajor();
    }
  }

  // xu li add smf
  isModalVisible: boolean = false;
  newSmf!: smfResponse;
  openAddSmf(){
    this.isModalVisible = true;
  }

  closeModal(){
    this.isModalVisible = false;
    this.assignForm.patchValue({
      idFacility: '',
      idMajor: '',
      idDepart: ''
    })
  }

  addSmf(){
    if(this.assignForm.valid){
      const idStaff = this.router.snapshot.paramMap.get('id') || '';
        this.newSmf = {
          ...this.assignForm.value
        }
        this.newSmf.staffId = idStaff;
        this.newSmf.status = 1;
        console.log(this.newSmf);
        this.smfService.createSmf(this.newSmf).subscribe({
          next: (res: any) =>{
            alert('them thanh cong');
            this.closeModal();
            this.getDetailAssignment(idStaff);
            this.getListFacility();
          },
          error: (error) =>{
            alert(error)
          }
        })
    }else{
      alert('Sai thong tin form')
    }
  }

   // xu li thay doi trang thai
   isConfirmDialogVisible: boolean = false;
  idSmf: string = ''; 
   openConfirm(id: string): void {
    this.idSmf = id
    console.log(id)
    console.log(this.idSmf)
     this.isConfirmDialogVisible = true;
   }

   cancelChange(): void {
     this.isConfirmDialogVisible = false;
   }
   
   confirmChange(): void {
     const idStaff = this.router.snapshot.paramMap.get('id') || '';
     this.smfService.deleteSmf(this.idSmf).subscribe({
       next: (res) => {
         alert('Xóa thành công');
         this.getDetailAssignment(idStaff);
         this.getListFacility();
       },
       error: (err) => {
         console.error('Lỗi khi xóa nhân viên:', err);
         alert('Đã xảy ra lỗi khi xóa nhân viên!');
       }
     });
     this.isConfirmDialogVisible = false;
   }
}
